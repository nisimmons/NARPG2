import java.util.ArrayList;
import java.util.Random;

public class BattleController {
    private final Player p;
    private ArrayList<Enemy> allies;
    private ArrayList<Enemy> enemies;
    private boolean won;
    private int expReward;
    public BattleController(Player p, ArrayList<Enemy> allies, ArrayList<Enemy> enemies){
        this.p = p;
        this.allies = allies;
        this.enemies = enemies;
        won = false;
        expReward = 0;
    }

    /**
     * output the current state of the battle
     * @return string with state
     */
    public ArrayList<String> battleState(){
        ArrayList<String> s = new ArrayList<>();
        s.add(p.getName());
        s.add("Current HP:   " + p.getStats().getCurrHP());
        s.add("Current Mana: " + p.getStats().getCurrMana());
        if (allies != null && allies.size() > 0){
            s.add("Allies");
            for(Enemy a: allies)
                s.add(a.toString());
        }
        s.add("Enemies:");
        for(Enemy e: enemies)
            s.add(e.toString());
        return s;
    }
    /**
     * @return a string with a list of entities
     */
    public ArrayList<String> listEntities(){
        ArrayList<String> s = new ArrayList<>();
        s.add("0. " + p.getName());
        int i = 0,j = 0;
        if (allies != null && !allies.isEmpty())
            for (; i < allies.size(); i++)
                s.add((i+1) + allies.get(i).getName());
        if (enemies != null && !enemies.isEmpty())
            for (; j < enemies.size(); j++)
               s.add((i+j+1) + ". " + enemies.get(j).getName());
        return s;
    }

    /**
     * player attacks a target index with item i
     * @param index target
     * @param i item to attack with
     * @return string stating what happened
     */
    public ArrayList<String> attack(int index, Item i){
        Character target;
        ArrayList<String> s = new ArrayList<>();
        s.add("You use " + i.getName() + "!"); //TODO correct line printing
        if (index == 0) {
            target = p;
        }
        else if (index > 0 && index < allies.size()) {
            target = allies.get(index-1);
        }
        else if (index > 0 && index <= allies.size() + enemies.size()) {
            target = enemies.get(index+allies.size()-1);
        }
        else {
            s.set(0, "Miss!");
            return s;
        }


        if (i instanceof Weapon){
            target.getStats().setCurrHP(target.getStats().getCurrHP() - p.getWeapon().getDamage());
            s.add(target.getName() + " was hit for " + p.getWeapon().getDamage() + " damage!");
        }
        else if (i instanceof Spell) {
            if (p.getStats().getCurrMana() >= ((Spell) i).getSpellCost()){ //if player has enough mana
                //spend the mana
                p.getStats().setCurrMana(p.getStats().getCurrMana() - ((Spell) i).getSpellCost());
                switch (((Spell) i).getType()) {
                    case DAMAGE:
                        //do damage
                        target.getStats().setCurrHP(target.getStats().getCurrHP() - ((Spell) i).getSpellDamage());
                        s.add(target.getName() + " was hit for " + ((Spell) i).getSpellDamage() + " damage!");
                        break;
                    case HEAL:
                        //heal
                        target.getStats().setCurrHP(target.getStats().getCurrHP() + ((Spell) i).getSpellDamage());
                        s.add(target.getName() + " was healed for " + ((Spell) i).getSpellDamage() + " damage.");
                        if (target.getStats().getCurrHP() > target.getStats().getMaxHP())     // in the case of overhealing
                        {
                            target.getStats().setCurrHP(target.getStats().getMaxHP());
                        }
                        break;
                }
            }
            else
                s.add("Insufficient Mana");
        }
        cleanUp();
        return s;
    }


    /**
     * have entities other than the player take a turn
     * @return output
     */
    public ArrayList<String> entityTurn(){
        ArrayList<String> s = new ArrayList<>();
        if (won) //TODO Check
            return s;
        s.add("Enemy Turn");
        Random rand = new Random();
        for (Enemy e : enemies) {
            int i = rand.nextInt(1 + allies.size());
            if (i == 0) {
                //attack the player
                //attack(i, e.getWeapon()); ?
                p.getStats().setCurrHP(p.getStats().getCurrHP() - e.getDamage());
                s.add(e.getName() + " attacked you for " + e.getDamage() + " damage");
                expReward += e.getDamage();
            }
            else if (i < allies.size()){
                allies.get(i).getStats().setCurrHP(allies.get(i).getStats().getCurrHP() - e.getDamage());
                s.add(e.getName() + " attacked " + allies.get(i).getName() + " for " + e.getDamage() + " damage");
            }
        }
        cleanUp();
        return s;
    }

    /**
     * clean up any corpses, add to player exp, check if battle is won
     */
    public void cleanUp(){

        for (int i = 0; i < enemies.size(); i++)
            if (enemies.get(i).getStats().getCurrHP() <= 0) {
                expReward += enemies.get(i).getStats().getLevel()*10;
                enemies.remove(i--);
            }
        for (int i = 0; i < allies.size(); i++)
                if (allies.get(i).getStats().getCurrHP() <= 0)
                    allies.remove(i--);
        if (enemies.size() == 0)
            won = true;
    }


    public int getExpReward() {
        return expReward;
    }
    public boolean isWon() {
        return won;
    }
}
