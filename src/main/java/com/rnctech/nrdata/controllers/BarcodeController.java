package com.rnctech.nrdata.controllers;

/**
 * Shared stuffs for this project
 * @Author Zilin Chen
 * @Date 2020/09/02
 */

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.rnctech.nrdata.generator.BarbecueBarcodeGenerator;
import com.rnctech.nrdata.services.BarcodeService;

@RestController
@RequestMapping("/barcode")
public class BarcodeController {

	@GetMapping(value = "/ean13/{barcode}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<BufferedImage> barbecueEAN13Barcode(@PathVariable("barcode") String barcode)
			throws Exception {
		return new ResponseEntity<>(BarbecueBarcodeGenerator.generateEAN13BarcodeImage(barcode),HttpStatus.OK);
	}
	
	//@PathVariable Optional<Integer> qrcode

    @GetMapping(value = "/qrgen/{qrcode}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> qrgen(@PathVariable("qrcode") String qrcode) throws Exception {
    	return new ResponseEntity<>(BarcodeService.generateQRCodeImage(qrcode), HttpStatus.OK);
    }
	
    @RequestMapping(value = "/jpgqrgen/{qrcode}", method=RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] showImageOnId(@PathVariable("qrcode") String qrcode) throws Exception{
        BufferedImage bi = BarcodeService.generateQRCodeImage(qrcode);        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
    
}
