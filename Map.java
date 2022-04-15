public class Map {
    private String name;
    private String id;
    private Location[][] map;
    Map(){
        this(5,5);
    }
    Map(int r, int c){
        map = new Location[r][c];
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
