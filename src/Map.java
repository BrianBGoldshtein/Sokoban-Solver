import java.util.ArrayList;
import java.util.Arrays;

public class Map {
    //'&' the barrel already in place
    //'.' the place for a barrel
    //'*' the barrel
    //'X' the wall
    //' ' an empty space
    protected char[][] board;
    protected ArrayList<Location> goals;
    public Map(char[][] board) {
        this.board = board;
        findGoals();
    }

    public Map(Map map) {
        board = map.getBoard();
        goals = map.getGoals();
    }

    public ArrayList<Location> getGoals() {
        return goals;
    }

    private void findGoals() {
        ArrayList<Location> goals = new ArrayList<>();

        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                if(board[row][col] == '.' || board[row][col] == '&') {
                    goals.add(new Location(row,col));
                }
            }
        }
        this.goals = goals;
    }


    char[][] getBoard() {
        return board;
    }
    public char get(int row, int col) {
        return board[row][col];
    }

    public char get(Location loc) {
        return board[loc.getRow()][loc.getCol()];
    }

    public String toString() {
        String out = "";

        for(char[] row: board) {
            for(char loc: row) {
                out+= loc;
            }
            out+="\n";
        }
        return out;
    }

    public Location findPlayer() {
        System.out.println(this);
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                if(board[row][col] == '@') {
                    System.out.println("Player located at " + row+ ", " + col);
                    return new Location(row,col);
                }
            }
        }
        return null;
    }
}
