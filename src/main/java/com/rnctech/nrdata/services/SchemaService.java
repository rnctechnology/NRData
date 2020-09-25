package com.rnctech.nrdata.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLSchema;

/*
 * @contributor zilin
 * 2020.09.25
 * 
 * graphql schema parser or related
 */

@Service
public class SchemaService {
	
	private static final Logger logger = LoggerFactory.getLogger(SchemaService.class);
	
	public GraphQLSchema getGQLSchema() {
		return null;
	}
	
	public boolean validateSchema(String content) {
		return true;
	}
}
