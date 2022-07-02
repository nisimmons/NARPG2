public class Player extends Character{
    private Inventory inventory;
    private Position position;
    private int gold;

    public Player() {
        this.inventory = new Inventory();
        this.position = new Position();
    }
    public Player(String name){
        super(name);
        this.inventory = new Inventory();
        this.position = new Position();
    }
    public void randomize(){
        this.setPosition(0,0);
        this.setWeapon((Weapon) DataAccess.getItem("Rusted-Sword"));
        this.setArmor((Armor) DataAccess.getItem("Ragged-Robes"));
        this.setStats(new Stats(new String[]{"1","0","15","15","10","10"}));
        this.getInventory().add(DataAccess.getItem("Flare"));
        this.getInventory().add(DataAccess.getItem("Gentle-Healing"));
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
        String s = "";
        s +=   "Name:         " + this.getName();
        s += "\nPosition:     " + this.position;
        s += "\nWeapon:       " + this.getWeapon();
        s += "\nArmor:        " + this.getArmor();
        s += "\nGold:         " + getGold() + "\n";
        s += "\n" + this.getStats().toString();
        return s;
    }
    public String toData(){
        String s= "";
        s += getName() + "\n";
        s += getStats().toData() + "\n";
        s += getPosition().toData() + "\n";
        s += getArmor().getId() + "\n";
        s += getWeapon().getId() + "\n";
        s += getInventory().toData() + "\n";
        s += getGold();
        return s;
    }
    public int getGold() {return gold;}
    public void setGold(int gold) {this.gold = gold;}
    public void addGold(int gold) {this.gold += gold;}
}
