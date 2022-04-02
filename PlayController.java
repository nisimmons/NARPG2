public class PlayController {
    Player player;
    Map map;
    public PlayController(Player p, Map m){
        this.player = p;
        this.map = m;
    }

    /**
     * moves the player in a direction, checking the map for null
     * @param d direction to move
     */
    public void move(Direction d){
        //move one unit in direction d
        Position old = player.getPosition(), next;
        switch (d) {
            case NORTH -> {
                next = new Position(old.getX(), old.getY() + 1);
                if (map.getLocation(next) != null)
                    player.setPosition(next);
            }
            case EAST -> {
                next = new Position(old.getX() + 1, old.getY());
                if (map.getLocation(next) != null)
                    player.setPosition(next);
            }
            case SOUTH -> {
                next = new Position(old.getX(), old.getY() - 1);
                if (map.getLocation(next) != null)
                    player.setPosition(next);
            }
            case WEST -> {
                next = new Position(old.getX() - 1, old.getY());
                if (map.getLocation(next) != null)
                    player.setPosition(next);
            }
            default -> {
            }
        }
    }


}
