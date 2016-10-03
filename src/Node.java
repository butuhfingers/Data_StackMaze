/**
 * Created by aaron on 9/20/16.
 * Implemented by : Derek Crew on 9/21/16
 */
public class Node {

    // set the default wall state to no walls.
    private Wall[] wall = new Wall[4];
    private boolean visited;
    private boolean abandoned;
    private Coordinate myPosition;

    // new squares are built without walls
    public Node(Coordinate p) {
        myPosition = p;
        visited = false;
        abandoned = false;

        wall[0] = new Wall(true, Direction.NORTH);
        wall[1] = new Wall(true, Direction.SOUTH);
        wall[2] = new Wall(true, Direction.EAST);
        wall[3] = new Wall(true, Direction.WEST);
    }

    // this might be nice to have....
    public Node(Coordinate p, boolean[] wallSet) {
        this(p);
        for (int i = 0; i < wallSet.length && i < wall.length; i++) {
            wall[i] = new Wall(wallSet[i], Direction.returnFromValue(i));
        }
    }

    public void toggleWall(Direction dir) {
        this.getWall(dir).Toggle();
    }

    public Wall getWall(Direction dir) {
        //Go through all the walls
        for(Wall myWall : wall){
            //Is the wall equal to our direction?
            if(myWall.Dir() == dir){
                return myWall;
            }
        }

        return null;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void visit() {
        this.visited = true;
    }

    public void clearVisit(){
        this.visited = false;
    }

    public boolean isAbandoned() {
        return this.abandoned;
    }

    public void clearAbandon(){
        this.abandoned = false;
    }

    public void abandon() {
        this.abandoned = true;
    }

    public void clear() {
        visited = false;
        abandoned = false;

        for(Wall myWall : wall){
            myWall = new Wall(false, myWall.Dir());
        }
    }

    public Coordinate getPosition() {
        return this.myPosition;
    }

    public boolean equals(Node other) {
        Coordinate tempCoordinate = other.myPosition;

        if(this.myPosition.isEqual(tempCoordinate))
            return true;

        return false;
    }

    @Override
    public String toString(){
        return getPosition().toString() + " Visited: " + isVisited();
    }
}