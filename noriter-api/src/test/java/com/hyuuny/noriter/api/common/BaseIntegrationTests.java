package com.hyuuny.noriter.api.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@MockMvcCustomConfig
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        properties = "spring.config.location="
                + "classpath:application.yml,"
                + "classpath:application-test.yml"
)
public abstract class BaseIntegrationTests {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

}
