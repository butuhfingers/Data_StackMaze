/**
 * Created by derek on 9/17/16.
 */
public class Coordinate implements iCoordinate {
    //Variables
    private int row = 0;
    private int column = 0;

    //Constructor
    public Coordinate(int row, int column) {
        setCol(column);
        setRow(row);
    }

    //Properties
    //Set our Y position
    private void setCol(int column) {
        this.column = column;
    }
    //Set our row position
    private void setRow(int row) {
        this.row = row;
    }

    //Return our column position
    public int getCol() {
        return this.column;
    }
    //Return our row position
    public int getRow() {
        return this.row;
    }

    @Override
    public String toString() {
        return "[Row:" + getRow() + ",Column:" + getCol() + "]";
    }

    public boolean isEqual(Coordinate p){
        if(this.row == p.getRow() && this.column == p.getCol())
            return true;

        return false;
    }
}
