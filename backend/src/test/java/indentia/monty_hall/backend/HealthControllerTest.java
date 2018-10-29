package indentia.monty_hall.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.testng.Assert.assertEquals;

@SpringBootTest(classes = {HealthControllerTest.Conf.class}, webEnvironment = RANDOM_PORT)
public class HealthControllerTest extends AbstractTestNGSpringContextTests {

    @Configuration
    @Import(MontyHallBackendMain.class)
    static class Conf{
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHealthGivesUP() {
        String health = restTemplate.getForObject("/health", String.class);

        assertEquals(health, "UP");
    }
}
