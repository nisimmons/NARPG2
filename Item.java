abstract class Item {
    private String name;
    private int id;
    private int level;
    public Item(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String toString(){return name;}
    public String getName() {
        return name;
    }

    public int getId() {return id;}
    public int getLevel() {return level;}

    public void setLevel(int level) {this.level = level;}
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
