import java.util.ArrayList;
import java.util.Random;

public class PlayController {
    private Player player;
    private Map map;
    private static final Position spawn = new Position(0,0);
    public PlayController(Player p, Map m){
        this.player = p;
        this.map = m;
    }

    /**
     * creates a new player
     * @param name playername
     * @return player
     */
    public static Player createRandomPlayer(String name){
        Player p = new Player();
        p.setName(name);
        p.setPosition(0,0);
        p.setWeapon((Weapon) DataAccess.getItem("Iron-Sword"));
        p.setArmor((Armor) DataAccess.getItem("Leather"));
        p.setStats(new Stats(new String[]{"1","0","15","15","15","10","10"}));
        p.getInventory().add(DataAccess.getItem("Flare"));
        p.getInventory().add(DataAccess.getItem("Healing"));
        return p;
    }

    public static Map createRandomMap(String s){return createRandomMap(50, s);}
    /**
     * sets up a randomized map
     * @param difficulty 1-100
     * @return the map
     */
    public static Map createRandomMap(int difficulty, String name){
        //default difficulty is 50, higher is more likely
        int towns = 0;//75 - difficulty;
        int dungeons = 0;//difficulty;
        int encounters = 100;//(difficulty/5) + 80;
        Map m = new Map();
        m.setName("Overworld");
        m.setId(name);
        Random rand = new Random();
        for(int r = 0; r < m.getMap().length; r++){
            for (int c = 0; c < m.getMap()[0].length; c++){
                if (r == 0 && c == 0) {
                    //set spawn
                    Wilderness w = new Wilderness("Spawn");
                    w.setRevealed(true);
                    w.setFaction(Faction.SPAWN);
                    m.setLocation(c, r, w);
                }
			    else if (r == m.getMap().length-1 && c == m.getMap()[0].length-1) {
                    //set end
                    Dungeon d = new Dungeon("Demon King's Fortress");
                    d.setFaction(Faction.FINALDUNGEON);
                    ArrayList<Enemy> e = new ArrayList<>();
                    e.add(DataAccess.getEnemy(66));
                    d.addBattle(e);
                    m.setLocation(c, r, d); //final dungeon
                }
                else if(rand.nextInt(100) < towns) {
                    //set town
                    Town t = new Town();
                    t.setFaction(Faction.TOWN);
                    //TODO set merchant information
                    m.setLocation(c, r, t);
                }
                else if(rand.nextInt(100) < dungeons) {
                    //set dungeon
                    Dungeon d = new Dungeon("Dungeon");
                    d.setFaction(Faction.DUNGEON);
                    ArrayList<Enemy> e = new ArrayList<>();
                    e.add(DataAccess.getEnemy(0));
                    e.add(DataAccess.getEnemy(0));
                    d.addBattle(e);
                    ArrayList<Enemy> e2 = new ArrayList<>();
                    e2.add(DataAccess.getEnemy(0));
                    e2.add(DataAccess.getEnemy(0));
                    d.addBattle(e2);
                    m.setLocation(c, r, d); //regular dungeon
                }
                else {
                    //set wilderness
                    if (rand.nextInt(100) < encounters) {
                        Wilderness w = new Wilderness();
                        w.setFaction(randomFaction());
                        ArrayList<Enemy> enemies = DataAccess.produceFaction(w.getFaction());
                        //get enemies of this faction within 5 levels of the area level
                        if (enemies != null && !enemies.isEmpty()){
                            double distance = Math.sqrt(Math.pow(Math.abs(spawn.getY()-r),2) + Math.pow(Math.abs(spawn.getX()-c),2));
                            int zone = (int) Math.floor((distance-1)*12.5); //find proportion of level(max 50) compared to distance(max 5), 50/5=10
                            for (int i = 0; i < enemies.size(); i++) {
                                //TODO fix enemy level zoning
                                int level = enemies.get(i).getStats().getLevel();

                                if (level < zone-7 || level > zone+15-rand.nextInt(10))
                                    enemies.remove(i--);
                                else
                                    System.out.println(zone + " / " + level);
                            }
                            if(!enemies.isEmpty()) {
                                //add up to 3 enemies to this location
                                //TODO add weight to enemy levels
                                int j = rand.nextInt(3) + 1;
                                double totalLevel = (zone * 1.6)+7;
                                for (int i = 0; i < j; i++) {
                                    int ran = rand.nextInt(enemies.size());
                                    int level = enemies.get(ran).getStats().getLevel();
                                    if (level <= totalLevel) {
                                        w.addEnemy(enemies.get(ran));
                                        totalLevel -= level;
                                    }
                                }

                                int k = rand.nextInt(100);
                                if (k < 20){ //three small
                                    //remove enemies that are too high


                                    for (int i = 0; i < 3; i++)
                                        w.addEnemy(enemies.get(rand.nextInt(enemies.size())));
                                }
                                else if (k < 40){ //mini boss
                                    w.addEnemy(enemies.get(enemies.size()-1));
                                }
                                else if (k < 60){

                                }
                                else if (k < 80){

                                }
                                else {

                                }


                            }
                        }
                        m.setLocation(c, r, w); //set enemy encounter
                    } else {
                        Wilderness w = new Wilderness("Wilderness");
                        w.setFaction(Faction.WILDERNESS);
                        m.setLocation(c, r, w); //set non enemy encounter
                    }
                }
            }
        }
        return m;
    }

    /**
     * moves the player in a direction, checking the map for null
     * @param d direction to move
     */
    public boolean move(Direction d){
        //move one unit in direction d
        Position old = player.getPosition(), next;
        switch (d) {
            case NORTH:
                next = new Position(old.getX(), old.getY() - 1);
                if (map.getLocation(next) != null) {
                    player.setPosition(next);
                    return true;
                } else
                    return false;
            case EAST:
                next = new Position(old.getX() + 1, old.getY());
                if (map.getLocation(next) != null) {
                    player.setPosition(next);
                    return true;
                } else
                    return false;
            case SOUTH:
                next = new Position(old.getX(), old.getY() + 1);
                if (map.getLocation(next) != null) {
                    player.setPosition(next);
                    return true;
                } else
                    return false;
            case WEST:
                next = new Position(old.getX() - 1, old.getY());
                if (map.getLocation(next) != null) {
                    player.setPosition(next);
                    return true;
                } else
                    return false;
            default:
                return false;
        }
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    public static Faction randomFaction(){
        Random rand = new Random();
        switch(rand.nextInt(6)){
            case 0:
                return Faction.FOREST;
            case 1:
                return Faction.DESERT;
            case 2:
                return Faction.PLAINS;
            case 3:
                return Faction.BEACH;
            case 4:
                return Faction.RUINS;
            case 5:
                return Faction.MOUNTAIN;
            default:
                return Faction.WILDERNESS;
        }
    }
}
