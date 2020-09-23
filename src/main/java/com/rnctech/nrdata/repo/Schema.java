package com.rnctech.nrdata.repo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @contributor zilin
 * 2020.09
 */

@Entity
@Table(name = "schema")
public class Schema extends JobBase {

	@Column(name = "name")
	String name;
	
	@Column(name = "definition", columnDefinition = "TEXT")
	String definition;
	
	@Column(name = "type")  //definition as graphql, json or properties 
	String type;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "version", unique = true, nullable = false)
	long version = 0l;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user;
	
}
