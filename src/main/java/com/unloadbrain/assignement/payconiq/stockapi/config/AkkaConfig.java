package com.unloadbrain.assignement.payconiq.stockapi.config;

import akka.actor.ActorSystem;
import com.unloadbrain.assignement.payconiq.stockapi.akka.StockActorFactory;
import com.unloadbrain.assignement.payconiq.stockapi.akka.AkkaSpringExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides AKKA related beans.
 */
@Configuration
public class AkkaConfig {

    private ApplicationContext applicationContext;

    public AkkaConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("akka-system");
        akkaSpringExtension().get(system).initialize(applicationContext);

        return system;
    }

    @Bean
    public AkkaSpringExtension akkaSpringExtension() {
        return new AkkaSpringExtension(applicationContext);
    }

    @Bean
    public StockActorFactory actorFactory() {
        return new StockActorFactory(actorSystem(), akkaSpringExtension());
    }

}
