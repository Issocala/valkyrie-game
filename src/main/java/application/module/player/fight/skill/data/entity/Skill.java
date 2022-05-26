package application.module.player.fight.skill.data.entity;

import application.util.AbstractBuilder;
import com.cala.orm.annotation.DbDeserialize;
import com.cala.orm.annotation.DbPojoBuilder;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.converter.Convert;
import com.cala.orm.converter.JsonAttributeConverter;

import javax.persistence.Column;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-2-18
 * @Source 1.0
 */
@DbDeserialize(builder = Skill.Builder.class)
public class Skill extends AbstractEntityBase {

    @Column
    @Convert(converter = JsonAttributeConverter.class)
    private final Set<Integer> enableSkillSet;

    @Column
    @Convert(converter = JsonAttributeConverter.class)
    private final Set<Integer> disableSkillSet;


    public Skill(long id) {
        this(id, new HashSet<>(), new HashSet<>());
    }

    public Skill(long id, Set<Integer> enableSkillSet, Set<Integer> disableSkillSet) {
        super(id);
        this.enableSkillSet = enableSkillSet;
        this.disableSkillSet = disableSkillSet;
    }

    public static Skill of(long id) {
        return new Skill(id);
    }

    @DbPojoBuilder
    public static class Builder extends AbstractBuilder {

        private Set<Integer> enableSkillSet;

        private Set<Integer> disableSkillSet;

        public Builder setEnableSkillSet(Set<Integer> enableSkillSet) {
            this.enableSkillSet = enableSkillSet;
            return this;
        }

        public Builder setDisableSkillSet(Set<Integer> disableSkillSet) {
            this.disableSkillSet = disableSkillSet;
            return this;
        }

        public Skill build() {
            return new Skill(getId(), enableSkillSet, disableSkillSet);
        }

    }

    public boolean isEnableSkill(int skillId) {
        return enableSkillSet.contains(skillId);
    }

    public Set<Integer> getEnableSkillSet() {
        return enableSkillSet;
    }

    public Set<Integer> getDisableSkillSet() {
        return disableSkillSet;
    }
}
