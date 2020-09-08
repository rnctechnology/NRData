package com.rnctech.nrdata.services;

import java.awt.image.BufferedImage;
import org.springframework.stereotype.Service;

import com.rnctech.nrdata.generator.BarbecueBarcodeGenerator;
import com.rnctech.nrdata.generator.Barcode4jBarcodeGenerator;
import com.rnctech.nrdata.generator.QRGenBarcodeGenerator;
import com.rnctech.nrdata.generator.ZXBarcodeGenerator;
/* 
* @Author Zilin Chen
* @Date 2020/09/05
*/

@Service
public class BarcodeService {
	
	public static BufferedImage generateEAN13BarcodeImage_barbecue(String barcodeText) throws Exception { 
	    return BarbecueBarcodeGenerator.generateEAN13BarcodeImage(barcodeText);
	}
	
	public static BufferedImage generateEAN13BarcodeImage_barcode4j(String barcodeText) {
	    return Barcode4jBarcodeGenerator.generateEAN13BarcodeImage(barcodeText);
	}
	
	public static BufferedImage generateEAN13BarcodeImage(String barcodeText) throws Exception {
	    return ZXBarcodeGenerator.generateEAN13BarcodeImage(barcodeText);
	}
	
	public static BufferedImage generateQRCodeImage_zxing(String barcodeText) throws Exception {	 
	    return ZXBarcodeGenerator.generateQRCodeImage(barcodeText);
	}
	
	public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
		return QRGenBarcodeGenerator.generateQRCodeImage(barcodeText);
	}
}
