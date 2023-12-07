
import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MainTest {

    private EntityManagerFactory emf;

    @Before
    public void setUp() {
        // Set up the entity manager factory before each test
        emf = Persistence.createEntityManagerFactory("default");
    }

    @After
    public void tearDown() {
        // Close the entity manager factory after each test
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    public void testGetWord() {

        assertAll(
                () -> assertEquals("Word does not exist in database!", Main.getWord("wordToFail")),
                () -> assertEquals("Word does not exist in database!", Main.getWord("wordToFail2")),
                () -> assertEquals("[Id: 2382 Word: mouse]", Main.getWord("mouse")),
                () -> assertEquals("[Id: 176 Word: book]", Main.getWord("book"))
        );
    }
}
