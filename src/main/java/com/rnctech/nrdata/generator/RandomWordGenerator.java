package com.rnctech.nrdata.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.rnctech.nrdata.NrdataConstants;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
public class RandomWordGenerator implements NrdataConstants {
	
	public static List generateNames(int total, int overlap, int diff) {

        final List<Name> names = new ArrayList<Name>(total);
        final int[] overlaps = getRandomCounts(0, total - overlap, overlap);
        printCount(overlaps);
        final int[] diffs = getRandomCounts(0, total - overlap, diff);
        printCount(diffs);
        final int lenForGen = total - overlap;
        for (int i = 0; i < lenForGen; i++) {
            final String fn = getRandomWord(10);
            final String md = getRandomWord(5);
            final String ln = getRandomWord(12);
            boolean isDup = false;
            boolean isDiff = false;
            int j = 0;
            while (!isDup && j < overlaps.length) {
                if (i == overlaps[j]) {
                    isDup = true;
                    System.out.println(i + " dup=" + isDup);
                }
                j++;
            }
            j = 0;
            while (!isDiff && j < diffs.length) {
                if (i == diffs[j]) {
                    isDiff = true;
                    System.out.println(i + " isDiff=" + isDiff);
                }
                j++;
            }
            String fullName = fn + " " + md + " " + ln;
            if (isDiff) {
                fullName = fn + "-" + md + "-" + ln;
            }

            names.add(new Name(fn, md, ln, fullName, false, isDiff));
            if (isDup) {
                names.add(new Name(fn, md, ln, fullName, isDup, isDiff));
            }
        }
        return (names);
        // publish(names);
    }

    private static void printCount(int[] count) {

        System.out.println("\n");
        for (final int i : count) {
            System.out.print(i + " ");
        }
        System.out.println("\n");
    }

    private static void publish(List<Name> names) {

        int i = 0;
        for (final Name n : names) {
            if (n.isDup && i != 0) {
                System.out
                        .println((i - 1) + "  " + names.get(i - 1).toString());
                System.out.println(i + "  " + n.toString());
            }
            if (n.isDiff) {
                System.out.println(i + "  " + n.toString());
            }
            i++;
        }

    }

    public static int[] getRandomCounts(int start, int total, int count) {

        final int[] counts = new int[count];
        int j = 0;
        while (j < count) {
            final int c = (int) (total * Math.random());
            boolean yn = false;
            for (int k = 0; k < j; k++) {
                if (c == counts[k]) {
                    yn = true;
                    break;
                }
            }
            if (!yn) {
                counts[j] = c+start;
                j++;
            }
        }
        return counts;
    }

    public static String VOCALS_FREQ_STRING = "AEIOUYHAEIOUAEIO";

    public static String CONS_FREQ_STRING   = "BCDFGHJKLMNPQRSTVWXZBCDFGJKLMNPRSTVWBCDFGJKLMNPRST";

    public static String getRandomWord(int maxlength) {

        final char[] vocals = VOCALS_FREQ_STRING.toCharArray();
        final char[] cons = CONS_FREQ_STRING.toCharArray();
        final char[] allchars = (VOCALS_FREQ_STRING + CONS_FREQ_STRING)
                .toCharArray();

        int consnum = 0;
        int vocalnum = 0;

        final StringBuilder name = new StringBuilder(maxlength);
        final String first = RandomStringUtils.random(1, allchars);
        if (VOCALS_FREQ_STRING.indexOf(first) != -1) {
            vocalnum++;
        } else {
            consnum++;
        }

        name.append(first);
        final int len = 3 + (int) (maxlength * Math.random());

        for (int i = 1; i < len; i++) {
            String t = "";
            switch (consnum) {
            case 2: {
                consnum = 0;
                t = RandomStringUtils.random(1, vocals);
                name.append(t);
                vocalnum = 1;
                break;
            }
            case 1: {
                if (vocalnum == 1) {
                    t = RandomStringUtils.random(1, cons);
                    consnum++;
                } else {
                    t = RandomStringUtils.random(1, allchars);
                    if (VOCALS_FREQ_STRING.indexOf(first) != -1) {
                        vocalnum++;
                    } else {
                        consnum++;
                    }
                }
                name.append(t);
                break;
            }
            case 0: {
                t = RandomStringUtils.random(1, cons);
                name.append(t);
                consnum++;
            }
            }
        }
        return name.toString();

    }
    
    public static class Name implements Serializable {

        private static final long serialVersionUID = 5815059072580512775L;

        private final String      FN;

        private final String      LN;

        private final String      MN;

        private final String      FullName;

        private final boolean     isDup;

        private final boolean     isDiff;

        public Name(String fn, String ln, String mn, String fullName,
                boolean isDup, boolean isDiff) {

            super();
            FN = fn;
            LN = ln;
            MN = mn;
            FullName = fullName;
            this.isDup = isDup;
            this.isDiff = isDiff;
        }

        @Override
        public String toString() {

            return FN + "\t" + MN + "\t" + LN + "\t\t" + FullName + "\t"
                    + isDup + "\t" + isDiff;
        }

        public String getFN() {

            return FN;
        }

        public String getLN() {

            return LN;
        }

        public String getMN() {

            return MN;
        }

        public String getFullName() {

            return FullName;
        }

        public boolean isDup() {

            return isDup;
        }

        public boolean isDiff() {

            return isDiff;
        }

    }
}
