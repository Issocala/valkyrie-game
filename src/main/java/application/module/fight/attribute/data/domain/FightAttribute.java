package application.module.fight.attribute.data.domain;

import application.util.AbstractBuilder;
import com.cala.orm.annotation.DbDeserialize;
import com.cala.orm.annotation.DbPojoBuilder;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DbStatus;
import com.cala.orm.converter.Convert;
import com.cala.orm.converter.JsonAttributeConverter;

import javax.persistence.Column;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
@DbDeserialize(builder = FightAttribute.Builder.class)
public class FightAttribute extends AbstractEntityBase {

    @Column
    @Convert(converter = JsonAttributeConverter.class)
    private final Map<Short, FightPower> type2FightPowerMap;

    public FightAttribute(Long id, DbStatus dbStatus, Map<Short, FightPower> type2FightPowerMap) {
        super(id, dbStatus);
        this.type2FightPowerMap = type2FightPowerMap;
    }

    @DbPojoBuilder
    public static class Builder extends AbstractBuilder {
        private Map<Short, FightPower> type2FightPowerMap;

        public Builder setType2FightPowerMap(Map<Short, FightPower> type2FightPowerMap) {
            this.type2FightPowerMap = type2FightPowerMap;
            return this;
        }

        public FightAttribute build() {
            return new FightAttribute(getId(), getDbStatus(), type2FightPowerMap);
        }
    }

    public Map<Short, FightPower> getType2FightPowerMap() {
        return type2FightPowerMap;
    }
}
