package com.rnctech.nrdata.model;

import com.rnctech.nrdata.NrdataConstants.DEVIATION_TYPE;
import com.rnctech.nrdata.NrdataConstants.OUT_TYPE;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
public class GenConfig {
	
	private int count = 100;
	private DEVIATION_TYPE dtype = DEVIATION_TYPE.percentage;
	private boolean isEvent;
	
	private OUT_TYPE outputType = OUT_TYPE.json;
	
}
