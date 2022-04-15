import java.io.FileNotFoundException;

public class LoadSaveController {
    public static Player loadPlayer(String id) {
        //request player data from DAO
        return DataAccess.getPlayer(id);
    }
    public static Map loadMap(String id){
        //request map data from DAO
        return DataAccess.getMap(id);
    }
    public static void savePlayer(Player p){
        //request DAO to save player data
        DataAccess.savePlayer(p);
    }
    public static void saveMap(Map m){
        //request map data from DAO
        DataAccess.saveMap(m);
    }

}
