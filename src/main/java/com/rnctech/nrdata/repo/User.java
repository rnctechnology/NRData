package com.rnctech.nrdata.repo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @contributor zilin
 * 2020.09
 */

@Entity
@Table(name = "users")
@Document(collection = "users")
public class User extends JobBase {

	public static final String SEQUENCE_NAME = "user_sequence"; 
	
	@Column(name = "name")
	String name;
	
	@Column(name = "userid")
	String userid;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "active")
	boolean isActive;
	
	@OneToMany(cascade = {
			CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Schema> schemas = new HashSet<>(0);
	
}
