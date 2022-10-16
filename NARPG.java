import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//up to date
public class NARPG {
    static final int WAITTIME = 300;
    static final Scanner scr = new Scanner(System.in);
    Map map;
    Player player;

    public NARPG(){}

    /**
     * Main function which allows the player to log in or create new,
     * play the game, save and quit
     */
    public void mainLoop() {
        String s;
        while(true) {
            player = null;
            switch (integerInput(1, 3, "1. New game\n2. Load save\n3. Quit")) {//new game
                case 1:
                    System.out.println("Enter Playername: ");
                    s = scr.nextLine();

                    //create player and map details
                    player = new Player(s);
                    player.randomize();
                    map = new Map(s);
                    map.randomize();
                    PlayController pc = new PlayController(player, map);
                    pc.respawn(true);

                    //play the game
                    if(playGame(pc) == 0)
                        save();
                    break;
                case 2:
                    //find and load the player and map
                    do {
                        System.out.println("Enter playername (or * to return): ");
                        s = scr.nextLine();
                        if (s.compareTo("*") == 0)
                            break;
                        player = LoadSaveController.loadPlayer(s);
                    } while (player == null);
                    map = LoadSaveController.loadMap(s);

                    //play the game
                    if (playGame(new PlayController(player, map)) == 0)
                        save();
                    break;
                case 3:
                    //quit
                    System.exit(0);
            }
        }
    }

    /**
     * Function for the gameplay loop. Lets the player choose what
     * to do at each location and calls PlayController and BattleController
     * functions accordingly.
     * @param pc PlayController object
     * @return 1 if lost, 0 to save and return to main
     */
    public int playGame(PlayController pc){
        //interact with user and call pc to do stuff
        while(true) { //main play loop
            Location loc = map.getLocation(player.getPosition());
            switch(loc.getFaction()) { //TODO improve location message. Put this switch in location and make it more specific
                case TOWN:
                    System.out.println("There is a town nearby");
                    break;
                case DUNGEON:
                    System.out.println("There is a Dungeon nearby");
                    break;
                case FINALDUNGEON:
                    System.out.println("The Demon King's Castle is nearby");
                    break;
                default:
                    System.out.println("There is a " + loc.getFaction().toString().toLowerCase() + " nearby");
                    break;
            }
            loc.setRevealed(true);
            switch (integerInput(1, 5, "1. Move\n2. Investigate Area\n3. Player info\n4. Print map\n5. Save + quit")) {
                case 1: //move
                    Direction d;
                    switch (integerInput(1, 4, "1. North\n2. East\n3. South\n4. West")){
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
                    if(!pc.move(d))//try to move
                        System.out.println("Area Impassable");
                    break;
                case 2: //investigate area
                    if (loc instanceof Wilderness){
                        //check for and print enemies
                        if (((Wilderness) loc).getEnemies() != null && !((Wilderness) loc).getEnemies().isEmpty()) {
                            BattleController b = new BattleController(player, new ArrayList<>(), ((Wilderness) loc).getEnemies());
                            switch(battle(b)){
                                case 1:
                                    System.out.println("You Suck!");
                                    return 1; //return to main screen
                                case 2:
                                    System.out.println("You Won!");
                                    System.out.println(player.addExp(b.getExpReward()));
                                    player.addGold(b.getExpReward()/3);

                                    Item reward = battleRewards(loc.getLevel(), loc.getFaction());
                                    if (reward != null) {
                                        System.out.println("You received a " + reward.getName() + "!");
                                        player.getInventory().add(reward);
                                    }
                                    break; //continue playing
                                case 3:
                                    System.out.println("You ran away...");
                                    break;
                            }
                        }
                    }
                    else if (loc instanceof Town) {
                        System.out.println("You enter the town.");
                        while(true) {
                            int choice = integerInput(1, 4, "1. Inn\n2. Market\n3. Guild Hall\n4. Leave Town");
                            switch (choice) {
                                case 1: //Inn
                                    System.out.println(player.rest());
                                    pc.respawn();
                                    break;
                                case 2: //Market
                                    System.out.println("\t\t*****MARKET*****\n#\tName\t\t\t\t\tGold\n" + "0\tNothing");
                                    Inventory inv = (((Town) loc).getMerchant()); //TODO ignore items the player already has. Allow user to sell items
                                    for (int i = 0; i < inv.size(); i++) {
                                        pause(WAITTIME/2);
                                        System.out.printf("%d\t%-20s\t%d\n", i + 1, inv.get(i).getName(), (int) (Math.floor(inv.get(i).getLevel() * 15)));
                                    }
                                    System.out.print("\nYour Gold:\t\t\t\t\t" + player.getGold() + "\nWould you like to purchase anything?");
                                    int i = integerInput(0, ((Town) loc).getMerchant().size());
                                    if (i != 0) {
                                        if (player.getGold() >= inv.get(i - 1).getLevel() * 15) {
                                            System.out.println("You Purchased " + (inv.get(i - 1)));
                                            player.getInventory().add(inv.take(i - 1));
                                        } else
                                            System.out.println("Not enough gold!");
                                    }
                                    break;
                                case 3: //Guild Hall
                                    System.out.println("There is nothing here yet");
                                    break;
                                case 4:
                                    break;
                            }
                            if (choice == 4)
                                break;
                        }
                    }
                    else {
                        //it's a dungeon
                        if (((Dungeon)loc).battleCount() != 0) {
                            System.out.println("You enter the dungeon...");
                            for (int i = 0; i < ((Dungeon) loc).battleCount(); i++) {
                                BattleController b = new BattleController(player, new ArrayList<>(), ((Dungeon) loc).getBattle(i));
                                switch (battle(b)) {
                                    case 1:
                                        System.out.println("You Suck!");
                                        return 1; //return to main screen
                                    case 2:
                                        System.out.println("You won this battle");
                                        try {
                                            Thread.sleep(WAITTIME);
                                        } catch (InterruptedException ignored) {}
                                        System.out.println(player.addExp(b.getExpReward()));
                                        player.addGold(b.getExpReward()/2);
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
                                //TODO Make sure battles are cleaned up

                                Item reward = battleRewards(loc.getLevel(), Faction.DUNGEON);
                                System.out.println("You received a " + reward.getName() + "!");
                                player.getInventory().add(reward);

                            }
                        }
                        else{
                            System.out.println("You've already conquered this Dungeon");
                        }
                    }
                    break;
                case 3:
                    //print player info
                    System.out.println("**** Player Status ****");
                    System.out.println(player);
                    System.out.println();
                    System.out.println("  **** Inventory ****");
                    for (String s : player.getInventory().toString().split("\n")) {
                        System.out.println(s);
                        pause(WAITTIME/2);
                    }
                    //TODO ask user to equip items
                    System.out.println();
                    break;
                case 4:
                    System.out.println(map);
                    break;
                default:
                    return 0; //save + quit
            }
        }
    }

    /**
     * Conducts a battle event
     * @param b BattleController object
     * @return 1 if lost, 2 if won, 3 if run
     */
    public int battle(BattleController b){
        System.out.println("**** BATTLE START! ****");
        while(!b.isWon()) {
            for (String s :b.battleState()) {
                System.out.println(s);
                try {
                    Thread.sleep(WAITTIME);
                } catch (InterruptedException ignored) {}
            }
            System.out.println("*********");
            int i;
            switch (integerInput(1, 3, "Fight:\n1. Attack\n2. Spell\n3. Run")) {
                case 1:
                    //attack
                    while (true) {
                        System.out.println("Which will you attack?");
                        for (String s :b.listEntities()) {
                            System.out.println(s);
                            try {Thread.sleep(WAITTIME);} catch (InterruptedException ignored) {}
                        }
                        try {
                            i = Integer.parseInt(scr.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid input");
                            continue;
                        }
                        for (String s :b.attack(i,player.getWeapon())) {
                            System.out.println(s);
                            try {Thread.sleep(WAITTIME);} catch (InterruptedException ignored) {}
                        }
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
            try {Thread.sleep(WAITTIME);} catch (InterruptedException ignored) {}
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
     * Finds Item to be rewarded
     * @param level Area Level
     * @return Level Appropriate Item of random type
     */
    public Item battleRewards(int level, Faction faction) {
        Item itemReward = null;
        int modifier = 0;

        switch (faction)
        {
            case DUNGEON:
            {
                modifier = 5;
                break;
            }
            /*
            case FINALDUNGEON:
            {
                // TODO: figure out if there are even items 10 levels above final boss level
                modifier = 10;
                break;
            }
            */
        }

        int low = level + modifier;
        int high = level + 5 + modifier;

        // Get item list for range +=5 of zone (maybe modifier for rewards on dungeon?)
        ArrayList<Item> arr = DataAccess.produceItemList(low, high);

        Random random = new Random();
        // Get rand int for type, weapon armor etc, 1-10
        int rand = (int) (random.nextInt(10)) + 1;

        // Make separate item type arrays
        ArrayList<Item> weapon = new ArrayList<>();
        ArrayList<Item> armor = new ArrayList<>();
        ArrayList<Item> spell = new ArrayList<>();

        // Fill arrays
        for (int j =0; j < arr.size(); j++)
        {
            if (arr.get(j) instanceof Weapon)
            {
                weapon.add(arr.get(j));
            }
            else if (arr.get(j) instanceof Armor)
            {
                armor.add(arr.get(j));
            }
            else if (arr.get(j) instanceof Spell)
            {
                spell.add(arr.get(j));
            }
        }

        if (faction != Faction.DUNGEON) // Faction is overworld
        {
            switch (rand)
            {
                case 5:
                case 6:
                {
                    rand = (int) (Math.random() * weapon.size());
                    itemReward = weapon.get(rand);
                    break;
                }
                case 7:
                case 8:
                {
                    rand = (int) (Math.random() * armor.size());
                    itemReward = armor.get(rand);
                    break;
                }
                case 9:
                case 10:
                {
                    rand = (int) (Math.random() * spell.size());
                    itemReward = spell.get(rand);
                    break;
                }
            }
        }
        else // Faction is dungeon or final dungeon
        {
            switch (rand)   //TODO switch to if else
            {
                case 1:
                case 2:
                case 3:
                case 4:
                {
                    rand = (int) (Math.random() * weapon.size());
                    itemReward = weapon.get(rand);
                    break;
                }
                case 5:
                case 6:
                case 7:
                {
                    rand = (int) (Math.random() * armor.size());
                    itemReward = armor.get(rand);
                    break;
                }
                case 8:
                case 9:
                case 10:
                {
                    rand = (int) (Math.random() * spell.size());
                    itemReward = spell.get(rand);
                    break;
                }
            }

        }

        return itemReward;
    }

    public int integerInput(int lower, int upper) {return integerInput(lower, upper, "");}
    /**
     * Prints screen to the player and gets input
     * @param lower Lower bound (inclusive)
     * @param upper Upper bound (inclusive)
     * @param s String to be printed
     * @return User input
     */
    public int integerInput(int lower, int upper, String s) {
        int i = -1;
        do {
            //System.out.println(s);
            for (String st : s.split("\n")) {
                System.out.println(st);
                pause(WAITTIME/3);
            }
            try {
                i = Integer.parseInt(scr.nextLine());
            } catch (Exception ignored) {}
        } while (i < lower || i > upper);
        return i;
    }

    /**
     * save player and map data to file
     */
    public void save(){
        LoadSaveController.savePlayer(player);
        LoadSaveController.saveMap(map);
    }
    public void pause(){pause(WAITTIME);}
    public void pause(int time){try {Thread.sleep(time);} catch (InterruptedException ignored) {}}
}
