public class Weapon extends Item {
    private int weaponDamage;

    public Weapon(String name, int id, int weaponDamage) {
        super(name, id);
        setWeaponDamage(weaponDamage);
    }

    public int getWeaponDamage() {
        return weaponDamage;
    }

    public void setWeaponDamage(int weaponDamage) {
        this.weaponDamage = weaponDamage;
    }
}
