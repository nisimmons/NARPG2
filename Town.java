import java.util.Scanner;

public class Town extends Location{
    private Inventory merchant;
    public Town(){this("Town");}
    public Town(String s){
        super(s);
        merchant = new Inventory();
        setFaction(Faction.Town);
    }

    /**
     * finds the data entry for this location
     * @return data string
     */
    public String toData(){
        String s = "T ";
        if (!isRevealed())
            s += "0 ";
        else
            s += "1 ";
        s += this.getLevel() + " ";
        s += getMerchant().toData();
        return s;
    }

    public void fromData(String s){
        Scanner scr = new Scanner(s);
        scr.next();
        if (scr.nextInt() == 1)
            setRevealed(true);
        setLevel(Integer.parseInt(scr.next()));
        if(scr.hasNext()) {
            String[] arr = scr.next().split("/");
            for (String st : arr)
                getMerchant().add(DataAccess.getItem(Integer.parseInt(st)));
        }
    }

    public String toString(){
        return "Town";
    }
    public Inventory getMerchant() {
        return merchant;
    }

    public void setMerchant(Inventory merchant) {
        this.merchant = merchant;
    }
}
