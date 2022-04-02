public class Map {
    Location[][] map;
    Map(){
        map = new Location[10][10];
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
        return map[x][y];
    }

}
