public class Enemy extends Character {
    public Enemy(){ super(); }
    public Enemy(String s){super(s);}
    public Enemy(String s, Stats stats, Armor armor, Weapon wep){super(s,stats,armor,wep);}
    public String toData(){
        return (getName()+"/"+getStats().toData()+"/"+getArmor().getId()+"/"+getWeapon().getId());
    }
    public String toString(){
        String s = "";
        s += getName() + "\n";
        s += "Current HP: " + getStats().getCurrHP();
        return s;
    }
}
