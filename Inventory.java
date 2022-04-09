import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory;
    private int capacity;

    public Inventory(int inventorySize) {
        this.inventory = new ArrayList<>();
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

    public String toString()
    {
        StringBuilder finalString = new StringBuilder();

        for (Item item : inventory) {
            finalString.append(item.getName()).append(" ");
        }
        return finalString.toString();
    }
    public String toData(){
        StringBuilder s = new StringBuilder();
        for (Item i:inventory)
            s.append(i.getId()).append("/");
        return s.toString();
    }

}
