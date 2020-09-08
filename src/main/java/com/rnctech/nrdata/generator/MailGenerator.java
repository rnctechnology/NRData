package com.rnctech.nrdata.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.rnctech.nrdata.utils.DataUtils;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
public class MailGenerator extends RandomWordGenerator {

	public static String[] domains = null;
	public static String EMAIL_DOMAIN = "email_domains.csv";
	public static int len = 0;

	public static List generateEmails(int total) {
		final List<String> emails = new ArrayList<String>(total);
		List<String> fname = NameGenerator.genFNames(total, 10);
		List<String> lname = NameGenerator.genLNames(total, 10);

		for (int i = 0; i < total; i++) {
			final long stno = (long) (100 * Math.random());
			final String dmn = getDomainName();
			int j = 4 *(i/4);
			String add="";
			if(i==j){
				add = "."+lname.get(i);
			}else if(i==j+1){
				add = "_"+stno;
			}else if(i==j+2){
				add = lname.get(0).toUpperCase();
			}
			String m = fname.get(i) + add + "@" + getRandomWord(2)+dmn;
			emails.add(m);
		}
		return emails;
	}

	private static String getDomainName() {
		if (null == domains) {
			domains = DataUtils.readFile(Data_Folder + EMAIL_DOMAIN,
					Integer.MAX_VALUE);
			len = 10 * (domains.length / 10);
		}

		return domains[RandomUtils.nextInt(1,len)];
	}
	
	public static void main(String[] args){
		List<String> mails = generateEmails(100);
		for(String s: mails){
			System.out.println(s);
		}
	}
}