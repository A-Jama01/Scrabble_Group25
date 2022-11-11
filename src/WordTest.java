import org.junit.*;
import static org.junit.Assert.*;

public class WordTest {
    private Word word;

    @Before
    public void setUp(){
        word = new Word();
    }
    @Test
    public void testWord(){
        assertEquals(4, word.score("test"));
    }

    @Test
    public void testEmpty(){
        assertEquals(0, word.score(""));
    }
}
