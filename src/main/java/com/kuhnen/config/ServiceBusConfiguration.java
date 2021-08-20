package com.kuhnen.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties("azure.servicebus")
public class ServiceBusConfiguration {

    @NotNull(message = "Url de conexao com Azure ServiceBus não informada!")
    private String connectionString;


    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
}
