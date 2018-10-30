package indentia.monty_hall.backend.rest;

import indentia.monty_hall.backend.EnumTypes.UserSwitchDoor;
import indentia.monty_hall.backend.MontyHallSimulator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@SuppressWarnings("unused")
@RestController
public class SimulationRestController {

    @SuppressWarnings("unused")
    @RequestMapping(value = "/simulate", method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public SimulationResponse simulate(@RequestBody SimulationRequest request) {
        return new SimulationResponse(MontyHallSimulator.simulate(request.getNbrOfDoors(),
                                                                  request.getNbrOfSimulations(),
                                                                  UserSwitchDoor.ALWAYS));
    }
}
