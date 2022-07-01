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
    public Inventory(ArrayList<Item> arr){
        this.inventory = arr;
        capacity = arr.size() + 10;
    }
    public void add(Item i)
    {
        if (inventory.size() < capacity) {
            inventory.add(i);
        }
    }
    public Item get(int index){
        return inventory.get(index);
    }
    public int indexOf(Item i)
    {
        return inventory.indexOf(i);
    }

    public String toString()
    {
        StringBuilder finalString = new StringBuilder();

        for (int i = 0; i < inventory.size(); i++) {
            finalString.append(i + 1).append(". ").append(inventory.get(i).getName());
            if (i < inventory.size()-1)
                finalString.append("\n");
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
