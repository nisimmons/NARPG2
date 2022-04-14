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

    public String addExp(int exp){
        String s = "You gained " + exp + " EXP.";
        getStats().setExp(getStats().getExp() + exp);
        if (getStats().getExp() > (getStats().getLevel()+1) * 100) {
            s += "\nYou have enough EXP to level up!";
        }
        return s;
    }

    public String rest(){
        String s = "You rest here.";
        if (getStats().getExp() > (getStats().getLevel()) * 100) {
            int levelsGained = (getStats().getExp()/100)+1 - getStats().getLevel();
            getStats().setLevel((getStats().getLevel() + levelsGained));
            s += "\nYou leveled up!\nNew Level: " + getStats().getLevel();
            getStats().setMaxHP(getStats().getLevel()*2 + 15);
            getStats().setMaxHP(getStats().getLevel()*3 + 10);
        }

        getStats().setCurrHP(getStats().getMaxHP());
        getStats().setCurrMana(getStats().getMaxMana());
        return s;
    }

    public String toString(){
        String s = "Name: ";
        s += this.getName();
        s += "\nPosition: ";
        s += this.position;
        s += "\n" + this.getStats().toString();
        s += "\n" + inventory.toString();
        return s;
    }
}
