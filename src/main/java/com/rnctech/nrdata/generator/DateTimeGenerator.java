package com.rnctech.nrdata.generator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomUtils;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
public class DateTimeGenerator {
	
	public final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd-HH.mm.ss.SSSSSS"); // 2006-06-08-00.00.00.000000

	public static List generateDT(int total, int startyear, int endyear) {
		final List<String> Dates = new ArrayList<String>(total);
		for (int i = 0; i < total; i++) {
			Dates.add(genSimpleDT(startyear, endyear));
		}
		return Dates;
	}
	
	public static String genSimpleDT(int startyear, int endyear) {
		Random r = new Random();
		int y = startyear + r.nextInt(endyear - startyear);
		Calendar cdr = Calendar.getInstance();
		cdr.set(Calendar.YEAR, y);
		cdr.set(Calendar.MONTH, RandomUtils.nextInt(1,12));
		cdr.set(Calendar.DATE, RandomUtils.nextInt(1,30));
		cdr.set(Calendar.HOUR_OF_DAY, 6);
		cdr.set(Calendar.MINUTE, 0);
		cdr.set(Calendar.SECOND, 0);
		long val1 = cdr.getTimeInMillis();
		cdr.set(Calendar.HOUR_OF_DAY, 20);
		cdr.set(Calendar.MINUTE, 0);
		cdr.set(Calendar.SECOND, 0);
		long val2 = cdr.getTimeInMillis();			
		long randomTS = (long) (r.nextDouble() * (val2 - val1)) + val1;
		Date d = new Date(randomTS);
		return sdf.format(d);
	}
	

    public static ArrayList generateDate(int total, int startyear, int endyear) {

        final ArrayList<String> Date = new ArrayList<String>(total);
        for (int i = 0; i < total; i++) {

            final String month = Integer
                    .toString((int) ((Math.random() * (12 - 1)) + 1));

            final String day = Integer
                    .toString((int) ((Math.random() * (31 - 1)) + 1));

            final String year = Integer
                    .toString((int) ((Math.random() * (endyear - startyear)) + startyear));

            final String date = month + "/" + day + "/" + year;
            Date.add(date);
            System.out.println(date);
        }
        return Date;
    }

    public static ArrayList generateDateTime(int total, int startyear,
            int endyear) {

        final ArrayList<String> DateTime = new ArrayList<String>(total);
        for (int i = 0; i < total; i++) {

            final String month = Integer
                    .toString((int) ((Math.random() * (12 - 1)) + 1));

            final String day = Integer
                    .toString((int) ((Math.random() * (31 - 1)) + 1));

            final String year = Integer
                    .toString((int) ((Math.random() * (endyear - startyear)) + startyear));

            final String date = month + "/" + day + "/" + year;

            final String hour = Integer
                    .toString((int) ((Math.random() * (24)) + 1));

            final String min = Integer
                    .toString((int) ((Math.random() * (60)) + 1));

            final String sec = Integer
                    .toString((int) ((Math.random() * (60)) + 1));

            final String time = hour + ":" + min + ":" + sec;
            DateTime.add(date + "  " + time);

            System.out.println(DateTime.get(i));

        }
        return DateTime;
    }

    public static ArrayList generateTime(int total) {

        final ArrayList<String> Time = new ArrayList<String>(total);
        for (int i = 0; i < total; i++) {
            final String hour = Integer
                    .toString((int) ((Math.random() * (24)) + 1));

            final String min = Integer
                    .toString((int) ((Math.random() * (60)) + 1));

            final String sec = Integer
                    .toString((int) ((Math.random() * (60)) + 1));

            final String time = hour + ":" + min + ":" + sec;
            Time.add(time);

            System.out.println(Time.get(i));
        }
        return Time;
    }

}
