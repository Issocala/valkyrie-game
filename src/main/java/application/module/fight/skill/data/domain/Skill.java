package application.module.fight.skill.data.domain;

import application.util.AbstractBuilder;
import com.cala.orm.annotation.DbDeserialize;
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
    private final Set<Integer> skillSet;


    public Skill(Long id, Set<Integer> skillSet) {
        super(id);
        this.skillSet = skillSet;
    }

    public static Skill of(long id) {
        return new Skill(id, new HashSet<>());
    }

    public static class Builder extends AbstractBuilder {

        private Set<Integer> skillSet;

        public Builder setSkillSet(Set<Integer> skillSet) {
            this.skillSet = skillSet;
            return this;
        }

        public Skill build() {
            return new Skill(getId(), skillSet);
        }

    }

    public boolean isSkill(int skillId) {
        return skillSet.contains(skillId);
    }

    public Set<Integer> getSkillSet() {
        return skillSet;
    }
}
