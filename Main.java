import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scr = new Scanner(System.in);
        Player p;
        do {
            System.out.println("Enter playername: ");
            p = LoadSaveController.loadPlayer(scr.nextLine());
        } while (p == null);
        Map m = LoadSaveController.loadMap(0);
        playGame(new PlayController(p,m));
        LoadSaveController.savePlayer(p);
        LoadSaveController.saveMap(m);
    }
    public static void playGame(PlayController pc){
        //interact with user and call pc to do stuff

    }

}
