import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    CharacterComparator offByTwo = new OffByN(2);
    CharacterComparator offByThree = new OffByN(3);

    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertFalse(offByTwo.equalChars('a', 'b'));
        assertTrue(offByTwo.equalChars('e', 'c'));
        assertFalse(offByTwo.equalChars('E', 'c'));
        assertTrue(offByThree.equalChars('a', 'd'));
        assertTrue(offByThree.equalChars('#', '&'));
        assertFalse(offByThree.equalChars('a', 'a'));
    }
}