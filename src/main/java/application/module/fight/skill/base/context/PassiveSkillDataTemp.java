package application.module.fight.skill.base.context;

import akka.actor.ActorRef;
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

    private long attackId;
    private List<TargetParameter> targetParameters;
    Client.ReceivedFromClient r;
    private final Map<Short, Long> attributeMap = new HashMap<>();
    private ActorRef scene;
    private ActorRef attributeData;
    private ActorRef buffData;
    private ActorRef stateData;

    public PassiveSkillDataTemp(UseSkillDataTemp useSkillDataTemp) {
        this.attackId = useSkillDataTemp.getAttackId();
        this.targetParameters = List.copyOf(useSkillDataTemp.getTargetParameters());
        this.r = useSkillDataTemp.getR();
        this.scene = useSkillDataTemp.getScene();
        this.attributeData = useSkillDataTemp.getAttributeData();
        this.stateData = useSkillDataTemp.getStateData();
        this.buffData = useSkillDataTemp.getBuffData();
    }

    public PassiveSkillDataTemp(long attackId, UseSkillDataTemp useSkillDataTemp) {
        this.attackId = attackId;
        this.targetParameters = List.of(new TargetParameter(attackId));
        this.r = useSkillDataTemp.getR();
        this.scene = useSkillDataTemp.getScene();
        this.attributeData = useSkillDataTemp.getAttributeData();
        this.stateData = useSkillDataTemp.getStateData();
        this.buffData = useSkillDataTemp.getBuffData();
    }


    public Map<Short, Long> getAttributeMap() {
        return attributeMap;
    }

    public ActorRef getScene() {
        return scene;
    }

    public void setScene(ActorRef scene) {
        this.scene = scene;
    }

    public ActorRef getAttributeData() {
        return attributeData;
    }

    public void setAttributeData(ActorRef attributeData) {
        this.attributeData = attributeData;
    }

    public ActorRef getBuffData() {
        return buffData;
    }

    public void setBuffData(ActorRef buffData) {
        this.buffData = buffData;
    }

    public long getAttackId() {
        return attackId;
    }

    public void setAttackId(long attackId) {
        this.attackId = attackId;
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

    public ActorRef getStateData() {
        return stateData;
    }

    public void setStateData(ActorRef stateData) {
        this.stateData = stateData;
    }
}
