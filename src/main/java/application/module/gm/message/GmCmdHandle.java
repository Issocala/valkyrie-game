package application.module.gm.message;

import mobius.modular.client.Client;

import java.util.List;

/**
 * @author HRT
 * @date 2022-4-11
 */
public record GmCmdHandle(Client.ReceivedFromClient receivedFromClient, List<String> args) {
}
