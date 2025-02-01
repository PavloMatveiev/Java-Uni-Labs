int computeFibonacci(int n) {
	int result = 1, lastResult = 1;
	
	for (int i = 2; i < n; i++) {
		int temp = result;
		result = result + lastResult;
		lastResult = temp;
	}
	return result;
}