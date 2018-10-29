package indentia.monty_hall.backend;

import indentia.monty_hall.backend.MontyHallEngine.Door;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static indentia.monty_hall.backend.MontyHallEngine.BehindDoor.CAR;
import static indentia.monty_hall.backend.MontyHallEngine.BehindDoor.GOAT;
import static indentia.monty_hall.backend.MontyHallEngine.DoorState.CLOSED;
import static indentia.monty_hall.backend.MontyHallEngine.DoorState.CLOSED_PICKED;
import static indentia.monty_hall.backend.MontyHallEngine.DoorState.OPEN;
import static org.testng.Assert.*;

public class MontyHallEngineTest {

    @Test
    public void testSetup() {

        try {
            new MontyHallEngine(2);
            fail();
        } catch (Exception e) {
        }

        int nbrDoors = 10;
        MontyHallEngine montyHallEngine = new MontyHallEngine(nbrDoors);

        long nbrDoorsWithCars = montyHallEngine.doors.stream().filter(d -> d.content == CAR).count();
        assertEquals(nbrDoorsWithCars, 1);

        long nbrDoorsWithGoats = montyHallEngine.doors.stream().filter(d -> d.content == GOAT).count();
        assertEquals(nbrDoorsWithGoats, nbrDoors - 1);

        long nbrDoorsClosed = montyHallEngine.doors.stream().filter(d -> d.doorState == CLOSED).count();
        assertEquals(nbrDoorsClosed, nbrDoors);
    }

    @Test
    public void testPlayGames() {
        MontyHallEngine montyHallEngine = new MontyHallEngine(3);
        montyHallEngine.userAlwaysSwitchesDoor();

        Map<String, Number> stats = newStats();
        for (int i = 0; i < 1000000; i++) {
            Door userPickedDoor = montyHallEngine.play();
            Number wins = stats.get("wins");
            Number losses = stats.get("losses");

            if (userPickedDoor.content == CAR) {
                wins = wins.intValue() + 1;
                stats.put("wins", wins.intValue());
            } else {
                losses = losses.intValue() + 1;
                stats.put("losses", losses.intValue());
            }
            Number winsPercentage = wins.doubleValue() / (double)i;
            stats.put("%wins",  winsPercentage);
            montyHallEngine.setup(3);
        }

        System.out.println(stats);
    }

    private Map<String, Number> newStats() {
        Map<String, Number> stats = new HashMap<>();
        stats.put("wins", 0);
        stats.put("losses", 0);
        return stats;
    }
}