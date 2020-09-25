package com.rnctech.nrdata.utils;
/* 
* @Author Zilin Chen
* @Date 2020/09/05
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.rnctech.nrdata.model.Person;
import com.rnctech.nrdata.model.Person.Gender;
import com.rnctech.nrdata.utils.RedisUtils.OBJ_TYPE;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class RedisUtils {
	
	public static enum OBJ_TYPE {
		MaleName, FemaleName, SurName, FullName, Company, Address, Domain	
	}
	
	public static List<String> getRandomValues(OBJ_TYPE type, int size){
		List<String> vs = new ArrayList<>();
		Jedis redis = getJedisInstnace();
		for(int i=0; i<size; i++) {
			String rk = redis.randomKey();
			while(!rk.startsWith(type.name())) {
				rk = redis.randomKey();
			}		
			vs.add(redis.get(rk));
		}
		return vs;
	}
	
	
	public static Jedis getJedisInstnace() {
		Jedis redis = new Jedis("localhost",6379);
		redis.auth("Admin123");
		//Jedis redis = new Jedis();
		return redis;
	}

	public static void putFileInRedisPipeline(OBJ_TYPE type, String csvfile) throws Exception {
		Jedis connection = getJedisInstnace();
		List<String> lines = Files.readAllLines(Paths.get(csvfile), Charset.forName("UTF-8"));

		Pipeline p = connection.pipelined();
		int i = 1;
		for (String line : lines) {
			String key = type.name() + "/" + i;
			p.set(key, line);
			i++;
		}
		p.sync();

	}
	
	public static void putFileToRedis(OBJ_TYPE type, String csvfile) throws Exception {
		Jedis redis = getJedisInstnace();
		BufferedReader br = new BufferedReader(new FileReader(csvfile));
		String line;
		int i=1;
		while(null != (line = br.readLine())) {
			String key = type.name()+"/"+i;
			redis.set(key, line);
			i++;
		}
		br.close();
	}
	
	static Person genRandomPerson(Jedis redis) {
		Person p = new Person();
		int isDone = 0;
		while(isDone < 4) {		
			String rk = redis.randomKey();
			if(null == p.getFname() && rk.startsWith(OBJ_TYPE.MaleName.name())) {
				p.setFname(redis.get(rk).toString());
				p.setGender(Gender.Male);
				isDone++;
				continue;
			}else if(null == p.getFname() && rk.startsWith(OBJ_TYPE.FemaleName.name())){
				p.setFname(redis.get(rk).toString());
				p.setGender(Gender.Female);
				isDone++;
				continue;
			}else if(rk.startsWith(OBJ_TYPE.SurName.name()) && null == p.getLname()) {
				p.setLname(redis.get(rk).toString());
				isDone++;
				continue;
			}else if(rk.startsWith(OBJ_TYPE.Company.name()) && null == p.getCompany()) {
				p.setCompany(redis.get(rk).toString());
				isDone++;
				continue;
			}else if(rk.startsWith(OBJ_TYPE.Domain.name()) && null == p.getEmail()) {
				p.setEmail(redis.get(rk).toString());
				isDone++;
			}			
		}
		
		String email = String.format("%s.%s@%s", p.getFname(), p.getLname(), p.getEmail());
		p.setEmail(email);
		return p;
	}

	public static void main(String[] args) throws Exception {
		//putFileToRedis(OBJ_TYPE.MaleName,  "src/main/resources/data/en/male_firstnames.csv");
		//putFileToRedis(OBJ_TYPE.FemaleName,  "src/main/resources/data/en/female_firstnames.csv");
		//putFileToRedis(OBJ_TYPE.SurName,  "src/main/resources/data/en/surnames.csv");
		//putFileToRedis(OBJ_TYPE.Company,  "src/main/resources/data/en/companies.csv");
		//putFileToRedis(OBJ_TYPE.Domain,  "src/main/resources/data/en/email_domains.csv");
		int size = 100;
		/*
		 * List<String> cs = getRandomValues(OBJ_TYPE.Domain, size); for(int i=0;
		 * i<cs.size(); i++) { System.out.println(cs.get(i)); }
		 */
		
		Jedis redis = getJedisInstnace();
		int i = 0;
		while(i < size) {
			Person p = genRandomPerson(redis);
			System.out.println(p.toString());
			i++;
		}
	}

}
