package com.unloadbrain.assignement.payconiq.stockapi.util;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class UuidUtilTest {

    @Test
    public void shouldReturnValidRandomUuid() {

        // Given

        UuidUtil uuidUtil = new UuidUtil();

        // When
        String uuid = uuidUtil.getRandomUuid();

        // Then
        assertNotNull(uuid);
        assertNotNull(UUID.fromString(uuid));
    }
}
