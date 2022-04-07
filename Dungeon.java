public class Dungeon extends Location {
    Map m;
    public Dungeon(){}
    public Dungeon(String s){super(s);}
    /**
     * finds the data entry for this location
     * @return data string
     */
    public String toData(){
        return "D";
    }
    public String toString(){
        return "D";
    }
}
