package com.rnctech.nrdata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:ColumnDefine.properties")
public class GenConfig {

	@Value( "${totalrow}" )
	private int totalRow;

}
