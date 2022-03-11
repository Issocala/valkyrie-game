package application.module.fight.skill.data.message;

import application.util.DataMessageAndReply;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-3-4
 * @Source 1.0
 */
public record SkillIsLearn(DataMessageAndReply dataMessageAndReply) implements DataBase {
}
