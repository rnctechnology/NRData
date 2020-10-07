package com.rnctech.nrdata.repo;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @contributor zilin 2020.09
 */

@Entity
@Table(name = "job_detail", uniqueConstraints = { @UniqueConstraint(columnNames = { "sessionid" }) })
public class JobDetail extends JobBase {

	public JobDetail() {
		super();
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "jobid", referencedColumnName = "id")
	private Job job;

	@Column(name = "sessionid")
	int sessionid = -1;

	@Column(name = "status")
	int status = -1;

	@Column(name = "sourceSystemName", length = 32)
	String sourceSystemName;

	@Column(name = "jobType", length = 32)
	String jobType;

	//total row,  deviations etc. runtime properties
	@Column(name = "jobProperties", columnDefinition = "TEXT")
	private String jobProperties;

	@Column(name = "finished")
	Date finished;

	@Column(name = "description", length = 2048)  //error info etc.
	String description;

	@Lob
	@Column(name = "zips")  //in case of snapshot, images
	private byte[] zips;

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public int getSessionid() {
		return sessionid;
	}

	public void setSessionid(int sessionid) {
		this.sessionid = sessionid;
	}

	public Date getFinished() {
		return finished;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSourceSystemName() {
		return sourceSystemName;
	}

	public void setSourceSystemName(String sourceSystemName) {
		this.sourceSystemName = sourceSystemName;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobProperties() {
		return jobProperties;
	}

	public void setJobProperties(String jobProperties) {
		this.jobProperties = jobProperties;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getZips() {
		return zips;
	}

	public void setZips(byte[] zips) {
		this.zips = zips;
	}
}
