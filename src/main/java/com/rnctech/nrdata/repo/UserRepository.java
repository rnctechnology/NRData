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
public interface UserRepository extends JpaRepository<User,  Long> {

	  
}
