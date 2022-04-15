import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
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
    public static Map getMap(String id) {
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
                if (Objects.equals(id, s))
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
        //find the correct enemy
        while (id != i) {
            for (int j = 0; j < 5; j++) //skip one enemy
                scr.nextLine();
            if (!scr.hasNext())
                return null;
            i = Integer.parseInt(scr.nextLine());
        }
        scr.nextLine();
        String name = scr.nextLine();
        Stats stats = new Stats(scr.nextLine().split("/"));
        Weapon wep = (Weapon) getItem(Integer.parseInt(scr.nextLine()));
        Armor armor = (Armor) getItem(Integer.parseInt(scr.nextLine()));
        return new Enemy(name,stats,armor,wep);
    }

    /*
     * get an enemy from data from given info
     * @param faction faction of enemy
     * @param level approximate level of enemy
     * @return the enemy
     */
    /*public static Enemy getEnemy(Faction faction, int level) {
        //go through enemyData.txt, find an enemy near the right level and return it's data
        Scanner scr;
        try {
            scr = new Scanner(new File("enemyData.txt"));
        } catch (FileNotFoundException f) {
            return null;
        }
        scr.nextLine();
        Faction f = Faction.valueOf(scr.nextLine());
        String name = scr.nextLine();
        scr.nextLine();
        Stats stats = new Stats(scr.nextLine().split("/"));
        while (faction != f || stats.getLevel() > level+5 || stats.getLevel() < level-5 ) {
            for (int j = 0; j < 2; j++) //skip one enemy
                scr.nextLine();
            if (!scr.hasNext())
                return null;
            f = Faction.valueOf(scr.nextLine());
            name = scr.nextLine();
            scr.nextLine();
            stats = new Stats(scr.nextLine().split("/"));
        }
        Weapon wep = (Weapon) getItem(Integer.parseInt(scr.nextLine()));
        Armor armor = (Armor) getItem(Integer.parseInt(scr.nextLine()));
        return new Enemy(name, stats, armor, wep);
    }*/

    /**
     * produces an arraylist with all the enemies in a faction
     * @param faction faction
     * @return all enemies of the faction
     */
    public ArrayList<Enemy> produceFaction(Faction faction){
        ArrayList<Enemy> enemies = new ArrayList<>();
        //go through enemyData.txt, find an enemy near the right level and return it's data
        Scanner scr;
        try {
            scr = new Scanner(new File("enemyData.txt"));
        } catch (FileNotFoundException f) {
            return null;
        }
        scr.nextLine();

        while (scr.hasNext()) {
            Faction f = Faction.valueOf(scr.nextLine());
            String name = scr.nextLine();
            scr.nextLine();
            Stats stats = new Stats(scr.nextLine().split("/"));
            while (faction != f) {
                for (int j = 0; j < 2; j++) //skip one enemy
                    scr.nextLine();
                if (!scr.hasNext())
                    return null;
                f = Faction.valueOf(scr.nextLine());
                name = scr.nextLine();
                scr.nextLine();
                stats = new Stats(scr.nextLine().split("/"));
            }
            Weapon wep = (Weapon) getItem(Integer.parseInt(scr.nextLine()));
            Armor armor = (Armor) getItem(Integer.parseInt(scr.nextLine()));
            enemies.add(new Enemy(name, stats, armor, wep));
        }
        return enemies;
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

    public static void savePlayer(Player p, int iasd){
        File data = new File("playerData.txt");
        File temp = new File("playerData2.txt");
        Scanner scr;
        FileWriter out;
        String id;
        //open the data file, and if it does not exist simply create and write to it
        try {
            scr = new Scanner(data);
        } catch (Exception e) {
            try {
                out = new FileWriter(data);
                out.write(p.toData());
                out.close();
                return;
            }
            catch(IOException e2){
                return;
            }
        }
        //read through the existing data file to find the map for overwrite
        try {
            out = new FileWriter(temp);
            //find the right map
            id = "-1";
            if (scr.hasNext())
                id = scr.nextLine();
            //copy other maps until we find this one or get to the end
            while(scr.hasNext() && p.getName().compareTo(id) != 0) {
                out.write(id+"\n"); //TODO
                for(int i = 0; i < 5; i++)
                    out.write(scr.nextLine()+"\n");
                if (scr.hasNext())
                    id = scr.nextLine();
            }

            //skip reading this map
            if (scr.hasNext()) {
                for (int i = 0; i < 5; i++)
                    scr.nextLine();
            }
            //write this map
            out.write(p.toData());
            //write any more maps
            while(scr.hasNext()) {
                out.write("\n");
                out.write(scr.nextLine());
            }
            out.close();
            scr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //copy from temp file back to data
        try {
            data.delete();
            scr = new Scanner(temp);
            out = new FileWriter(data);
            while (scr.hasNext()) {
                out.write(scr.nextLine());
                if (scr.hasNext())
                    out.write("\n");
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        scr.close();
        //delete temp file
        temp.delete();
    }
    /**
     * saves all map data to file
     * @param m map to save
     */
    /*public static void saveMap(Map m, int asdf) {
        //save all map data
        FileWriter out;
        try {
            out = new FileWriter("mapData.txt");
            out.write(m.toData());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static void saveMap(Map m){
        File data = new File("mapData.txt");
        File temp = new File("mapData2.txt");
        Scanner scr;
        FileWriter out;
        int r, c;
        String id;
        //open the data file, and if it does not exist simply create and write to it
        try {
            scr = new Scanner(data);
        } catch (Exception e) {
            try {
                out = new FileWriter(data);
                out.write(m.toData());
                out.close();
                return;
            }
            catch(IOException e2){
                return;
            }
        }
        //read through the existing data file to find the map for overwrite
        try {
            out = new FileWriter(temp);
            //find the right map
            id = "-1";
            if (scr.hasNext())
                id = scr.nextLine();
            //copy other maps until we find this one or get to the end
            while(scr.hasNext() && m.getId().compareTo(id) != 0) {
                out.write(id+"\n");
                out.write(scr.nextLine()+"\n");
                r = Integer.parseInt(scr.nextLine());
                c = Integer.parseInt(scr.nextLine());
                out.write(r + "\n");
                out.write(c + "\n");
                for (int i = 0; i < r*c; i++)
                    out.write(scr.nextLine()+"\n");
                if (scr.hasNext())
                    id = scr.nextLine();
            }

            //skip reading this map
            if (scr.hasNext()) {
                scr.nextLine();
                r = Integer.parseInt(scr.nextLine());
                c = Integer.parseInt(scr.nextLine());
                for (int i = 0; i < r * c; i++)
                    scr.nextLine();
            }
            //write this map
            out.write(m.toData());
            //write any more maps
            while(scr.hasNext()) {
                out.write("\n");
                out.write(scr.nextLine());
            }
            out.close();
            scr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //copy from temp file back to data
        try {
            data.delete();
            scr = new Scanner(temp);
            out = new FileWriter(data);
            while (scr.hasNext()) {
                out.write(scr.nextLine());
                if (scr.hasNext())
                    out.write("\n");
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        scr.close();
        //delete temp file
        temp.delete();
    }
}
