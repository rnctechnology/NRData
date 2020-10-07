package com.rnctech.nrdata.repo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @contributor zilin
 * 2020.09
 */

@Entity
@Table(name = "users")
public class User extends JobBase {

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
