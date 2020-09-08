package com.rnctech.nrdata.test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rnctech.nrdata.generator.AddressGenerator;
import com.rnctech.nrdata.generator.DateTimeGenerator;
import com.rnctech.nrdata.generator.NumberGenerator;
import com.rnctech.nrdata.generator.SensitiveDataGenerator;
import com.rnctech.nrdata.generator.UIDGenerator;
import com.rnctech.nrdata.generator.USPhoneNoGenerator;
/* 
* @Author Zilin Chen
* @Date 2020/09/06
*/
public class DemoDataTest {

    public static void main(String args[]) {

        // generate unique ids, enter the max and min limits as the paramerters
        final UIDGenerator du = new UIDGenerator();
        du.generateUID(10, 100000, 1000);

        // date and time generation enter the startyear and the endyear as
        // parameters
        final DateTimeGenerator d = new DateTimeGenerator();
        d.generateDate(5, 1901, 2015);
        d.generateDateTime(5, 1901, 2015);

        // name and address generation
        final AddressGenerator dnd = new AddressGenerator();
        dnd.generateNames(5, 2, 3);
        dnd.generateAddress(5);

        // SSN generation. Enter the no of valid and invalid ssn u want
        final SensitiveDataGenerator dsd = new SensitiveDataGenerator();
        // generate 3 valid and 2 invalid ssn
        final ArrayList<ArrayList> ssnos = dsd.generateSsnDash(5, 3, 2);
        // here m gettin the arraylist of valid ssn as "ssnvalid"
        final ArrayList<String> ssnvalid = ssnos.get(0);
        for (int i = 0; i < ssnvalid.size(); i++) {
            System.out.println(ssnvalid.get(i));
        }

        final USPhoneNoGenerator dpno = new USPhoneNoGenerator();
        final ArrayList<ArrayList> phnos = dpno.generatePhoneNo(6, 3, 3);
        final ArrayList<String> phvalid = phnos.get(0);
        for (int j = 0; j < phvalid.size(); j++) {
            System.out.println(phvalid.get(j));
        }

        final NumberGenerator dv = new NumberGenerator();
        dv.getBoolean(5);
        dv.getInteger();
        dv.getDouble(4);

    }

    public static boolean isValidSSN(String ssn) {

        final String expression = "^(?:\\+?1[-. ]?)?\\(?([2-9][0-8][0-9])\\)?[-. ]?([2-9][0-9]{2})[-. ]?([0-9]{4})$";
        // final String expression = "^\\d{3}[- ]?\\d{2}[- ]?\\d{4}$";
        final CharSequence inputStr = ssn;
        final Pattern pattern = Pattern.compile(expression);
        final Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();

    }
}
