package com.rnctech.nrdata.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.rnctech.nrdata.generator.PokerBridgeGenerator;
import com.rnctech.nrdata.generator.SudoGenerator;
import com.rnctech.nrdata.model.PokerCard;

/* 
* @Author Zilin Chen
* @Date 2020/09/20
*/

@Service
public class GameService {
	
	public static final int sudo_level = 24;  //easy 12~24, middle 25~36, hard 37~48, super hard 49~63 
			
	public Map<String, Set<PokerCard>> generateBridge(){
		int[] cs = PokerBridgeGenerator.deal();
		return PokerBridgeGenerator.getFourHands(cs);
	}

	public List<String> generateSudo(int level, boolean withsolution){
		int l = (12 > level || level >= 64)?sudo_level:level;
		int[][] org = SudoGenerator.generate();
		int[][] board = SudoGenerator.markDown(org, l);
		List<String> al = new ArrayList<>();
		for(int[] row: board) {
			al.add(Arrays.toString(row));
		}
		
		if(withsolution) {
			al.add("\n\n\nPlease try yourself before check below\n\n\n");
			al.add("-----------------------\n\n\n");
			for(int[] row: org) {
				al.add(Arrays.toString(row));
			}
		}
		
		return al;
	}
	
}
