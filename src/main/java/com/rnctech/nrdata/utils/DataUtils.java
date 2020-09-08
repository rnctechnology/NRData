package com.rnctech.nrdata.utils;
/* 
* @Author Zilin Chen
* @Date 2020/09/05
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.rnctech.nrdata.NrdataConstants.GENERATE_TYPE;
import com.rnctech.nrdata.model.Column;

public class DataUtils {
	
	public static String reverse(String str) {
		if (str == null)
			return "";
		String revstr = "";
		for (int i = str.length() - 1; i >= 0; i--) {
			revstr += str.charAt(i);
		}

		return revstr;
	}
	
	public static String[] readFile(String path, int limit){
		File f= new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = null;
			List<String> s = new ArrayList<String>();
			if(limit < 0) limit = Integer.MAX_VALUE;
			int i = 0;
			while(i<limit && null != (line = br.readLine())){
				if(line.trim().length() > 0)
				s.add(line.trim());
				i++;
			}
			return s.toArray(new String[s.size()]);
		} catch (Exception e) {
		}
		return new String[0];
	}
	
	public static void reWriteFile(String inpath, String outpath){
		File f= new File(inpath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			PrintWriter pw = new PrintWriter(outpath);
			String line = null;
			while(null != (line = br.readLine())){
				if(line.trim().length() > 0){
					String s = null;
					try {
						s = line.substring(line.lastIndexOf("\t")+1);
					} catch (Exception e) {
						continue;
					}
					if(null !=s && s.trim().length()>0){
						pw.println(s.trim());
					}
				}				
			}
			pw.flush();
			pw.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	  public static List<Column> getBaseColumns(Properties p, String prefix) {
		List<Column> columns = new ArrayList<Column>();

		if (prefix == null || prefix.length() == 0) {
			return columns;
		}

		String prefixDot = prefix;
		if (prefix.charAt(prefix.length() - 1) != '.') {
			prefixDot = prefix + '.';
		}
		String key;
		for (Enumeration e = p.keys(); e.hasMoreElements();) {
			key = (String) e.nextElement();
			if (key.startsWith(prefixDot)) {
				String[] s = p.getProperty(key).split(":");
				if (s.length != 4)
					continue;
				int sqltype = Integer.parseInt(s[0]);
				GENERATE_TYPE gtype = GENERATE_TYPE.valueOf(s[1]);
				boolean notnull = Boolean.parseBoolean(s[2]);
				int collen = Integer.parseInt(s[3]);
				Column c = new Column(key.substring(prefixDot.length()),
						sqltype, gtype, notnull);
				c.setSize(collen);
				columns.add(c);
			}
		}
		return columns;
	}

	  public static List<String> getListValues(Properties p, String prefix) {
			List<String> values = new ArrayList<String>();

			if (prefix == null || prefix.length() == 0) {
				return values;
			}

			String prefixDot = prefix;
			if (prefix.charAt(prefix.length() - 1) != '.') {
				prefixDot = prefix + '.';
			}
			String key;
			for (Enumeration e = p.keys(); e.hasMoreElements();) {
				key = (String) e.nextElement();
				if (key.startsWith(prefixDot)) {
					String[] s = p.getProperty(key).split(":");
					values.add(p.getProperty(key));
				}
			}
			return values;
		}
	  
	  public static Map<String, String> getKeyValues(Properties p, String prefix, boolean wprefix) {
			Map<String, String> pairs = new HashMap<String, String>();

			if (prefix == null || prefix.length() == 0) {
				return pairs;
			}

			String prefixDot = prefix;
			if (prefix.charAt(prefix.length() - 1) != '.') {
				prefixDot = prefix + '.';
			}
			String key;
			for (Enumeration e = p.keys(); e.hasMoreElements();) {
				key = (String) e.nextElement();
				if (key.startsWith(prefixDot)) {
					if(wprefix){
						pairs.put(key, p.getProperty(key));
					}else{
						pairs.put(key.substring(prefixDot.length()), p.getProperty(key));
					}
				}
			}
			return pairs;
		}
}
