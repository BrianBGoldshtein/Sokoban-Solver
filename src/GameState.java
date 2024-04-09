import java.util.ArrayList;
import java.util.Arrays;

public class GameState extends Map {
    private char[][] playerBoard;

    private Location playerLoc;
    private GameState previousState;
    private Direction lastDirection;

    public GameState(char[][] board) {
        super(board);
        playerBoard = initiatePlayerBoard(board);
    }

    public boolean equals(Object o) {
        GameState other = (GameState) o;

        for (int row = 0; row < playerBoard.length; row++) {
            for (int col = 0; col < playerBoard[row].length; col++) {
                if (this.getPlayerBoard()[row][col] == other.getPlayerBoard()[row][col]) continue;
                return false;
            }
        }
        return true;
    }

    public Direction lastMove() {
        return lastDirection;
    }

    public Location setPlayerLoc(Location loc) {
        playerBoard[loc.getRow()][loc.getCol()] = ' ';
        this.playerLoc = loc.clone();
        playerBoard[loc.getRow()][loc.getCol()] = '@';
        return this.playerLoc;
    }

    public Location getPlayerLoc() {
        return playerLoc;
    }

    public GameState(GameState last, Direction lastDirection) {
        super(last);
        previousState = last;
        this.lastDirection = lastDirection;
        playerBoard = initiatePlayerBoard(last.getPlayerBoard().clone());
        playerLoc = last.getPlayerLoc();
    }

    @Override
    public String toString() {
        String out = "";

        char[][] displayBoard = generateDisplay();

        for (char[] row : displayBoard) {
            for (char loc : row) {
                out += loc;
            }
            out += "\n";
        }
        return out;
    }

    private char[][] generateDisplay() {
        char[][] out = new char[playerBoard.length][];
        for (int i = 0; i < playerBoard.length; i++) {
            out[i] = Arrays.copyOf(playerBoard[i], playerBoard[i].length);
        }

        for (Location loc : goals) {
            out[loc.getRow()][loc.getCol()] = '.';
        }

        for (int row = 0; row < out.length; row++) {
            for (int col = 0; col < out[row].length; col++) {

                if (out[row][col] == '.') {

                    if (getPlayerBoard()[row][col] == '*') out[row][col] = '&';
                    else if (getPlayerBoard()[row][col] == '@') out[row][col] = '@';
                }
            }
        }
        return out;
    }

    public int getStateID() {
        if (previousState == null) return 0;

        return previousState.getStateID() + 1;
    }

    public GameState getParent() {
        return previousState;
    }

    private char[][] initiatePlayerBoard(char[][] in) {
        char[][] out = new char[in.length][];
        for (int row = 0; row < in.length; row++) {
            out[row] = new char[in[row].length];
            for (int col = 0; col < in[row].length; col++) {
                out[row][col] = in[row][col];
                if (in[row][col] == '.') out[row][col] = ' ';
                if (in[row][col] == '&') out[row][col] = '*';
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
        for (int row = 0; row < getPlayerBoard().length; row++) {
            for (int col = 0; col < getPlayerBoard()[row].length; col++) {
                if (playerBoard[row][col] == '*') out.add(new Location(row, col));
            }
        }
        return out;
    }

    public boolean isSolved() {
        ArrayList<Location> barrels = findAllBarrels();

        nextBarrel:
        for (Location barrel : barrels) {
            for (Location goal : getGoals()) {
                if (barrel.equals(goal)) continue nextBarrel;
            }
            return false;
        }
        return true;
    }

    public void setPlayerBoard(Location loc, char val) {
        playerBoard[loc.getRow()][loc.getCol()] = val;
    }

    public GameState getNextState(Location tempLoc, Direction direction) {
        Location targetLoc = tempLoc.getNewLoc(direction);
        char target = this.getPlayerBoard(targetLoc);

        //if there's a barrel in front, run move again in front of the barrel
        if (target == '*') return getNextState(targetLoc, direction);
        //if there's a wall in front, we've failed.
        if (target == 'X') return null;

        Location loc = this.getPlayerLoc();

        GameState out = new GameState(this, direction);

        //if there's a clear space in front of player, just move forward.
        if (out.getPlayerBoard(loc.getNewLoc(direction)) == ' ') {

            out.setPlayerBoard(loc, ' ');
            loc = out.setPlayerLoc(loc.getNewLoc(direction));
            out.setPlayerBoard(loc, '@');
            return out;
        }

        //if the space in front of the barrel is open, move barrel to end and move player forward.
        if (target == ' ') {
            out.setPlayerBoard(targetLoc, '*');
            out.setPlayerBoard(this.getPlayerLoc(), ' ');

            loc = out.setPlayerLoc(loc.getNewLoc(direction));
            out.setPlayerBoard(loc, '@');
            return out;
        }
        System.out.println(target);
        System.out.println("Move failed - no case found");
        return null;

    }

    public GameState getNextState(Direction direction) {
        return getNextState(getPlayerLoc(), direction);
    }

    public boolean isImpossibleState() {
        ArrayList<Location> barrels = findAllBarrels();
        boolean horizontalSides = false;
        boolean verticalSides = false;
        for (Location barrel : barrels) {
            if (getPlayerBoard(barrel.getNewLoc(Direction.UP)) == 'X' || getPlayerBoard(barrel.getNewLoc(Direction.DOWN)) == 'X')
                horizontalSides = true;
            if (getPlayerBoard(barrel.getNewLoc(Direction.LEFT)) == 'X' || getPlayerBoard(barrel.getNewLoc(Direction.RIGHT)) == 'X')
                verticalSides = true;

            if(horizontalSides && verticalSides) return true;
            horizontalSides = false;
            verticalSides = false;
        }
        return false;

    }

    public ArrayList<GameState> getNextStates() {
        ArrayList<GameState> out = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            GameState state = this.getNextState(direction);
            if (state == null) continue;
            out.add(state);
        }
        return out;
    }

}
