import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        Player p;
        do {
            System.out.println("Enter playername: ");
            p = loadPlayer(scr.nextLine());
        } while (p == null);
        Map m = loadMap(0);
        playGame(p, m);
    }
    public static void playGame(Player p, Map m){


        save(p);
    }
    public static void save(Player p){
        //request DAO to save player data
        DataAccess.savePlayer(p);
    }
    public static Player loadPlayer(String id){
        //request player data from DAO
        return DataAccess.getPlayer(id);
    }
    public static Map loadMap(int id){
        //request map data from DAO
        return DataAccess.getMap(id);
    }
}
