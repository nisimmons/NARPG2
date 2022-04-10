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

        Scanner scr;
        try {
            scr = new Scanner(new File("itemData.txt"));
        } catch (FileNotFoundException e) {
            return null;
        }

        //<int id>
        int itemId = Integer.parseInt(scr.nextLine());
        //find the correct ID
        while (itemId != id) {

            scr.nextLine();
            if(scr.nextLine().compareTo("spell") == 0)
            {
                for(int i = 0; i < 3; i++) //skip one Item
                {
                    scr.nextLine();
                }
            }
            else
            { scr.nextLine(); }

            if (!scr.hasNext())
                return null;

            itemId = Integer.parseInt(scr.nextLine());

        }

        //Item
        String itemName = scr.nextLine();
        //<weapon/armor/spell>
        String itemCategory = scr.nextLine();
        //<weapon dmg/armor health/spell dmg>
        int itemStat = Integer.parseInt(scr.nextLine());

        switch (itemCategory)
        {
            case "weapon":
            {
                return new Weapon(itemName, id, itemStat);      // returns new weapon object
            }
            case "armor":
            {
                return new Armor(itemName, id, itemStat);       // returns new armor object
            }
            case "spell":
            {
                //<spell mana cost>
                int spellCost = Integer.parseInt(scr.nextLine());
                //<spell details>
                SpellType spellKeyword = SpellType.valueOf(scr.nextLine());

                return new Spell(itemName, id, itemStat, spellCost, spellKeyword);
            }

        }

        return null;
    }

    /**
     * gets a map from mapData file
     * @param id id of map
     * @return map
     */
    public static Map getMap(int id) {
        //return null if not found
        Map m;
        Scanner scr = null;
        try {
            scr = new Scanner(new File("mapData.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scr != null;
        String s;
        //find the map
        while(true){
            try{
                s = scr.nextLine();
                if (id == Integer.parseInt(s))
                    break;
            }
            catch(Exception ignored){         }
        }
        String name = scr.nextLine();
        int y = Integer.parseInt(scr.nextLine());
        int x = Integer.parseInt(scr.nextLine());
        m = new Map(y,x);
        m.setId(id);
        m.setName(name);
        for(int i = 0; i < y; i++)
            for(int j = 0; j < x; j++){
                s = scr.nextLine();
                if (s.charAt(0) == 'W') {
                    Wilderness w = new Wilderness();
                    w.fromData(s);
                    m.setLocation(i, j, w);
                }
                else if (s.charAt(0) == 'T') {
                    Town t = new Town();
                    t.fromData(s);
                    m.setLocation(i, j, t);
                }
                else if (s.charAt(0) == 'D') {
                    Dungeon d = new Dungeon();
                    d.fromData(s);
                    m.setLocation(i, j, d);
                }
            }
        return m;
    }

    /**
     * Gets a player from the playerData file
     *
     * @param id username of user
     * @return player object
     */
    public static Player getPlayer(String id) {
        //return null if not found
        Scanner scr;
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
        if (InventoryArray[0].compareTo("") != 0)
            for(String str : InventoryArray)
                inventory.add(getItem(Integer.parseInt(str)));


        //set player info and return
        Player p = new Player();
        p.setName(username);
        p.setStats(stats);
        p.setPosition(pos);
        p.setArmor((Armor) getItem(armorID));
        p.setWeapon((Weapon) getItem(weaponID));
        p.setInventory(inventory);
        return p;
    }

    /**
     * find an enemy from enemyData
     * @param id id of enemy
     * @return enemy or null if not found
     */
    public static Enemy getEnemy(int id) {
        Scanner scr;
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
        Weapon wep = (Weapon) getItem(Integer.parseInt(scr.nextLine()));
        Armor armor = (Armor) getItem(Integer.parseInt(scr.nextLine()));
        return new Enemy(name,stats,armor,wep);
    }

    /**
     * save all player data to file
     * @param p player to save
     */
    public static void savePlayer(Player p) {
        //save all player data
        /*Scanner scr;
        try {
            scr = new Scanner(new File("playerData.txt"));
        } catch (FileNotFoundException e) {
            return;
        }
        String username = scr.nextLine();
        //find the correct player
        while (p.getName().compareTo(username) != 0) {
            for(int i = 0; i < 5; i++) //skip one player
                scr.nextLine();
            if (!scr.hasNext())
                return;
            username = scr.nextLine();
        }
        */
        FileWriter out;
        try {
            out = new FileWriter("playerData.txt");
            out.write(p.getName() + "\n");
            out.write(p.getStats().toData() + "\n");
            out.write(p.getPosition().toData() + "\n");
            out.write(p.getArmor().getId() + "\n");
            out.write(p.getWeapon().getId() + "\n");
            out.write(p.getInventory().toData() + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        FileWriter out;
        try {
            out = new FileWriter("mapData.txt");
            out.write(m.toData());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
