public class Weapon extends Item {
    private int damage;

    public Weapon(String name, int id, int damage) {
        super(name, id);
        setDamage(damage);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
