abstract class Item {
    private String name;
    private int id;

    public Item(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String toString(){
        return name;
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
