public class Spell extends Item {
    private int spellDamage;
    private int spellCost;
    private String spellKeyword;

    public Spell(String name, int id, int spellDamage, int spellCost, String spellKeyword) {
        super(name, id);
        setSpellDamage(spellDamage);
        setSpellCost(spellCost);
        setSpellKeyword(spellKeyword);
    }

    public int getSpellDamage() {
        return spellDamage;
    }

    public int getSpellCost() {
        return spellCost;
    }

    public String getSpellKeyword() {
        return spellKeyword;
    }

    public void setSpellDamage(int spellDamage) {
        this.spellDamage = spellDamage;
    }

    public void setSpellCost(int spellCost) {
        this.spellCost = spellCost;
    }

    public void setSpellKeyword(String spellKeyword) {
        this.spellKeyword = spellKeyword;
    }
}
