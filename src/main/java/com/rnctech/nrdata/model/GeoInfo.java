package com.rnctech.nrdata.model;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoInfo {

   private String state;	
   private String country;
   
   private String city;
   
   private String postcode;
   
   private String address;
   
   private double longitude;
   
   private double latitude;
   
   public GeoInfo(String add, String city, String state, String country, String pcode) {
	   this.address = add;
	   this.city = city;
	   this.state = state;
	   this.country = country;
	   this.postcode = pcode;
	}
}
