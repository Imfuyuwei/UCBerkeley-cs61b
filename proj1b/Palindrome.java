public class Palindrome {
	public Deque<Character> wordToDeque(String word) {
		Deque<Character> dequeOfWord = new LinkedListDeque<>();
		for (int i = 0; i < word.length(); i++) {
			dequeOfWord.addLast(word.charAt(i));
		}
		return dequeOfWord;
	}

	private boolean isPalindromeForDeque(Deque<Character> dequeOfWord) {
		if (dequeOfWord.size() == 0 || dequeOfWord.size() == 1) {
			return true;
		} else if (dequeOfWord.removeFirst() != dequeOfWord.removeLast()) {
			return false;
		} else {
			return isPalindromeForDeque(dequeOfWord);
		}
	}

	public boolean isPalindrome(String word) {
		Deque<Character> dequeOfWord = wordToDeque(word);
		return isPalindromeForDeque(dequeOfWord);
	}

	private boolean isPalindromeForDequeWithCC(Deque<Character> dequeOfWord, CharacterComparator cc) {
		if (dequeOfWord.size() == 0 || dequeOfWord.size() == 1) {
			return true;
		} else if (!cc.equalChars(dequeOfWord.removeFirst(), dequeOfWord.removeLast())) {
			return false;
		} else {
			return isPalindromeForDequeWithCC(dequeOfWord, cc);
		}
	}

	/** Create a new isPanlindrome method that overloads the previous one */
	public boolean isPalindrome(String word, CharacterComparator cc) {
		Deque<Character> dequeOfWord = wordToDeque(word);
		return isPalindromeForDequeWithCC(dequeOfWord, cc);
	}
}


