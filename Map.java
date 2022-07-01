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
        this.setName("Overworld");
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
                    e.add(DataAccess.getEnemy(66));
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
                        /*ArrayList<Enemy> enemies = DataAccess.produceFaction(w.getFaction());
                        //get enemies of this faction within 5 levels of the area level
                        if (enemies != null && !enemies.isEmpty()){
                            int areaLevel = w.getLevel(); //find proportion of level(max 50) compared to distance(max 5), 50/5=10
                            int k = rand.nextInt(100);
                            if (k < 25){
                                //mini boss

                                //remove enemies outside bounds
                                for (int i = 0; i < enemies.size(); i++) {
                                    int level = enemies.get(i).getStats().getLevel();
                                    if (level < areaLevel-7 || level > areaLevel+10-rand.nextInt(10))
                                        enemies.remove(i--);
                                }

                                //find the strongest enemy remaining
                                Enemy e = enemies.get(0);
                                for (Enemy enemy : enemies)
                                    if (enemy.getStats().getLevel() > e.getStats().getLevel())
                                        e = enemy;
                                w.addEnemy(e);

                            }
                            else if (k < 70){
                                //two enemies

                                //remove enemies outside bounds
                                for (int i = 0; i < enemies.size(); i++) {
                                    int level = enemies.get(i).getStats().getLevel();
                                    if ((level < areaLevel-10-rand.nextInt(5) || level > areaLevel+10+rand.nextInt(5)))
                                        enemies.remove(i--);
                                }
                                double totalLevel = (areaLevel * 2.2)+7;

                                //add three enemies
                                int count = 0;
                                for (int i = 0; i < 10; i++) {
                                    int ran = rand.nextInt(enemies.size()); //TODO elusive bug here
                                    int level = enemies.get(ran).getStats().getLevel();
                                    if (level <= totalLevel) {
                                        w.addEnemy(enemies.get(ran));
                                        totalLevel -= level;
                                        count++;
                                        if (count > 1)
                                            break;
                                    }
                                }

                            }
                            else {
                                //three small enemies

                                //remove enemies outside bounds
                                for (int i = 0; i < enemies.size(); i++) {
                                    int level = enemies.get(i).getStats().getLevel();
                                    if (level < areaLevel-30 || level > areaLevel+8 || (areaLevel > 10 && level > areaLevel-5) || (areaLevel > 25 && (level < areaLevel-20 || level > areaLevel-15)))
                                        enemies.remove(i--);
                                }
                                double totalLevel = (areaLevel * 2.5)+7;

                                //add three enemies
                                int count = 0;
                                for (int i = 0; i < 10; i++) {
                                    int ran = rand.nextInt(enemies.size()); //TODO bug
                                    int level = enemies.get(ran).getStats().getLevel();
                                    if (level <= totalLevel) {
                                        w.addEnemy(enemies.get(ran));
                                        totalLevel -= level;
                                        count++;
                                        if (count > 2)
                                            break;
                                    }
                                }

                            }
                        }*/
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
