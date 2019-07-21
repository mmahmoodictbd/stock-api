package com.unloadbrain.assignement.payconiq.stockapi.akka;

import akka.actor.ExtendedActorSystem;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;


public class AkkaSpringExtensionTest {

    @Test
    public void shouldReturnAkkaSpringExtension() {

        // Given

        ApplicationContext applicationContextMock = mock(ApplicationContext.class);

        AkkaSpringExtension akkaSpringExtension = new AkkaSpringExtension(applicationContextMock);
        ExtendedActorSystem extendedActorSystemMock = mock(ExtendedActorSystem.class);

        // When
        AkkaSpringExtension.SpringExt springExt = akkaSpringExtension.createExtension(extendedActorSystemMock);

        // Then
        assertNotNull(springExt);
    }
}