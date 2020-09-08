package com.rnctech.nrdata.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomUtils;
import com.rnctech.nrdata.utils.DataUtils;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
public class NameGenerator extends RandomWordGenerator {
	
	public static String MIIDEL_NAMES= "middle_names.csv";
	public static String SUR_NAMES= "surnames.csv";
	public static String GIVEN_NAMES= "givennames.csv";
	
	public static Map<String, String[]> partsArray = new HashMap<String, String[]>();
	
	
	public static List genFNames(int total, int size) {
		return genNameParts(total, size, GIVEN_NAMES);
	}

	public static List genMNames(int total, int size) {
		return genNameParts(total, size, MIIDEL_NAMES);
	}

	public static List genLNames(int total, int size) {
		return genNameParts(total, size, SUR_NAMES);
	}
	
	public static List genNameParts(int total, int size, String fname) {
		List<String> nameparts = new ArrayList<String>();
		String[] options = partsArray.get(fname);
		if(null == options){
			options = DataUtils.readFile(Data_Folder+fname, Integer.MAX_VALUE);
			partsArray.put(fname, options);			
		}
		int count = 0;
		while(count<total){
			String t = options[RandomUtils.nextInt(1, options.length)];
			if(t.length() <= size){
				nameparts.add(t);
				count++;
			}
		}
		return nameparts;
	}
	
	public static String genSimpleName() {
		String fname = GIVEN_NAMES;
		String[] options = partsArray.get(fname);
		if(null == options){
			options = DataUtils.readFile(Data_Folder+fname, Integer.MAX_VALUE);
			partsArray.put(fname, options);			
		}
		return options[RandomUtils.nextInt(1, options.length)];
	}
	
	
	public static List genFullNames(int total, int size) {
		return genFullNames(total, size, " ");
	}

	public static List genFullNames(int total, int size, String deli) {
		List<String> fns = genFNames(total, 2 * (size / 5));
		List<String> mns = genMNames(total, (size / 5));
		List<String> lns = genLNames(total, 3 * (size /5));
		for(int i=0;i<total;i++){
			fns.set(i, fns.get(i)+deli+mns.get(i)+deli+lns.get(i));
		}
		return fns;
	}
}
