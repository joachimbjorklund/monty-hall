package indentia.monty_hall.backend.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import indentia.monty_hall.backend.Stats;

@SuppressWarnings("unused")
public class SimulationResponse {

    @JsonProperty("result")
    private Stats stats;

    SimulationResponse(Stats stats) {
        this.stats = stats;
    }

    public SimulationResponse() {
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return stats.toString();
    }
}
