package com.unloadbrain.assignement.payconiq.config;

import com.unloadbrain.assignement.payconiq.util.UuidUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides application related beans.
 */
@Configuration
@AllArgsConstructor
public class AppConfig {

    @Bean
    public UuidUtil uuidUtil() {
        return new UuidUtil();
    }
}
