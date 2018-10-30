package indentia.monty_hall.backend;

import indentia.monty_hall.backend.Door.DoorWithCar;
import indentia.monty_hall.backend.Door.DoorWithGoat;
import indentia.monty_hall.backend.EnumTypes.UserSwitchDoor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static indentia.monty_hall.backend.Door.DoorContent.CAR;
import static indentia.monty_hall.backend.MontyHallSimulator.State.GAME_OVER;
import static indentia.monty_hall.backend.MontyHallSimulator.State.MONTY_OPENS_GOAT_DOORS;
import static indentia.monty_hall.backend.MontyHallSimulator.State.USER_MIGHT_SWITCH_DOOR;
import static indentia.monty_hall.backend.MontyHallSimulator.State.USER_OPEN_PICKED_DOOR;
import static indentia.monty_hall.backend.MontyHallSimulator.State.USER_PICK_DOOR;

@SuppressWarnings("unused")
public class MontyHallSimulator {

    private static final int DEFAULT_NBR_DOORS = 3;

    private List<Door> doors;
    private Optional<Door> userPickedDoor;
    private UserSwitchDoor userSwitchDoor = UserSwitchDoor.ALWAYS;
    private State state = GAME_OVER;

    public MontyHallSimulator() {
        reset(DEFAULT_NBR_DOORS);
    }

    public MontyHallSimulator(int nbrDoors) {
        reset(nbrDoors);
    }

    public static Stats simulate(int nbrDoors, int nbrSimulations, UserSwitchDoor userSwitchDoor) {
        MontyHallSimulator simulator = new MontyHallSimulator(nbrDoors);
        simulator.userSwitchDoor = userSwitchDoor;

        Stats stats = new Stats();
        for (int i = 0; i < nbrSimulations; i++) {
            Door userPickedDoor = simulator.simulate();

            if (userPickedDoor.haveCar()) {
                stats.addWin();
            } else {
                stats.addLoss();
            }
            simulator.reset(nbrDoors);
        }
        return stats;
    }

    private Door simulate() {
        userPicksDoor();
        montyOpensGoatDoors();
        userMightSwitchDoor();
        return userOpenPickedDoor();
    }

    private void userPicksDoor() {
        if (state != USER_PICK_DOOR) {
            throw new RuntimeException("wrong state");
        }
        state = MONTY_OPENS_GOAT_DOORS;
        pickRandomDoor();
    }

    private void montyOpensGoatDoors() {
        if (state != MONTY_OPENS_GOAT_DOORS) {
            throw new RuntimeException("wrong state");
        }

        List<Door> openedDoors = new ArrayList<>();
        doors.forEach(d -> {
            if ((d.isClosed()) && (d.haveGoat())) {
                d.open();
                openedDoors.add(d);
            }
        });

        userPickedDoor.filter(Door::haveCar).ifPresent(d -> {
            Collections.shuffle(openedDoors);
            openedDoors.get(0).close();
        });
        state = USER_MIGHT_SWITCH_DOOR;
    }

    private void userMightSwitchDoor() {
        if (state != USER_MIGHT_SWITCH_DOOR) {
            throw new RuntimeException("wrong state");
        }

        if (shouldUserSwitchDoor()) {
            List<Door> closedDoors = getClosedDoors();
            if (closedDoors.size() != 1) {
                throw new RuntimeException("closed dooer != 1, " + closedDoors.size());
            }
            int index = doors.indexOf(closedDoors.get(0));
            pickDoor(index);
        }

        state = USER_OPEN_PICKED_DOOR;
    }

    private Door userOpenPickedDoor() {
        if (state != USER_OPEN_PICKED_DOOR) {
            throw new RuntimeException("wrong state");
        }
        state = GAME_OVER;
        return userPickedDoor.get();
    }

    private boolean shouldUserSwitchDoor() {
        return ((userSwitchDoor == UserSwitchDoor.RANDOM) && new Random().nextBoolean()) || (userSwitchDoor == UserSwitchDoor.ALWAYS);
    }

    private List<Door> getClosedDoors() {
        return doors.stream().filter(Door::isClosed).collect(Collectors.toList());
    }

    private void reset() {
        reset(DEFAULT_NBR_DOORS);
    }

    private void reset(int nbrDoors) {
        if (state != GAME_OVER) {
            throw new RuntimeException("wrong state");
        }

        if (nbrDoors < 3) {
            throw new RuntimeException("must have at least 3 doors");
        }

        doors = new ArrayList<>();
        for (int i = 0; i < nbrDoors; i++) {
            doors.add(new DoorWithGoat());
        }

        int doorWithCar = new Random().nextInt(doors.size());
        doors.set(doorWithCar, new DoorWithCar());

        userPickedDoor = Optional.empty();
        state = USER_PICK_DOOR;
    }

    private void pickDoor(int number) {
        userPickedDoor.ifPresent(Door::unpick);
        Door door = doors.get(number);
        door.pick();
        userPickedDoor = Optional.of(door);
    }

    private void pickRandomDoor() {
        pickDoor(new Random().nextInt(doors.size()));
    }

    void userAlwaysSwitchesDoor() {
        this.userSwitchDoor = UserSwitchDoor.ALWAYS;
    }

    List<Door> getDoors() {
        return doors;
    }

    enum State {
        USER_PICK_DOOR,
        MONTY_OPENS_GOAT_DOORS,
        USER_MIGHT_SWITCH_DOOR,
        USER_OPEN_PICKED_DOOR,
        GAME_OVER
    }
}
