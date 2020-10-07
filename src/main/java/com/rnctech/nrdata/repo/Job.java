package com.rnctech.nrdata.repo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * @contributor zilin
 * 2020.09
 */

@Entity
@Table(name = "job", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"schemaid","userid"})}
)
public class Job extends JobBase {

	public Job() {
		super();
	}

	@OneToMany(cascade = {
			CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "job")
	private Set<JobDetail> jobdetails = new HashSet<>(0);
	
	
	  @Column(name = "jobname")
	  String jobname;
	 
	  @Column(name = "jobid")
	  long jobid = -1l;
	  
	  @Column(name = "userid")
	  String userid;
	  
	  @Column(name = "status")
	  int status= -1;
	  
	  @Column(name = "schedulexpr")
	  String schedulexpr;
	  
	  @Column(name = "scheduletype")
	  int scheduletype = 0;
	  
	  @Column(name = "schemaid")
	  String schemaid;
	  
	  @Column(name = "output")  //json, csv, sql, binary of output file type
	  String output; 
	  
	  @Column(name = "description", length = 2048)
	  String description;
	  
	  @Column(name = "version")
	  String version;
	  

	public Set<JobDetail> getJobdetails() {
		return jobdetails;
	}

	public void setJobdetails(Set<JobDetail> jobdetails) {
		this.jobdetails = jobdetails;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public long getJobid() {
		return jobid;
	}

	public void setJobid(long jobid) {
		this.jobid = jobid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSchedulexpr() {
		return schedulexpr;
	}

	public void setSchedulexpr(String schedulexpr) {
		this.schedulexpr = schedulexpr;
	}

	public int getScheduletype() {
		return scheduletype;
	}

	public void setScheduletype(int scheduletype) {
		this.scheduletype = scheduletype;
	}

	public String getSchemaid() {
		return schemaid;
	}

	public void setSchemaid(String schemaid) {
		this.schemaid = schemaid;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
