package com.rnctech.nrdata.model;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Person")
public class Person implements Serializable {
  
    public enum Gender { 
        Male, Female
    }
    private long id;
    private String fname;
    private String lname;
    private String email;
    private Gender gender=Gender.Female;
    private String company;
    
	public Person(long id, String fname, String lname, String company, String g) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.company = company;
		this.gender = Gender.valueOf(g);
		this.email = String.format("%s.%s@%s.com", fname, lname, company);
	}
	
	public Person() {
	}
	
	@Override
	public String toString() {
		return fname+" "+lname+"\t"+gender.name()+" "+email+"\t"+company;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}

}