import org.junit.Before;

import java.io.FileNotFoundException;

public class TestClass {
    @Before
    public void setUp() {

    }

    @org.junit.Test
    public void testData() {
        Map m = PlayController.createRandomMap("Nate");
        DataAccess.saveMap(m);


    }

}