import java.util.ArrayList;

public class Wilderness extends Location {

    private ArrayList<Enemy> enemies;

    public Wilderness(){
        this("Wilderness");
    }
    public Wilderness(String s){
        super(s);
        enemies = new ArrayList<>();
    }
    public Wilderness(String[] s)
    {



    }


    public void addEnemy(Enemy e){enemies.add(e);}

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * finds the data entry for this location
     * @return data string
     */
    public String toData(){
        StringBuilder s = new StringBuilder("W ");
        if(!isRevealed())
            s.append("0");
        else
            s.append("1");
        s.append(" ").append(getLevel());
        s.append(" ").append(getFaction());
        for(Enemy e: enemies)
            s.append(" ").append(e.toData());
        return s.toString();
    }

    /**
     * fills this wilderness data with string given
     * @param s string data
     */
    public void fromData(String s){

        // tile, revealed, faction, name/lvl/currHealth/maxHealth/currMana/maxMana/armorID/weaponID  "        "    "        "
        String[] subjectGrouping = s.split(" ");
        if (Integer.parseInt(subjectGrouping[1]) == 1)
            setRevealed(true);
        setLevel(Integer.parseInt(subjectGrouping[2]));
        setFaction(Faction.valueOf(subjectGrouping[3]));
        for (int x = 5; x <= subjectGrouping.length; x++)
        {
            Enemy e = new Enemy();
            e.fromData(subjectGrouping[x-1]);
            /*
            String[] enemy = subjectGrouping[x-1].split("/");
            String enemyName = enemy[0];
            Stats stats = new Stats(new String[]{enemy[1], enemy[2],enemy[3],enemy[4],enemy[5], enemy[6]});

            int enemyArmor = Integer.parseInt(enemy[7]);
            int enemyWeapon = Integer.parseInt(enemy[8]);

            Enemy newEnemy = new Enemy(enemyName, stats, (Armor)DataAccess.getItem(enemyArmor), (Weapon)DataAccess.getItem(enemyWeapon));
*/
            enemies.add(e);
        }

    }

    public String toString(){
        return faction.toString();
    }

    public void resetBattles() {enemies = new ArrayList<>();}
}
