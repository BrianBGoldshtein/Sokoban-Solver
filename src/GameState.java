import java.util.ArrayList;
import java.util.Arrays;

public class GameState extends Map{
    private char[][] playerBoard;
    private GameState previousState;
    public GameState(char[][] board) {
        super(board);
        playerBoard = initiatePlayerBoard(board.clone());
    }
    public GameState(GameState last) {
        super(last);
        previousState = last;
        playerBoard = initiatePlayerBoard(last.getPlayerBoard().clone());
    }

    @Override
    public String toString() {
        String out = "";

        char[][] displayBoard = generateDisplay();

        for(char[] row: displayBoard) {
            for(char loc: row) {
                out+= loc;
            }
            out+="\n";
        }
        return out;
    }

    private char[][] generateDisplay() {
        char[][] out = new char[board.length][];
        for(int i = 0; i < board.length; i++) {
            out[i] = Arrays.copyOf(board[i], board[i].length);
        }

        for(Location loc: goals) {
            out[loc.getRow()][loc.getCol()] = '.';
        }

        for(int row = 0; row < out.length; row++) {
            for(int col = 0; col < out[row].length; col++) {

                if(out[row][col] == '.') {

                    if(getPlayerBoard()[row][col] == '*') out[row][col] = '&';
                    else if(getPlayerBoard()[row][col] == '@') out[row][col] = '@';
                }
            }
        }
        return out;
    }

    public int getStateID() {
        if(previousState == null) return 0;

        return previousState.getStateID()+1;
    }

    public GameState getParent() {
        return previousState;
    }
    private char[][] initiatePlayerBoard(char[][] in) {
        char[][] out = in.clone();
        for(int row = 0; row < in.length; row++) {
            for(int col = 0; col < in[row].length; col++) {
                if(in[row][col] == '.') out[row][col] = ' ';
                if(in[row][col] == '&') out[row][col] = '*';
            }
        }
        return out;
    }

    public char getPlayerBoard(Location loc) {
        return playerBoard[loc.getRow()][loc.getCol()];
    }

    public char[][] getPlayerBoard() {
        return playerBoard;
    }



    public ArrayList<Location> findAllBarrels() {
        ArrayList<Location> out = new ArrayList<>();
        for(int row = 0; row < getPlayerBoard().length; row++) {
            for(int col = 0; col < getPlayerBoard()[row].length; col++) {
                if(playerBoard[row][col] == '*') out.add(new Location(row,col));
            }
        }
        return out;
    }

    public boolean isSolved() {
        ArrayList<Location> barrels = findAllBarrels();

        nextBarrel: for(Location barrel: barrels) {
            for(Location goal: getGoals()) {
                if(barrel.equals(goal)) continue nextBarrel;
            }
            return false;
        }
        return true;
    }

    public void setPlayerBoard(Location loc, char val) {
        playerBoard[loc.getRow()][loc.getCol()] = val;
    }
}
