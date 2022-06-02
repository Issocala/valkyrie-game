package application.module.scene.fight.buff;

import application.module.organism.FightOrganism;
import application.module.scene.fight.buff.type.BuffCoverRuleType;
import template.FightBuffTemplate;

import java.util.*;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public class FightBuffMgr {

    private FightOrganism owner;

    private final Map<Integer, FightOrganismBuff> fightOrganismBuffs = new HashMap<>();

    /**
     * 免疫某种效果类型
     */
    private final Set<Integer> immuneTypeSet = new HashSet<>();

    /**
     * 免疫特定buffTemplateId
     */
    private final Set<Integer> immuneIdSet = new HashSet<>();

    public void addBuff(int templateId, FightOrganism from, FightOrganism to) {
        addBuff(templateId, from, to, 0);
    }

    public void addBuff(int templateId, FightOrganism from, FightOrganism to, long duration) {
        FightOrganismBuff buff = createBuff(templateId, from, to, duration);
        addBuff(buff);
    }

    public void addBuff(FightOrganismBuff buff) {
        if (!valid(buff)) {
            return;
        }
        FightOrganismBuff oldBuff = this.fightOrganismBuffs.get(buff.getBuffTemplateId());
        if (Objects.isNull(oldBuff)) {
            buff.addSelf();
            this.fightOrganismBuffs.put(buff.getBuffTemplateId(), buff);
        } else {
            oldBuff.addSelf();
        }

        owner.getScene().getPlayerSceneMgr().sendToAllClient(owner.getScene(), FightBuffProtocols.ADD,
                FightBuffProtocolBuilder.getSc10071(owner.getId(), buff));
    }

    public boolean removeBuff(FightOrganismBuff buff) {
        if (Objects.isNull(buff)) {
            return false;
        }
        if (buff.getCurrCoverCount() > 0) {
            buff.setCurrCoverCount(buff.getCurrCoverCount() - 1);
            buff.removeSelf();
        }
        if (buff.getCurrCoverCount() <= 0) {
            this.fightOrganismBuffs.remove(buff.getBuffTemplateId());
        }
        owner.getScene().getPlayerSceneMgr().sendToAllClient(owner.getScene(), FightBuffProtocols.REMOVE,
                FightBuffProtocolBuilder.getSc10072(owner.getId(), buff));
        return true;
    }

    public boolean removeBuff(int templateId) {
        FightOrganismBuff buff = this.fightOrganismBuffs.get(templateId);
        if (Objects.isNull(buff)) {
            return false;
        }
        return removeBuff(buff);
    }

    private void removeBuff(List<FightOrganismBuff> removeList) {
        for (FightOrganismBuff buff : removeList) {
            int currCoverCount = buff.getCurrCoverCount();
            if (currCoverCount > 1) {
                for (int i = 0; i < currCoverCount; i++) {
                    removeBuff(buff);
                }
            } else {
                removeBuff(buff);
            }
        }
    }

    public FightOrganismBuff createBuff(int templateId, FightOrganism from, FightOrganism to) {
        return new FightOrganismBuff(templateId, from, to);
    }

    public static FightOrganismBuff createBuff(int templateId, FightOrganism from, FightOrganism to, long duration) {
        return new FightOrganismBuff(templateId, from, to, duration);
    }

    public boolean valid(FightOrganismBuff buff) {

        if (isImmuneId(buff)) {
            return false;
        }

        if (isImmuneType(buff)) {
            return false;
        }

        if (isResist(buff)) {
            return false;
        }

        FightOrganismBuff oldBuff = getBuff(buff.getBuffTemplateId());
        if (Objects.nonNull(oldBuff)) {
            return doEqualTemplateId(buff, oldBuff);
        }
        return true;
    }

    /**
     * 是否免疫指定id的buff
     */
    private boolean isImmuneId(FightOrganismBuff buff) {
        return false;
    }

    /**
     * 是否免疫指定Type的buff
     */
    private boolean isImmuneType(FightOrganismBuff buff) {
        return false;
    }

    private boolean isResist(FightOrganismBuff buff) {
        byte buffType = buff.getFightBuffTemplate().buffType();
        return false;
    }

    /**
     * 处理相同buffTemplateId
     */
    public boolean doEqualTemplateId(FightOrganismBuff buff, FightOrganismBuff oldBuff) {
        return switch (buff.getFightBuffTemplate().buffCoverType()) {
            case BuffCoverRuleType.REPLACE_TIME_BUFF -> validReplaceTime(buff, oldBuff);
            case BuffCoverRuleType.REPEAT_TIME_BUFF -> validRepeatTime(buff, oldBuff);
            case BuffCoverRuleType.REPEAT_EFFECT_AND_REPLACE_TIME -> validRepeatEffectAndReplaceTime(buff, oldBuff);
            case BuffCoverRuleType.REPEAT_EFFECT_AND_REPEAT_TIME -> validRepeatEffectAndRepeatTime(buff, oldBuff);
            case BuffCoverRuleType.REPEAT_COVER_AND_REPLACE_TIME -> validRepeatCoverAndReplaceTime(buff, oldBuff);
            case BuffCoverRuleType.REPEAT_COVER_AND_REPEAT_TIME -> validRepeatCoverAndRepeatTime(buff, oldBuff);
            case BuffCoverRuleType.REPEAT_COVER -> validRepeatCover(buff, oldBuff);
            default -> false;
        };
    }

    private boolean validReplaceTime(FightOrganismBuff buff, FightOrganismBuff oldBuff) {
        oldBuff.setExpiredTime(buff.getDuration() + System.currentTimeMillis());
        return true;
    }

    private boolean validRepeatTime(FightOrganismBuff buff, FightOrganismBuff oldBuff) {
        oldBuff.setExpiredTime(buff.getDuration() + oldBuff.getExpiredTime());
        return true;
    }

    private boolean validRepeatEffectAndReplaceTime(FightOrganismBuff buff, FightOrganismBuff oldBuff) {
        oldBuff.setAttributeMap(buff.getAttributeMap());
        oldBuff.setExpiredTime(buff.getDuration() + System.currentTimeMillis());
        return true;
    }

    private boolean validRepeatEffectAndRepeatTime(FightOrganismBuff buff, FightOrganismBuff oldBuff) {
        oldBuff.setAttributeMap(buff.getAttributeMap());
        oldBuff.setExpiredTime(buff.getDuration() + oldBuff.getExpiredTime());
        return true;
    }

    private boolean validRepeatCoverAndReplaceTime(FightOrganismBuff buff, FightOrganismBuff oldBuff) {
        int count = buff.getFightBuffTemplate().buffCoverCount();
        if (count <= oldBuff.getCurrCoverCount()) {
            return false;
        }
        oldBuff.setCurrCoverCount(oldBuff.getCurrCoverCount() + 1);
        oldBuff.setExpiredTime(buff.getDuration() + System.currentTimeMillis());
        return true;
    }

    private boolean validRepeatCoverAndRepeatTime(FightOrganismBuff buff, FightOrganismBuff oldBuff) {
        int count = buff.getFightBuffTemplate().buffCoverCount();
        if (count <= oldBuff.getCurrCoverCount()) {
            return false;
        }
        oldBuff.setCurrCoverCount(oldBuff.getCurrCoverCount() + 1);
        oldBuff.setExpiredTime(buff.getDuration() + oldBuff.getExpiredTime());
        return true;
    }

    private boolean validRepeatCover(FightOrganismBuff buff, FightOrganismBuff oldBuff) {
        int count = buff.getFightBuffTemplate().buffCoverCount();
        if (count <= oldBuff.getCurrCoverCount()) {
            return false;
        }
        oldBuff.setCurrCoverCount(oldBuff.getCurrCoverCount() + 1);
        return true;
    }

    public void switchScene() {
        List<FightOrganismBuff> temp = new ArrayList<>();
        fightOrganismBuffs.values().forEach(fightOrganismBuff -> {
            FightBuffTemplate template = fightOrganismBuff.getFightBuffTemplate();
            if (template.switchSceneRemove() == 1) {
                temp.add(fightOrganismBuff);
            }
        });
        temp.forEach(buff -> {
            int currCount = buff.getCurrCoverCount();
            for (int i = 0; i < currCount; i++) {
                if (buff.getCurrCoverCount() > 0) {
                    buff.setCurrCoverCount(buff.getCurrCoverCount() - 1);
                    buff.removeSelf();
                }
            }
            this.fightOrganismBuffs.remove(buff.getBuffTemplateId());
            owner.getScene().getPlayerSceneMgr().sendToAllClient(owner.getScene(), FightBuffProtocols.REMOVE,
                    FightBuffProtocolBuilder.getSc10072(buff.getTo().getId(), buff));
        });
    }

    public void tick() {
        if (fightOrganismBuffs.isEmpty()) {
            return;
        }
        List<FightOrganismBuff> list = new ArrayList<>();
        fightOrganismBuffs.values().forEach(buff -> {
            buff.tick();
            if (buff.isExpiredTime()) {
                list.add(buff);
            }
        });
        if (list.isEmpty()) {
            return;
        }
        removeBuff(list);
    }


    // get and set

    public FightOrganismBuff getBuff(int templateTd) {
        return this.fightOrganismBuffs.get(templateTd);
    }

    public FightOrganism getOwner() {
        return owner;
    }

    public void setOwner(FightOrganism owner) {
        this.owner = owner;
    }

    public Map<Integer, FightOrganismBuff> getFightOrganismBuffs() {
        return fightOrganismBuffs;
    }
}
