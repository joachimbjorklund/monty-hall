package indentia.monty_hall.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SuppressWarnings("unused")
@RestController
public class HealthRestController {

    @RequestMapping(value = "/health", method = GET)
    public String health() {
        return "UP";
    }
}
