import java.util.ArrayList;

public class Wilderness extends Location {

    private ArrayList<Enemy> enemies;

    public Wilderness(){
        this("Wilderness");
    }
    public Wilderness(String s){
        super(s);
        enemies = new ArrayList<Enemy>();
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
        for(Enemy e: enemies)
            s.append(e.getName()).append(" ");
        return s.toString();
    }
    public String toString(){
        return "W";
    }
}
