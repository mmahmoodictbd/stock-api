package com.unloadbrain.assignement.payconiq.stockapi.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.FactoryBean;

@AllArgsConstructor
public class StockActorFactory implements FactoryBean<ActorRef> {

    private ActorSystem actorSystem;
    private AkkaSpringExtension akkaSpringExtension;

    @Override
    public ActorRef getObject() {
        return actorSystem.actorOf(
                akkaSpringExtension.get(actorSystem)
                        .props("stockActor")
        );
    }

    @Override
    public Class<?> getObjectType() {
        return ActorRef.class;
    }
}
