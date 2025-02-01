void printPrimes(int max) {
	// The version with "<" instead of "<=" would also be marked correct
	for (int n = 2; n <= max; n++) {
		boolean prime = true;
		// Here it has to be <= or you won't find square numbers correctly
		for (int f = 2; f <= Math.sqrt(n); f++) {
			if (n % f == 0) {
				prime = false;
				break;
			}
		}
		if (prime) {
			System.out.println(n + " is prime");
		}
	}
}