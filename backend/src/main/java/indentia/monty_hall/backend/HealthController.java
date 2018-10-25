package indentia.monty_hall.backend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HealthController {

    @RequestMapping(value = "/health", method = GET)
    public String health() {
        return "UP";
    }
}
