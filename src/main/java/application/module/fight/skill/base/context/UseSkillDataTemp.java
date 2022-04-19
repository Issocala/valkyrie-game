/**
 * Copyright 2013-2015 Sophia
 * <p>
 * Licensed under the Apache License; Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing; software
 * distributed under the License is distributed on an "AS IS" BASIS;
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND; either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package application.module.fight.skill.base.context;

import akka.actor.ActorRef;
import mobius.modular.client.Client;
import protocol.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 技能释放上下文临时数据
 *
 * @author Luo Yong
 * @date 2022-2-25
 * @Source 1.0
 */
public class UseSkillDataTemp {

    private int skillId;
    private long attackId;
    private List<Long> targetId;
    private float skillPosX;
    private float skillPosY;
    private float posX;
    private float posY;
    private long sceneId;
    private byte profession;
    private Client.ReceivedFromClient r;
    private ActorRef scene;
    private ActorRef attributeData;
    private ActorRef skillModule;
    private ActorRef buffData;
    private int attackLevel;
    private Map<Short, Long> attackAttributeMap;

    private final List<TargetParameter> targetParameters = new ArrayList<>();

    public UseSkillDataTemp() {
    }

    public UseSkillDataTemp(int skillId, long attackId, List<Long> targetId, float skillPosX, float skillPosY, float posX, float posY) {
        this.skillId = skillId;
        this.attackId = attackId;
        this.targetId = targetId;
        this.skillPosX = skillPosX;
        this.skillPosY = skillPosY;
        this.posX = posX;
        this.posY = posY;
    }


    public static UseSkillDataTemp of(Skill.CS10052 cs10052, ActorRef scene) {
        UseSkillDataTemp useSkillDataTemp = new UseSkillDataTemp(cs10052.getSkillId(), cs10052.getFightOrganismId(), cs10052.getTargetIdList(),
                cs10052.getSkillPositionX(), cs10052.getSkillPositionY(), 0, 0);
        useSkillDataTemp.setScene(scene);
        return useSkillDataTemp;
    }

    public static UseSkillDataTemp of(Skill.CS10052 cs10052) {
        return new UseSkillDataTemp(cs10052.getSkillId(), cs10052.getFightOrganismId(), cs10052.getTargetIdList(),
                cs10052.getSkillPositionX(), cs10052.getSkillPositionY(), 0, 0);
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public long getAttackId() {
        return attackId;
    }

    public void setAttackId(long attackId) {
        this.attackId = attackId;
    }

    public List<Long> getTargetId() {
        return targetId;
    }

    public void setTargetId(List<Long> targetId) {
        this.targetId = targetId;
    }

    public float getSkillPosX() {
        return skillPosX;
    }

    public void setSkillPosX(float skillPosX) {
        this.skillPosX = skillPosX;
    }

    public float getSkillPosY() {
        return skillPosY;
    }

    public void setSkillPosY(float skillPosY) {
        this.skillPosY = skillPosY;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setSceneId(long sceneId) {
        this.sceneId = sceneId;
    }

    public long getSceneId() {
        return sceneId;
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

    public Map<Short, Long> getAttackAttributeMap() {
        return attackAttributeMap;
    }

    public void setAttackAttributeMap(Map<Short, Long> attackAttributeMap) {
        this.attackAttributeMap = attackAttributeMap;
    }

    public byte getProfession() {
        return profession;
    }

    public void setProfession(byte profession) {
        this.profession = profession;
    }

    public int getAttackLevel() {
        return attackLevel;
    }

    public void setAttackLevel(int attackLevel) {
        this.attackLevel = attackLevel;
    }

    public List<TargetParameter> getTargetParameters() {
        return targetParameters;
    }

    public Client.ReceivedFromClient getR() {
        return r;
    }

    public void setR(Client.ReceivedFromClient r) {
        this.r = r;
    }

    public void setSkillModule(ActorRef skillModule) {
        this.skillModule = skillModule;
    }

    public ActorRef getSkillModule() {
        return skillModule;
    }

    public ActorRef getBuffData() {
        return buffData;
    }

    public void setBuffData(ActorRef buffData) {
        this.buffData = buffData;
    }
}