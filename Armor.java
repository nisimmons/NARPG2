public class Armor extends Item{
    private int armorRating;

    public Armor(String name, int id, int armorRating) {
        super(name, id);
        setArmorRating(armorRating);
    }

    public int getArmorRating() {
        return armorRating;
    }

    public void setArmorRating(int armorRating) {
        this.armorRating = armorRating;
    }

}
