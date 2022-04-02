public class Player extends Character{
    Inventory inventory;
    Position position;
    public Player(){

    }

    Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public void setPosition(int x, int y) {
        position = new Position(x,y);
    }


}
