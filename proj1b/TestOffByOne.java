import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('d', 'c'));
        assertFalse(offByOne.equalChars(' ', 'm'));

        // Test non-alphabetical characters
        assertTrue(offByOne.equalChars('&', '%'));

        // Make sure that uppercase and lowercase characters are not off by one
        assertFalse(offByOne.equalChars('A', 'b'));

        // A character is not off by one compared to itself
        assertFalse(offByOne.equalChars('a', 'a'));

    }
}

/* Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/