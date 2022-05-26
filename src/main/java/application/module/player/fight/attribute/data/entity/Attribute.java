package application.module.player.fight.attribute.data.entity;

import application.util.AbstractBuilder;
import com.cala.orm.annotation.DbDeserialize;
import com.cala.orm.annotation.DbPojoBuilder;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.converter.Convert;
import com.cala.orm.converter.JsonAttributeConverter;

import javax.persistence.Column;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
@DbDeserialize(builder = Attribute.Builder.class)
public class Attribute extends AbstractEntityBase {

    @Column
    @Convert(converter = JsonAttributeConverter.class)
    private final Map<Short, FightPower> type2FightPowerMap;

    public Attribute(Long id, Map<Short, FightPower> type2FightPowerMap) {
        super(id);
        this.type2FightPowerMap = type2FightPowerMap;
    }

    @DbPojoBuilder
    public static class Builder extends AbstractBuilder {
        private Map<Short, FightPower> type2FightPowerMap;

        public Builder setType2FightPowerMap(Map<Short, FightPower> type2FightPowerMap) {
            this.type2FightPowerMap = type2FightPowerMap;
            return this;
        }

        public Attribute build() {
            return new Attribute(getId(), type2FightPowerMap);
        }
    }

    public static Attribute of(long id) {
        return new Attribute(id, null);
    }

    public Map<Short, FightPower> getType2FightPowerMap() {
        return type2FightPowerMap;
    }
}
