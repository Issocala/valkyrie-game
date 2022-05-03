package application.module.state;

import protocol.State;

/**
 * @author Luo Yong
 * @date 2022-3-28
 * @Source 1.0
 */
public class StateProtocolBuilder {

    public static State.SC10061 getSc10061(long organismId, short stateType) {
        return State.SC10061.newBuilder()
                .setOrganismId(organismId)
                .setStateType(stateType)
                .build();
    }

    public static State.SC10062 getSc10062(long organismId, short stateType) {
        return State.SC10062.newBuilder()
                .setOrganismId(organismId)
                .setStateType(stateType)
                .build();
    }
}
