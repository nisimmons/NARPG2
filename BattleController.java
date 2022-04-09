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

    public String attack(int i){
        String s = "";
        if (i == 0) {
            p.getStats().setCurrHP(p.getStats().getCurrHP() - p.getWeapon().getWeaponDamage());
            s += "You took " + p.getWeapon().getWeaponDamage() + " damage!";
        }
        else if (i > 0 && i < allies.size()) {
            allies.get(i-1).getStats().setCurrHP(allies.get(i-1).getStats().getCurrHP() - p.getWeapon().getWeaponDamage());
            s += allies.get(i-1).getName() + " took " + p.getWeapon().getWeaponDamage() + " damage!";
            if (allies.get(i-1).getStats().getCurrHP() <= 0)
                enemies.remove(allies.get(i-1));
        }
        else if (i > 0 && i <= allies.size() + enemies.size()) {
            enemies.get(i+allies.size()-1).getStats().setCurrHP(enemies.get(i+allies.size()-1).getStats().getCurrHP() - p.getWeapon().getWeaponDamage());
            s += enemies.get(i+allies.size()-1).getName() + " took " + p.getWeapon().getWeaponDamage() + " damage!";
            if (enemies.get(i+allies.size()-1).getStats().getCurrHP() <= 0)
                enemies.remove(i+allies.size()-1);
            if (enemies.isEmpty())
                won = true;
        }
        else
            s += "Miss!";
        return s;
    }

    /**
     * allow the player to take a turn
     * @return output
     */
    public String playerTurn(){
        //TODO playerTurn
        return null;
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
