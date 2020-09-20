package com.rnctech.nrdata.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rnctech.nrdata.model.PokerCard;
import com.rnctech.nrdata.services.BridgeService;

@RestController
@RequestMapping("/bridge")
public class BridgeController {
	
	@Autowired
	BridgeService bridgeService;
	
    @GetMapping(value = "/hands", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Set<PokerCard>>> genPersons() throws Exception {
    	Map<String, Set<PokerCard>> ps = bridgeService.generate();
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }
}
