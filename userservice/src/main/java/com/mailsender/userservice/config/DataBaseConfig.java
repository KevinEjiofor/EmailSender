package com.mailsender.userservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class DataBaseConfig {

    @Value("${DATABASE_HOST}")
    private String databaseHost;

    @Value("${DATABASE_PORT}")
    private String databasePort;

    @Value("${DATABASE_NAME}")
    private String databaseName;

    @Value("${DATABASE_PASSWORD}")
    private String databasePassword;

    @Value("${DATABASE_ID}")
    private String databaseUsername;
}
