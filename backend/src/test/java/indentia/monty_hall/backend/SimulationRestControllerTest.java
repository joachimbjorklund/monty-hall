package indentia.monty_hall.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import indentia.monty_hall.backend.rest.SimulationRequest;
import indentia.monty_hall.backend.rest.SimulationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Collections.singletonList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(classes = {SimulationRestControllerTest.Conf.class}, webEnvironment = RANDOM_PORT)
public class SimulationRestControllerTest extends AbstractTestNGSpringContextTests {

    @Configuration
    @Import(MontyHallBackendMain.class)
    static class Conf {
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSimulate() {

        SimulationRequest body = new SimulationRequest(3, 1000);

        ResponseEntity<String> json = restTemplate.postForEntity("/simulate", jsonEntity(body), String.class);
        System.out.println("json: " + json.getBody());

        ResponseEntity<SimulationResponse> response = restTemplate.postForEntity("/simulate", jsonEntity(body), SimulationResponse.class);

        System.out.println(response);
        Assert.assertEquals((int)response.getBody().getStats().getStats().get(Stats.StatKey.ROUNDS), 1000);
        Assert.assertTrue((int)response.getBody().getStats().getStats().get(Stats.StatKey.WIN_PERCENTAGE) > 60);
    }

    private <T> HttpEntity<T> jsonEntity(T entity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        return new HttpEntity<>(entity, headers);
    }
}
