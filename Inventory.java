import java.util.ArrayList;
import java.util.TreeMap;

public class Inventory {
    private TreeMap<String, Item> items;
    private TreeMap<Integer, String> numbers;
    private int capacity;

    public Inventory() {
        this(20);
    }

    public Inventory(int capacity) {
        this.items = new TreeMap<>();
        this.numbers = new TreeMap<>();
        this.capacity = capacity;
    }

    public Inventory(ArrayList<Item> arr) {
        this.items = new TreeMap<>();
        this.numbers = new TreeMap<>();
        capacity = 20;
        for (Item thing: arr)
        {
            add(thing);
        }
        sortNumbers();
    }

    public boolean add(Item item)
    {
        if (items.size() < capacity) {
            items.put(item.getName(), item);
            sortNumbers();
            return true;
        }
        else
        {
            return false;
        }
    }

    private void sortNumbers()
    {
        numbers.clear();
        int i = 1;
        for (Item j: items.values())
            numbers.put(i++, j.getName());
    }

    public Item get(int i) { return items.get(numbers.get(i+1)); }

    public Item get(String i) { return items.get(i+1);}

    public void remove(int i) { items.remove(numbers.get(i+1)); sortNumbers();}

    public void remove(String i) { items.remove(i+1); sortNumbers();}

    public Item take(int index){
        Item i = items.get(numbers.get(index+1));
        remove(index);
        return i;
    }

    public Item take(String name){
        Item i = items.get(name);
        remove(name);
        return i;
    }

    public String toString(){
        StringBuilder finalString = new StringBuilder();

        for (int i = 1; i < items.size()+1; i++) {
            finalString.append(i).append(". ").append(numbers.get(i));
            if (i < items.size())
                finalString.append("\n");
        }
        return finalString.toString();
    }

    public String toData(){
        StringBuilder s = new StringBuilder();
        for (Item i:items.values())
            s.append(i.getId()).append("/");
        return s.toString();
    }

    public int size() { return items.size(); }


}

