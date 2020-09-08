package com.rnctech.nrdata.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
public class SensitiveDataGenerator {
	
	public final static String expression = "^([0-6]\\d{2}|7[0-6]\\d|77[0-2])([ \\-\\.]?)(\\d{2})([ \\-\\.]?)(\\d{4})$";
	
    public static ArrayList generateSsnDash(int total, int noofvalid,
            int noofinvalid) {
    	return generateSsn(total, noofvalid, noofinvalid, "-");
    }
    
    public static ArrayList generateSsnSpace(int total, int noofvalid,
            int noofinvalid) {
    	return generateSsn(total, noofvalid, noofinvalid, " ");
    }

    public static ArrayList generateSsn(int total, int noofvalid,
			int noofinvalid, String deli) {
		if (null == deli)
			deli = "";

		final ArrayList<String> combineno = new ArrayList<String>(total);
		int i = 0;
		int j = 0;
		while (i + j < total) {
			final String fno = Integer
					.toString((int) ((Math.random() * (800)) + 100));
			final String mno = Integer
					.toString((int) ((Math.random() * (89)) + 10));
			final String lno = Integer
					.toString((int) ((Math.random() * (8999)) + 1000));
			final String tempssn = fno + deli + mno + deli + lno;

			if (isValidSSN(tempssn) && i < noofvalid) {
				combineno.add(tempssn);
				i++;
			} else if (j <= noofinvalid) {
				combineno.add(tempssn);
				j++;
			}
		}

		return combineno;
	}

    public static boolean isValidSSN(String ssn) {        
        // final String expression = "^\\d{3}[- ]?\\d{2}[- ]?\\d{4}$";
        final CharSequence inputStr = ssn;
        final Pattern pattern = Pattern.compile(expression);
        final Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();

    }
}
