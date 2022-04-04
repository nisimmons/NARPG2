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
}
