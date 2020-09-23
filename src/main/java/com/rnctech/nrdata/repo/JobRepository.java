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
public interface JobRepository extends JpaRepository<Job,  Long> {

	  @Query("SELECT j FROM Job j WHERE LOWER(j.userid) = LOWER(:userid)")
	  public List<Job> findByUser(@Param("userid") String user);

	  @Query("SELECT j FROM Job j WHERE LOWER(j.userid) = LOWER(:userid) AND j.status >= :status")
	  public List<Job> findByUserAndStatus(@Param("userid") String user, @Param("status") int status);
	  
}
