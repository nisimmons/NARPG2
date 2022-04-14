import javax.xml.crypto.Data;

public class Enemy extends Character {
    public Enemy(){ super(); }
    public Enemy(String s){super(s);}
    public Enemy(String s, Stats stats, Armor armor, Weapon wep){super(s,stats,armor,wep);}
    public String toData(){
        return (getName()+"/"+getStats().toData()+"/"+getArmor().getId()+"/"+getWeapon().getId());
    }
    public void fromData(String s){
        String[] enemy = s.split("/");

        setName(enemy[0]);
        setStats(new Stats(new String[]{enemy[1], enemy[2],enemy[3],enemy[4],enemy[5], enemy[6]}));

        setArmor((Armor)DataAccess.getItem(Integer.parseInt(enemy[7])));
        setWeapon((Weapon)DataAccess.getItem(Integer.parseInt(enemy[8])));
    }

    public String toString(){
        String s = "";
        s += getName() + "\n";
        s += "Current HP: " + getStats().getCurrHP();
        return s;
    }
}
