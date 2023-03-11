package com.hyuuny.noriter.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(
        scanBasePackages = {
                "com.hyuuny.noriter.api",
                "com.hyuuny.noriter.support"
        }
)
public class NoriterApiApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:application-support.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(NoriterApiApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);

    }


}
