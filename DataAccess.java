import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
     */
    public static Player getPlayer(String id) {
        //return null if not found
        Scanner scr = null;
        try {
            scr = new Scanner(new File("playerData.txt"));
        } catch (FileNotFoundException e) {
            return null;
        }
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
        String[] s = scr.nextLine().split("/");
        Stats stats = new Stats(s);

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
     * find an enemy from enemyData
     * @param id id of enemy
     * @return enemy or null if not found
     */
    public static Enemy getEnemy(int id) {
        Scanner scr = null;
        try {
            scr = new Scanner(new File("enemyData.txt"));
        } catch (FileNotFoundException e) {
            return null;
        }
        int i = Integer.parseInt(scr.nextLine());
        //find the correct player
        while (id != i) {
            for (int j = 0; j < 4; j++) //skip one player
                scr.nextLine();
            if (!scr.hasNext())
                return null;
            i = Integer.parseInt(scr.nextLine());
        }
        String name = scr.nextLine();
        Stats stats = new Stats(scr.nextLine().split("/"));
        Armor armor = (Armor) getItem(Integer.parseInt(scr.nextLine()));
        Weapon wep = (Weapon) getItem(Integer.parseInt(scr.nextLine()));
        return new Enemy(name,stats,armor,wep);
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
        /*
        Scanner scr = null;
        try {
            scr = new Scanner(new File("mapData.txt"));
        } catch (FileNotFoundException e) {
        }
        String s = scr.nextLine();
        while (m.getId() != Integer.parseInt(s))
            s = scr.nextLine();
        */
        FileWriter out = null;
        try {
            out = new FileWriter(new File("mapData.txt"));
            out.write(m.getId() + "\n");
            out.write(m.getName() + "\n");
            for (int r = 0; r < m.getMap().length; r++) {
                for (int c = 0; c < m.getMap()[0].length; c++) {
                    out.write(m.getLocation(c, r).toData());
                    out.write("\n");
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
