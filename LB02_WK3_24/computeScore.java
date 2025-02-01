int computeScore (String str) {
		// Initialise counter and normalise string
		int total = 0;
		str = str.toLowerCase();

		// Go through the string and add up the score
		for (char c : str.toCharArray()) {
			switch (c) {
			case 'a':
			case 'e':
			case 'i':
			case 'l':
			case 'n':
			case 'o':
			case 'r':
			case 's':
			case 't':
			case 'u':
				total++;
				break;

			case 'd':
			case 'g':
				total += 2;
				break;

			case 'b':
			case 'c':
			case 'm':
			case 'p':
				total += 3;
				break;

			case 'f':
			case 'h':
			case 'v':
			case 'w':
			case 'y':
				total += 4;
				break;

			case 'k':
				total += 5;
				break;

			case 'j':
			case 'x':
				total += 8;
				break;

			case 'q':
			case 'z':
				total += 10;
				break;

			default:
				// Nothing needed here -- we ignore all other characters
			}
		}
		// This is the total score
		return total;
	}
