package com.rnctech.nrdata.generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class ImageGenerator {
	
	BufferedImage ti;

	public enum IMG_TYPE {
		jpg, png, gif
	}
	
	public BufferedImage genImage() {
		return this.ti;
	}
	
	
	public BufferedImage standardizeImage(BufferedImage img) {
		//1, resize
		//2, rgb amend, such as to black-white, red-enhancement etc.
		//3, transformer, such as sharp-boundary
		
		return img;
	}
	
	
	
	//@PostConstruct
	protected BufferedImage initTempateImg() throws IOException {
		File tmpf = new File("templates/bfb.jpg");
		ti = ImageIO.read(tmpf);
		return ti;
	}
}
