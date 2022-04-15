import org.junit.Before;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;

public class TestClass {
    @Before
    public void setUp() {

    }

    @org.junit.Test
    public void testData() {
        Player p = PlayController.createRandomPlayer("Nate");
        DataAccess.savePlayer(p, 1);



//        Map m = PlayController.createRandomMap("Nate");
//        DataAccess.saveMap(m);

    }

}