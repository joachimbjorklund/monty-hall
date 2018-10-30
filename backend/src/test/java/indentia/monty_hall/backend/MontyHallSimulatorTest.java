package indentia.monty_hall.backend;

import org.testng.Assert;
import org.testng.annotations.Test;

import static indentia.monty_hall.backend.EnumTypes.UserSwitchDoor.ALWAYS;
import static indentia.monty_hall.backend.Stats.StatKey.WIN_PERCENTAGE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class MontyHallSimulatorTest {

    @Test
    public void testSetup() {

        try {
            new MontyHallSimulator(2);
            fail();
        } catch (Exception ignore) {
        }

        int nbrDoors = 10;
        MontyHallSimulator montyHallSimulator = new MontyHallSimulator(nbrDoors);

        long nbrDoorsWithCars = montyHallSimulator.getDoors().stream().filter(Door::haveCar).count();
        assertEquals(nbrDoorsWithCars, 1);

        long nbrDoorsWithGoats = montyHallSimulator.getDoors().stream().filter(Door::haveGoat).count();
        assertEquals(nbrDoorsWithGoats, nbrDoors - 1);

        long nbrDoorsClosed = montyHallSimulator.getDoors().stream().filter(Door::isClosed).count();
        assertEquals(nbrDoorsClosed, nbrDoors);
    }

    @Test
    public void testSimulation() {
        MontyHallSimulator montyHallSimulator = new MontyHallSimulator(3);
        montyHallSimulator.userAlwaysSwitchesDoor();
        Stats stats = MontyHallSimulator.simulate(3, 1000, ALWAYS);
        Assert.assertTrue(stats.getStats().get(WIN_PERCENTAGE) > 60);
    }
}