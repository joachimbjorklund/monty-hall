package indentia.monty_hall.backend;

import static indentia.monty_hall.backend.Door.DoorContent.CAR;
import static indentia.monty_hall.backend.Door.DoorContent.GOAT;
import static indentia.monty_hall.backend.Door.DoorState.CLOSED;
import static indentia.monty_hall.backend.Door.DoorState.CLOSED_PICKED;

public abstract class Door {
    final DoorContent content;
    private DoorState doorState;

    Door(DoorContent content) {
        this.content = content;
        this.doorState = CLOSED;
    }

    void open() {
        doorState = DoorState.OPEN;
    }

    void close() {
        doorState = CLOSED;
    }

    void pick() {
        if (doorState != CLOSED) {
            throw new RuntimeException("can only pick closed doors");
        }
        doorState = DoorState.CLOSED_PICKED;
    }

    void unpick() {
        if (doorState != CLOSED_PICKED) {
            throw new RuntimeException("can only pick closed doors");
        }
        doorState = CLOSED;
    }

    @Override
    public String toString() {
        return "Door{" +
               "content=" + content +
               ", doorState=" + doorState +
               '}';
    }

    boolean isClosed() {
        return doorState == CLOSED;
    }

    boolean haveGoat() {
        return content == GOAT;
    }

    boolean haveCar() {
        return content == CAR;
    }

    enum DoorContent {
        GOAT,
        CAR
    }

    enum DoorState {
        OPEN,
        CLOSED,
        CLOSED_PICKED
    }

    static class DoorWithGoat extends Door {
        DoorWithGoat() {
            super(GOAT);
        }
    }

    static class DoorWithCar extends Door {
        DoorWithCar() {
            super(CAR);
        }
    }
}
