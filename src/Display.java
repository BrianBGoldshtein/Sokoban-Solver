import processing.core.PApplet;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Display extends PApplet {
    public Game game;
    public void settings(){
        size(600,600);
    }
    public void setup(){
        game = new Game(0);
        System.out.println("Map Loaded:\n" + game.player.getGameState());
    }
    public void draw(){
        //TODO: Display barrel goal locations from GAME, barrels and Player from GAMESTATE
    }

    public void keyReleased() {
        game.keyReleased(keyCode);
        if (game.player.getGameState().isSolved()) exit();

    }

    public static void main(String[] args) {

        //PApplet.main("Display");
        Solver solver = new Solver(0);
        GameState solved = solver.solveBreadthFirst();
        System.out.println(solved);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "Steps to Solve:");
        System.out.println(Arrays.toString(solver.movesToState(solved).toArray()));
    }
}
