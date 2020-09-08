package com.rnctech.nrdata.generator;

import java.util.ArrayList;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
public class NumberGenerator {

    public static ArrayList getBoolean(int total) {

        final ArrayList<String> Boolean = new ArrayList<String>(total);
        final String[] boolarray = { "true", "false", "true", "false" };
        for (int i = 0; i < total; i++) {
            final int no = (int) (Math.random() * 3);

            Boolean.add(boolarray[no]);

            // System.out.println(Boolean.get(i));
        }
        return Boolean;
    }

    public static int getInteger() {

        final int no = (int) (Math.random() * 1000000000);
        System.out.println(no);
        return no;
    }

    public static double getDouble(int precision) {

        double no = Math.random() * 10000000;
        int preno = 1;
        for (int i = 0; i < precision; i++) {
            preno = preno * 10;
        }
        no = (int) (no * preno);

        no = no / preno;
        System.out.println(no);
        return no;

    }
}
