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
package application.module.scene.fight.skill.base.context;

import application.guid.IdUtils;
import application.module.organism.FightOrganism;
import application.module.organism.OrganismType;
import application.module.scene.Scene;
import application.util.LongId;
import com.google.protobuf.GeneratedMessageV3;
import mobius.modular.client.Client;
import protocol.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 技能释放上下文临时数据
 *
 * @author Luo Yong
 * @date 2022-2-25
 * @Source 1.0
 */
public class UseSkillDataTemp extends LongId {

    private int skillId;
    private long attackId;
    private byte attackType;
    private List<Long> targetId;
    private long targetType;
    private int attackTemplateId;
    private float skillPosX;
    private float skillPosY;
    private float posX;
    private float posY;
    private long sceneId;
    private Client.ReceivedFromClient r;
    private Scene scene;
    private FightOrganism attack;
    private FightOrganism target;
    private int skillProcessId;
    private int skillOrganismId;

    private final Map<Short, Long> changeAttributeMap = new HashMap<>();

    private final List<TargetParameter> targetParameters = new ArrayList<>();

    public UseSkillDataTemp() {
        super(IdUtils.fastSimpleUUIDLong());
    }

    public UseSkillDataTemp(int skillId, long attackId, List<Long> targetId, float skillPosX, float skillPosY, float posX, float posY) {
        super(IdUtils.fastSimpleUUIDLong());
        this.skillId = skillId;
        this.attackId = attackId;
        this.targetId = targetId;
        this.skillPosX = skillPosX;
        this.skillPosY = skillPosY;
        this.posX = posX;
        this.posY = posY;
    }


    public static UseSkillDataTemp of(Skill.CS10052 cs10052, Scene scene) {
        UseSkillDataTemp useSkillDataTemp = new UseSkillDataTemp(cs10052.getSkillId(), cs10052.getFightOrganismId(), cs10052.getTargetIdList(),
                cs10052.getSkillPositionX(), cs10052.getSkillPositionY(), 0, 0);
        useSkillDataTemp.setScene(scene);
        useSkillDataTemp.setAttackType(OrganismType.PLAYER);
        return useSkillDataTemp;
    }

    public static UseSkillDataTemp of(Skill.CS10055 cs10055, Scene scene) {
//        UseSkillDataTemp useSkillDataTemp = new UseSkillDataTemp(cs10055.getSkillProcessId(), cs10055.getFightOrganismId(), cs10055.getTargetIdList(),
//                cs10055.getSkillPositionX(), cs10055.getSkillPositionY(), 0, 0);
        UseSkillDataTemp useSkillDataTemp = new UseSkillDataTemp(cs10055.getSkillProcessId(), cs10055.getFightOrganismId(), cs10055.getTargetIdList(),
                0, 0, 0, 0);
        useSkillDataTemp.setScene(scene);
        useSkillDataTemp.setAttackType(OrganismType.PLAYER);
        return useSkillDataTemp;
    }

    public static UseSkillDataTemp of(Skill.CS10052 cs10052) {
        return new UseSkillDataTemp(cs10052.getSkillId(), cs10052.getFightOrganismId(), cs10052.getTargetIdList(),
                cs10052.getSkillPositionX(), cs10052.getSkillPositionY(), 0, 0);
    }


    public void sendToAllClient(int protoId, GeneratedMessageV3 message) {
        this.scene.getPlayerSceneMgr().sendToAllClient(scene, protoId, message);
    }

    public void addBuff(int templateId, FightOrganism from, FightOrganism to) {
        addBuff(templateId, from, to, 0);
    }

    public void addBuff(int templateId, FightOrganism from, FightOrganism to, long duration) {
        to.getFightBuffMgr().addBuff(templateId, from, to, duration);
    }

    public void removeBuff(int buffTemplateId, FightOrganism to) {
        to.getFightBuffMgr().removeBuff(buffTemplateId);
    }

    //get and set

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

    public byte getAttackType() {
        return attackType;
    }

    public void setAttackType(byte attackType) {
        this.attackType = attackType;
    }

    public int getAttackTemplateId() {
        return attackTemplateId;
    }

    public void setAttackTemplateId(int attackTemplateId) {
        this.attackTemplateId = attackTemplateId;
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


    public long getTargetType() {
        return targetType;
    }

    public void setTargetType(long targetType) {
        this.targetType = targetType;
    }

    public long getSceneId() {
        return sceneId;
    }

    public void setSceneId(long sceneId) {
        this.sceneId = sceneId;
    }

    public Client.ReceivedFromClient getR() {
        return r;
    }

    public void setR(Client.ReceivedFromClient r) {
        this.r = r;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public FightOrganism getAttack() {
        return attack;
    }

    public void setAttack(FightOrganism attack) {
        this.attack = attack;
    }

    public FightOrganism getTarget() {
        return target;
    }

    public void setTarget(FightOrganism target) {
        this.target = target;
    }

    public List<TargetParameter> getTargetParameters() {
        return targetParameters;
    }

    public Map<Short, Long> getChangeAttributeMap() {
        return changeAttributeMap;
    }

    public Map<Short, Long> getFightMap() {
        return getAttack().getFightMap();
    }

    public int getSkillProcessId() {
        return skillProcessId;
    }

    public void setSkillProcessId(int skillProcessId) {
        this.skillProcessId = skillProcessId;
    }

    public int getSkillOrganismId() {
        return skillOrganismId;
    }

    public void setSkillOrganismId(int skillOrganismId) {
        this.skillOrganismId = skillOrganismId;
    }
}

