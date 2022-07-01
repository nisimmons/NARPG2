import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestClass {

    @org.junit.Test
    public void test1() throws IOException {
        FileWriter out = new FileWriter(new File("AAA.txt"));
        int id = 0;
        for (int i = 1; i < 11; i++) {
            out.write("***********" + "\n");
            out.write((100+i) + "\n");
            out.write("WEP" + i + "\n");
            out.write(i*5 + "\n"); //level
            out.write("weapon" + "\n");
            out.write(i*10 + "\n"); //damage
        }
        for (int i = 1; i < 11; i++) {
            out.write("***********" + "\n");
            out.write((200+i) + "\n");
            out.write("ARMOR" + i + "\n");
            out.write(i*5 + "\n"); //level
            out.write("armor" + "\n");
            out.write(i*10 + "\n"); //damage
        }
        for (int i = 1; i < 11; i+=2) {
            out.write("***********" + "\n");
            out.write((300+i) + "\n");
            out.write("DAMAGESPELL" + i + "\n");
            out.write(i*5 + "\n"); //level
            out.write("spell" + "\n");
            out.write(i*10 + "\n"); //damage
            out.write(i*5 + "\n"); //cost
            out.write("DAMAGE" + "\n");
        }
        for (int i = 1; i < 11; i+=2) {
            out.write("***********" + "\n");
            out.write((400+i) + "\n");
            out.write("HEALSPELL" + i + "\n");
            out.write(i*5 + "\n"); //level
            out.write("spell" + "\n");
            out.write(i*10 + "\n"); //damage
            out.write(i*5 + "\n"); //cost
            out.write("HEAL" + "\n");
        }

        out.close();
    }

    @org.junit.Test
    public void test2()   {
        ArrayList<Item> arr = DataAccess.produceItemList(20,35);
        for (Item i : arr)
            System.out.println(i.getLevel() + "\t" + i.getName());
    }

}

/*String s;
        Scanner scr = new Scanner(new File("enemyData.txt"));
        FileWriter out = new FileWriter("enemyData1.txt");
        while(scr.hasNext()){
            for (int i = 0; i < 3; i++) {
                s = scr.nextLine();
                out.write(s + "\n");
            }
            s = scr.nextLine();
            String[]s1 = s.split("/");
            out.write( s1[0] + "/5/10\n5\n10\n");
            scr.nextLine();
            scr.nextLine();
        }
        out.close();
        scr.close();      */
        /*FileWriter out = new FileWriter(new File("enemyData1.txt"));
        int id = 0;
        Faction fac = null;
        for (int f = 0; f < 6; f++) {
            for (int i = 0; i < 51; i += 5) {
                switch (f){
                    case 0:
                        fac = Faction.FOREST;
                        break;
                    case 1:
                        fac = Faction.DESERT;
                        break;
                    case 2:
                        fac = Faction.PLAINS;
                        break;
                    case 3:
                        fac = Faction.BEACH;
                        break;
                    case 4:
                        fac = Faction.RUINS;
                        break;
                    case 5:
                        fac = Faction.MOUNTAIN;
                        break;
                }
                out.write((id++) + "\n");
                out.write(fac + "\n");
                out.write(fac + "" + i + "\n");
                out.write(i + "/HP/Mana\n");
                out.write(i + "DMG\nDEF\n");
            }
        }
        out.close();*/