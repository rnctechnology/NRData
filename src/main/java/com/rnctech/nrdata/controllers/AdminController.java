package com.rnctech.nrdata.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rnctech.nrdata.services.UserService;

/*
 * @contributor zilin
 * 2020.09.30
 * 
 * admin controller for user and schema etc
 */

@RestController
@RequestMapping(value = "api/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	UserService userService;
}
