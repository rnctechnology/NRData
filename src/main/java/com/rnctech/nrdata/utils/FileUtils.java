package com.rnctech.nrdata.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FileUtils {

	public static String BaseFolder = "C:/Users/admin/git/Poker-game/";
	public static String FileType = ".jpg";

	public static void renamefile(String f1, String f2) throws Exception {
		File sf = new File(f1);
		if (!sf.exists())
			throw new NoSuchFileException(f1);

		File tf = new File(f2);
		if (tf.exists()) {
			throw new Exception(f2 + " file already exist.");
		}
		sf.renameTo(tf);
		System.out.println("File in " + f2);
	}

	public static void copyfile(String f1, String f2) throws Exception {
		File sf = new File(f1);
		if (!sf.exists())
			throw new NoSuchFileException(f1);

		File tf = new File(f2);
		if (tf.exists()) {
			throw new Exception(f2 + " file already exist.");
		}
		FileInputStream fis = new FileInputStream(sf);
		FileOutputStream fos = new FileOutputStream(tf);

		try {
			int currentByte = fis.read();
			while (currentByte != -1) {
				fos.write(currentByte);
				currentByte = fis.read();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			fis.close();
			fos.close();
		}

		System.out.println("File copy to " + f2);
	}

	public static void copyFileInPath(String f1, String f2) throws Exception {
		Path source = Paths.get(new URI("file:///"+f1)); 
		Path target = Paths.get(new URI("file:///"+f2)); 

		//Files.createDirectories(target);
		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		System.out.println("File in " + target);
		
	}
	
	public static void copyImgFile(String f1, String f2, double rate) throws Exception {
		File sf = new File(f1);
		if (!sf.exists())
			throw new NoSuchFileException(f1);

		File tf = new File(f2);
		if (tf.exists()) {
			return;
			//throw new Exception(f2 + " file already exist.");
		}
		BufferedImage img = ImageIO.read(sf); // load image
		double newh = (rate * img.getHeight());
		double neww = (rate * img.getWidth());

		Mat matImg = Imgcodecs.imread(f1);
		Mat matrix = new Mat();
		Imgproc.resize(matImg, matrix, new Size(neww, newh));
		Imgcodecs.imwrite(f2, matrix);
		
		
	}
	
	public static void main(String[] args) throws Exception {
		String opencvpath = "C:/lib/winutils/opencv/build/java/x64/";
		//String libPath = System.getProperty("java.library.path");
		System.load(opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll");
		
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// shift file names
		String targetFolder = "C:/Users/admin/git/NRData/src/main/resources/imgs/";
		String tmp = BaseFolder + "tmp_";
		List<String> tmps = new ArrayList<>();
		try{
		for (int i = 52; i > 0; i--) {
			if (i % 13 == 0) {
				tmp = tmp + i;
				copyfile(BaseFolder + i + FileType, tmp);
				tmps.add(tmp);
			}else{ 
				if (i % 13 == 1) {
					copyImgFile(tmp, targetFolder + i + FileType, 0.25);
				} 
				copyImgFile(BaseFolder + i + FileType,
						targetFolder + (i + 1) + FileType, 0.25);
			}
		}
		}finally{  //clean up
			for(int i = 0; i<tmps.size();i++){
				File tf = new File(tmps.get(i));
				if(tf.exists()){
					tf.delete();
				}
			}
			System.out.println("Well Done!");
		}
	}

}
