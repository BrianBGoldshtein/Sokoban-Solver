import processing.core.PApplet;

public class Player extends PApplet{

    private GameState game;
    public Player(Map map) {
        this.game = new GameState(map.getBoard());

        game.setPlayerLoc(game.findPlayer());
    }


    public GameState getGameState() {
        return game;
    }

    public boolean move(Direction direction) {
        GameState next = game.getNextState(direction);

        return useState(next);
    }


    public boolean useState(GameState next) {
        if(next == null) return false;
        game = next;
        return true;
    }

}
