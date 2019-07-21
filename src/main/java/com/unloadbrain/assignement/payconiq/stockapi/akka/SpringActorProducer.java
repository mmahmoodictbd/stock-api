package com.unloadbrain.assignement.payconiq.stockapi.akka;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;

@AllArgsConstructor
public class SpringActorProducer implements IndirectActorProducer {

    private ApplicationContext applicationContext;
    private String beanActorName;

    @Override
    public Actor produce() {
        return (Actor) applicationContext.getBean(beanActorName);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext.getType(beanActorName);
    }

}