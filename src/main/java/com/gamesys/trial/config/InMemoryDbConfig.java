package com.gamesys.trial.config;

import com.gamesys.trial.model.TravelDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Profile("inMemory")
public class InMemoryDbConfig {

    @Bean
    public Map<String, TravelDetail> inMemoryDb(){
        return new ConcurrentHashMap<>();
    }
}
