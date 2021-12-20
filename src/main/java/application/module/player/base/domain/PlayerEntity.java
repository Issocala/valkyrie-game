package application.module.player.base.domain;


import com.cala.orm.annotation.DbDeserialize;
import com.cala.orm.annotation.DbPojoBuilder;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DbStatus;
import com.cala.orm.converter.Convert;
import com.cala.orm.converter.JsonAttributeConverter;

import javax.persistence.Column;

/**
 * @author Luo Yong
 * @date 2021-11-15
 * @Source 1.0
 */
@DbDeserialize(builder = PlayerEntity.Builder.class)
public class PlayerEntity extends AbstractEntityBase {

    @Column
    private final String name;

    @Column
    @Convert(converter = JsonAttributeConverter.class)
    private final PlayerInfo playerInfo;

    @Column
    @Convert(converter = JsonAttributeConverter.class)
    private final Person person;

    public PlayerEntity(Long id, String name, DbStatus dbStatus, PlayerInfo playerInfo, Person person) {
        super(id, dbStatus);
        this.name = name;
        this.playerInfo = playerInfo;
        this.person = person;
    }

    public PlayerEntity(PlayerEntity playerEntity, DbStatus dbStatus) {
        super(playerEntity.getId(), dbStatus);
        this.name = playerEntity.getName();
        this.playerInfo = playerEntity.getPlayerInfo();
        this.person = playerEntity.getPerson();
    }


    @DbPojoBuilder
    public static class Builder {
        private String name;
        private Long id;
        private PlayerInfo playerInfo;
        private DbStatus dbStatus;
        private Person person;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setPlayerInfo(PlayerInfo playerInfo) {
            this.playerInfo = playerInfo;
            return this;
        }

        public Builder setDbStatus(DbStatus dbStatus) {
            this.dbStatus = dbStatus;
            return this;
        }

        public Builder setPerson(Person person) {
            this.person = person;
            return this;
        }

        public PlayerEntity build() {
            return new PlayerEntity(id, name, dbStatus, playerInfo, person);
        }
    }

    @Override
    public AbstractEntityBase valueOf(AbstractEntityBase entityBase, DbStatus dbStatus) {
        if (!(entityBase instanceof PlayerEntity playerEntity)) {
            return null;
        }
        return new PlayerEntity(playerEntity, dbStatus);
    }

    public String getName() {
        return name;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public Person getPerson() {
        return person;
    }
}
