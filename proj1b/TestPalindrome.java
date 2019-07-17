import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("Noon"));
        assertFalse(palindrome.isPalindrome("horse"));
    }


    /** Test the new Palindrome method which takes two parameters */
    @Test
    public void testNewIsPalindrome() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertTrue(palindrome.isPalindrome("BBC",cc));
        assertTrue(palindrome.isPalindrome("%&", cc));
        assertFalse(palindrome.isPalindrome("noon", cc));
        assertFalse(palindrome.isPalindrome("Noon", cc));
        assertFalse(palindrome.isPalindrome("horse", cc));
    }
}
/* Uncomment this class once you've created your Palindrome class. */