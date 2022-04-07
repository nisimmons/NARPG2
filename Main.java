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
                case 1:
                    System.out.println("Enter playername: ");
                    s = scr.nextLine();
                    //create player and map details
                    p = PlayController.createRandomPlayer(s);
                    m = PlayController.createRandomMap(50);
                    //play the game
                    playGame(new PlayController(p, m), scr);
                    //save the game
                    save(p, m);
                    break;
                case 2:
                    do {
                        //load game and play
                        //find and load the player
                        System.out.println("Enter playername (or * to return): ");
                        s = scr.nextLine();
                        if (s.compareTo("*") == 0)
                            break;
                        p = LoadSaveController.loadPlayer(s);
                    } while (p == null);
                    //load the map
                    //m = LoadSaveController.loadMap(0); //TODO implement proper map loading
                    m = PlayController.createRandomMap(50);
                    //play the game
                    playGame(new PlayController(p, m), scr);
                    //save the game
                    save(p, m);
                    break;
                case 3:
                    //quit
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
                        Location loc = pc.getMap().getLocation(pc.getPlayer().getPosition());
                        System.out.println(loc);
                        loc.setRevealed(true);
                        if (loc instanceof Wilderness){
                            //check for and print enemies
                            if (((Wilderness) loc).getEnemies() != null)
                                for (Enemy e: ((Wilderness) loc).getEnemies())
                                    System.out.println(e);
                        }
                    }
                    else
                        System.out.println("Area Impassable");
                    break;
                case 2:
                    //investigate area
                    System.out.println(pc.getMap().getLocation(pc.getPlayer().getPosition()));
                    break;
                case 3:
                    //print player info
                    System.out.println(pc.getPlayer());
                    break;
                case 4:
                    System.out.println(pc.getMap());
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
        } while (i < 1 || i > 4);
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
            System.out.println("1. Move\n2. Investigate Area\n3. Player info\n4. Print map\n5. Save + quit");
            try {
                i = Integer.parseInt(scr.nextLine());
                if (i < 1 || i > 5)
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
