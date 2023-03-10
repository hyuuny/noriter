package com.hyuuny.noriter.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(
        scanBasePackages = {
                "com.hyuuny.noriter.api",
                "com.hyuuny.noriter.support"
        }
)
public class NoriterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoriterApiApplication.class, args);
    }

}
