package com.rnctech.nrdata.controllers;

import java.util.List;

/**
 * Api for generate poker bridge game
 *
 * @Author Zilin Chen
 * @Date 2020/09/20
 */

import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.rnctech.nrdata.model.PokerCard;
import com.rnctech.nrdata.services.GameService;

@RestController
@RequestMapping("/game")
public class BridgeController {
	
	@Autowired
	GameService gameService;
	
    @GetMapping(value = "/hands", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Set<PokerCard>>> genBridgeHands() throws Exception {
    	Map<String, Set<PokerCard>> ps = gameService.generateBridge();
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }
    
    @GetMapping(value = "/sudo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<String>> genSudoBoard(@RequestParam(required = false, name="level") String l) throws Exception {
    	int level = -1;
    	try {
    	if(null != l) {
    		level = Integer.parseInt(l);
    	}
    	}catch(Exception e) {
    	}
    	List<String> ps = gameService.generateSudo(level, true);
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }
    
    
    @GetMapping(value = "/sukudo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> genSukudoBoard(@RequestParam(required = false, name="level") String l) throws Exception {
    	int level = -1;
    	try {
    	if(null != l) {
    		level = Integer.parseInt(l);
    	}
    	}catch(Exception e) {
    	}
    	List<String> ps = gameService.generateSudo(level, true);
        return ps;
    }
}
