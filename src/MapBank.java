import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MapBank {
    //'&' the barrel already in place
    //'.' the place for a barrel
    //'*' the barrel
    //'X' the wall
    //' ' an empty space

    private Map[] maps;

    public MapBank() throws FileNotFoundException {
        maps = new Map[60];
        loadMaps();
    }

    public Map get(int id) {
        return maps[id];
    }

    private void loadMaps() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("sokoban-maps-60-plain.txt"));
        int line = 1;
        String text;
        do {
            text = scanner.nextLine();
            if(text.equals("*************************************")) {
                parseMap(line);
                }
            line++;
            } while(!text.equals("END"));

        }



    private void parseMap(int start) throws FileNotFoundException {
        //initialize to current loc
        Scanner scanner = new Scanner(new File("sokoban-maps-60-plain.txt"));
        int line = 1;
        while(line != start+1) {
            scanner.nextLine();
            line++;
        }
        //set IDs and Sizes
        String stringID = scanner.nextLine();
        int id = Integer.parseInt(stringID.substring(6))-1;
        scanner.nextLine();
        int sizeX = Integer.parseInt(scanner.nextLine().substring(8));
        int sizeY = Integer.parseInt(scanner.nextLine().substring(8));

        //initialize board
        scanner.nextLine(); scanner.nextLine(); scanner.nextLine();

        char[][] board = new char[sizeY][sizeX];
        line = 0;

        //fill in board
        do {
            String txt = scanner.nextLine();
            board[line] = txt.toCharArray();
            line++;
        } while (line < sizeY);

        //create map object
        maps[id] = new Map(board);
    }
}
