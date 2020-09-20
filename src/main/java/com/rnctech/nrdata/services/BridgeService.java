package com.rnctech.nrdata.services;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.rnctech.nrdata.generator.PokerBridgeGenerator;
import com.rnctech.nrdata.model.PokerCard;

/* 
* @Author Zilin Chen
* @Date 2020/09/20
*/

@Service
public class BridgeService {
	
	public Map<String, Set<PokerCard>> generate(){
		int[] cs = PokerBridgeGenerator.deal();
		return PokerBridgeGenerator.getFourHands(cs);
	}

}
