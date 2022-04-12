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
        String s = "D ";
        if (!isRevealed())
            s += "0";
        else
            s += "1";
        return s;
    }

    public void fromData(String s){
        if(s.charAt(2) == '1')
            setRevealed(true);
    }

    public String toString(){
        return "D";
    }
}
