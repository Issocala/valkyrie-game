package application.module.scene.fight.skill.base.context;

import application.module.organism.FightOrganism;
import application.module.scene.Scene;
import mobius.modular.client.Client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-15
 * @Source 1.0
 */
public class PassiveSkillDataTemp {

    private FightOrganism attack;
    private List<TargetParameter> targetParameters;
    Client.ReceivedFromClient r;
    private final Map<Short, Long> attributeMap = new HashMap<>();
    private Scene scene;

    public PassiveSkillDataTemp(UseSkillDataTemp useSkillDataTemp) {
        this.attack = useSkillDataTemp.getAttack();
        this.targetParameters = List.copyOf(useSkillDataTemp.getTargetParameters());
        this.r = useSkillDataTemp.getR();
        this.scene = useSkillDataTemp.getScene();
    }

    public PassiveSkillDataTemp(FightOrganism attack, UseSkillDataTemp useSkillDataTemp) {
        this.attack = attack;
        this.targetParameters = List.of(new TargetParameter(useSkillDataTemp.getAttack()));
        this.r = useSkillDataTemp.getR();
        this.scene = useSkillDataTemp.getScene();
    }

    public void addBuff(int templateId, FightOrganism from, FightOrganism to) {
        addBuff(templateId, from, to, 0);
    }

    public void addBuff(int templateId, FightOrganism from, FightOrganism to, long duration) {
        getAttack().getFightBuffMgr().addBuff(templateId, from, to, duration);
    }

    public void removeBuff(int buffTemplateId) {
        getAttack().getFightBuffMgr().removeBuff(buffTemplateId);
    }

    public void addHp(FightOrganism attack, long hp) {
        getAttack().addHp(attack, hp);
    }

    public FightOrganism getAttack() {
        return attack;
    }

    public void setAttack(FightOrganism attack) {
        this.attack = attack;
    }

    public List<TargetParameter> getTargetParameters() {
        return targetParameters;
    }

    public void setTargetParameters(List<TargetParameter> targetParameters) {
        this.targetParameters = targetParameters;
    }

    public Client.ReceivedFromClient getR() {
        return r;
    }

    public void setR(Client.ReceivedFromClient r) {
        this.r = r;
    }

    public Map<Short, Long> getAttributeMap() {
        return attributeMap;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
