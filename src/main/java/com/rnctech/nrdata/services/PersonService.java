package com.rnctech.nrdata.services;
/* 
* @Author Zilin Chen
* @Date 2020/09/05
*/
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.rnctech.nrdata.NrdataConstants;
import com.rnctech.nrdata.model.Person;
import com.rnctech.nrdata.model.Person.Gender;
import com.rnctech.nrdata.utils.RedisUtils.OBJ_TYPE;

@Service
public class PersonService implements NrdataConstants {
	
 	@Autowired
 	RedisTemplate<String, Object> redis;
 	
	public List<Person> generatePersons(int total){
		List<Person> res = new ArrayList<>();
		int i = 0;
		int count = (10 >= total) ? DFT_COUNT: total;
		while(i < count) {
			res.add(genRandomPerson());
			i++;
		}
		return res;
	}

	private Person genRandomPerson() {
		Person p = new Person();
		int isDone = 0;
		while(isDone < 4) {		
			String rk = redis.randomKey();
			if(null == p.getFname() && rk.startsWith(OBJ_TYPE.MaleName.name())) {
				p.setFname(redis.opsForValue().get(rk).toString());
				p.setGender(Gender.Male);
				isDone++;
				continue;
			}else if(null == p.getFname() && rk.startsWith(OBJ_TYPE.FemaleName.name())){
				p.setFname(redis.opsForValue().get(rk).toString());
				p.setGender(Gender.Female);
				isDone++;
				continue;
			}else if(rk.startsWith(OBJ_TYPE.SurName.name()) && null == p.getLname()) {
				p.setLname(redis.opsForValue().get(rk).toString());
				isDone++;
				continue;
			}else if(rk.startsWith(OBJ_TYPE.Company.name()) && null == p.getCompany()) {
				p.setCompany(redis.opsForValue().get(rk).toString());
				isDone++;
				continue;
			}else if(rk.startsWith(OBJ_TYPE.Domain.name()) && null == p.getEmail()) {
				p.setEmail(redis.opsForValue().get(rk).toString());
				isDone++;
			}			
		}
		
		String email = String.format("%s.%s@%s", p.getFname(), p.getLname(), p.getEmail());
		p.setEmail(email);
		return p;
	}
}
