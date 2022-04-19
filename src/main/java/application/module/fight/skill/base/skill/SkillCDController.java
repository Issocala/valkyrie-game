package application.module.fight.skill.base.skill;

import application.util.CDMgr;
import template.FightSkillTemplate;

/**
 * @author Luo Yong
 * @date 2022-2-24
 * @Source 1.0
 */
public class SkillCDController {
    public static final int BASIC_CD = 500;

    CDMgr cdMgr = new CDMgr(BASIC_CD);

    public boolean inCDTime(FightSkillWrap fightSkillWrap) {
        return inCDTime(fightSkillWrap.getFightSkillTemplate().id());
    }

    public boolean inCDTime(int skillTempId) {
        if (!cdMgr.isCDStarted(skillTempId)) {
            return false;
        }
        return cdMgr.isOutOfCD(skillTempId);
    }

    public void startCD(FightSkillTemplate fightSkillTemplate) {
        int id = fightSkillTemplate.id();
        long cdTime = fightSkillTemplate.cdTime();
        if (!cdMgr.isCDStarted(id)) {
            cdMgr.startCD(id, cdTime);
        }
        if (cdMgr.getFightCDMillis(id) != cdTime) {
            resetCDTime(id, cdTime);
        }
        cdMgr.update(id);
    }

    public void resetCDTime(int id, long CDMillis) {
        cdMgr.resetCD(id, CDMillis);
    }

}
