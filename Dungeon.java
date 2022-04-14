import java.util.ArrayList;

public class Dungeon extends Location {
    Map m;
    private ArrayList<ArrayList<Enemy>> battles;
    public Dungeon(){}
    public Dungeon(String s){
        super(s);
        battles = new ArrayList<ArrayList<Enemy>>();
    }
    public int battleCount(){return battles.size();}
    public void addBattle(ArrayList<Enemy> arr){battles.add(arr);}
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
        s.append(getFaction());
        for (int i = 0; i < battles.size(); i++) {
            ArrayList<Enemy> enemies = battles.get(i);
            for (Enemy e : enemies)
                s.append((" ")).append(e.toData());
            if (i < battles.size() - 1)
                s.append("//");
        }
        return s.toString();
    }

    public void fromData(String s){
        //D 0 DUNGEON Imp/2/0/5/10/5/15/201/102 Imp/2/0/5/10/5/15/201/102// Imp/2/0/5/10/5/15/201/102 Imp/2/0/5/10/5/15/201/102
        String[] s2 = s.split(" ");
        setRevealed(Integer.parseInt(s2[1]) == 1);
        setFaction(Faction.valueOf(s2[2]));
        //TODO
        StringBuilder s3 = new StringBuilder();
        for (int i = 3; i < s2.length; i++) {
            s3.append(s2[i]);
            if (i < s2.length - 1)
                s3.append(" ");
        }
        String [] battleList = s3.toString().split("//");
        for (int i = 0; i < battleList.length; i++) {
            battles.add(new ArrayList<Enemy>());
            String[] enemyList = battleList[i].split(" ");
            for (int x = 0; x <= enemyList.length; x++) {
                Enemy e = new Enemy();
                e.fromData(enemyList[x]);
                battles.get(i).add(e);//TODO split up battles
            }
        }
    }

    public String toString(){
        return "D";
    }
}
