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
        for(Enemy e: enemies)
            s.append(" ").append(e.getName());
        return s.toString();
    }

    public void fromData(String s){
        // TODO fromData
        // s will be a string from the data file in the form
        // take data from string and input it to this object
        // "W <0/1 revealed> <enemy0Name> <enemy0Stats> <enemy0Armor> <enemy0Weapon> <enemy1Name>..."
        // there may not be any enemies

    }

    public String toString(){
        return "W";
    }
}
