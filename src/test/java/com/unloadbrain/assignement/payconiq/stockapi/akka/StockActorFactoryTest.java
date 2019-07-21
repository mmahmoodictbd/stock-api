package com.unloadbrain.assignement.payconiq.stockapi.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StockActorFactoryTest {

    @Test
    public void shouldReturnStockActor() {

        // Given

        ActorSystem actorSystemMock = mock(ActorSystem.class);
        AkkaSpringExtension.SpringExt springExtMock = mock(AkkaSpringExtension.SpringExt.class);

        AkkaSpringExtension akkaSpringExtensionMock = mock(AkkaSpringExtension.class);
        when(akkaSpringExtensionMock.get(any())).thenReturn(springExtMock);

        ActorRef actorMock = mock(ActorRef.class);
        when(actorSystemMock.actorOf(any())).thenReturn(actorMock);

        StockActorFactory stockActorFactory = new StockActorFactory(actorSystemMock, akkaSpringExtensionMock);

        // When
        ActorRef stockActor = stockActorFactory.getObject();
        Class actorClass = stockActorFactory.getObjectType();

        // Then
        assertNotNull(stockActor);
        assertNotNull(actorClass);
    }

}