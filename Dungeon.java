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

    public void fromData(String s){
        // TODO fromData
        // s will be a string from the data file in the form
        // take data from string and input it to this object
        // "D <0/1 revealed>"
    }

    public String toString(){
        return "D";
    }
}
