package com.rnctech.nrdata.services;
/* 
* @Author Zilin Chen
* @Date 2020/09/05
*/
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.rnctech.nrdata.NrdataConstants;
import com.rnctech.nrdata.generator.AddressGenerator;
import com.rnctech.nrdata.model.GeoInfo;

@Service
public class GeoInfoService  implements NrdataConstants {
	
 	@Autowired
 	RedisTemplate<String, Object> redis; 	
 	
 	public List<GeoInfo> generateGeoInfos(int total){
 		total = (total <= 0)? DFT_COUNT:total;
 		List<GeoInfo> ginfos = new ArrayList<>();
 		int i=0;
 		while(i<total) {
 			ginfos.add(AddressGenerator.genGeoInfo());
 		}
 		return ginfos;
 	}
}
