abstract class Location {
    String name;
    public Location(){}
    public Location(String name){
        this.name = name;
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
}
