import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataAccess {

    /**
     * gets an item from itemData file
     * @param id item id
     * @return item
     */
    public static Item getItem(int id) {
        //return null if not found
        //TODO
        return null;
    }

    /**
     * gets a map from mapData file
     * @param id id of map
     * @return map
     */
    public static Map getMap(int id) {
        //return null if not found
        //TODO
        return null;
    }

    /**
     * Gets a player from the playerData file
     *
     * @param id username of user
     * @return player object
     * @throws FileNotFoundException if filenotfound
     */
    public static Player getPlayer(String id) throws FileNotFoundException {
        //return null if not found
        Scanner scr = new Scanner(new File("playerData.txt"));
        String username = scr.nextLine();
        //find the correct player
        while (id.compareTo(username) != 0) {
            for(int i = 0; i < 5; i++) //skip one player
                scr.nextLine();
            if (!scr.hasNext())
                return null;
            username = scr.nextLine();
        }

        //now get all the info on the player

        // level, will use regex for more variables
        Stats stats = new Stats();
        String[] s = scr.nextLine().split("/");
        stats.setLevel(Integer.parseInt(s[0]));
        stats.setCurrHP(Integer.parseInt(s[1]));
        stats.setMaxHP(Integer.parseInt(s[2]));
        stats.setCurrMana(Integer.parseInt(s[3]));
        stats.setMaxMana(Integer.parseInt(s[4]));

        // Position
        s = scr.nextLine().split("/");
        Position pos = new Position(Integer.parseInt(s[0]),Integer.parseInt(s[1]));

        // Armor ID
        int armorID = Integer.parseInt(scr.nextLine());
        // Weapon ID
        int weaponID = Integer.parseInt(scr.nextLine());

        // Inventory
        String[] InventoryArray = scr.nextLine().split("/");
        Inventory inventory = new Inventory();
        for(String str : InventoryArray)
            inventory.add(getItem(Integer.parseInt(str)));

        //set player info and return
        Player p = new Player();
        p.setName(username);
        p.setStats(stats);
        p.setPosition(pos);
        p.setArmor(getItem(armorID));
        p.setWeapon(getItem(weaponID));
        p.setInventory(inventory);
        return p;
    }

    /**
     * find an entity from entityData
     * @param id id of entity
     * @return entity
     */
    public static Character getEntity(int id) {
        //return null if not found
        //TODO
        return null;
    }

    /**
     * save all player data to file
     * @param p player to save
     */
    public static void savePlayer(Player p) {
        //save all player data
        //TODO find the correct location for this player and overwrite their data
    }

    /**
     * saves all map data to file
     * @param m map to save
     */
    public static void saveMap(Map m) {
        //save all map data
        //TODO find correct location to write this into mapData.txt
        for(int r = 0; r < m.getMap().length; r++) {
            for (int c = 0; c < m.getMap()[0].length; c++) {
                System.out.print(m.getLocation(c, r).toData());
                System.out.print("\n");
            }
        }
    }
}
