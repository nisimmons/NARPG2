import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scr = new Scanner(System.in);
        String s;
        Player p = null;
        Map m;
        while(true) {
            switch (menuScreen(scr)) {
                case 1 -> {
                    //new game
                    System.out.println("Enter playername: ");
                    s = scr.nextLine();
                    p = PlayController.createRandomPlayer(s);
                    m = PlayController.createRandomMap(50);
                    //m = LoadSaveController.loadMap(0);
                    playGame(new PlayController(p, m), scr);
                    save(p, m);
                }
                case 2 -> {
                    //load game and play
                    do {
                        System.out.println("Enter playername (or * to return): ");
                        s = scr.nextLine();
                        if (s.compareTo("*") == 0)
                            break;
                        p = LoadSaveController.loadPlayer(s);
                    } while (p == null);
                    //m = LoadSaveController.loadMap(0);
                    m = PlayController.createRandomMap(50);
                    playGame(new PlayController(p, m), scr);
                    save(p, m);
                }
                case 3 -> //quit
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
                    if (pc.move(moveScreen(scr))) //try to move, print location or error message
                        System.out.println(pc.getMap().getLocation(pc.getPlayer().getPosition()));
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
        return switch (i) {
            case 1 -> Direction.NORTH;
            case 2 -> Direction.EAST;
            case 3 -> Direction.SOUTH;
            default -> Direction.WEST;
        };
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
