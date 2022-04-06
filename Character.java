abstract class Character {
    private Stats stats;
    private String name;
    private Item armor;
    private Item weapon;
    public Character() {}
    public Character(String s) {
        name = s;
    }
    public Character(String s, Stats stats, Item armor, Item wep){
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

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public Item getArmor() {
        return armor;
    }

    public void setArmor(Item armor) {
        this.armor = armor;
    }

    public String getName() {
        return name;
    }

}
