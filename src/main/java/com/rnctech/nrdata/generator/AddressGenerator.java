package com.rnctech.nrdata.generator;

/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.rnctech.nrdata.model.GeoInfo;
import com.rnctech.nrdata.utils.DataUtils;

public class AddressGenerator extends RandomWordGenerator{
	
	public static String[] adjs = null;
	public static String[] nous = null;
	public static int len1 = 0;
	public static int len2 = 0;

	public final static String[] state_list = {"AL","Alabama","AK","Alaska","AZ","Arizona","AR","Arkansas","CA","California","CO","Colorado","CT","Connecticut","DE","Delaware","DC",
		"District Of Columbia","FL","Florida","GA","Georgia","HI","Hawaii","ID","Idaho","IL","Illinois","IN","Indiana","IA","Iowa","KS","Kansas","KY","Kentucky","LA","Louisiana","ME",
		"Maine","MD","Mayland","MA","Massachusetts","MI","Michigan","MN","Minnesota","MS","Mississippi","MO","Missouri","MT","Montana","NE","Nebraska","NV","Nevada","NH","New Hampshire",
		"NJ","New ersey","NM","New Mexico","NY","New York","NC","North Carolina","ND","North Dakota","OH","Ohio","OK","Oklahoma","OR","Oregon","PA","Pennsylvania","RI","Rhode Island","SC",
		"South Carolina","SD","South Dakota","TN","Tennessee","TX","Texas","UT","Utah","VT","Vermont","VA","Virginia","WA","Washington","WV","West Virginia","WI","Wisconsin","WY","Wyoming"};
		
	public final static String[] street_list = {"RD","AVE","PI","Mt.","Dr.","LN","PA","Ave.","Rd.","DR","Ave","Alley","Link","Avenue","Blvd","Mount","CI","Centre","Passage","Plaza","Drive",
		"Road","Path","Place","Gate","Street","Trail","Hill","View","Way","Heights","Terrace","Square","Lane"};
    
	public final static String[] city_list = {"Abbeville","Aaberdeen","Abilene","Akron","Albany","Alexandria","Allentown","Amarillo","Anaheim","Anchorage","Anderson","Ann","Arbor","Annapolis",
		"Anniston","Arlington","Asheville","Ashland","Astoria","Athens","Atlanta","Atlantic","Augusta","Austin","Bakersfield","Baltimore","Baton","Rouge","Bellevue","Bellingham","Bend",
		"Bethesda","Billings","Biloxi","Bismarck","Bloomfield","Bloomington","Boise","Boston","Bowling","Green","Bozeman","Bremerton","Bridgeport","Brunswick","Buffalo","Burlington",
		"Caldwell","Cambridge","Camden","Canton","Cedar City","Chandler","Chapel Hill","Charleston","Charlotte","Cheyenne","Chicago","Cincinnati","Claymont","Clearfield","Clearwater",
		"Cleveland","Cody","Columbia","Columbus","Concord","Cumberland","Dallas","Dayton","Dayton","Ohio","Denver","Desmoines","Detroit","Douglas","Duluth","Durham","Eau Claire","Elizabeth",
		"Erie","Escondido","Essex","Evansville","Everett","Exeter","Fairbanks","Fargo","Fayetteville","Flagstaff","Flint","Florence","Fontana","Fort Lauderdale","Fort Pierce","Fort Wayne",
		"Frederick Free Port","Fresno","Galveston","Gastonia","Glendale","Goleta","Green Bay","Greenville","Greenwich","Hamilton","Harrisburg","Hartford","Hastings","Hattiesburg","Hawthorne",
		"Helena","Henderson","Honolulu","Hot","Springs","Houston","Indianapolis","Iowa City","Jacksonville","Jamestown","Jersey City","Johnson City","Junction","Kennewick","Kenosha","Key West",
		"Kissimmee","Knoxville","Kodiak","Laconia","Lafayette","Lakewood","Lancaster","Lansing","Las Vegas","Laughlin","Lawrence","Lincoln","Littleton","Lompoc","Long Beach","Long Branch",
		"Los Angeles","Lubbock","Macon","Madison","Martinsburg","Memphis","Mesquite","Miami","Middlebury","Middletown","Milford","Millville","Milwaukee","Minneapolis","Missoula","Modesto",
		"Monroe","Montgomery","Morgantown","Myrtle Beach","Nampa","Nashua","Nashville","New Brunswick","New Orleans","New Port","New York","Newark","Oakland","Oceanside","Olympia","Omaha",
		"Ottawa","Overton","pahrump","Palm Springs","Palmer","Panama","Park City","Paterson","Pawtucket","Pensacola","Peoria","Philadelphia","Phoenix","Pittsburgh","Plainfield","Plymouth",
		"Pocatello","Portland","Prescott","Princeton","Providence","Quincy","Racine","Raleigh","Redmond","Reno","Richland","Richmond","Riverside","Rochester","Rockford","Russellville",
		"Sacramento","Salt Lake City","San Angelo","San Antonio","San Diego","San Francisco","San Jose","Santa Monica","Santa Barbara","Savannah","Scranton","Seattle","Shelbyville","Shreveport",
		"Sioux Falls","Solvang","Spartanburg","Spokane","Springfield","Springfield Missouri","Stamford","Stockton","St Louis","Tacoma","Texarkana","Toledo","Trenton","Tucson","Tulsa","Valdosta",
		"Vicksburg","Villas","Vineland","Walla","Walla","Washington","Waterloo","Weatherford","Wenatchee","Windsor","Woonsocket","Worthington","Yakima","Yankton","Youngstown","Yuma"};



    public static List<String> generateRandonAddress(int total) {
        final List<String> address = new ArrayList<String>(total);
        for (int i = 0; i < total; i++) {
            final long stno = (long) ((10000) * Math.random());
            final String f_word = getRandomWord(8);
            final String l_word = street_list[(int) ((Math.random() * street_list.length))];
            final String add = stno + " " +f_word + "  " + l_word;
            address.add(add);
        }
        return address;
    }
    
    public static List generateAddress(int total) {
        final List<String> address = new ArrayList<String>(total);
        for (int i = 0; i < total; i++) {
            final long stno = (long) ((10000) * Math.random());
            final String f_word = getStreetName();
            final String l_word = street_list[(int) ((Math.random() * street_list.length))];
            final String add = stno + " " +f_word + " " + l_word;
            address.add(add.toUpperCase());
        }
        return address;
    }
    
    private static String getStreetName() {
		if(null == adjs){
			adjs = DataUtils.readFile(Data_Folder+"commonadjectives.txt", Integer.MAX_VALUE);
			len1 = 10 * (adjs.length / 10);
		}
		if(null == nous){
			nous = DataUtils.readFile(Data_Folder+"commonnoun.txt", Integer.MAX_VALUE);
			len2 = 10 * (nous.length / 10);
		}
		return adjs[RandomUtils.nextInt(1,len1)]+" "+nous[RandomUtils.nextInt(1,len2)];
	}

	public static List generateStates(int total, boolean fullname) {
    	int statescount = state_list.length / 2;
    	final List<String> states = new ArrayList<String>(total);
    	for (int i = 0; i < total; i++) {
    		int r = (int)((Math.random() * statescount));
    		String s = state_list[2*r];
    		if(fullname){
    			s = state_list[2*r+1];
    		}   		
    		states.add(s);
    	}
    	return states;
    }
    
    public static List generateCitys(int total) {
    	final List<String> citys = new ArrayList<String>(total);
    	for (int i = 0; i < total; i++) {
    		int r = (int)((Math.random() * city_list.length));
    		String s = city_list[r];  		
    		citys.add(s);
    	}
    	return citys;
    }
    
    public static List generatePostCode(int total) {
    	final List<String> pcs = new ArrayList<String>(total);  		
    	int i = 0;
    	while(i<total){
    		int pcode = 10000 + (int) ((Math.random() * 10000));
    		pcs.add(String.valueOf(pcode));
    		i++;
    	}
    	return pcs;
    }
    
    public static List generateAddressFull(int total) {
        final List<Address> address = new ArrayList<Address>(total);
        int statescount = state_list.length / 2;
        for (int i = 0; i < total; i++) {

            final long stno = (long) ((10000) * Math.random());

            final String streetname = getStreetName();
            
            final String l_word = street_list[(int) ((Math.random() * street_list.length))];
            final String sname = streetname + "  " + l_word;

            final String state = state_list[2*((int) ((Math.random() * statescount)))];

            int r = (int)((Math.random() * city_list.length));
    		String city = city_list[r]; 

            final long pcode = (long) ((Math.random() * 99999) + 1);

            address.add(new Address(stno, sname, city, state, pcode));
            System.out.println(address.get(i));

        }

        return address;

    }
    
	public static GeoInfo genGeoInfo() {
        
        final long stno = (long) ((10000) * Math.random());
        final String f_word = getStreetName();
        final String l_word = street_list[(int) ((Math.random() * street_list.length))];
        final String sname = f_word+ " " + l_word;

        int statescount = state_list.length / 2;
        final String state = state_list[2*((int) ((Math.random() * statescount)))];

        int r = (int)((Math.random() * city_list.length));
		String city = city_list[r]; 

        long pc = (long) ((Math.random() * 99999) + 1);
        String pcode = String.valueOf(pc);
        
        String add = stno+" "+sname;
        return new GeoInfo(add, city, state, "United States", pcode);
	}


    public static class Address implements Serializable {

        private static final long serialVersionUID = 5815059072580512775L;

        private final long        _stno;

        private final String      _streetname;

        private final String      _city;

        private final String      _state;

        private final long        _postalcode;

        // private final boolean isDiff;

        public Address(long stno, String streetname, String city, String state,
                long pcode) {

            super();
            _stno = stno;
            _streetname = streetname;
            _city = city;
            _state = state;
            _postalcode = pcode;

        }

        @Override
        public String toString() {

            return _stno + "\t" + _streetname + "\t" + _city + "\t\t" + _state
                    + "\t" + _postalcode;
        }

        public long getStreetNo() {

            return _stno;
        }

        public String getstreetname() {

            return _streetname;
        }

        public String getCity() {

            return _city;
        }

        public String getState() {

            return _state;
        }

        public long isDup() {

            return _postalcode;
        }

    }
}
