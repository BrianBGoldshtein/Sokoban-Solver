import java.util.ArrayList;

public class Solver {
    private Game game;
    private static int counter;
    public Solver(int id) {
        game = new Game(id);
    }

    public GameState solveDepthFirst() {
        counter = 0;
        return solveDepthFirst(game.player.getGameState(), new ArrayList<GameState>(), 300);
    }

    public GameState solveBreadthFirst() {
        return solveBreadthFirst(game.player.getGameState());
    }


    public ArrayList<Direction> movesToState(GameState goal) {

        if(goal.getParent() == null) {
            System.out.println(goal);
            return new ArrayList<Direction>();
        }


        ArrayList<Direction> out = movesToState(goal.getParent());
        out.add(goal.lastMove());
        System.out.println(goal);
        return out;
    }

    public GameState solveBreadthFirst(GameState state) {
        ArrayList<GameState> checked = new ArrayList<>();
        ArrayList<GameState> toVisit = new ArrayList<>();
        toVisit.add(state);

        while(toVisit.size() != 0) {
            GameState current = toVisit.remove(0);
            System.out.println(current);

            if(current.isSolved()) return current;

            checked.add(current);

            ArrayList<GameState> nextStates = current.getNextStates();
            for(GameState next: nextStates) {
                if(!checked.contains(next) && !toVisit.contains(next) && !next.isImpossibleState()) {
                    toVisit.add(next);
                }
            }
        }

        return null;
    }

    public GameState solveDepthFirst(GameState state, ArrayList<GameState> alreadyChecked, int limit) {
        ArrayList<GameState> nextMoves = state.getNextStates();
        //System.out.println(state);



        if(state.getStateID() > limit) return null;


        for(GameState s: nextMoves) {
            counter++;
            System.out.println(counter);
            if(s.isSolved()) {
                System.out.println("Solved!");
                return s;
            }
            if(s.isImpossibleState()) continue;
            //if(alreadyChecked.contains(s)) continue;
            alreadyChecked.add(state);

            GameState nextStep = solveDepthFirst(s, alreadyChecked, limit);
            if(nextStep != null) return nextStep;
        }

        return null;
    }
}
