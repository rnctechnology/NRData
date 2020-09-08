package com.rnctech.nrdata.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
public class USPhoneNoGenerator {
	
	public final static String expression = "^(?:\\+?1[-. ]?)?\\(?([2-9][0-8][0-9])\\)?[-. ]?([2-9][0-9]{2})[-. ]?([0-9]{4})$";

    public static ArrayList generatePhoneNo(int total, int noofvalidphno,
            int noofinvalidphno) {

        final ArrayList<String> svalidno = new ArrayList<String>(total);
        final ArrayList<String> sinvalidno = new ArrayList<String>(total);
        final ArrayList<ArrayList> combineno = new ArrayList<ArrayList>(total);
        final String[] random = { "1", "1-", "+1", "" };
        while (svalidno.size() != noofvalidphno
                && sinvalidno.size() != noofinvalidphno) {
            final int isempty = (noofvalidphno - svalidno.size()
                    + noofinvalidphno - sinvalidno.size());

            for (int i = 0; i < isempty; i++) {
                final String fno = Integer
                        .toString((int) ((Math.random() * (800)) + 100));
                final String mno = Integer
                        .toString((int) ((Math.random() * (800)) + 100));
                final String lno = Integer
                        .toString((int) ((Math.random() * (8999)) + 1000));
                final int fchar = (int) (Math.random() * 3);
                final String tempssn = random[fchar] + fno + " " + mno + " "
                        + lno;
                if (isValidPhNo(tempssn) && svalidno.size() < noofvalidphno) {
                    svalidno.add(tempssn);
                } else if (sinvalidno.size() <= noofinvalidphno) {
                    sinvalidno.add(tempssn);
                }

            }
        }
        // svalidno is the arraylist of valid phone nos
        // sinvalidno is the arraylist of invalid phone nos
        combineno.add(svalidno);
        combineno.add(sinvalidno);
        return combineno;
    }
    
    public static List generatePhonesBasic(int total, int noofvalidphno,
            int noofinvalidphno, String deli1, String deli2) {

    	if(null == deli1) deli1 =	"";
    	if(null == deli2) deli2 = 	"";
    	List<String> phones = new ArrayList<String>(total);    	
        int i = 0;
        int j = 0;
        while (i+j < total) {
                final String fno = Integer
                        .toString((int) ((Math.random() * (800)) + 100));
                final String mno = Integer
                        .toString((int) ((Math.random() * (800)) + 100));
                final String lno = Integer
                        .toString((int) ((Math.random() * (8999)) + 1000));

                final String tempssn = fno + deli1 + mno + deli2 + lno;
                
                if (isValidPhNo(tempssn) && i < noofvalidphno) {
                	phones.add(tempssn);
                	i++;
                } else if (j <= noofinvalidphno) {
                	phones.add(tempssn);
                	j++;
                }
        }

        return phones;
    }

    public static boolean isValidPhNo(String phno) {
        final CharSequence inputStr = phno;
        final Pattern pattern = Pattern.compile(expression);
        final Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
}
