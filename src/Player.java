import processing.core.PApplet;

public class Player extends PApplet{

    private GameState game;
    private Location loc;
    public Player(Map map) {
        this.game = new GameState(map.getBoard());

        loc = game.findPlayer();
    }

    public Location getLoc() {
        return loc;
    }

    public GameState getGameState() {
        return game;
    }

    public boolean move(Direction direction) {
        return move(loc, direction);
    }
    public boolean move(Location tempLoc, Direction direction) {
        Location targetLoc = tempLoc.getNewLoc(direction);
        char target = game.getPlayerBoard(targetLoc);

        //if there's a barrel in front, run move again in front of the barrel
        if(target == '*') return move(targetLoc, direction);
        //if there's a wall in front, we've failed.
        if(target == 'X') return false;

        game = new GameState(game);

        //if there's a clear space in front of player, just move forward.
        if(game.getPlayerBoard(loc.getNewLoc(direction)) == ' ') {

            game.setPlayerBoard(loc, ' ');
            loc = loc.getNewLoc(direction);
            game.setPlayerBoard(loc, '@');
            return true;
        }

        //if the space in front of the barrel is open, move barrel to end and move player forward.
        if(target == ' ') {
            game.setPlayerBoard(targetLoc, '*');
            game.setPlayerBoard(loc, ' ');
            loc = loc.getNewLoc(direction);
            game.setPlayerBoard(loc, '@');
            return true;
        }
        System.out.println(target);
        System.out.println("Move failed - no case found");
        return false;

    }


}
