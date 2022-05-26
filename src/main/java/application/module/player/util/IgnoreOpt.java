package application.module.player.util;

import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2022-5-23
 * @Source 1.0
 */
public record IgnoreOpt() implements OperateType {
    public final static IgnoreOpt INSTANCE = new IgnoreOpt();
}
