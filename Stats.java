public class Stats {
    private int level;
    private int exp;
    private int maxHP;
    private int currHP;
    private int maxMana;
    private int currMana;
    public Stats(){

    }
    public Stats(String[] s){
        setLevel(Integer.parseInt(s[0]));
        setExp(Integer.parseInt(s[1]));
        setCurrHP(Integer.parseInt(s[2]));
        setMaxHP(Integer.parseInt(s[3]));
        setCurrMana(Integer.parseInt(s[4]));
        setMaxMana(Integer.parseInt(s[5]));
    }
    public String toString(){
        return "Level: " + level + "EXP: " + exp + "\ncurrHP: " + currHP + "\ncurrMana: "+currMana;
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getCurrHP() {
        return currHP;
    }

    public void setCurrHP(int currHP) {
        this.currHP = currHP;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getCurrMana() {
        return currMana;
    }

    public void setCurrMana(int currMana) {
        this.currMana = currMana;
    }
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
    public String toData(){
        return level+"/"+exp+"/"+currHP+"/"+maxHP+"/"+currMana+"/"+maxMana;
    }

}
