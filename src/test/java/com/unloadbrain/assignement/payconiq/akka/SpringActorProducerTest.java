package com.unloadbrain.assignement.payconiq.akka;

import akka.actor.Actor;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpringActorProducerTest {

    @Test
    public void shouldProduceStockActor() {

        // Given

        Actor actorMock = mock(Actor.class);
        ApplicationContext applicationContextMock = mock(ApplicationContext.class);
        when(applicationContextMock.getBean("stockActor")).thenReturn(actorMock);

        Class actorClassMock = actorMock.getClass();
        when(applicationContextMock.getType("stockActor")).thenReturn(actorClassMock);

        SpringActorProducer springActorProducer
                = new SpringActorProducer(applicationContextMock, "stockActor");

        // When
        Actor actor= springActorProducer.produce();
        Class<? extends Actor> actorClass = springActorProducer.actorClass();

        // Then
        assertNotNull(actor);
        assertNotNull(actorClass);
    }

}