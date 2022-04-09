public class Player extends Character{
    private Inventory inventory;
    private Position position;

    public Player() {
        this.inventory = new Inventory();
        this.position = new Position();
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

    public String toString(){
        String s = "Name: ";
        s += this.getName();
        s += "\nPosition: ";
        s += this.position;
        s += "\n" + this.getStats().toString();
        return s;
    }
}
