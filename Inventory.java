import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory;
    private int capacity;

    public Inventory(int inventorySize) {
        this.inventory = new ArrayList<Item>();
        capacity = inventorySize;
    }

    public Inventory()
    {
        this(20);
    }

    public void add(Item i)
    {
        if (inventory.size() < capacity) {
            inventory.add(i);
        }
    }

    public int indexOf(Item i)
    {
        return inventory.indexOf(i);
    }

    public String inventoryToString()
    {
        StringBuilder finalString = new StringBuilder();

        for (Item item : inventory) {
            finalString.append(item.getName()).append(" ");
        }
        return finalString.toString();
    }


}
