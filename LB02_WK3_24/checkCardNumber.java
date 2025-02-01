boolean checkCardNumber (String cardNumber) {
	// Check length first -- can't be valid if length is out of range
	if (cardNumber.length() < 13 || cardNumber.length() > 19) {
		return false;
	}
	
	// Check for a valid prefix, and also check for correct length while doing this
	switch (cardNumber.charAt(0)) {
		case '4':
			// Visa cards should be 13, 16, or 19 digits long
			if (cardNumber.length() != 13 && cardNumber.length() != 16 && cardNumber.length() != 19) {
				return false;
			}
			break;
			
		case '3':
			// American Express cards should have 4 or 7 as second digit and should be 15 digits long
			if (cardNumber.length() != 15) {
				return false;
			}
			char digit2 = cardNumber.charAt(1);
			if (digit2 != '4' && digit2 != '7') {
				return false;
			}
			break;
			
		case '5':
			// MasterCard (old cards) -- should 16 digits with prefix between 51 and 55
			if (cardNumber.length() != 16) {
				return false;
			}
			int prefix = Integer.valueOf(cardNumber.substring(0, 2));
			if (prefix < 51 || prefix > 55) {
				return false;
			}
			break;
			
		case '2':
			// MasterCard (new cards) -- should be 16 digits with prefix between 2221 and 2720
			if (cardNumber.length() != 16) {
				return false;
			}
			int prefix2 = Integer.valueOf(cardNumber.substring(0, 4));
			if (prefix2 < 2221 || prefix2 > 2720) {
				return false;
			}
			break;
		
		default:
			// Prefix must be invalid so can't be a valid number
			return false;
	}
	
	// If we get here, prefix and length must be right. Time to do the Luhn check.
	boolean even = false;
	int total = 0;
	
	// Loop backwards through the string one character at a time
	for (int i = cardNumber.length() - 1; i >= 0; i--) {
		// Convert to a number
		int digit = Character.getNumericValue(cardNumber.charAt(i));
		
		// Double even digits
		if (even) {
			int sum = digit * 2;
			if (sum > 9) {
				sum -= 9;
			}
			total += sum;
		} else {
			total += digit;
		}
		
		// Even and odd alternate
		even = !even;
	}
	
	// Card is valid exactly if final total is a multiple of 10
	return (total % 10 == 0);
}