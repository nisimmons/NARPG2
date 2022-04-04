import java.util.Random;

public class PlayController {
    Player player;
    Map map;
    public PlayController(Player p, Map m){
        this.player = p;
        this.map = m;
    }

    public static Player createRandomPlayer(String name){
        Player p = new Player();
        p.setName(name);
        p.setPosition(0,0);
        p.setWeapon(DataAccess.getItem(0));
        p.setArmor(DataAccess.getItem(1));
        p.setStats(new Stats());
        p.getStats().setLevel(1);
        p.getStats().setCurrHP(10);
        p.getStats().setMaxHP(10);
        p.getStats().setCurrMana(10);
        p.getStats().setMaxMana(10);
        return p;
    }

    /**
     * sets up a randomized map
     * @param difficulty 1-100
     * @return the map
     */
    public static Map createRandomMap(int difficulty){
        int towns = 110 - difficulty;
        int dungeons = difficulty - 10;
        int encounters = (difficulty/5) + 50;
        Map m = new Map();
        Random rand = new Random();
        for(int r = 0; r < m.getMap().length; r++){
            for (int c = 0; c < m.getMap()[0].length; c++){
                //TODO make map setup better
                if (r == 0 && c == 0)
                    //set spawn
                    m.setLocation(c,r,new Wilderness());
			    else if (r == m.getMap().length-1 && c == m.getMap()[0].length-1)
                    //set end
                    m.setLocation(c,r,new Dungeon()); //final dungeon
                else if(rand.nextInt(100) < towns)
                    //set town
                    m.setLocation(c,r,new Town());
                else if(rand.nextInt(100) < dungeons)
                    //set dungeon
                    m.setLocation(c,r,new Dungeon()); //regular dungeon
                else
                    //set wilderness
                    if (rand.nextInt(100) < encounters)
                        m.setLocation(c,r,new Wilderness()); //set enemy encounter
                    else
                        m.setLocation(c,r,new Wilderness()); //set non enemy encounter
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
            case NORTH -> {
                next = new Position(old.getX(), old.getY() - 1);
                if (map.getLocation(next) != null){
                    player.setPosition(next);
                    return true;
                }
                else
                    return false;
            }
            case EAST -> {
                next = new Position(old.getX() + 1, old.getY());
                if (map.getLocation(next) != null){
                    player.setPosition(next);
                    return true;
                }
                else
                    return false;
            }
            case SOUTH -> {
                next = new Position(old.getX(), old.getY() + 1);
                if (map.getLocation(next) != null){
                    player.setPosition(next);
                    return true;
                }
                else
                    return false;
            }
            case WEST -> {
                next = new Position(old.getX() - 1, old.getY());
                if (map.getLocation(next) != null){
                    player.setPosition(next);
                    return true;
                }
                else
                    return false;
            }
            default -> { return false;
            }
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
