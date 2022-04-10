public class Dungeon extends Location {
    Map m;
    public Dungeon(){}
    public Dungeon(String s){super(s);}
    /**
     * finds the data entry for this location
     * @return data string
     */
    public String toData(){
        String s = "D ";
        if (!isRevealed())
            s += "0";
        else
            s += "1";
        return s;
    }

    public void fromData(String s){
        if(s.charAt(2) == '1')
            setRevealed(true);
    }

    public String toString(){
        return "D";
    }
}
