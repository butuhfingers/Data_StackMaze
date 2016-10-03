/**
 * Created by derek on 9/26/16.
 */
public class Wall {
    //Variables
    private boolean exists = false;
    private Direction direction;


    public boolean Exists(){
        return exists;
    }
    public void Toggle(){
        exists = !exists;
    }

    public Direction Dir(){
        return direction;
    }

    public Wall(boolean exists, Direction direction){
        this.exists = exists;
        this.direction = direction;
    }
}
