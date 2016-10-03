/**
 * Created by derek on 9/26/16.
 */
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import static java.lang.System.*;

public class Maze {
    //Variables
    private int rows;
    private int columns;
    private Node[][] nodes;

    private Node startNode;
    private Node finishNode;

    public Node squareAt(Coordinate pos){
        return nodes[pos.getRow()][pos.getCol()];
    }

    public Node StartNode(){
        return startNode;
    }

    public Node FinishNode(){
        return this.finishNode;
    }

    //Constructors
    Maze(int rows, int columns){
        //Initialize
        this.rows = rows;
        this.columns = columns;

        nodes = new Node[rows][columns];
        for(int r = 0;r < rows;r++){
            for(int c = 0;c < columns;c++){
                nodes[r][c] = new Node(new Coordinate(r, c));
            }
        }
    }

    public Coordinate northOf(Coordinate p) {
        return(new Coordinate(p.getRow() - 1, p.getCol()));
    }

    public Coordinate eastOf(Coordinate p) {
        return(new Coordinate(p.getRow(), p.getCol() + 1));
    }

    public Coordinate southOf(Coordinate p) {
        return(new Coordinate(p.getRow() + 1, p.getCol()));
    }

    public Coordinate westOf(Coordinate p) {
        return(new Coordinate(p.getRow(), p.getCol() - 1));
    }


    //Generate the maze
    public void GenerateMaze(){
        out.println("Start Generation");
        //Generate the start node
        startNode = this.squareAt(new Coordinate(new Random().nextInt(this.rows) , 0));
        startNode.getWall(Direction.WEST).Toggle();
        //Generate the finish node
        finishNode = this.squareAt(new Coordinate(new Random().nextInt(this.rows) , this.columns - 1));
        finishNode.getWall(Direction.EAST).Toggle();

        //Show the viewer
        MazeViewer mazeViewer = new MazeViewer(this, rows, columns);
        mazeViewer.showMaze();

        //Start the stack generation of the maze
        Stack<Node> newStack = new Stack<Node>();
        newStack.push(StartNode());
        newStack.getTop().visit();


        while(newStack.getCount() > 0) {

            Coordinate coord = null;

            ArrayList<Node> neighbors = this.GetUnvisitedNeighbors(newStack.getTop());

            if(neighbors.size() > 0){
                Random rand = new Random();
                Node neighbor = neighbors.get(rand.nextInt(neighbors.size()));
                RemoveWall(newStack.getTop(), neighbor);


                newStack.push(neighbor);
                newStack.getTop().visit();
            }else{
                newStack.getTop().abandon();
                Node stackNode = newStack.pop();
            }

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            }catch(Exception e){
                //Do nothing
            }

            UpdateAll(mazeViewer);
        }



        //Once the maze is generated, we need to set everything to not visited and not abandoned
        for(int r = 0;r > rows;r++){
            for(int c = 0;c > columns;c++){
                squareAt(new Coordinate(r, c)).clearAbandon();
                squareAt(new Coordinate(r, c)).clearVisit();

                out.println("Row: " + r);
    //            System.out.println(squareAt(new Coordinate(r, c)).toString());
            }
        }

        out.println("Generation done: " + columns);

        UpdateAll(mazeViewer);
    }

    public void UpdateAll(MazeViewer mazeViewer){
        for(int r = 0;r < rows;r++){
            for(int c = 0;c < columns;c++){
                mazeViewer.update(new Coordinate(r, c));
            }
        }
    }

    public void RemoveWall(Node nodeA, Node nodeB){

        //Check where we are for the walls
        if(northOf(nodeA.getPosition()).isEqual(nodeB.getPosition())){
            nodeA.getWall(Direction.NORTH).Toggle();
            nodeB.getWall(Direction.SOUTH).Toggle();

        }
        else if(southOf(nodeA.getPosition()).isEqual(nodeB.getPosition())){
            nodeA.getWall(Direction.SOUTH).Toggle();
            nodeB.getWall(Direction.NORTH).Toggle();

        }
        else if(eastOf(nodeA.getPosition()).isEqual(nodeB.getPosition())){
            nodeA.getWall(Direction.EAST).Toggle();
            nodeB.getWall(Direction.WEST).Toggle();
            return;
        }
        else if(westOf(nodeA.getPosition()).isEqual(nodeB.getPosition())){
            nodeA.getWall(Direction.WEST).Toggle();
            nodeB.getWall(Direction.EAST).Toggle();
            return;
        }

    }

    public ArrayList<Node> GetUnvisitedNeighbors(Node node){
        //Create the new array
        ArrayList<Node> neighbors = new ArrayList<Node>();

        if(this.MovePossible(node.getPosition(), northOf(node.getPosition()))) {
            neighbors.add(this.squareAt(northOf(node.getPosition())));
        }
        if(this.MovePossible(node.getPosition(), southOf(node.getPosition()))) {
            neighbors.add(this.squareAt(southOf(node.getPosition())));
        }
        if(this.MovePossible(node.getPosition(), eastOf(node.getPosition()))) {
            neighbors.add(this.squareAt(eastOf(node.getPosition())));
        }
        if(this.MovePossible(node.getPosition(), westOf(node.getPosition()))) {
            neighbors.add(this.squareAt(westOf(node.getPosition())));
        }

        return neighbors;
    }

    public boolean Contains(Coordinate[] haystack, Coordinate needle){
        for(Coordinate myCoord : haystack){
            if(myCoord.isEqual(needle))
                return true;
        }

        return false;
    }

    public boolean MovePossible(Coordinate from, Coordinate to){
        //Check if the coordinate is within the maze
        if(to.getCol() < 0 || to.getCol() >= this.columns ||
                to.getRow() < 0 || to.getRow() >= this.rows){

            return false;
        }

        //Check if the coordinate is unvisisted
        if(squareAt(to).isVisited()) {
            return false;
        }

        //Check if the coordinate is adjacent
        for(int r = from.getRow() - 1;r < from.getRow() + 2;r++){
            for(int c= from.getCol() - 1;c < from.getCol() + 2;c++){
                //Return true if the coordinates are equal
                if(to.isEqual(new Coordinate(r, c))) {

                    return true;
                }
            }
        }

        //The move is not possible
        return false;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        // output the top
        for (int i = 0; i < columns; i++)
            buf.append("__");
        buf.append("_\n");

        // output the rows
        for (int i = 0; i < rows; i++) {
            if (i != StartNode().getPosition().getRow()) {
                buf.append("|");
            } else {
                buf.append(" ");
            }

            for (int j = 0; j < columns; j++) {
                if (nodes[i][j].getWall(Direction.SOUTH).Exists()) {
                    buf.append("_");
                } else {
                    buf.append(" ");
                }

                if (nodes[i][j].getWall(Direction.EAST).Exists()) {
                    buf.append("|");
                } else {
                    if (j + 1 < columns) {
                        if (nodes[i][j + 1].getWall(Direction.SOUTH).Exists()) {
                            buf.append("_");
                        } else {
                            buf.append(".");
                        }
                    }
                }
            }
            buf.append("\n");
        }
        return buf.toString();
    }
}
