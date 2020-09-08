package com.rnctech.nrdata.controllers;

/**
 * Api for generate person entities
 *
 * @Author Zilin Chen
 * @Date 2020/09/06
 */

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rnctech.nrdata.model.Person;
import com.rnctech.nrdata.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	PersonService personService;
	
    @GetMapping(value = "/basic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> genPersons() throws Exception {
    	List<Person> ps = personService.generatePersons(-1);
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }
}
