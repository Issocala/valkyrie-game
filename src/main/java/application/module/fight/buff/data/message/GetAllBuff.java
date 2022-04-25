package application.module.fight.buff.data.message;

import application.module.fight.skill.base.context.UseSkillDataTemp;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-4-24
 * @Source 1.0
 */
public record GetAllBuff(UseSkillDataTemp useSkillDataTemp) implements DataBase {
}
