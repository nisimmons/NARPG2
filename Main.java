import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
//up to date
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scr = new Scanner(System.in);
        String s;
        Player p;
        Map m;
        while(true) {
            p = null;
            switch (integerInput(scr, 1, 3, "1. New game\n2. Load save\n3. Quit")) {//new game
                case 1:
                    System.out.println("Enter Playername: ");
                    s = scr.nextLine();
                    //create player and map details
                    p = PlayController.createRandomPlayer(s);
                    m = PlayController.createRandomMap(50, s);
                    //play the game
                    playGame(new PlayController(p, m), scr);
                    //save the game
                    save(p, m);
                    break;
                case 2:
                    do {
                        //load game and play
                        //find and load the player
                        System.out.println("Enter playername (or * to return): ");
                        s = scr.nextLine();
                        if (s.compareTo("*") == 0)
                            break;
                        p = LoadSaveController.loadPlayer(s);
                    } while (p == null);
                    //load the map
                    m = LoadSaveController.loadMap(s);
                    //play the game
                    playGame(new PlayController(p, m), scr);
                    //save the game
                    save(p, m);
                    break;
                case 3:
                    //quit
                    System.exit(0);
            }
        }
    }
    public static void playGame(PlayController pc, Scanner scr){
        //interact with user and call pc to do stuff
        Player player = pc.getPlayer();
        Map map = pc.getMap();
        while(true) {
            switch (integerInput(scr, 1, 5, "1. Move\n2. Investigate Area\n3. Player info\n4. Print map\n5. Save + quit")) {
                case 1:
                    //move
                    Direction d;
                    switch (integerInput(scr, 1, 4, "1. North\n2. East\n3. South\n4. West")){
                        case 1:
                            d = Direction.NORTH;
                            break;
                        case 2:
                            d = Direction.EAST;
                            break;
                        case 3:
                            d = Direction.SOUTH;
                            break;
                        default:
                            d = Direction.WEST;
                            break;
                    }
                    if (pc.move(d)) {//try to move, print location or error message
                        Location loc = map.getLocation(player.getPosition());
                        System.out.println(loc);
                        if (loc instanceof Wilderness){
                            //check for and print enemies
                            if (((Wilderness) loc).getEnemies() != null && !((Wilderness) loc).getEnemies().isEmpty()) {
                                BattleController b = new BattleController(player, new ArrayList<>(), ((Wilderness) loc).getEnemies());
                                switch(battle(scr, player, b)){
                                    case 1:
                                        System.out.println("You Suck!");
                                        return; //return to main screen
                                    case 2:
                                        System.out.println("You Won!");
                                        System.out.println(player.addExp(b.getExpReward()));
                                        break; //continue playing
                                    case 3:
                                        System.out.println("You ran away...");
                                        break;
                                }
                            }
                        }
                        else if (loc instanceof Town){
                            //Heal player/restore mana
                            System.out.println(player.rest());
                            System.out.println("Would you like to purchase anything");
                            System.out.println(((Town) loc).getMerchant());
                            //TODO take user input and purchase items
                        }
                        else {
                            //it's a dungeon
                            if (((Dungeon)loc).battleCount() != 0) {
                                for (int i = 0; i < ((Dungeon) loc).battleCount(); i++) {
                                    BattleController b = new BattleController(player, new ArrayList<>(), ((Dungeon) loc).getBattle(i));
                                    switch (battle(scr, player, b)) {
                                        case 1:
                                            System.out.println("You Suck!");
                                            return; //return to main screen
                                        case 2:
                                            System.out.println("You won this battle");
                                            System.out.println(player.addExp(b.getExpReward()));
                                            break; //continue playing
                                        case 3:
                                            System.out.println("You ran away...");
                                            i = ((Dungeon) loc).battleCount();
                                            ((Dungeon) loc).cleanUp();
                                            break;
                                    }
                                }
                                if (((Dungeon)loc).battleCount() == 0) {
                                    System.out.println("You conquered the Dungeon!");
                                    ((Dungeon) loc).cleanUp();
                                    //TODO implement player winning stuff from the dungeon
                                }
                            }
                            else{
                                System.out.println("You've already conquered this Dungeon");
                            }
                        }
                        loc.setRevealed(true);
                    }
                    else
                        System.out.println("Area Impassable");
                    break;
                case 2:
                    //investigate area
                    System.out.println(map.getLocation(player.getPosition()));
                    break;
                case 3:
                    //print player info
                    System.out.println(player);
                    break;
                case 4:
                    System.out.println(map);
                    break;
                default:
                    return;
            }
        }
    }
    public static int battle(Scanner scr, Player player, BattleController b){
        while(!b.isWon()) {
            System.out.println(b.battleState());
            int i;
            switch (integerInput(scr, 1, 3, "Fight:\n1. Attack\n2. Spell\n3. Run")) {
                case 1:
                    //attack
                    while (true) {
                        System.out.println("Which will you attack?");
                        System.out.println(b.listEntities());
                        try {
                            i = Integer.parseInt(scr.nextLine());
                        } catch (Exception ignored) {
                            System.out.println("Invalid input");
                            continue;
                        }
                        System.out.println(b.attack(i, player.getWeapon()));
                        break;
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.println("What spell will you use?");
                        System.out.println(player.getInventory());
                        i = Integer.parseInt(scr.nextLine()) - 1;
                        if (player.getInventory().get(i) instanceof Spell)
                            break;
                        else
                            System.out.println("Not a spell");
                    }
                    //use the spell

                    Spell sp = (Spell) player.getInventory().get(i);

                    System.out.println("Which will you cast it on?");
                    System.out.println(b.listEntities());
                    try {
                        i = Integer.parseInt(scr.nextLine());
                    } catch (Exception ignored) {
                        continue;
                    }
                    System.out.println(b.attack(i, sp));

                    break;
                case 3:
                    //run
                    return 3;
            }
            System.out.println(b.entityTurn());

            if (player.getStats().getCurrHP() <= 0)
                return 1; //lose

        }
        return 2;
    }
    public static int integerInput(Scanner scr, int lower, int upper, String s) {
        int i = 0;
        do {
            System.out.println(s);
            try {
                i = Integer.parseInt(scr.nextLine());
            } catch (Exception ignored) {
            }
        } while (i < lower || i > upper);
        return i;
    }
    public static void save(Player p, Map m){
        LoadSaveController.savePlayer(p);
        LoadSaveController.saveMap(m);
    }
}
