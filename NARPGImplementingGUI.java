import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//up to date
public class NARPGImplementingGUI {
    static final int WAITTIME = 300;
    static final Scanner scr = new Scanner(System.in);
    BattleController bc;
    PlayController pc;
    GraphicalUserInterface gui;
    Dimension standardDimension = new Dimension(300,60);

    public NARPGImplementingGUI() {
        gui = new GraphicalUserInterface(1280,720,"NARPG");
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(300,60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> {addMainMenuButtons();});
        button.setText("Start game");
        gui.addToBottom(button);
        gui.redraw();
    }

    public void addMainMenuButtons(){
        System.out.println("DEBUG: Main menu");
        gui.clear();
        JButton button1 = new JButton();
        button1.setPreferredSize(standardDimension);
        button1.setAlignmentY(Component.CENTER_ALIGNMENT);
        button1.addActionListener(e -> {
            newGameMenu();});
        button1.setText("New game");
        gui.addToBottom(button1);

        JButton button2 = new JButton();
        button2.setPreferredSize(standardDimension);
        button2.setAlignmentY(Component.CENTER_ALIGNMENT);
        button2.addActionListener(e -> loadGameMenu());
        button2.setText("Load game");
        gui.addToBottom(button2);

        JButton button3 = new JButton();
        button3.setPreferredSize(standardDimension);
        button3.setAlignmentY(Component.CENTER_ALIGNMENT);
        button3.addActionListener(e -> {
            System.out.println("DEBUG: Quit");
            System.exit(0);
        });
        button3.setText("Quit");
        gui.addToBottom(button3);
        gui.redraw();
    }


    public void newGameMenu(){
        System.out.println("DEBUG: New game");
        gui.clear();

        JButton backbutton = new JButton();
        backbutton.setPreferredSize(new Dimension(50,50));
        backbutton.addActionListener(e -> {addMainMenuButtons();});
        backbutton.setText("Back");
        backbutton.setAlignmentX(Component.LEFT_ALIGNMENT);
        gui.addToBottom(backbutton);

        JLabel label = new JLabel("Enter your name: ");
        label.setBackground(Color.gray);

        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(200,50));

        JButton button = new JButton("Submit");
        button.setPreferredSize(standardDimension);
        button.addActionListener(e1 -> {
            System.out.println("DEBUG: Submitted Playername");
            String name = textField.getText();
            gui.clear();
            Player player = new Player(name);
            player.randomize();
            System.out.println("DEBUG: Playername: " + player.getName());
            Map map = new Map(player.getName());
            map.randomize();
            pc = new PlayController(player, map);
            pc.respawn(true);
            if (NARPGImplementingGUI.this.playGame(pc) == 0)
                NARPGImplementingGUI.this.save();
        });
        button.setHorizontalAlignment(JButton.CENTER);
        button.setVerticalAlignment(JButton.CENTER);

        gui.addToTop(label);
        gui.addToBottom(textField);
        gui.addToBottom(button);
        gui.redraw();
    }
    public void loadGameMenu(){
        System.out.println("DEBUG: Load game");
        gui.clear();

        JButton backbutton = new JButton();
        backbutton.setPreferredSize(new Dimension(50,50));
        backbutton.addActionListener(e -> {addMainMenuButtons();});
        backbutton.setText("Back");
        gui.addToBottom(backbutton);

        JLabel label = new JLabel("Enter your name: ");
        label.setBackground(Color.gray);
        label.setPreferredSize(new Dimension(100,100));

        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(200,50));

        JButton button = new JButton("Submit");
        button.setPreferredSize(standardDimension);
        button.addActionListener(e1 -> {
            System.out.println("DEBUG: Submitted Playername");
            String name = textField.getText();
            System.out.println("DEBUG: Playername: \"" + name + "\"");
            gui.clear();
            Player player = LoadSaveController.loadPlayer(name);
            Map map = LoadSaveController.loadMap(name);
            pc = new PlayController(player, map);
            playGame(pc);
        });
        button.setHorizontalAlignment(JButton.CENTER);
        button.setVerticalAlignment(JButton.CENTER);

        gui.addToTop(label);
        gui.addToBottom(textField);
        gui.addToBottom(button);
        gui.redraw();
    }



    public int playGame(PlayController pc) {
        gui.clear();


        //main play loop
        Location loc = pc.map.getLocation(pc.player.getPosition());
        switch (loc.getFaction()) {
            case Town:
                System.out.println("There is a town nearby");
                gui.addTopText("There is a town nearby");
                break;
            case Dungeon:
                System.out.println("There is a Dungeon nearby");
                gui.addTopText("There is a Dungeon nearby");
                break;
            case FinalDungeon:
                System.out.println("The Demon King's Castle is nearby");
                gui.addTopText("The Demon King's Castle is nearby");
                break;
            default:
                System.out.println("There is a " + loc.getFaction().toString().toLowerCase() + " nearby");
                gui.addTopText("There is a " + loc.getFaction().toString().toLowerCase() + " nearby");
                break;
        }
        loc.setRevealed(true);
        JButton moveButton = new JButton("Move");
        moveButton.setPreferredSize(new Dimension(100, 50));
        moveButton.addActionListener(e -> {
            gui.clear();
            gui.addTopText("Where would you like to move?");

            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(100, 50));
            backButton.addActionListener(e1 -> {
                playGame(pc);
            });
            gui.addToBottom(backButton);
            JButton northButton = new JButton("North");
            northButton.setPreferredSize(new Dimension(100, 50));
            northButton.addActionListener(e1 -> {
                if (!pc.move(Direction.NORTH))
                    System.out.println("Area Impassable");
                playGame(pc);
            });
            gui.addToBottom(northButton);
            JButton southButton = new JButton("South");
            southButton.setPreferredSize(new Dimension(100, 50));
            southButton.addActionListener(e1 -> {
                if (!pc.move(Direction.SOUTH))
                    System.out.println("Area Impassable");
                playGame(pc);
            });
            gui.addToBottom(southButton);
            JButton eastButton = new JButton("East");
            eastButton.setPreferredSize(new Dimension(100, 50));
            eastButton.addActionListener(e1 -> {
                if (!pc.move(Direction.EAST))
                    System.out.println("Area Impassable");
                playGame(pc);
            });
            gui.addToBottom(eastButton);
            JButton westButton = new JButton("West");
            westButton.setPreferredSize(new Dimension(100, 50));
            westButton.addActionListener(e1 -> {
                if (!pc.move(Direction.WEST))
                    System.out.println("Area Impassable");
                playGame(pc);
            });
            gui.addToBottom(westButton);
            gui.redraw();
        });
        gui.addToBottom(moveButton);

        JButton investigateButton = new JButton("Investigate Area");
        investigateButton.setPreferredSize(new Dimension(100, 50));
        investigateButton.addActionListener(e -> {
            System.out.println("Investigating area...");
            investigateArea(pc);
        });
        gui.addToBottom(investigateButton);

        JButton playerInfoButton = new JButton("Player Info");
        playerInfoButton.setPreferredSize(new Dimension(100, 50));
        playerInfoButton.addActionListener(e -> {
            gui.clearTop();
            System.out.println("Printing player info...");
            for(String s: pc.player.toString().split("\n")) //TODO let user equip items
                gui.addTopText(s);
            gui.redraw();

            /*
            case 3:
                //print player info
                System.out.println("**** Player Status ****");
                System.out.println(player);
                System.out.println();
                System.out.println("  **** Inventory ****\n");
                //allow the user to equip items from their inventory
                for (int i = 0; i < player.getInventory().size(); i++) {
                    pause(WAITTIME / 2);
                    System.out.printf("%d\t%-20s\t%d\n", i + 1, player.getInventory().get(i).getName(), player.getInventory().get(i).getLevel());
                }
                System.out.print("\nEnter number to equip item or 0 to go back");
                int i = integerInput(0, (player.getInventory().size()));
                if (i != 0) {
                    Item item = player.getInventory().get(i-1);
                    if(item instanceof Armor){
                        Armor oldArmor = player.getArmor();
                        player.setArmor((Armor) item);
                        player.getInventory().remove(i-1);
                        player.getInventory().add(oldArmor);
                    }
                    else if(item instanceof Weapon){
                        Weapon oldWeapon = player.getWeapon();
                        player.setWeapon((Weapon) item);
                        player.getInventory().remove(i-1);
                        player.getInventory().add(oldWeapon);
                    }
                    else{
                        System.out.println("Cannot equip");
                    }
                }
                System.out.println();
                break;
             */
        });
        gui.addToBottom(playerInfoButton);

        JButton printMapButton = new JButton("Print Map");
        printMapButton.setPreferredSize(new Dimension(100, 50));
        printMapButton.addActionListener(e -> {
            gui.clearTop();
            System.out.println("Printing map...");
            gui.showMap(pc.map);
            //for(String s: pc.map.toString().split("\n")) //TODO fix map drawing. Implement 2d graphic?
            //    gui.addTopText(s);
            gui.redraw();
        });
        gui.addToBottom(printMapButton);

        JButton saveButton = new JButton("Save + Quit");
        saveButton.setPreferredSize(new Dimension(100, 50));
        saveButton.addActionListener(e -> {
            save();
            addMainMenuButtons();
        });
        gui.addToBottom(saveButton);


        gui.redraw();
        return 1;
    }


    public void investigateArea(PlayController pc) {
        Location loc = pc.map.getLocation(pc.player.getPosition());
        if (loc instanceof Wilderness){
            //check for and print enemies
            if (((Wilderness) loc).getEnemies() != null && !((Wilderness) loc).getEnemies().isEmpty()) {
                bc = new BattleController(pc.player, new ArrayList<>(), ((Wilderness) loc).getEnemies());
                gui.clear();
                battleButtons(pc, loc);
            }
        }
        else if (loc instanceof Town) {
            gui.clear();
            gui.addTopText("You enter the town.");
            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(100, 50));
            backButton.addActionListener(e -> {
                playGame(pc);
            });
            gui.addToBottom(backButton);
            JButton innButton = new JButton("Inn");
            innButton.setPreferredSize(new Dimension(100, 50));
            innButton.addActionListener(e -> {
                gui.clearTop();
                gui.addTopText("You rest here.");
                System.out.println(pc.player.rest());
                pc.respawn();
                gui.redraw();
            });
            gui.addToBottom(innButton);
            JButton marketButton = new JButton("Market");
            marketButton.setPreferredSize(new Dimension(100, 50));
            marketButton.addActionListener(e -> {
                System.out.println("\t\t*****MARKET*****\n#\tName\t\t\t\t\tGold\n" + "0\tNothing");
                Inventory merchantInventory = (((Town) loc).getMerchant());
                Inventory playerInventory = pc.player.getInventory();
                for (int i = 0; i < merchantInventory.size(); i++) {
                    pause(WAITTIME / 2);
                    System.out.printf("%d\t%-20s\t%d\n", i + 1, merchantInventory.get(i).getName(), (int) (Math.random() * 100));
                }
                int marketChoice = integerInput(0, merchantInventory.size(), "What would you like to buy?");
                if (marketChoice == 0) {
                    System.out.println("You leave the market.");
                } else {
                    Item item = merchantInventory.get(marketChoice - 1);
                    if (pc.player.getGold() >= item.getCost()) {
                        System.out.println("You bought a " + item.getName() + " for " + item.getCost() + " gold.");
                        pc.player.getInventory().add(item);
                        pc.player.setGold(pc.player.getGold() - item.getCost());
                    } else {
                        System.out.println("You don't have enough gold!");
                    }
                }
            });
            gui.addToBottom(marketButton);
            JButton guildButton = new JButton("Guild Hall");
            guildButton.setPreferredSize(new Dimension(100, 50));
            guildButton.addActionListener(e -> {
                gui.clearTop();
                gui.addTopText("There is nothing here yet.");
                gui.redraw();
            });
            gui.addToBottom(guildButton);
            gui.redraw();

        }
        else {
            //TODO implement dungeons
            //it's a dungeon
            if (((Dungeon)loc).battleCount() != 0) {
                System.out.println("You enter the dungeon...");
                for (int i = 0; i < ((Dungeon) loc).battleCount(); i++) {
                    BattleController b = new BattleController(pc.player, new ArrayList<>(), ((Dungeon) loc).getBattle(i));
                    switch (battle(b)) {
                        case 1:
                            System.out.println("You Suck!");
                            addMainMenuButtons();
                        case 2:
                            System.out.println("You won this battle");
                            try {
                                Thread.sleep(WAITTIME);
                            } catch (InterruptedException ignored) {}
                            System.out.println(pc.player.addExp(b.getExpReward()));
                            System.out.println(pc.player.addGold(b.getExpReward()/2));
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
                    Item reward = battleRewards(loc.getLevel(), Faction.Dungeon);
                    System.out.println("You received a " + reward.getName() + "!");
                    pc.player.getInventory().add(reward);

                }
            }
            else{
                System.out.println("You've already conquered this Dungeon");
            }
        }
    }

    private void battleButtons(PlayController pc, Location loc) {
        gui.clearBottom();
        for(String s: bc.battleState()){
            gui.addTopText(s);
        }
        JButton attackButton = new JButton("Attack");
        attackButton.setPreferredSize(new Dimension(100, 50));
        attackButton.addActionListener(e -> {
            gui.clear();
            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(100, 50));
            backButton.addActionListener(e1 -> {
                battleButtons(pc, loc);
            });
            gui.addToBottom(backButton);
            ArrayList<String> entities = bc.listEntities();
            for (int i = 0; i < entities.size(); i++) {
                String s = entities.get(i);
                gui.addTopText(s);
                JButton button = new JButton(String.valueOf(i));
                button.setPreferredSize(new Dimension(100, 50));
                button.addActionListener(e1 -> {
                    gui.clearTop();
                    for(String s1: bc.attack(Integer.parseInt(((JButton) e1.getSource()).getText()), pc.player.getWeapon())){
                        gui.addTopText(s1);
                    }
                    for(String s1: bc.entityTurn()){
                        gui.addTopText(s1);
                    }
                    if (pc.player.getStats().getCurrHP() <= 0){
                        gui.clearBottom();
                        gui.addTopText("You died.");
                        JButton okayButton = new JButton("Understood");
                        okayButton.setPreferredSize(new Dimension(100, 50));
                        okayButton.addActionListener(e2 -> {
                            gui.clear();
                            addMainMenuButtons();
                        });
                        gui.addToBottom(okayButton);
                        gui.redraw();
                    }
                    else if(bc.isWon()){
                        gui.clearBottom();
                        gui.addTopText("You won the battle!");
                        gui.addTopText(pc.player.addExp(bc.getExpReward()));
                        pc.player.addGold(bc.getExpReward()/2);
                        Item reward = battleRewards(loc.getLevel(), loc.getFaction());
                        if (reward != null) {
                            gui.addTopText("You received a " + reward.getName() + "!");
                            pc.player.getInventory().add(reward);
                        }
                        JButton okayButton = new JButton("Understood");
                        okayButton.setPreferredSize(new Dimension(100, 50));
                        okayButton.addActionListener(e2 -> {
                            gui.clear();
                            playGame(pc);
                        });
                        gui.addToBottom(okayButton);
                        gui.redraw();
                    }
                    else
                        battleButtons(pc, loc);
                });
                gui.addToBottom(button);
            }
            gui.redraw();
        });
        gui.addToBottom(attackButton);
        JButton spellButton = new JButton("Spell");
        spellButton.setPreferredSize(new Dimension(100, 50));
        spellButton.addActionListener(e -> {
            System.out.println("There is nothing here yet."); //TODO add spell functionality

            /*
            System.out.println("What spell will you use?");
                        for (String s : pc.player.getInventory().toString().split("\n")) {
                            System.out.println(s);
                            try {
                                Thread.sleep(WAITTIME);
                            } catch (InterruptedException ignored) {}
                        }
                        i = Integer.parseInt(scr.nextLine()) - 1;
                        if (pc.player.getInventory().get(i) instanceof Spell)
                            break;
                        else
                            System.out.println("Not a spell");
                    }
                    //use the spell

                    Spell sp = (Spell) pc.player.getInventory().get(i);

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
             */
        });
        gui.addToBottom(spellButton);
        JButton runButton = new JButton("Run");
        runButton.setPreferredSize(new Dimension(100, 50));
        runButton.addActionListener(e -> {
            playGame(pc);
        });
        gui.addToBottom(runButton);
        gui.redraw();
    }




    /**
     * Function for the gameplay loop. Lets the player choose what
     * to do at each location and calls PlayController and BattleController
     * functions accordingly.
     * @param pc PlayController object
     * @return 1 if lost, 0 to save and return to main
     */
    public int playGame1(PlayController pc){

        while(true) { //main play loop
            Location loc = pc.map.getLocation(pc.player.getPosition());
            switch(loc.getFaction()) {
                case Town:
                    System.out.println("There is a town nearby");
                    break;
                case Dungeon:
                    System.out.println("There is a Dungeon nearby");
                    break;
                case FinalDungeon:
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
                            BattleController b = new BattleController(pc.player, new ArrayList<>(), ((Wilderness) loc).getEnemies());
                            switch(battle(b)){
                                case 1:
                                    System.out.println("You Suck!");
                                    return 1; //return to main screen
                                case 2:
                                    System.out.println("You Won!");
                                    System.out.println(pc.player.addExp(b.getExpReward()));
                                    pc.player.addGold(b.getExpReward()/2);

                                    Item reward = battleRewards(loc.getLevel(), loc.getFaction());
                                    if (reward != null) {
                                        System.out.println("You received a " + reward.getName() + "!");
                                        pc.player.getInventory().add(reward);
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
                                    System.out.println(pc.player.rest());
                                    pc.respawn();
                                    break;
                                case 2: //Market
                                    int marketChoice = integerInput(1, 2, "1. Buying\n2. Selling\n");
                                    Inventory merchantInventory = (((Town) loc).getMerchant());
                                    Inventory playerInventory = pc.player.getInventory();
                                    if (marketChoice == 1) {
                                        System.out.println("\t\t*****MARKET*****\n#\tName\t\t\t\t\tGold\n" + "0\tNothing");
                                        merchantInventory.remove(playerInventory);
                                        for (int i = 0; i < merchantInventory.size(); i++) {
                                            pause(WAITTIME / 2);
                                            System.out.printf("%d\t%-20s\t%d\n", i + 1, merchantInventory.get(i).getName(), (int) (Math.floor(merchantInventory.get(i).getCost())));
                                        }
                                        System.out.print("\nYour Gold:\t\t\t\t\t" + pc.player.getGold() + "\nWould you like to purchase anything?");
                                        int i = integerInput(0, ((Town) loc).getMerchant().size());
                                        if (i != 0) {
                                            if (pc.player.getGold() >= merchantInventory.get(i - 1).getCost()) {
                                                System.out.println("You Purchased " + (merchantInventory.get(i - 1)));
                                                playerInventory.add(merchantInventory.take(i - 1));
                                                pc.player.setGold(pc.player.getGold()-merchantInventory.get(i - 1).getCost());
                                            } else
                                                System.out.println("Not enough gold!");
                                        }
                                        break;
                                    } else if (marketChoice == 2)
                                    {
                                        System.out.println("\t\t****INVENTORY****\n#\tName\t\t\t\t\tGold\n" + "0\tNothing");
                                        for (int i = 0; i < playerInventory.size(); i++) {
                                            pause(WAITTIME / 2);
                                            System.out.printf("%d\t%-20s\t%d\n", i + 1, playerInventory.get(i).getName(), (int) (Math.floor(playerInventory.get(i).getCost()*.6)));
                                        }
                                        System.out.print("\nYour Gold:\t\t\t\t\t" + pc.player.getGold() + "\nWould you like to sell anything?");
                                        int i = integerInput(0, (playerInventory.size()));
                                        if (i != 0) {
                                            pc.player.addGold((int) (playerInventory.get(i-1).getCost()*.6));
                                            System.out.println("You Sold " + (playerInventory.get(i-1)));
                                            merchantInventory.add(playerInventory.take(i-1));
                                            //player.getInventory().remove(i-1);
                                        }
                                        break;
                                    }
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
                                BattleController b = new BattleController(pc.player, new ArrayList<>(), ((Dungeon) loc).getBattle(i));
                                switch (battle(b)) {
                                    case 1:
                                        System.out.println("You Suck!");
                                        return 1; //return to main screen
                                    case 2:
                                        System.out.println("You won this battle");
                                        try {
                                            Thread.sleep(WAITTIME);
                                        } catch (InterruptedException ignored) {}
                                        System.out.println(pc.player.addExp(b.getExpReward()));
                                        System.out.println(pc.player.addGold(b.getExpReward()/2));
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
                                Item reward = battleRewards(loc.getLevel(), Faction.Dungeon);
                                System.out.println("You received a " + reward.getName() + "!");
                                pc.player.getInventory().add(reward);

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
                    System.out.println(pc.player);
                    System.out.println();
                    System.out.println("  **** Inventory ****\n");
                    //allow the user to equip items from their inventory
                    for (int i = 0; i < pc.player.getInventory().size(); i++) {
                        pause(WAITTIME / 2);
                        System.out.printf("%d\t%-20s\t%d\n", i + 1, pc.player.getInventory().get(i).getName(), pc.player.getInventory().get(i).getLevel());
                    }
                    System.out.print("\nEnter number to equip item or 0 to go back");
                    int i = integerInput(0, (pc.player.getInventory().size()));
                    if (i != 0) {
                        Item item = pc.player.getInventory().get(i-1);
                        if(item instanceof Armor){
                            Armor oldArmor = pc.player.getArmor();
                            pc.player.setArmor((Armor) item);
                            pc.player.getInventory().remove(i-1);
                            pc.player.getInventory().add(oldArmor);
                        }
                        else if(item instanceof Weapon){
                            Weapon oldWeapon = pc.player.getWeapon();
                            pc.player.setWeapon((Weapon) item);
                            pc.player.getInventory().remove(i-1);
                            pc.player.getInventory().add(oldWeapon);
                        }
                        else{
                            System.out.println("Cannot equip");
                        }
                    }
                    System.out.println();
                    break;
                case 4: //print the map
                    System.out.println(pc.map);
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
                        for (String s :b.attack(i, pc.player.getWeapon())) {
                            System.out.println(s);
                            try {Thread.sleep(WAITTIME);} catch (InterruptedException ignored) {}
                        }
                        break;
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.println("What spell will you use?");
                        for (String s : pc.player.getInventory().toString().split("\n")) {
                            System.out.println(s);
                            try {
                                Thread.sleep(WAITTIME);
                            } catch (InterruptedException ignored) {}
                        }
                        i = Integer.parseInt(scr.nextLine()) - 1;
                        if (pc.player.getInventory().get(i) instanceof Spell)
                            break;
                        else
                            System.out.println("Not a spell");
                    }
                    //use the spell

                    Spell sp = (Spell) pc.player.getInventory().get(i);

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
            if (pc.player.getStats().getCurrHP() <= 0)
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

        if (faction == Faction.Dungeon) {
            modifier = 5;
        }

        int low = level + modifier;
        int high = level + 5 + modifier;

        // Get item list for range +=5 of zone (maybe modifier for rewards on dungeon?)
        ArrayList<Item> arr = DataAccess.produceItemList(low, high);

        Random random = new Random();
        // Get rand int for type, weapon armor etc, 1-10
        int rand = (int) (random.nextInt(10)) + 1;

        // Make separate item type arrays
        ArrayList<Item> weaponArr = new ArrayList<>();
        ArrayList<Item> armorArr = new ArrayList<>();
        ArrayList<Item> spellArr = new ArrayList<>();

        // Fill arrays
        if(arr != null) {
            for (Item item : arr) {
                if (item instanceof Weapon) {
                    weaponArr.add(item);
                } else if (item instanceof Armor) {
                    armorArr.add(item);
                } else if (item instanceof Spell) {
                    spellArr.add(item);
                }
            }
        }
        if (faction != Faction.Dungeon && faction != Faction.FinalDungeon) // Faction is overworld
        {
            if(rand >= 5 && rand <= 6 && !weaponArr.isEmpty()){
                rand = (int) (Math.random() * weaponArr.size());
                itemReward = weaponArr.get(rand);
            }
            if(rand >= 7 && rand <= 8 && !armorArr.isEmpty()) {
                rand = (int) (Math.random() * armorArr.size());
                itemReward = armorArr.get(rand);
            }
            if(rand >= 9 && !spellArr.isEmpty()) {
                rand = (int) (Math.random() * spellArr.size());
                itemReward = spellArr.get(rand);
            }
        }
        else // Faction is dungeon or final dungeon
        {
            if(rand <= 4 && !weaponArr.isEmpty()) {
                rand = (int) (Math.random() * weaponArr.size());
                itemReward = weaponArr.get(rand);
            }
            else if(rand <= 7 && !armorArr.isEmpty()){
                rand = (int) (Math.random() * armorArr.size());
                itemReward = armorArr.get(rand);
            }
            else if(!spellArr.isEmpty()){
                rand = (int) (Math.random() * spellArr.size());
                itemReward = spellArr.get(rand);
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
        LoadSaveController.savePlayer(pc.player);
        LoadSaveController.saveMap(pc.map);
    }
    public void pause(){pause(WAITTIME);}
    public void pause(int milliseconds){try {Thread.sleep(milliseconds);} catch (InterruptedException ignored) {}}


    public static void main(String[] args) {
        new NARPGImplementingGUI();
    }


}
