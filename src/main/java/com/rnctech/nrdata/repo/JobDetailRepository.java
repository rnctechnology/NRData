package com.rnctech.nrdata.repo;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @contributor zilin
 * 2020.09
 */

@Transactional
public interface JobDetailRepository extends JpaRepository<JobDetail,  Long> {

	  @Query("SELECT jd FROM JobDetail jd WHERE LOWER(jd.jobType) = LOWER(:jobType) AND jd.job.id = :jobid")
	  public JobDetail findByTypeAndJobid(@Param("jobType") String jobtype, @Param("jobid") Long jobid);
	  
	  public List<JobDetail> findBySessionid(String sessionid);
	  
	  public Iterable<JobDetail> findByJob(Job job);
	  
}
