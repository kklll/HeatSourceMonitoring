package com.nuc.heatsourcemonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class HeatsourcemonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeatsourcemonitoringApplication.class, args);
    }

}
