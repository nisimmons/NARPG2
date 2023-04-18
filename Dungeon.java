import java.util.ArrayList;

public class Dungeon extends Location {
    Map m;
    private ArrayList<ArrayList<Enemy>> battles;
    public Dungeon(){battles = new ArrayList<>();}
    public Dungeon(String s){
        super(s);
        battles = new ArrayList<>();
    }
    public int battleCount(){return battles.size();}
    public void addBattle(ArrayList<Enemy> arr){battles.add(arr);}
    public void resetBattles(){battles = new ArrayList<>();}
    public ArrayList<Enemy> getBattle(int i){return battles.get(i);}
    /**
     * finds the data entry for this location
     * @return data string
     */
    public String toData(){
        StringBuilder s = new StringBuilder("D ");
        if (!isRevealed())
            s.append("0 ");
        else
            s.append("1 ");
        s.append(this.getLevel()).append(" ");
        s.append(getFaction()).append(" ");
        for (int i = 0; i < battles.size(); i++) {
            ArrayList<Enemy> enemies = battles.get(i);
            if (enemies.isEmpty())
                continue;
            for (int j = 0; j < enemies.size(); j++) {
                s.append(enemies.get(j).toData());
                if (j < enemies.size()-1)
                    s.append(" ");
            }
            if (i < battles.size() - 1)
                s.append("//");
        }
        return s.toString();
    }
    public void cleanUp(){
        for (int i = 0; i < battles.size(); i++)
            if (battles.get(i).isEmpty())
                battles.remove(i--);
    }
    public void fromData(String s){
        //D 0 DUNGEON Imp/2/0/5/10/5/15/201/102 Imp/2/0/5/10/5/15/201/102// Imp/2/0/5/10/5/15/201/102 Imp/2/0/5/10/5/15/201/102

        //parse initial information
        String[] s2 = s.split(" ");
        setRevealed(Integer.parseInt(s2[1]) == 1);
        setLevel(Integer.parseInt(s2[2]));
        setFaction(Faction.valueOf(s2[3]));

        //parse out each battle
        StringBuilder s3 = new StringBuilder();
        for (int i = 4; i < s2.length; i++) {
            s3.append(s2[i]);
            if (i < s2.length - 1)
                s3.append(" ");
        }
        String [] battleList = s3.toString().split("//");

        //parse out each enemy in each battle
        for (int i = 0; i < battleList.length; i++) {
            battles.add(new ArrayList<>());
            String[] enemyList = battleList[i].split(" ");
            for (String value : enemyList) {
                if (value.compareTo("") != 0) {
                    Enemy e = new Enemy();
                    e.fromData(value);
                    battles.get(i).add(e);
                }
            }
        }
        cleanUp();
    }

    public String toString(){
        return "Dungeon";
    }
}
