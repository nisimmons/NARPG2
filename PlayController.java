import java.util.ArrayList;
import java.util.Random;

public class PlayController {
    private Player player;
    private Map map;
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
        p.setWeapon((Weapon) DataAccess.getItem(101));
        p.setArmor((Armor) DataAccess.getItem(201));
        p.setStats(new Stats(new String[]{"1","0","15","15","15","10","10"}));
        p.getInventory().add(DataAccess.getItem(301));
        return p;
    }

    public static Map createRandomMap(){return createRandomMap(50);}
    /**
     * sets up a randomized map
     * @param difficulty 1-100
     * @return the map
     */
    public static Map createRandomMap(int difficulty){
        //default difficulty is 50
        int towns = 85 - difficulty;
        int dungeons = difficulty;
        int encounters = (difficulty/5) + 80;
        Map m = new Map();
        m.setName("Overworld");
        m.setId(0);
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
                    e.add(DataAccess.getEnemy(1));
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
                else
                    //set wilderness
                    if (rand.nextInt(100) < encounters) {
                        Wilderness w = new Wilderness();
                        //TODO set faction randomly and get random enemies based on faction and level of area
                        w.setFaction(Faction.FOREST);
                        Enemy e = DataAccess.getEnemy(0);
                        w.addEnemy(e);
                        m.setLocation(c, r, w); //set enemy encounter
                    }
                    else {
                        Wilderness w = new Wilderness("Wilderness");
                        w.setFaction(Faction.FOREST);
                        //TODO set faction randomly
                        m.setLocation(c, r, w); //set non enemy encounter
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

}
