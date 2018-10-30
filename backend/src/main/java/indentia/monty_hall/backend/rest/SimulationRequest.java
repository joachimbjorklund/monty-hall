package indentia.monty_hall.backend.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class SimulationRequest {
    @JsonProperty
    private int nbrOfDoors;

    @JsonProperty
    private int nbrOfSimulations;

    public SimulationRequest() {
    }

    public SimulationRequest(int nbrOfDoors, int nbrOfSimulations) {
        this.nbrOfDoors = nbrOfDoors;
        this.nbrOfSimulations = nbrOfSimulations;
    }

    int getNbrOfDoors() {
        return nbrOfDoors;
    }

    public void setNbrOfDoors(int nbrOfDoors) {
        this.nbrOfDoors = nbrOfDoors;
    }

    int getNbrOfSimulations() {
        return nbrOfSimulations;
    }

    public void setNbrOfSimulations(int nbrOfSimulations) {
        this.nbrOfSimulations = nbrOfSimulations;
    }
}
