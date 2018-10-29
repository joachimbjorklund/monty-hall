package indentia.monty_hall.backend;

import org.testng.annotations.Test;

import java.util.Map;

import static indentia.monty_hall.backend.MontyHallSimulator.BehindDoor.CAR;
import static indentia.monty_hall.backend.MontyHallSimulator.BehindDoor.GOAT;
import static indentia.monty_hall.backend.MontyHallSimulator.DoorState.CLOSED;
import static indentia.monty_hall.backend.MontyHallSimulator.UserDecision.ALWAYS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class MontyHallSimulatorTest {

    @Test
    public void testSetup() {

        try {
            new MontyHallSimulator(2);
            fail();
        } catch (Exception e) {
        }

        int nbrDoors = 10;
        MontyHallSimulator montyHallSimulator = new MontyHallSimulator(nbrDoors);

        long nbrDoorsWithCars = montyHallSimulator.doors.stream().filter(d -> d.content == CAR).count();
        assertEquals(nbrDoorsWithCars, 1);

        long nbrDoorsWithGoats = montyHallSimulator.doors.stream().filter(d -> d.content == GOAT).count();
        assertEquals(nbrDoorsWithGoats, nbrDoors - 1);

        long nbrDoorsClosed = montyHallSimulator.doors.stream().filter(d -> d.doorState == CLOSED).count();
        assertEquals(nbrDoorsClosed, nbrDoors);
    }

    @Test
    public void testSimulation() {
        MontyHallSimulator montyHallSimulator = new MontyHallSimulator(3);
        montyHallSimulator.userAlwaysSwitchesDoor();
        Map<MontyHallSimulator.Stat, Integer> stats = MontyHallSimulator.simulate(3, 1000, ALWAYS);
        System.out.println(stats);
    }
}