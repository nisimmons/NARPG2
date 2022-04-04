public class Map {
    Location[][] map;
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
        return map[p.getY()][p.getX()];
    }

    public Location getLocation(int x, int y) {
        return map[y][x];
    }
    public void setLocation(int x, int y, Location l){
        map[y][x] = l;
    }
}
