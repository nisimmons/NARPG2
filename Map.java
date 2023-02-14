import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

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
        List<List<java.lang.Character>> baseMap = procedurallyGeneratedMap();
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

                        switch(baseMap.get(r).get(c).toString().charAt(0)){
                            case 'F':
                                w.setFaction(Faction.FOREST);
                                break;
                            case 'R':
                                w.setFaction(Faction.RUINS);
                                break;
                            case 'B':
                                w.setFaction(Faction.BEACH);
                                break;
                            case 'D':
                                w.setFaction(Faction.DESERT);
                                break;
                            case 'P':
                                w.setFaction(Faction.PLAINS);
                                break;
                            case 'M':
                                w.setFaction(Faction.MOUNTAIN);
                                break;
                        }


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
    public static List<List<java.lang.Character>> procedurallyGeneratedMap() {
        List<List<java.lang.Character>> map = new ArrayList<>();
        Random random = new Random();
        char[] letters = {'F', 'R', 'B', 'D', 'P', 'M'};

        // Create a 2D array with 5 rows and 5 columns
        for (int i = 0; i < 5; i++) {
            List<java.lang.Character> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                row.add('x');
            }
            map.add(row);
        }

        //fill array with x
        for(java.lang.Character c: letters){
            while(true) {
                int row = random.nextInt(map.size());
                int col = random.nextInt(map.get(0).size());
                if (map.get(row).get(col) == 'x') {
                    map.get(row).set(col, c);
                    break;
                }
            }
        }

        //expand each letter until there are no more x's
        boolean hasX = true;
        while(hasX){
            hasX = false;

            for(java.lang.Character c: letters){
                boolean expanded = false;
                for (int i = 0; i < 5; i++) {
                    List<java.lang.Character> row = map.get(i);
                    for (int j = 0; j < 5; j++) {
                        char curr = row.get(j);
                        if(curr == c){
                            //check up
                            if (i > 0 && map.get(i-1).get(j) == 'x') {
                                map.get(i - 1).set(j, map.get(i).get(j));
                                expanded = true;
                                break;
                            }
                            //check down
                            if (i < 4 && map.get(i+1).get(j) == 'x') {
                                map.get(i + 1).set(j, map.get(i).get(j));
                                expanded = true;
                                break;
                            }
                            //check left
                            if (j > 0 && map.get(i).get(j-1) == 'x') {
                                map.get(i).set(j-1, map.get(i).get(j));
                                expanded = true;
                                break;
                            }
                            //check right
                            if (j < 4 && map.get(i).get(j + 1) == 'x') {
                                map.get(i).set(j + 1, map.get(i).get(j));
                                expanded = true;
                                break;
                            }
                        }

                    }
                    if(expanded)
                        break;
                }

            }

            for (int i = 0; i < 5; i++) {
                List<java.lang.Character> row = map.get(i);
                for (int j = 0; j < 5; j++) {
                    if (row.get(j) == 'x') {
                        hasX = true;
                        break;
                    }
                }
                if(hasX)
                    break;
            }
        }


        //count up each letter
        TreeMap<java.lang.Character, Integer> hash = new TreeMap<>();
        for (int i = 0; i < 5; i++) {
            List<java.lang.Character> row = map.get(i);
            for (int j = 0; j < 5; j++) {
                char c = row.get(j);
                if (!hash.containsKey(c))
                    hash.put(c, 1);
                else
                    hash.put(c, hash.get(c) + 1);
            }
        }

        //attempt to expand letters with lower count at the expense of letters with higher count
        for (int i = 0; i < 5; i++) {
            List<java.lang.Character> row = map.get(i);
            for (int j = 0; j < 5; j++) {
                char c = row.get(j);
                if(hash.get(c) < 4){
                    //check up
                    if (i > 0 && hash.get(map.get(i-1).get(j)) > 4) {
                        hash.put(map.get(i-1).get(j), hash.get(map.get(i-1).get(j))-1);
                        map.get(i - 1).set(j, map.get(i).get(j));
                        hash.put(c,hash.get(c)+1);
                        break;
                    }
                    //check down
                    if (i < 4 && hash.get(map.get(i+1).get(j)) > 4) {
                        hash.put(map.get(i+1).get(j), hash.get(map.get(i+1).get(j))-1);
                        map.get(i + 1).set(j, map.get(i).get(j));
                        hash.put(c,hash.get(c)+1);
                        break;
                    }
                    //check left
                    if (j > 0 && hash.get(map.get(i).get(j-1)) > 4) {
                        hash.put(map.get(i).get(j-1), hash.get(map.get(i).get(j-1))-1);
                        map.get(i).set(j-1, map.get(i).get(j));
                        hash.put(c,hash.get(c)+1);
                        break;
                    }
                    //check right
                    if (j < 4 && hash.get(map.get(i).get(j + 1)) > 4) {
                        hash.put(map.get(i).get(j + 1), hash.get(map.get(i).get(j + 1))-1);
                        map.get(i).set(j + 1, map.get(i).get(j));
                        hash.put(c,hash.get(c)+1);
                        break;
                    }


                }

            }
        }
        /*
        //debug print the map
        System.out.println(hash);
        for(List<java.lang.Character> a: map){
            System.out.println(a);
        }
        */



        return map;
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
