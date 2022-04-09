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

    /**
     * allows the player to take a turn
     */
    public void playerTurn(){
        //TODO playerTurn

    }

    /**
     * has entities other than the player take a turn
     */
    public void entityTurn(){
        //TODO entityTurn
    }
}
