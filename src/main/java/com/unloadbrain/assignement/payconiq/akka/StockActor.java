package com.unloadbrain.assignement.payconiq.akka;

import akka.actor.AbstractActor;
import com.unloadbrain.assignement.payconiq.service.StockPersistenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
@AllArgsConstructor
public class StockActor extends AbstractActor {

    private StockPersistenceService stockPersistenceService;

    @Override
    public Receive createReceive() {

        return receiveBuilder()
                .matchAny(message -> {
                    log.info("Unknown message received: {}", message);
                    unhandled(message);
                })
                .build();
    }
}