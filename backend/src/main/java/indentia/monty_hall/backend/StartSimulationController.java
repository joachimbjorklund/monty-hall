package indentia.monty_hall.backend;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static indentia.monty_hall.backend.MontyHallSimulator.UserDecision.ALWAYS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class StartSimulationController {

    @SuppressWarnings("unused")
    @RequestMapping(value = "/simulate", method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public SimulationResponse simulate(@RequestBody SimulationRequest request) {
        Map<MontyHallSimulator.Stat, Integer> stats = MontyHallSimulator.simulate(request.nbrOfDoors, request.nbrOfSimulations, ALWAYS);

        Integer nbrWins = stats.get(MontyHallSimulator.Stat.WINS);
        Integer nbrLosses = stats.get(MontyHallSimulator.Stat.LOSSES);
        return new SimulationResponse(nbrWins, nbrLosses, (nbrWins / (double) request.nbrOfSimulations) * 100);
    }

    class SimulationResponse {
        final int nbrWins;
        final int nbrLosses;
        final double winPercentage;

        SimulationResponse(int nbrWins, int nbrLosses, double winPercentage) {
            this.nbrWins = nbrWins;
            this.nbrLosses = nbrLosses;
            this.winPercentage = winPercentage;
        }

        public int getNbrWins() {
            return nbrWins;
        }

        public int getNbrLosses() {
            return nbrLosses;
        }

        public double getWinPercentage() {
            return winPercentage;
        }
    }

    public static class SimulationRequest {
        int nbrOfDoors;
        int nbrOfSimulations;

        public SimulationRequest() {
        }

        public int getNbrOfDoors() {
            return nbrOfDoors;
        }

        public void setNbrOfDoors(int nbrOfDoors) {
            this.nbrOfDoors = nbrOfDoors;
        }

        public int getNbrOfSimulations() {
            return nbrOfSimulations;
        }

        public void setNbrOfSimulations(int nbrOfSimulations) {
            this.nbrOfSimulations = nbrOfSimulations;
        }
    }
}
