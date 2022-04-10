public class Spell extends Item {
    private int spellDamage;
    private int spellCost;
    private SpellType type;

    public Spell(String name, int id, int spellDamage, int spellCost, SpellType spellKeyword) {
        super(name, id);
        setSpellDamage(spellDamage);
        setSpellCost(spellCost);
        setType(spellKeyword);
    }

    public int getSpellDamage() {
        return spellDamage;
    }

    public int getSpellCost() {
        return spellCost;
    }

    public SpellType getType(){return type;}

    public void setSpellDamage(int spellDamage) {
        this.spellDamage = spellDamage;
    }

    public void setSpellCost(int spellCost) {
        this.spellCost = spellCost;
    }

    public void setType(SpellType spellKeyword) {
        this.type = spellKeyword;
    }
}
