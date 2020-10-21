package com.rnctech.nrdata.services;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rnctech.nrdata.repo.User;
import com.rnctech.nrdata.repo.UserRepo;

/*
 * @contributor zilin
 * 2020.10
 * 
 * for user management
 */

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	MongoSeqService seqService;
	
	public User registeUser(User user) {
		user.setId(seqService.generateSequence(User.SEQUENCE_NAME));
		return userRepo.save(user);
	}
	
	public List<User> listUsers(){
		return userRepo.findAll();
	}
	
	public String unRegister(Long id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {
			userRepo.delete(user.get());
			return "User unRegistered "+id;
		}else {
			throw new RuntimeException("User not exists "+id);
		}
	}
}
