import processing.core.PApplet;

import java.io.FileNotFoundException;

public class Display extends PApplet {
    public Game game;
    public void settings(){
        size(600,600);
    }
    public void setup(){
        game = new Game(0);
    }
    public void draw(){
        //TODO: Display barrel goal locations from GAME, barrels and Player from GAMESTATE
    }

    public void keyReleased() {
        game.keyReleased(keyCode);
        if (game.player.getGameState().isSolved()) exit();

    }

    public static void main(String[] args) {
        PApplet.main("Display");
    }
}
