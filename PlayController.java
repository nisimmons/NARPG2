public class PlayController {
    Player player;
    Map map;
    public PlayController(Player p, Map m){
        this.player = p;
        this.map = m;
    }

    public void move(Direction d){
        //move one unit in direction d
        Position p;
        switch (d){
            case NORTH:
                p = new Position(player.getPosition().getX(), player.getPosition().getY() + 1);
                if (map.getLocation(p) != null)
                    player.setPosition(p);
                break;
            case EAST:
                break;
            case SOUTH:
                break;
            case WEST:
                break;
            default:
                break;
        }
    }


}
