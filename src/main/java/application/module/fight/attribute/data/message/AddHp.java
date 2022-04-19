package application.module.fight.attribute.data.message;

import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-11
 * @Source 1.0
 */
public record AddHp(long playerId, Client.ReceivedFromClient r, long hp) {
}
