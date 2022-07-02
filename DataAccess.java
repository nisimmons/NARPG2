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
     * @return item or null if not found
     */
    public static Item getItem(int id) {
        Scanner scr;
        try {
            scr = new Scanner(new File("itemData.txt"));
        } catch (FileNotFoundException e) {
            return null;
        }
        scr.nextLine();
        //<int id>
        int itemId = Integer.parseInt(scr.nextLine());
        //find the correct ID
        while (itemId != id) {
            //skip one item
            while(scr.nextLine().charAt(0) != '*') {
                if (!scr.hasNext())
                    return null;
            }
            itemId = Integer.parseInt(scr.nextLine());

        }
        String itemName = scr.nextLine();
        int level = Integer.parseInt(scr.nextLine());
        String itemCategory = scr.nextLine();
        int itemStat = Integer.parseInt(scr.nextLine());

        switch (itemCategory)
        {
            case "weapon": {
                Weapon w = new Weapon(itemName, id, itemStat);
                w.setLevel(level);
                return w;}
            case "armor": {
                Armor a = new Armor(itemName, id, itemStat);
                a.setLevel(level);
                return a;}
            case "spell":
            {
                //<spell mana cost>
                int spellCost = Integer.parseInt(scr.nextLine());
                //<spell details>
                SpellType spellKeyword = SpellType.valueOf(scr.nextLine());
                Spell s = new Spell(itemName, id, itemStat, spellCost, spellKeyword);
                s.setLevel(level);
                return s;
            }
        }
        return null;
    }

    public static Item getItem(String name) {
        //return null if not found

        Scanner scr;
        try {
            scr = new Scanner(new File("itemData.txt"));
        } catch (FileNotFoundException e) {
            return null;
        }

        scr.nextLine();
        int id = Integer.parseInt(scr.nextLine());
        String itemName = scr.nextLine();
        //find the correct ID
        while (itemName.compareTo(name) != 0) {
            while(scr.nextLine().charAt(0) != '*') {
                if (!scr.hasNext())
                    return null;
            }
            id = Integer.parseInt(scr.nextLine());
            itemName = scr.nextLine();
        }

        int level = Integer.parseInt(scr.nextLine());
        String itemCategory = scr.nextLine();
        int itemStat = Integer.parseInt(scr.nextLine());

        switch (itemCategory)
        {
            case "weapon": {
                Weapon w = new Weapon(itemName, id, itemStat);
                w.setLevel(level);
                return w;
            }
            case "armor": {
                Armor a = new Armor(itemName, id, itemStat);
                a.setLevel(level);
                return a;       // returns new armor object
            }
            case "spell": {
                int spellCost = Integer.parseInt(scr.nextLine());
                SpellType spellKeyword = SpellType.valueOf(scr.nextLine());
                Spell s = new Spell(itemName, id, itemStat, spellCost, spellKeyword);
                s.setLevel(level);
                return s;
            }
            default:
                return null;
        }
    }

    public static ArrayList<Item> produceItemList(int low, int high){
        ArrayList<Item> arr = new ArrayList<>();
        Scanner scr;
        try {
            scr = new Scanner(new File("itemData.txt"));
        } catch (FileNotFoundException e) {
            return null;
        }
        while (scr.hasNext()) {
            scr.nextLine();
            int id = Integer.parseInt(scr.nextLine());
            String itemName = scr.nextLine();
            int level = Integer.parseInt(scr.nextLine());
            //find a good level
            while (level > high || level < low) {
                while (scr.nextLine().charAt(0) != '*') {
                    if (!scr.hasNext())
                        return arr;
                }
                id = Integer.parseInt(scr.nextLine());
                itemName = scr.nextLine();
                level = Integer.parseInt(scr.nextLine());
            }

            //<weapon/armor/spell>
            String itemCategory = scr.nextLine();
            //<weapon dmg/armor health/spell dmg>
            int itemStat = Integer.parseInt(scr.nextLine());

            switch (itemCategory) {
                case "weapon": {
                    Weapon w = new Weapon(itemName, id, itemStat);
                    w.setLevel(level);
                    arr.add(w);      // returns new weapon object
                    break;
                }
                case "armor": {
                    Armor a = new Armor(itemName, id, itemStat);
                    a.setLevel(level);
                    arr.add(a);       // returns new armor object
                    break;
                }
                case "spell": {
                    int spellCost = Integer.parseInt(scr.nextLine());
                    SpellType spellKeyword = SpellType.valueOf(scr.nextLine());
                    Spell s = new Spell(itemName, id, itemStat, spellCost, spellKeyword);
                    s.setLevel(level);
                    arr.add(s);
                    break;
                }
                default:
                    break;
            }
        }
        return arr;
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
        m = new Map(name,y,x);
        m.setId(id);
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
        int gold = scr.nextInt();

        //set player info and return
        Player p = new Player();
        p.setName(username);
        p.setStats(stats);
        p.setPosition(pos);
        p.setArmor((Armor) getItem(armorID));
        p.setWeapon((Weapon) getItem(weaponID));
        p.setInventory(inventory);
        p.setGold(gold);
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
        String [] s = scr.nextLine().split("/");
        Stats stats = new Stats(new String[]{s[0], "0", s[1], s[1],s[2],s[2]});
        int damage = Integer.parseInt(scr.nextLine());
        int armor = Integer.parseInt(scr.nextLine());
        return new Enemy(name,stats,armor,damage);
    }

    public static Enemy getEnemy(String id) {
        Scanner scr;
        try {
            scr = new Scanner(new File("enemyData.txt"));
        } catch (FileNotFoundException e) {
            return null;
        }
        String i = scr.nextLine();
        //find the correct enemy
        while (id.compareTo(i) != 0) {
            for (int j = 0; j < 5; j++) //skip one enemy
                scr.nextLine();
            if (!scr.hasNext())
                return null;
            i = scr.nextLine();
        }
        scr.nextLine();
        String name = scr.nextLine();
        Stats stats = new Stats(scr.nextLine().split("/"));
        int damage = Integer.parseInt(scr.nextLine());
        int armor = Integer.parseInt(scr.nextLine());
        return new Enemy(name,stats,armor,damage);
    }


    /**
     * produces an arraylist with all the enemies in a faction
     * @param faction faction
     * @return all enemies of the faction
     */
    public static ArrayList<Enemy> produceFaction(Faction faction){
        ArrayList<Enemy> enemies = new ArrayList<>();
        //go through enemyData.txt, find an enemy near the right level and return its data
        Scanner scr;
        try {
            scr = new Scanner(new File("enemyData.txt"));
        } catch (FileNotFoundException f) {
            return null;
        }
        while (scr.hasNext()) {
            scr.nextLine();
            Faction f = Faction.valueOf(scr.nextLine());
            while (faction != f) {
                for (int j = 0; j < 4; j++) //skip one enemy
                    scr.nextLine();
                if (!scr.hasNext())
                    return enemies;
                scr.nextLine();
                f = Faction.valueOf(scr.nextLine());
            }
            String name = scr.nextLine();
            String [] s = scr.nextLine().split("/");
            Stats stats = new Stats(new String[]{s[0], "0", s[1], s[1],s[2],s[2]});
            int damage = Integer.parseInt(scr.nextLine());
            int defense = Integer.parseInt(scr.nextLine());
            enemies.add(new Enemy(name, stats, defense, damage));
        }
        return enemies;
    }
    /**
     * save all player data to file
     * @param p player to save
     */
    public static void savePlayer(Player p){
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
        //read through the existing data file to find the player for overwrite
        try {
            out = new FileWriter(temp);
            //find the right player
            id = "-1";
            if (scr.hasNext())
                id = scr.nextLine();
            //copy other players until we find this one or get to the end
            while(scr.hasNext() && p.getName().compareTo(id) != 0) {
                out.write(id+"\n");
                for(int i = 0; i < 5; i++)
                    out.write(scr.nextLine()+"\n");
                if (scr.hasNext())
                    id = scr.nextLine();
            }

            //skip reading this player
            if (scr.hasNext()) {
                for (int i = 0; i < 5; i++)
                    scr.nextLine();
            }
            //write this player
            out.write(p.toData());
            //write any more players
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
    /*
     * get an enemy from data from given info
     * @param faction faction of enemy
     * @param level approximate level of enemy
     * @return the enemy
     */
    /*public static Enemy getEnemy(Faction faction, int level) {
        //go through enemyData.txt, find an enemy near the right level and return its data
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
        int wep = getItem(Integer.parseInt(scr.nextLine()));
        int armor = getItem(Integer.parseInt(scr.nextLine()));
        return new Enemy(name, stats, armor, wep);
    }*/
}
