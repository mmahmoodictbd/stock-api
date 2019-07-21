package com.unloadbrain.assignement.payconiq.akka;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

public class AkkaSpringExtensionTest {

    @Test
    public void shouldReturnAkkaSpringExtension() {

        // Given

        ApplicationContext applicationContextMock = mock(ApplicationContext.class);

        AkkaSpringExtension akkaSpringExtension = new AkkaSpringExtension(applicationContextMock);

        // When
        AkkaSpringExtension.SpringExt springExt = akkaSpringExtension.createExtension(any());

        // Then
        assertNotNull(springExt);
    }
}