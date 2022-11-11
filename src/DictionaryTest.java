import org.junit.*;
import static org.junit.Assert.*;

public class DictionaryTest {
    private Dictionary dict;

    @Before
    public void setUp(){
        dict = new Dictionary();
    }
    @Test
    public void testDict(){
        assertTrue(dict.check("apple") == true);
    }

    @Test
    public void testEmpty(){
        assertTrue(dict.check("") == false);
    }

    @Test
    public void testWrongWord(){
        assertTrue(dict.check("asdfg") == false);
    }
}
