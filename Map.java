public class Map {
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
            for (Location col : row)
                s.append(col).append(" ");
            s.append("\n");
        }
        return s.toString();
    }
}
