package application.module.gm.cmd.sub;

import application.module.gm.cmd.GmCmd;
import application.module.gm.domain.GmCmdArgs;
import application.module.gm.domain.GmCmdResult;
import mobius.modular.client.Client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HRT
 * @date 2022-4-13
 */
public abstract class GmCmdSub extends GmCmd {

    private final Map<String, Method> subMethods = new HashMap<>();

    public GmCmdSub() {
        for (Method method : getClass().getDeclaredMethods()) {
            GmCmdSubMethod cmdMethod = method.getAnnotation(GmCmdSubMethod.class);
            if (cmdMethod == null) {
                continue;
            }
            if (method.getReturnType() != GmCmdResult.class) {
                throw new RuntimeException(String.format("%s.%s 返回值类型必须为GmCmdResult",
                        this.getClass().getSimpleName(), method.getName()));
            }
            method.setAccessible(true);
            String subCmd = cmdMethod.value().isEmpty() ? method.getName() : cmdMethod.value();
            Method old = subMethods.put(subCmd, method);
            if (old != null) {
                throw new RuntimeException(String.format("%s.%s subCmd出现重复",
                        this.getClass().getSimpleName(), method.getName()));
            }
        }
    }

    @Override
    protected GmCmdResult onHandle(Client.ReceivedFromClient r, GmCmdArgs args) {
        if (!args.hasNext()) {
            return GmCmdResult.ofErr(getExample());
        }
        String subCmd = args.next();
        Method method = subMethods.get(subCmd);
        if (method == null) {
            return GmCmdResult.ofErr(subCmd + " not found");
        }
        Parameter[] parameters = method.getParameters();
        int length = parameters.length;
        Object[] params = new Object[length];
        for (int i = 0; i < length; i++) {
            Parameter parameter = parameters[i];
            Class<?> type = parameter.getType();
            if (type == Client.ReceivedFromClient.class) {
                params[i] = r;
            } else if (type == GmCmdArgs.class) {
                params[i] = args;
            } else {
                // 0是subCmd
                params[i] = args.getValue(i + 1, type);
            }
        }
        try {
            return (GmCmdResult) method.invoke(this, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
