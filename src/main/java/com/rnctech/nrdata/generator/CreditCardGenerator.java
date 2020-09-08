package com.rnctech.nrdata.generator;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomUtils;

import com.rnctech.nrdata.utils.DataUtils;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/

public class CreditCardGenerator {

	public static final String[] ALL_TYPES = new String[] { "4539", "4556",
			"4916", "4532", "4929", "40240071", "4485", "4716", "4", "51",
			"52", "53", "54", "55", "34", "37", "6011", "300", "301", "302",
			"303", "36", "38", "2014", "2149", "3088", "3096", "3112", "3158",
			"3337", "3528", "2100", "1800", "8699" };

	public static final String[] VISA = new String[] { "4539", "4556", "4916",
			"4532", "4929", "40240071", "4485", "4716", "4" };

	public final static String[] MASTERCARD = new String[] { "51", "52", "53",
			"54", "55" };

	public final static String[] AMEX = new String[] { "34", "37" };

	public final static String[] DISCOVER = new String[] { "6011" };

	public final static String[] DINERS = new String[] { "300", "301", "302",
			"303", "36", "38" };

	public final static String[] ENROUTE = new String[] { "2014", "2149" };

	public final static String[] JCB_16 = new String[] { "3088", "3096",
			"3112", "3158", "3337", "3528" };

	public final static String[] JCB_15 = new String[] { "2100", "1800" };

	public final static String[] VOYAGER = new String[] { "8699" };

	public static List genMasterCardNumbers(int total, int invalid) {
		return genCardNumber(MASTERCARD, 16, total, invalid);
	}

	public static List genVisaCardNumbers(int total, int invalid) {
		return genCardNumber(VISA, 16, total, invalid);
	}

	public static List genAllTypedCard(int total, int invalid) {
		return genCardNumber(ALL_TYPES, 16, total, invalid);
	}

	private static String genCardNumber(String prefix, int length) {
		StringBuilder ccnumber = new StringBuilder(prefix);
		while (ccnumber.length() < (length - 1)) {
			ccnumber.append(RandomUtils.nextInt(1, 10));
		}
		String revString = DataUtils.reverse(ccnumber.toString());
		Integer[] revnumber = new Integer[revString.length()];
		for (int i = 0; i < revString.length(); i++) {
			revnumber[i] = Integer.valueOf(revString.charAt(i));
		}

		int sum = 0;
		int pos = 0;
		while (pos < length - 1) {
			int odd = revnumber[pos] * 2;
			if (odd > 9) {
				odd -= 9;
			}
			sum += odd;
			if (pos != (length - 2)) {
				sum += revnumber[pos + 1];
			}
			pos += 2;
		}
		// calculate check digit
		int checkdigit = (int) (((Math.floor(sum / 10) + 1) * 10 - sum) % 10);
		ccnumber.append(checkdigit);
		return ccnumber.toString();

	}

	// length = 13 | 16
	public static List genCardNumber(String[] types, int length,
			int total, int invalid) {
		List<String> cards = new ArrayList<String>();
		int i = 0;
		int j = 0;
		while (i + j < total) {
			int index = RandomUtils.nextInt(1, types.length);
			String cprefix = types[index];
			String c = genCardNumber(cprefix, length);
			if (isValidCardNumber(c)) {
				cards.add(c);
				i++;
			} else {
				if (j < invalid) {
					cards.add(c);
					j++;
				}
			}
		}
		return cards;
	}

	public static boolean isValidCardNumber(String cnumber) {
		boolean isValid = false;
		try {
			String reversedNumber = new StringBuffer(cnumber).reverse()
					.toString();
			int mod10Count = 0;
			for (int i = 0; i < reversedNumber.length(); i++) {
				int augend = Integer.parseInt(String.valueOf(reversedNumber
						.charAt(i)));
				if (((i + 1) % 2) == 0) {
					String productString = String.valueOf(augend * 2);
					augend = 0;
					for (int j = 0; j < productString.length(); j++) {
						augend += Integer.parseInt(String.valueOf(productString
								.charAt(j)));
					}
				}

				mod10Count += augend;
			}

			if ((mod10Count % 10) == 0) {
				isValid = true;
			}
		} catch (NumberFormatException e) {
		}

		return isValid;
	}
	
	  public static boolean isLuhnValid (String cnumber) {
		    int sum = 0;
		    int digit = 0;
		    int addend = 0;
		    boolean timesTwo = false;

		    for (int i = cnumber.length () - 1; i >= 0; i--) {
		      digit = Integer.parseInt (cnumber.substring (i, i + 1));
		      if (timesTwo) {
		        addend = digit * 2;
		        if (addend > 9) {
		          addend -= 9;
		        }
		      }
		      else {
		        addend = digit;
		      }
		      sum += addend;
		      timesTwo = !timesTwo;
		    }

		    int modulus = sum % 10;
		    return modulus == 0;

		  }

	public static void main(String[] args) {
		List cards = genAllTypedCard(100, 3);
		for (int i = 0; i < cards.size(); i++) {
			System.out
					.println(cards.get(i)
							+ ":"
							+ (isValidCardNumber((String) cards
									.get(i)) ? "valid" : "invalid"));
		}
	}
}
