package application.data.model.java.api;

import mobius.core.java.api.AbstractLogActor;
import mobius.modular.client.Client;

/**
 * test for AbstractLogActor
 * <p>
 * Created by RXL on 2021/11/16.
 * Maintainer:
 * RXL
 */
public class MyActor extends AbstractLogActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Integer.class, i -> {
                    sender().tell(i * 2, self());
                })
                .match(Client.ReceivedFromClient.class, r ->{
                    log().debug("xxxxx");
                })
                .build();
    }

    private void AccessLog() {
        this.getLog().debug("from inside of $" + this.getClass());
    }
}
