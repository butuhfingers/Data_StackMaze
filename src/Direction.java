/**
 * Created by aaron on 9/20/16.
 * Implemented by: Derek Crew
 */
public enum Direction {
    NORTH(0),
    SOUTH(1),
    EAST(2),
    WEST(3),
    NONE(4);

    private final int value;

    Direction(int value) {
        this.value = value;
    }
    int value() {
        return this.value;
    }

    public static Direction returnFromValue(int value){
        switch (value){
            case 0:
                return NORTH;
            case 1:
                return SOUTH;
            case 2:
                return EAST;
            case 3:
                return WEST;
            default:
                return NONE;
        }
    }
}