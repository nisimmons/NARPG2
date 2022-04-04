import org.junit.Before;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class TestClass {
    @Before
    public void setUp() {

    }

    @org.junit.Test
    public void name() throws FileNotFoundException {
        Player p = LoadSaveController.loadPlayer("Nate");

        assertEquals("Nate",p.getName());
        assertEquals(2,p.getStats().getLevel());


    }

}