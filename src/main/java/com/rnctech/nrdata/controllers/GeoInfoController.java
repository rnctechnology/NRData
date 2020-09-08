package com.rnctech.nrdata.controllers;

/**
 * Api for generate geo related infos
 *
 * @Author Zilin Chen
 * @Date 2020/09/06
 */

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rnctech.nrdata.model.GeoInfo;
import com.rnctech.nrdata.services.GeoInfoService;

@RestController
@RequestMapping("/geoinfo")
public class GeoInfoController {
	
	@Autowired
	GeoInfoService ginfoService;
	
    @GetMapping(value = "/basic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoInfo>> genPersons() throws Exception {
    	List<GeoInfo> ps = ginfoService.generateGeoInfos(-1);
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }
}
