package com.rnctech.nrdata;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * Shared stuffs for this project
 * @Author Zilin Chen
 * @Date 2020/09/07
 */

public interface NrdataConstants {
	
	public final static int DFT_COUNT=100;
	public static final String Data_Folder = "data/en";
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS");  
	
	public static int DEVIATION_DEFAULT_VALUE = 1;  //count as 1, or 1%
	public static enum DEVIATION_TYPE {
		count, percentage,range,random
	}
	
	public static enum OUT_TYPE {
		mysql, postgres, json, csv, kvpair 
	}
	
	public static int TIME_EXTENSFY = 24*60*60;  //1s as 1 day
	
	public static enum GENERATE_TYPE {
		NAME, FN, MN, LN, ADDRESS, PHONE, TYPEADD, TYPEACC, SSN, CITY, STATE, COUNTRY, POSTCODE, SEQ, ID, EMAIL, CREDITCARD, FULLDATE, DATE, TIME, SEQUUID, SEQORDEER, IDALPHANUMBER, IDINTEGER, OTHER
	}
	
	public static enum VARIATION_TYPE {
		PREFIX, SUFFIX, DELI, PARADELI, PARA, VARY
	}
	

}
