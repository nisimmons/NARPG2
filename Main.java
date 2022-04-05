import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scr = new Scanner(System.in);
        String s;
        Player p;
        Map m;
        while(true) {
            p = null;
            switch (menuScreen(scr)) {//new game
//create player and map details
//play the game
//save the game
                case 1:
                    System.out.println("Enter playername: ");
                    s = scr.nextLine();
                    p = PlayController.createRandomPlayer(s);
                    m = PlayController.createRandomMap(50);
                    playGame(new PlayController(p, m), scr);
                    save(p, m);
                    break;
//load game and play
//load the map
//m = LoadSaveController.loadMap(0); //TODO implement proper map loading
//play the game
//save the game
                case 2:
                    do {
                        //find and load the player
                        System.out.println("Enter playername (or * to return): ");
                        s = scr.nextLine();
                        if (s.compareTo("*") == 0)
                            break;
                        p = LoadSaveController.loadPlayer(s);
                    } while (p == null);
                    m = PlayController.createRandomMap(50);
                    playGame(new PlayController(p, m), scr);
                    save(p, m);
                    break;
//quit
                case 3:
                    System.exit(0);
            }
        }
    }
    public static void playGame(PlayController pc, Scanner scr){
        //interact with user and call pc to do stuff
        while(true) {
            switch (turnScreen(scr)) {
                case 1:
                    //move
                    if (pc.move(moveScreen(scr))) {//try to move, print location or error message
                        System.out.println(pc.getMap().getLocation(pc.getPlayer().getPosition()));
                        //TODO interact with the new area
                    }
                    else
                        System.out.println("Area Impassable");
                    break;
                case 2:
                    //investigate area
                    System.out.println(pc.getMap().getLocation(pc.getPlayer().getPosition()));
                    break;
                case 3:
                    System.out.println(pc.getPlayer());
                    break;
                default:
                    return;

            }
        }
    }

    public static Direction moveScreen(Scanner scr){
        int i = 0;
        do {
            System.out.println("1. North\n2. East\n3. South\n4. West");
            try {
                i = Integer.parseInt(scr.nextLine());
            }
            catch(Exception ignored){}
        } while (i < 1 || i > 3);
        switch (i) {
            case 1:
                return Direction.NORTH;
            case 2:
                return Direction.EAST;
            case 3:
                return Direction.SOUTH;
            default:
                return Direction.WEST;
        }
    }
    public static int turnScreen(Scanner scr){
        int i;
        while(true) {
            System.out.println("1. Move\n2. Investigate Area\n3. Player info\n4. Save + quit");
            try {
                i = Integer.parseInt(scr.nextLine());
                if (i < 1 || i > 4)
                    throw new Exception();
                else
                    break;
            }
            catch(Exception ignored){}
        }
        return i;
    }
    public static int menuScreen(Scanner scr){
        int i = 0;
        do {
            System.out.println("1. New game\n2. Load save\n3. Quit");

            try {
                i = Integer.parseInt(scr.nextLine());
            }
            catch(Exception ignored){}
        } while (i < 1 || i > 3);
        return i;
    }
    public static void save(Player p, Map m){
        LoadSaveController.savePlayer(p);
        LoadSaveController.saveMap(m);
    }
}
