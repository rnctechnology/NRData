package com.rnctech.nrdata.utils;
/* 
* @Author Zilin Chen
* @Date 2020/09/05
*/
import java.io.*;
import java.util.*;

public class TextFileUtils {
	static int NAME_LEN = 62;
	static String[] skipLine = {"TAXABLE INCOME SERIES", "a series of"};
	
	public static void removeDuplicateLine(String fin, String fout, int skip, String mf) throws Exception {
		List<String> names = new ArrayList<>();
		FileReader fr = new FileReader(fin);
		BufferedReader bf = new BufferedReader(fr);
		
		BufferedReader mr = null;
		if(null != mf) {
			FileReader mfr = new FileReader(mf);
			mr = new BufferedReader(mfr);
		}
		FileWriter fw = new FileWriter(fout);
		PrintWriter pw = new PrintWriter(fw);
		String line;
		String mergeline=null;
		String fmergeline=null;
		if(null != mf) {
			mergeline=mr.readLine().trim();
			fmergeline = format(mergeline);
		}
		int count = 1;
		while(null != (line = bf.readLine())) {
			if(count > skip) {
				String l = line.trim();
				//int idx = line.indexOf("\t");
				int idx = line.length();
				if(idx > NAME_LEN) {
					l = l.substring(0, NAME_LEN).trim();
				}
				
				boolean yn = false;
				if(0 == l.length())
					yn = true;
				for(int i =0; i< skipLine.length; i++) {
					if(l.contains(skipLine[i])) {
						yn = true;
						break;
					}
				}
				String fl = format(l);
				if(!yn && !names.contains(fl)) {
					//merge in order
					if(null != mr) {
						do {
							if(null != mergeline && fmergeline.compareTo(fl) <= 0) {
								if(!names.contains(fmergeline)) {
									names.add(fmergeline);
									pw.write(mergeline+"\n");
									System.out.println("M => "+mergeline);
								}
								mergeline = mr.readLine();
								if(null != mergeline) {
									mergeline = mergeline.trim();
									fmergeline = format(mergeline);
								}
							}
						} while(null != mergeline && fmergeline.compareTo(fl) < 0); 
					}					
					names.add(fl);
					pw.write(l+"\n");
					//System.out.println(l);
				}
			}
			count ++;
		}
		bf.close();
		pw.flush();
		pw.close();
	}
	
	
	private static String format(String l) {
		String s = l.trim().toLowerCase();
		if('.' == s.charAt(s.length()-1)) {
			s = s.substring(0, (s.length()-1));
		}
		return s;
	}
	
	public static void mergeCompany() throws Exception {
		String fin = "src/main/resources/data/company.idx";
		String fout = "src/main/resources/data/en/companies.csv";
		File rf = new File(fin);
		if(!rf.exists()) {
			System.out.println(rf.getAbsolutePath()+" doesn't exist!");
			System.exit(1);
		}
		String mergef = "src/main/resources/data/company_us.txt";
		removeDuplicateLine(fin,fout, 10, mergef);
		System.out.println("Done in "+(new File(fout)).getAbsolutePath());
	}
	
	public static TreeSet<String> getOderList(String cfile, String outfile, boolean camel) throws Exception {
		TreeSet<String> ts = new TreeSet<String>();
		BufferedReader br = new BufferedReader(new FileReader(cfile));

		String line = br.readLine();
		while(null != line) {
			String name = line.trim().replaceAll(" ", "").replaceAll("-", "");
			if(0 != name.length()) {
				if(camel) {
					ts.add(CapString(name));
				}else {
					ts.add(name);  
				}
			}
			line = br.readLine();
		}
		br.close();
		if(null != outfile) {
			PrintWriter out = new PrintWriter(new FileWriter(outfile));
			Iterator<String> ws = ts.iterator();
			while(ws.hasNext()) {
				out.write(ws.next()+"\n");
			}
			out.flush();
			out.close();
		}
		
		return ts;
	}
	
	private static String CapString(String name) {
		char fc = name.charAt(0);
		if(fc >= 'a') {
			fc = (char)((int)fc + ('a' - 'A'));
		}
		return fc+name.substring(1).toLowerCase();
	}
	
	public static void mergeFileWithOrderedSet(String infile, String outfile, Set<String> s) throws Exception {
		PrintWriter pw = new PrintWriter(new FileWriter(outfile));
		
		Iterator<String> i = s.iterator();
		String first = i.next();
		
		BufferedReader br = new BufferedReader(new FileReader(infile));
		String line = br.readLine();
		while(null != line) {
			String a = line.trim();  //file already Camelled and ordered
			do{
				if(!i.hasNext())
					break;
				int c = first.compareTo(a);
				if(c <= 0) {
					if(c < 0) {
						pw.write(first+"\n");
						System.out.print(first+" ");
					}
					if(i.hasNext()) first = i.next();
				}else {
					break;
				}
			}while(i.hasNext());	
			
			pw.write(a+"\n");
			System.out.print("\n"+a+" : ");
			System.out.flush();
			line = br.readLine();
		}	
		br.close();
		pw.flush();
		pw.close();
	}

	public static void main(String[] args) throws Exception {
		String surnamefile = "src/main/resources/data/en/free_email_provider_domains.txt";
		Set<String> names = getOderList(surnamefile, "src/main/resources/data/en/email_domains.csv", false);		
		//mergeFileWithOrderedSet("src/main/resources/email_domain.txt","src/main/resources/data/en/email_domains.csv", names);
	}

	public static void printOut(Set<String> names) {
		char c='A';
		Iterator<String> i = names.iterator();
		int total=0;
		int count = 0;
		while(i.hasNext()) {
			char x = i.next().charAt(0);
			if(c == x) {
				count++;
			}else {
				System.out.println(c+" == "+count);
				total += count;
				c = x;
				count = 1;
			}
		}
		System.out.println(c+" == "+count);
		System.out.println("Total == "+(total+count));
	}

}
