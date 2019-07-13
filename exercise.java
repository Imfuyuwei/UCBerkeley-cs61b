public class exercise {
	public static void main(String[] args) {
		int i = 1;
		while (i < 6) {
			for (int j = 0; j < i; j++) {
				System.out.print('*');
			}
			System.out.println();
			i += 1;
		}
	}
}