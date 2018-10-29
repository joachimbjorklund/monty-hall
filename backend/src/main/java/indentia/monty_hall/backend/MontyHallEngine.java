package indentia.monty_hall.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static indentia.monty_hall.backend.MontyHallEngine.BehindDoor.CAR;
import static indentia.monty_hall.backend.MontyHallEngine.BehindDoor.GOAT;
import static indentia.monty_hall.backend.MontyHallEngine.DoorState.CLOSED;
import static indentia.monty_hall.backend.MontyHallEngine.DoorState.CLOSED_PICKED;
import static indentia.monty_hall.backend.MontyHallEngine.GameState.GAME_OVER;
import static indentia.monty_hall.backend.MontyHallEngine.GameState.MONTY_OPENS_GOAT_DOORS;
import static indentia.monty_hall.backend.MontyHallEngine.GameState.USER_MIGHT_SWITCH_DOOR;
import static indentia.monty_hall.backend.MontyHallEngine.GameState.USER_OPEN_PICKED_DOOR;
import static indentia.monty_hall.backend.MontyHallEngine.GameState.USER_PICK_DOOR;

public class MontyHallEngine {


    private static final int DEFAULT_NBR_DOORS = 3;

    List<Door> doors;
    private Optional<Door> pickedDoor;
    private boolean userSwitchesDoor;
    private UserDecision userDecision = UserDecision.RANDOM;
    private GameState gameState = GAME_OVER;

    public MontyHallEngine() {
        setup(DEFAULT_NBR_DOORS);
    }

    public MontyHallEngine(int nbrDoors) {
        setup(nbrDoors);
    }

    public Door userPicksDoor() {
        if (gameState != USER_PICK_DOOR) {
            throw new RuntimeException("wrong state");
        }
        gameState = MONTY_OPENS_GOAT_DOORS;
        return pickRandomDoor();
    }

    public Door userOpenPickedDoor() {
        if (gameState != USER_OPEN_PICKED_DOOR) {
            throw new RuntimeException("wrong state");
        }
        gameState = GAME_OVER;
        return pickedDoor.get();
    }

    public List<Door> montyOpensGoatDoors() {
        if (gameState != MONTY_OPENS_GOAT_DOORS) {
            throw new RuntimeException("wrong state");
        }

        List<Door> openedDoors = new ArrayList<>();
        doors.forEach(d -> {
            if ((d.doorState == CLOSED) && (d.content == GOAT)) {
                d.open();
                openedDoors.add(d);
            }
        });

        if (pickedDoor.get().content == CAR) {
            Collections.shuffle(openedDoors);
            openedDoors.get(0).close();
        }
        gameState = USER_MIGHT_SWITCH_DOOR;
        return openedDoors;
    }

    public Door userMightSwitchDoor() {
        if (gameState != USER_MIGHT_SWITCH_DOOR) {
            throw new RuntimeException("wrong state");
        }

        if (((userDecision == UserDecision.RANDOM) && new Random().nextBoolean()) || (userDecision == UserDecision.ALWAYS)) {
            List<Door> closedDoors = getClosedDoors();
            if (closedDoors.size() != 1) {
                throw new RuntimeException("fatal");
            }
            int index = doors.indexOf(closedDoors.get(0));
            pickDoor(index);
            userSwitchesDoor = true;
        }

        gameState = USER_OPEN_PICKED_DOOR;
        return pickedDoor.orElseThrow(() -> new RuntimeException("fatal"));
    }

    public boolean userSwitchedDoor() {
        return userSwitchesDoor;
    }

    List<Door> getClosedDoors() {
        return doors.stream().filter(d -> d.doorState == CLOSED).collect(Collectors.toList());
    }

    void setup() {
        setup(DEFAULT_NBR_DOORS);
    }

    void setup(int nbrDoors) {
        if (gameState != GAME_OVER) {
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

        pickedDoor = Optional.empty();
        gameState = USER_PICK_DOOR;
    }

    BehindDoor openDoor(int number) {
        if ((number < 2) || (number > (doors.size() - 1))) {
            throw new RuntimeException("no such door");
        }
        Door door = doors.get(number);
        return door.open();
    }

    private Door pickDoor(int number) {
        pickedDoor.ifPresent(Door::unpick);
        Door door = doors.get(number);
        door.pick();
        pickedDoor = Optional.of(door);
        return pickedDoor.get();
    }

    Door pickRandomDoor() {
        return pickDoor(new Random().nextInt(doors.size()));
    }

    public Door play() {
        Door pickedDoor = userPicksDoor();
        List<Door> openGoatDoors = montyOpensGoatDoors();
        Door userDecidedDoor = userMightSwitchDoor();
        Door openedDoor = userOpenPickedDoor();

        return openedDoor;
    }

    public void userRandomlySwitchesDoor() {
        this.userDecision = UserDecision.RANDOM;
    }

    public void userAlwaysSwitchesDoor() {
        this.userDecision = UserDecision.ALWAYS;
    }

    public void userNeverSwitchesDoor() {
        this.userDecision = UserDecision.NEVER;
    }

    abstract class Door {
        final BehindDoor content;
        DoorState doorState;

        protected Door(BehindDoor content) {
            this.content = content;
            this.doorState = CLOSED;
        }

        private BehindDoor open() {
            doorState = DoorState.OPEN;
            return content;
        }

        public void close() {
            doorState = DoorState.CLOSED;
        }

        private Door pick() {
            if (doorState != CLOSED || pickedDoor.isPresent()) {
                throw new RuntimeException("can only pick closed doors");
            }
            doorState = DoorState.CLOSED_PICKED;
            return this;
        }

        private Door unpick() {
            if (doorState != CLOSED_PICKED) {
                throw new RuntimeException("can only pick closed doors");
            }
            pickedDoor = Optional.empty();
            doorState = DoorState.CLOSED;
            return this;
        }

        @Override
        public String toString() {
            return "Door{" +
                   "content=" + content +
                   ", doorState=" + doorState +
                   '}';
        }
    }

    class DoorWithGoat extends Door {
        DoorWithGoat() {
            super(GOAT);
        }
    }

    class DoorWithCar extends Door {
        DoorWithCar() {
            super(BehindDoor.CAR);
        }
    }

    enum UserDecision {
        RANDOM,
        NEVER,
        ALWAYS
    }

    enum BehindDoor {
        GOAT,
        CAR
    }

    enum DoorState {
        OPEN,
        CLOSED,
        CLOSED_PICKED
    }

    enum GameState {
        USER_PICK_DOOR,
        MONTY_OPENS_GOAT_DOORS,
        USER_MIGHT_SWITCH_DOOR,
        USER_OPEN_PICKED_DOOR,
        GAME_OVER
    }
}
