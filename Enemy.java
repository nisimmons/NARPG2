public class Enemy extends Character {
    private int damage;
    private int defense;
    public Enemy(){ super(); }
    public Enemy(String s){super(s);}
    public Enemy(String s, Stats stats, int defense, int damage){
        super(s,stats,null, null);
        this.damage = damage;
        this.defense = defense;
    }
    public String toData(){
        return (getName()+"/"+getStats().toData()+"/"+defense+"/"+damage);
    }
    public void fromData(String s){
        String[] enemy = s.split("/");

        setName(enemy[0]);
        setStats(new Stats(new String[]{enemy[1], enemy[2],enemy[3],enemy[4],enemy[5], enemy[6]}));

        //setArmor((Armor)DataAccess.getItem(Integer.parseInt(enemy[7])));
        //setWeapon((Weapon)DataAccess.getItem(Integer.parseInt(enemy[8])));
        defense = Integer.parseInt(enemy[7]);
        damage = Integer.parseInt(enemy[8]);
    }

    public String toString(){
        String s = "";
        s += getName().replace('-',' ') + "\n";
        s += "Current HP: " + getStats().getCurrHP();
        return s;
    }
    public int getDamage() {
        return damage;
    }
    public int getDefense() {
        return defense;
    }
}
