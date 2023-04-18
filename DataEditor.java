import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataEditor extends JFrame {

    int WIDTH = 1600;
    int HEIGHT = 900;
    JFrame frame;
    JPanel topPanel;
    JPanel bottomPanel;
    ArrayList<Enemy> enemies;
    ArrayList<Item> items;

    public DataEditor(){
        frame = new JFrame("Data Editor");
        frame.setLayout(new GridLayout(2, 1));
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        //set background of top to grey
        topPanel.setBackground(Color.GRAY);
        //set background of bottom to white
        bottomPanel.setBackground(Color.WHITE);

        //make buttons for enemyData, itemData
        JButton enemyData = new JButton("Enemy Data");
        JButton itemData = new JButton("Item Data");

        //add functions to buttons
        enemyData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showEnemies();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        itemData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showItems();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //add buttons
        topPanel.add(enemyData);
        topPanel.add(itemData);

        frame.add(topPanel);
        frame.add(bottomPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void mainMenu(){
        topPanel.removeAll();
        bottomPanel.removeAll();
        //make buttons for enemyData, itemData
        JButton enemyData = new JButton("Enemy Data");
        JButton itemData = new JButton("Item Data");

        //add functions to buttons
        enemyData.addActionListener(e -> {
            try {
                showEnemies();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        itemData.addActionListener(e -> {
            try {
                showItems();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });


        //add buttons
        topPanel.add(enemyData);
        topPanel.add(itemData);
        frame.repaint();
        frame.setVisible(true);
    }

    private void showItems() throws FileNotFoundException {
        topPanel.removeAll();
        //create a back button
        JButton back = new JButton("Save");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeItems();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                mainMenu();
            }
        });

        //add back button to panel
        topPanel.add(back);

        if(items == null) {
            Scanner itemScanner = new Scanner(new File("itemData.txt"));
            //create a list of item names
            items = new ArrayList<>();
            while (itemScanner.hasNextLine()) {
                itemScanner.nextLine();
                int id = Integer.parseInt(itemScanner.nextLine());
                String name = itemScanner.nextLine();
                int level = Integer.parseInt(itemScanner.nextLine());
                String type = itemScanner.nextLine();
                switch (type) {
                    case "weapon" -> {
                        int damage = Integer.parseInt(itemScanner.nextLine());
                        Weapon w = new Weapon(name, id, damage);
                        w.setLevel(level);
                        items.add(w);
                    }
                    case "armor" -> {
                        int defense = Integer.parseInt(itemScanner.nextLine());
                        Armor a = new Armor(name, id, defense);
                        a.setLevel(level);
                        items.add(a);
                    }
                    case "spell" -> {
                        int stat = Integer.parseInt(itemScanner.nextLine());
                        int cost = Integer.parseInt(itemScanner.nextLine());
                        SpellType effect = SpellType.valueOf(itemScanner.nextLine());
                        Spell s = new Spell(name, id, stat, cost, effect);
                        s.setLevel(level);
                        items.add(s);
                    }
                }
            }
        }
        //create a list of item buttons
        ArrayList<JButton> itemButtons = new ArrayList<>();
        for(Item item : items){
            JButton itemButton = new JButton(item.getName());
            itemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //show item data
                    showItem(item);
                }
            });
            itemButtons.add(itemButton);
        }

        //add item buttons to panel
        for(JButton itemButton : itemButtons){
            topPanel.add(itemButton);
        }

        //new weapon button
        JButton newWeapon = new JButton("New Weapon");
        newWeapon.addActionListener(e -> {
            Weapon w = new Weapon("Weapon Name", items.size()+1, 0);
            items.add(w);
            showItem(w);
        });
        //new armor button
        JButton newArmor = new JButton("New Armor");
        newArmor.addActionListener(e -> {
            Armor a = new Armor("Armor Name", items.size()+1, 0);
            items.add(a);
            showItem(a);
        });

        //new spell button
        JButton newSpell = new JButton("New Spell");
        newSpell.addActionListener(e -> {
            Spell s = new Spell("Spell Name", items.size()+1, 0, 0, SpellType.HEAL);
            items.add(s);
            showItem(s);
        });

        topPanel.add(newWeapon);
        topPanel.add(newArmor);
        topPanel.add(newSpell);

        frame.repaint();
        frame.setVisible(true);
    }

    private void showItem(Item item) {
        bottomPanel.removeAll();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        //create editable fields for item data and labels for each
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField(item.getName());
        JLabel idLabel = new JLabel("ID: ");
        JTextField idField = new JTextField(item.getId() + "");
        JLabel levelLabel = new JLabel("Level: ");
        JTextField levelField = new JTextField(item.getLevel() + "");
        JTextField damageField = null;
        JTextField defenseField = null;
        JTextField costField = null;
        JComboBox effect = null;

        if(item instanceof Weapon){
            JLabel damageLabel = new JLabel("Damage: ");
            damageField = new JTextField(((Weapon) item).getDamage() + "");
            bottomPanel.add(damageLabel);
            bottomPanel.add(damageField);
        }
        else if(item instanceof Armor){
            JLabel defenseLabel = new JLabel("Defense: ");
            defenseField = new JTextField(((Armor) item).getArmorRating() + "");
            bottomPanel.add(defenseLabel);
            bottomPanel.add(defenseField);
        }
        else if(item instanceof Spell){
            JLabel damageLabel = new JLabel("Strength of effect: ");
            damageField = new JTextField(((Spell) item).getSpellDamage() + "");
            JLabel costLabel = new JLabel("Cost: ");
            costField = new JTextField(((Spell) item).getSpellCost() + "");
            JLabel effectLabel = new JLabel("Effect: ");
            effect = new JComboBox(SpellType.values());
            effect.setSelectedItem(((Spell) item).getType());


            bottomPanel.add(damageLabel);
            bottomPanel.add(damageField);
            bottomPanel.add(costLabel);
            bottomPanel.add(costField);
            bottomPanel.add(effectLabel);
            bottomPanel.add(effect);
        }
        //save button
        JButton save = new JButton("Save");
        JTextField finalDamageField = damageField;
        JTextField finalDefenseField = defenseField;
        JTextField finalCostField = costField;
        JComboBox finalEffect = effect;
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //save item data
                item.setName(nameField.getText());
                item.setId(Integer.parseInt(idField.getText()));
                item.setLevel(Integer.parseInt(levelField.getText()));
                if(item instanceof Weapon){
                    ((Weapon) item).setDamage(Integer.parseInt(finalDamageField.getText()));
                }
                else if(item instanceof Armor){
                    ((Armor) item).setArmorRating(Integer.parseInt(finalDefenseField.getText()));
                }
                else if(item instanceof Spell){
                    ((Spell) item).setSpellDamage(Integer.parseInt(finalDamageField.getText()));
                    ((Spell) item).setSpellCost(Integer.parseInt(finalCostField.getText()));
                    ((Spell) item).setType((SpellType) finalEffect.getSelectedItem());
                }
                try {
                    //update item list
                    showItems();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //delete button
        JButton delete = new JButton("Delete");
        delete.addActionListener(e -> {
            //delete item
            items.remove(item);
            try {
                //update item list
                showItems();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });


        bottomPanel.add(nameLabel);
        bottomPanel.add(nameField);
        bottomPanel.add(idLabel);
        bottomPanel.add(idField);
        bottomPanel.add(levelLabel);
        bottomPanel.add(levelField);
        bottomPanel.add(save);
        bottomPanel.add(delete);
        frame.repaint();
        frame.setVisible(true);

    }

    private void writeItems() throws IOException {
        int i = 1;
        FileWriter writer = new FileWriter("itemData.txt");
        for (Item item : items) {
            writer.write("***********\n");
            writer.write(i++ + "\n");
            //writer.write(item.getId() + "\n");
            writer.write(item.getName() + "\n");
            writer.write(item.getLevel() + "\n");
            if (item instanceof Weapon) {
                writer.write("weapon" + "\n");
                writer.write(((Weapon) item).getDamage() + "\n");
            } else if (item instanceof Armor) {
                writer.write("armor" + "\n");
                writer.write(((Armor) item).getArmorRating() + "\n");
            } else if (item instanceof Spell) {
                writer.write("spell" + "\n");
                writer.write(((Spell) item).getSpellDamage() + "\n");
                writer.write(((Spell) item).getSpellCost() + "\n");
                writer.write(((Spell) item).getType() + "\n");
            }
        }
        writer.close();
    }

    public void showEnemies() throws FileNotFoundException {
        topPanel.removeAll();

        //create a back button
        JButton back = new JButton("Save");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeEnemies();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                mainMenu();
            }
        });
        //add back button to panel
        topPanel.add(back);

        if(enemies == null) {
            //open enemy data scanner
            Scanner enemyScanner = new Scanner(new File("enemyData.txt"));
            //create a list of enemy names
            enemies = new ArrayList<>();
            while (enemyScanner.hasNextLine()) {
                Enemy enemy = new Enemy();
                enemyScanner.nextLine();
                enemy.id = Integer.parseInt(enemyScanner.nextLine());
                enemy.faction = Faction.valueOf(enemyScanner.nextLine());
                enemy.name = enemyScanner.nextLine();
                String stats = enemyScanner.nextLine();
                String[] statsArray = stats.split("/");
                enemy.level = Integer.parseInt(statsArray[0]);
                enemy.health = Integer.parseInt(statsArray[1]);
                enemy.mana = Integer.parseInt(statsArray[2]);
                enemy.attack = Integer.parseInt(enemyScanner.nextLine());
                enemy.defense = Integer.parseInt(enemyScanner.nextLine());
                enemies.add(enemy);
            }
        }

        //create a list of enemy buttons
        ArrayList<JButton> enemyButtons = new ArrayList<>();
        for(Enemy enemy : enemies){
            JButton enemyButton = new JButton(enemy.name);
            enemyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //show enemy data
                    showEnemy(enemy);
                }
            });
            enemyButtons.add(enemyButton);
        }
        //add enemy buttons to panel
        for(JButton enemyButton : enemyButtons){
            topPanel.add(enemyButton);
        }
        //add new enemy button
        JButton newEnemy = new JButton("New Enemy");
        newEnemy.addActionListener(e -> {
            //create new enemy
            Enemy enemy = new Enemy();
            enemy.id = enemies.size()+1;
            enemy.name = "Enter Name";
            enemy.faction = Faction.Forest;
            enemy.level = 1;
            enemy.health = 1;
            enemy.mana = 1;
            enemy.attack = 1;
            enemy.defense = 1;
            enemies.add(enemy);
            //show enemy data
            showEnemy(enemy);
        });
        topPanel.add(newEnemy);
        frame.repaint();
        frame.setVisible(true);
    }

    private void showEnemy(Enemy e){
        //create a panel for the enemy data
        bottomPanel.removeAll();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        //make a back button
        //create editable fields for enemy data and labels for each
        JLabel nameLabel = new JLabel("Name: ");
        JTextField name = new JTextField(e.name);
        JLabel factionLabel = new JLabel("Faction: ");
        JComboBox faction = new JComboBox(Faction.values());
        faction.setSelectedItem(e.faction);
        JLabel levelLabel = new JLabel("Level: ");
        JTextField level = new JTextField(e.level + "");
        JLabel healthLabel = new JLabel("Health: ");
        JTextField health = new JTextField(e.health + "");
        JLabel manaLabel = new JLabel("Mana: ");
        JTextField mana = new JTextField(e.mana + "");
        JLabel attackLabel = new JLabel("Attack: ");
        JTextField attack = new JTextField(e.attack + "");
        JLabel defenseLabel = new JLabel("Defense: ");
        JTextField defense = new JTextField(e.defense + "");
        //create a save button
        JButton save = new JButton("Save");
        save.addActionListener(ae -> {
            //save enemy data
            e.name = name.getText();
            e.level = Integer.parseInt(level.getText());
            e.health = Integer.parseInt(health.getText());
            e.mana = Integer.parseInt(mana.getText());
            e.attack = Integer.parseInt(attack.getText());
            e.defense = Integer.parseInt(defense.getText());
            e.faction = (Faction) faction.getSelectedItem();
            try {
                //update enemy list
                showEnemies();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        //delete button
        JButton delete = new JButton("Delete");
        delete.addActionListener(ae -> {
            //delete enemy
            enemies.remove(e);
            try {
                //update enemy list
                showEnemies();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        //add fields to panel
        bottomPanel.add(nameLabel);
        bottomPanel.add(name);
        bottomPanel.add(factionLabel);
        bottomPanel.add(faction);
        bottomPanel.add(levelLabel);
        bottomPanel.add(level);
        bottomPanel.add(healthLabel);
        bottomPanel.add(health);
        bottomPanel.add(manaLabel);
        bottomPanel.add(mana);
        bottomPanel.add(attackLabel);
        bottomPanel.add(attack);
        bottomPanel.add(defenseLabel);
        bottomPanel.add(defense);
        bottomPanel.add(save);
        bottomPanel.add(delete);

        bottomPanel.repaint();
        frame.repaint();
        frame.setVisible(true);
    }

    public void writeEnemies() throws IOException {
        FileWriter writer = new FileWriter("enemyData.txt");
        for(Enemy enemy : enemies){
            writer.write("***********\n");
            writer.write(enemy.id + "\n");
            writer.write(enemy.faction + "\n");
            writer.write(enemy.name + "\n");
            writer.write(enemy.level + "/" + enemy.health + "/" + enemy.mana + "\n");
            writer.write(enemy.attack + "\n");
            writer.write(enemy.defense + "");
            if(enemies.indexOf(enemy) != enemies.size() - 1)
                writer.write("\n");
        }
        writer.close();
    }

    public static void main(String[] args){
        new DataEditor();
    }

    static class Enemy{
        int id;
        Faction faction;
        String name;
        int level;
        int health;
        int mana;
        int attack;
        int defense;
    }
}
