package com.rnctech.nrdata.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rnctech.nrdata.repo.RepoException;
/*
 * @contributor zilin
 * 2020.09.25
 * 
 * service to access persisted schema/job etc.
 */

@Service
public class RepoService {

	@Autowired
	AuthService authService;
	
	private static final Logger logger = LoggerFactory.getLogger(RepoService.class);
	
	public String storeFile(MultipartFile file) throws RepoException {
		try {
			String fileName = generatename(file);
			//persistence into schema
			String sessionUser = authService.getLoggedUser();
			
			return fileName;
		}catch(Throwable t) {
			throw new RepoException(t.getMessage());
		}
	}

	private String generatename(MultipartFile file) {
		return file.getName()+"_"+System.currentTimeMillis();
	}

	public Resource loadFile(String fileName) {
		//check user
		String sessionUser = authService.getLoggedUser();
		return null;
	}



}
