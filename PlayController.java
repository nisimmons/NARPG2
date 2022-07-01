import java.util.ArrayList;
import java.util.Random;

public class PlayController {
    private final Player player;
    private final Map map;
    public static final Position spawn = new Position(0,0);
    public static final int difficulty = 50;
    public static final int towns = 80 - difficulty;
    public static final int dungeons = difficulty - 20;
    public static final int encounters = (difficulty/5) + 80;
    public PlayController(Player p, Map m){
        this.player = p;
        this.map = m;
    }

    public void respawn(){
        respawn(false);
    }
    public void respawn(boolean spawnAll){
        for(int r = 0; r < map.getMap().length; r++)
            for (int c = 0; c < map.getMap()[0].length; c++)
                if(map.getLocation(c,r).isRevealed() || spawnAll)
                    spawn(r, c);
    }
    private void spawn(int r, int c){
        double distance = Math.sqrt(Math.pow(Math.abs(spawn.getY()-r),2) + Math.pow(Math.abs(spawn.getX()-c),2));
        Random rand = new Random();
        if(map.getLocation(c,r) instanceof Town) {
            //set town
            Town t = (Town) map.getLocation(c,r);
            ArrayList<Item> arr = DataAccess.produceItemList(t.getLevel() - 15, t.getLevel() + 15);
            if (arr != null)
                t.setMerchant(new Inventory(arr));
            else
                t.setMerchant(new Inventory());
        }
        else if(map.getLocation(c,r) instanceof Dungeon && map.getLocation(c,r).getFaction() != Faction.FINALDUNGEON) {
            //set dungeon
            Dungeon d = (Dungeon) map.getLocation(c,r);
            d.resetBattles();
            ArrayList<Enemy> e = new ArrayList<>();
            e.add(DataAccess.getEnemy(0));
            e.add(DataAccess.getEnemy(0));
            d.addBattle(e);
            ArrayList<Enemy> e2 = new ArrayList<>();
            e2.add(DataAccess.getEnemy(0));
            e2.add(DataAccess.getEnemy(0));
            d.addBattle(e2);
        }
        else {
            //set wilderness
            if (map.getLocation(c,r).getFaction() != Faction.WILDERNESS && map.getLocation(c,r).getFaction() != Faction.FINALDUNGEON
                    && map.getLocation(c,r).getFaction() != Faction.SPAWN) {
                Wilderness w = (Wilderness) map.getLocation(c,r);
                w.resetBattles();
                ArrayList<Enemy> enemies = DataAccess.produceFaction(w.getFaction());
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
                            int ran = rand.nextInt(enemies.size());
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
                }
            }
        }
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
    public Player getPlayer() {return player;}
    public Map getMap() {return map;}
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
