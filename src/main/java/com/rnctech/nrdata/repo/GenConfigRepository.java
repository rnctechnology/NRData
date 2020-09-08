package com.rnctech.nrdata.repo;
/* 
* @Author Zilin Chen
* @Date 2020/09/05
*/
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rnctech.nrdata.model.GenConfig;
import com.rnctech.nrdata.model.Person;

@Repository
public interface GenConfigRepository extends CrudRepository<GenConfig, String> {}

