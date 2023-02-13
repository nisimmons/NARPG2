import java.util.ArrayList;
import java.util.Random;

public class Map {
    private String name;
    private String id;
    private Location[][] map;
    Map(String name){
        this(name,5,5);
    }
    Map(String name, int r, int c){
        map = new Location[r][c];
        this.name = name;
    }
    public void randomize(){
        this.setId(name);
        Random rand = new Random();
        for(int r = 0; r < this.getMap().length; r++){
            for (int c = 0; c < this.getMap()[0].length; c++){
                double distance = Math.sqrt(Math.pow(Math.abs(PlayController.spawn.getY()-r),2) + Math.pow(Math.abs(PlayController.spawn.getX()-c),2));

                if (r == 0 && c == 0) {
                    //set spawn
                    Wilderness w = new Wilderness("Spawn");
                    w.setLevel(0);
                    w.setRevealed(true);
                    w.setFaction(Faction.SPAWN);
                    this.setLocation(c, r, w);
                }
                else if (r == this.getMap().length-1 && c == this.getMap()[0].length-1) {
                    //set end
                    Dungeon d = new Dungeon("Demon King's Fortress");
                    d.setLevel(50);
                    d.setFaction(Faction.FINALDUNGEON);
                    ArrayList<Enemy> e = new ArrayList<>();
                    e.add(DataAccess.getEnemy("Demon-King"));
                    d.addBattle(e);
                    this.setLocation(c, r, d); //final dungeon
                }
                else if(rand.nextInt(100) < PlayController.towns) {
                    //set town
                    Town t = new Town();
                    t.setLevel((int) Math.floor((distance-1)*12.5));
                    t.setFaction(Faction.TOWN);
                    this.setLocation(c, r, t);
                }
                else if(rand.nextInt(100) < PlayController.dungeons) {
                    //set dungeon
                    Dungeon d = new Dungeon("Dungeon");
                    d.setLevel((int) Math.floor((distance-1)*12.5));
                    d.setFaction(Faction.DUNGEON);
                    /*ArrayList<Enemy> e = new ArrayList<>();
                    e.add(DataAccess.getEnemy(0));
                    e.add(DataAccess.getEnemy(0));
                    d.addBattle(e);
                    ArrayList<Enemy> e2 = new ArrayList<>();
                    e2.add(DataAccess.getEnemy(0));
                    e2.add(DataAccess.getEnemy(0));
                    d.addBattle(e2);*/
                    this.setLocation(c, r, d); //regular dungeon
                }
                else {
                    //set wilderness
                    if (rand.nextInt(100) < PlayController.encounters) {
                        Wilderness w = new Wilderness();
                        w.setLevel((int) Math.floor((distance-1)*12.5));
                        w.setFaction(PlayController.randomFaction());
                        this.setLocation(c, r, w); //set enemy encounter
                    } else {
                        Wilderness w = new Wilderness("Wilderness");
                        w.setFaction(Faction.WILDERNESS);
                        this.setLocation(c, r, w); //set non enemy encounter
                    }
                }
            }
        }
    }
    public Location[][] getMap() {
        return map;
    }

    public String toData(){
        StringBuilder s = new StringBuilder();
        s.append(id).append("\n");
        s.append(name).append("\n");
        s.append(map.length).append("\n");
        s.append(map[0].length).append("\n");
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                s.append(getLocation(r, c).toData());
                if (r < map[0].length-1 || c < map[0].length-1)
                    s.append("\n");
            }
        }
        return s.toString();
    }
    /**
     * returns location under a given position
     * @param p position
     * @return location at p
     */
    public Location getLocation(Position p) {
        if (p.getY() < 0 || p.getY() >= map.length || p.getX() < 0 || p.getX() >= map[0].length)
            return null;
        else
            return map[p.getY()][p.getX()];
    }

    public Location getLocation(int x, int y) {
        return map[y][x];
    }
    public void setLocation(int x, int y, Location l){
        map[y][x] = l;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Location[] row : map) {
            for (Location col : row) {
                if (col.isRevealed())
                    s.append(col).append(" ");
                else
                    s.append(". ");
            }
            s.append("\n");
        }
        return s.toString();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
