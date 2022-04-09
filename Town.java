public class Town extends Location{
    public Town(){this("Town");}
    public Town(String s){
        super(s);
    }

    /**
     * finds the data entry for this location
     * @return data string
     */
    public String toData(){
        String s = "T ";
        if (!isRevealed())
            s += "0";
        else
            s += "1";
        return s;
    }

    public void fromData(String s){
        // TODO fromData
        // s will be a string from the data file in the form
        // take data from string and input it to this object
        // "T <0/1 revealed>"

    }

    public String toString(){
        return "T";
    }
}
