import java.util.ArrayList;

public class BattleController {
    private final Player p;
    private ArrayList<Enemy> allies;
    private ArrayList<Enemy> enemies;
    private boolean won;
    public BattleController(Player p, ArrayList<Enemy> allies, ArrayList<Enemy> enemies){
        this.p = p;
        this.allies = allies;
        this.enemies = enemies;
        won = false;
    }

    /**
     * output the current state of the battle
     * @return string with state
     */
    public String battleState(){
        String s = "";
        s += p.getName() + "\n";
        s += "Current HP: " + p.getStats().getCurrHP() + "\n";
        s += "Current Mana: " + p.getStats().getCurrMana() + "\n";
        if (allies != null){
            s += "Allies\n";
            for(Enemy a: allies)
                s += a.toString();
        }
        s += "\nEnemies:\n";
        for(Enemy e: enemies)
            s += e.toString();
        return s;
    }
    public boolean isWon() {
        return won;
    }
    /**
     * @return a string with a list of entities
     */
    public String listEntities(){
        String s = "";
        s += "0. " + p.getName() + "\n";
        int i = 0,j = 0;
        if (allies != null && !allies.isEmpty())
            for (; i < allies.size(); i++)
                s += (i+1) + allies.get(i).getName();
        if (enemies != null && !enemies.isEmpty())
            for (; j < enemies.size(); j++)
               s += (i+j+1) + ". " + enemies.get(j).getName();
        return s;
    }

    public String attack(int index, Item i){
        Character c;
        String s = "";
        if (index == 0) {
            c = p;
        }
        else if (index > 0 && index < allies.size()) {
            c = allies.get(index-1);
        }
        else if (index > 0 && index <= allies.size() + enemies.size()) {
            c = enemies.get(index+allies.size()-1);
        }
        else
            return "Miss!";

        s += c.getName() + " ";

        if (i instanceof Weapon){
            c.getStats().setCurrHP(c.getStats().getCurrHP() - p.getWeapon().getDamage());
            s += "was hit for " + p.getWeapon().getDamage() + " damage!";
        }
        else if (i instanceof Spell) {
            switch (((Spell) i).getType()) {
                case DAMAGE:
                    //do damage
                    c.getStats().setCurrHP(c.getStats().getCurrHP() - ((Spell) i).getSpellDamage());
                    s += "was hit for " + ((Spell) i).getSpellDamage() + " damage!";
                    break;
                case HEAL:
                    //heal
                    c.getStats().setCurrHP(c.getStats().getCurrHP() + ((Spell) i).getSpellDamage());
                    s += "was healed for " + ((Spell) i).getSpellDamage() + " damage.";
                    if (c.getStats().getCurrHP() > c.getStats().getMaxHP())     // in the case of overhealing
                    {
                        c.getStats().setCurrHP(c.getStats().getMaxHP());
                    }

                    break;
            }
        }
        return s;
    }



    /**
     * has entities other than the player take a turn
     * @return output
     */
    public String entityTurn(){
        //TODO entityTurn
        return null;
    }
}
