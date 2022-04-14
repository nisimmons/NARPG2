abstract class Character {
    private Stats stats;
    private String name;
    private Armor armor;
    private Weapon weapon;

    public Character() {}
    public Character(String s) {
        name = s;
    }
    public Character(String s, Stats stats, Armor armor, Weapon wep){
        this.name = s;
        this.stats = stats;
        this.armor = armor;
        this.weapon = wep;
    }

    public String toString(){
        return name + "\n" + stats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public String getName() {
        return name;
    }

}
