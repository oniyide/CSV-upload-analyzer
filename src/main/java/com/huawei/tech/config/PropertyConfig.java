package com.huawei.tech.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class PropertyConfig {

    /**
     * Folder location for storing files
     */
    @Value("${tech.storage}")
    public String storageLocation;

    @Bean
    public Path csvFilePath(){
        Path path = Paths.get(storageLocation);
        return path;
    }
}
