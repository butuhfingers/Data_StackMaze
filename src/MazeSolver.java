import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by aaron on 9/20/16.
 */
public class MazeSolver {
    public static MazeViewer myWindow;

    public static void findPath(Maze theMaze, MazeViewer mazeViewer) {
        //Start the stack generation of the maze
        Stack<Node> newStack = new Stack<Node>();
        newStack.push(theMaze.StartNode());
        newStack.getTop().visit();


        while(newStack.getCount() > 0 && !newStack.getTop().getPosition().isEqual(theMaze.FinishNode().getPosition())) {

            Coordinate coord = null;

            ArrayList<Node> neighbors = getPossibleMoves(theMaze, newStack.getTop());

            if(neighbors.size() > 0){
                Random rand = new Random();
                Node neighbor = neighbors.get(rand.nextInt(neighbors.size()));

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

            theMaze.UpdateAll(mazeViewer);
        }
    }

    private static ArrayList<Node> getPossibleMoves(Maze theMaze, Node node) {
        ArrayList<Node> nodes = new ArrayList<Node>();


        return nodes;
    }

    public static void main(String[] args) {
        Scanner fromUser = new Scanner(System.in);
        System.out.print("Number of rows? ");
        int ROWS = fromUser.nextInt();
        System.out.print("Number of cols? ");
        int COLS = fromUser.nextInt();

        for (int i = 0; i < 5; i++) {
            Maze aMaze = new Maze(ROWS, COLS);
            aMaze.GenerateMaze();
            myWindow = new MazeViewer(aMaze, ROWS, COLS);
            myWindow.showMaze();
  //          findPath(aMaze, myWindow);
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
            }
            myWindow.destroyMaze();
        }
    }
}