abstract class Location {
    String name;
    boolean revealed;
    public Location(){revealed = false;}
    public Location(String name){
        this.name = name;
        revealed = false;
    }

    public String toString(){
        return name;
    }

    public abstract String toData();
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
