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
        StringBuilder s = new StringBuilder("T");
        return s.toString();
    }
    public String toString(){
        return "T";
    }
}
