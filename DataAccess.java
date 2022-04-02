import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataAccess {

    public static Item getItem(int id) {
        //return null if not found
        return null;
    }

    public static Map getMap(int id) {
        //return null if not found
        return null;
    }

    public static Player getPlayer(String id) throws FileNotFoundException {
        //return null if not found
        Scanner scr = new Scanner(new File("playerData.txt"));
        String s = scr.nextLine();
        //find the player's name, enter all their info into stats and a player and return that


        return null;
    }

    public static Character getEntity(int id) {
        //return null if not found
        return null;
    }
    public static void savePlayer(Player p) {
        //save all player data
    }
    public static void saveMap(Map m) {
        //save all map data

    }
}
