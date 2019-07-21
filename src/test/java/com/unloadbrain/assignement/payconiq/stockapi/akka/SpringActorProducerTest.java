package com.unloadbrain.assignement.payconiq.stockapi.akka;

import akka.actor.Actor;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpringActorProducerTest {

    @Test
    public void shouldProduceStockActor() throws ClassNotFoundException {

        // Given

        Actor actorMock = mock(Actor.class);
        ApplicationContext applicationContextMock = mock(ApplicationContext.class);
        when(applicationContextMock.getBean(anyString())).thenReturn(actorMock);

        Class actorClassMock = Class.forName("akka.actor.AbstractActor");
        when(applicationContextMock.getType(anyString())).thenReturn(actorClassMock);

        SpringActorProducer springActorProducer
                = new SpringActorProducer(applicationContextMock, anyString());

        // When
        Actor actor = springActorProducer.produce();
        Class<? extends Actor> actorClass = springActorProducer.actorClass();

        // Then
        assertNotNull(actor);
        assertNotNull(actorClass);
    }

}