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
                    p = new Player();
                    p.setName(s); //TODO set default player stats
                    m = LoadSaveController.loadMap(0);
                    playGame(new PlayController(p, m));
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
                    m = LoadSaveController.loadMap(0);
                    playGame(new PlayController(p, m));
                    save(p, m);
                }
                case 3 -> //quit
                        System.exit(0);
            }
        }
    }
    public static void playGame(PlayController pc){
        //interact with user and call pc to do stuff

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
