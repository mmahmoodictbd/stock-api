package com.unloadbrain.assignement.payconiq.stockapi.util;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DateTimeTest {

    @Test
    public void shouldReturnCurrentTime() {

        // Given

        long timeDiffBetweenCalls = 10;
        DateTimeUtil dateTimeUtil = new DateTimeUtil();

        // When
        long nowInEpochMilli = dateTimeUtil.getCurrentTimeEpochMilli();

        // Then
        assertTrue(Instant.now().getEpochSecond() - nowInEpochMilli < timeDiffBetweenCalls);
    }
}
