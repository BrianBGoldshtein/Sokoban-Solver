import processing.core.PApplet;

import java.io.FileNotFoundException;

public class Game extends PApplet {
    public Player player;
    public static MapBank mapBank;

    static {
        try {
            mapBank = new MapBank();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Game(int mapID) {
        player = new Player(mapBank.get(mapID));
    }

    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case UP:
                player.move(Direction.UP);
                break;

            case DOWN:
                player.move(Direction.DOWN);
                break;

            case LEFT:
                player.move(Direction.LEFT);
                break;

            case RIGHT:
                player.move(Direction.RIGHT);
                break;
        }
//        System.out.println("GameState ID: " + player.getGameState().getStateID() + "-  Player Loc: " + player.getGameState().getPlayerLoc());
//        System.out.println(player.getGameState());
    }
}
