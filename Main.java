import java.util.ArrayList;
import java.util.Scanner;

//up to date
public class Main {
    static final int WAITTIME = 300;
    public static void main(String[] args) {
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
                    if (playGame(new PlayController(p, m), scr) == 0)
                        //save the game
                        save(p, m);
                    break;
                case 3:
                    //quit
                    System.exit(0);
            }
        }
    }
    public static int playGame(PlayController pc, Scanner scr){
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
                    if (pc.move(d)) {//try to move, print location or error messaged
                        Location loc = map.getLocation(player.getPosition());
                        System.out.println(loc);
                        if (loc instanceof Wilderness){
                            //check for and print enemies
                            if (((Wilderness) loc).getEnemies() != null && !((Wilderness) loc).getEnemies().isEmpty()) {
                                BattleController b = new BattleController(player, new ArrayList<>(), ((Wilderness) loc).getEnemies());
                                switch(battle(scr, player, b)){
                                    case 1:
                                        System.out.println("You Suck!");
                                        return 1; //return to main screen
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
                            System.out.println("You enter the town.");
                            switch(integerInput(scr, 1, 3, "1. Inn\n2. Market\n3. Guild Hall")){
                                //TODO paying for inns, buying at market, quests at guild hall
                                case 1: //Inn
                                    System.out.println(player.rest());
                                    //TODO respawn enemies?
                                    break;
                                case 2: //Market
                                    System.out.println("Would you like to purchase anything");
                                    System.out.println(((Town) loc).getMerchant());
                                    break;
                                case 3: //Guild Hall
                                    System.out.println("There is nothing here yet");
                                    break;
                                default:
                                    break;
                            }
                        }
                        else {
                            //it's a dungeon
                            if (((Dungeon)loc).battleCount() != 0) {
                                for (int i = 0; i < ((Dungeon) loc).battleCount(); i++) {
                                    BattleController b = new BattleController(player, new ArrayList<>(), ((Dungeon) loc).getBattle(i));
                                    switch (battle(scr, player, b)) {
                                        case 1:
                                            System.out.println("You Suck!");
                                            return 1; //return to main screen
                                        case 2:
                                            System.out.println("You won this battle");
                                            try {
                                                Thread.sleep(WAITTIME);
                                            } catch (InterruptedException ignored) {}
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
                                    try {
                                        Thread.sleep(WAITTIME);
                                    } catch (InterruptedException ignored) {}
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
                    System.out.println("Inventory");
                    for (String s : player.getInventory().toString().split("\n")) {
                        System.out.println(s);
                        try {
                            Thread.sleep(WAITTIME*2/3);
                        } catch (InterruptedException ignored) {}
                    }
                    System.out.println();
                    break;
                case 4:
                    System.out.println(map);
                    break;
                default:
                    return 0;
            }
        }
    }
    public static int battle(Scanner scr, Player player, BattleController b){
        while(!b.isWon()) {
            for (String s :b.battleState()) {
                System.out.println(s);
                try {
                    Thread.sleep(WAITTIME);
                } catch (InterruptedException ignored) {}
            }
            //System.out.println(b.battleState());
            int i;
            switch (integerInput(scr, 1, 3, "Fight:\n1. Attack\n2. Spell\n3. Run")) {
                case 1:
                    //attack
                    while (true) {
                        System.out.println("Which will you attack?");
                        for (String s :b.listEntities()) {
                            System.out.println(s);
                            try {
                                Thread.sleep(WAITTIME);
                            } catch (InterruptedException ignored) {}
                        }
                        try {
                            i = Integer.parseInt(scr.nextLine());
                        } catch (Exception ignored) {
                            System.out.println("Invalid input");
                            continue;
                        }
                        for (String s :b.attack(i,player.getWeapon())) {
                            System.out.println(s);
                            try {
                                Thread.sleep(WAITTIME);
                            } catch (InterruptedException ignored) {}
                        }
                        //System.out.println(b.attack(i, player.getWeapon()));
                        break;
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.println("What spell will you use?");
                        for (String s : player.getInventory().toString().split("\n")) {
                            System.out.println(s);
                            try {
                                Thread.sleep(WAITTIME);
                            } catch (InterruptedException ignored) {}
                        }
                        i = Integer.parseInt(scr.nextLine()) - 1;
                        if (player.getInventory().get(i) instanceof Spell)
                            break;
                        else
                            System.out.println("Not a spell");
                    }
                    //use the spell

                    Spell sp = (Spell) player.getInventory().get(i);

                    System.out.println("Which will you cast it on?");
                    try {
                        Thread.sleep(WAITTIME);
                    } catch (InterruptedException ignored) {}
                    for (String s :b.listEntities()) {
                        System.out.println(s);
                        try {
                            Thread.sleep(WAITTIME);
                        } catch (InterruptedException ignored) {}
                    }
                    try {
                        i = Integer.parseInt(scr.nextLine());
                    } catch (Exception ignored) {
                        continue;
                    }
                    for (String s :b.attack(i,sp)) {
                        System.out.println(s);
                        try {
                            Thread.sleep(WAITTIME);
                        } catch (InterruptedException ignored) {}
                    }
                    //System.out.println(b.attack(i, sp));
                    break;
                case 3:
                    //run
                    return 3;
            }
            try {
                Thread.sleep(WAITTIME);
            } catch (InterruptedException ignored) {}
            for(String s:b.entityTurn()) {
                System.out.println(s);
                try {
                    Thread.sleep(WAITTIME);
                } catch (InterruptedException ignored) {
                }
            }
            if (player.getStats().getCurrHP() <= 0)
                return 1; //lose
        }
        return 2;
    }

    /**
     * Prints screen to the player and gets input
     * @param scr Scanner
     * @param lower Lower bound (inclusive)
     * @param upper Upper bound (inclusive)
     * @param s String to be printed
     * @return User input
     */
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

    /**
     * save player and map data to file
     * @param p player
     * @param m map
     */
    public static void save(Player p, Map m){
        LoadSaveController.savePlayer(p);
        LoadSaveController.saveMap(m);
    }
}
