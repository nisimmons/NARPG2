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
    public Location getLocation(Position p) {
        return map[p.getY()][p.getX()];
    }
}
