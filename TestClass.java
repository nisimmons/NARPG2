import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestClass {

    @org.junit.Test
    public void test1() throws IOException {
        String s;
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
        scr.close();

        /*
        <ID>
        <FACTION>
        <Name>
        <LVL>/0/<HP>/<HP>/<MANA>/<MANA>
        <DMG>
        <DEFENSE>
         */
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

        /*Player p = PlayController.createRandomPlayer("Nate");
        DataAccess.savePlayer(p, 1);*/



//        Map m = PlayController.createRandomMap("Nate");
//        DataAccess.saveMap(m);

    }

    @org.junit.Test
    public void test2()   {
        Map m = PlayController.createRandomMap("Nate");
        ArrayList<Integer> arr = new ArrayList<>();
//        for (Location[] locations : m.getMap())
//            for (Location l: locations)

        LoadSaveController.saveMap(m);
    }

}