package com.rnctech.nrdata.utils;
/* 
* @Author Zilin Chen
* @Date 2020/09/05
*/
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.rnctech.nrdata.generator.QRGenBarcodeGenerator;

public class ImageUtils {
	
	public static String tmpfolder = "/var/log";
	public static String tmpname = "Tmp_";
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
	
	public static enum IMG_TYPE{
		png, jpg, gif
	}
	
	public static void imageToFile(BufferedImage image) {
		String fname = tmpname+sdf.format(new Date());
		imageToFile(image, fname);
	}
	
	public static void imageToFile(BufferedImage image, String fname) {
		imageToFile(image, fname, IMG_TYPE.png);
	}
	
	
	public static void imageToFile(BufferedImage image, String fname, IMG_TYPE ftype) {
	    try {
			File outputfile = new File(tmpfolder+"/"+fname+"."+ftype.name());
			ImageIO.write(image, ftype.name(), outputfile);
		} catch (IOException e) {
			File outputfile = new File("/tmp/"+fname+"."+ftype.name());
			try {
				ImageIO.write(image, ftype.name(), outputfile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedImage img = QRGenBarcodeGenerator.generateQRCodeImage("Admin123");
		imageToFile(img);
	    JLabel lbl = new JLabel(new ImageIcon(img));
	    JOptionPane.showMessageDialog(null, lbl, "ImageDialog", 
	                                 JOptionPane.PLAIN_MESSAGE, null);
		//showImage(img);
	}
	
	public static void showImage(BufferedImage img) {
	    JFrame f = new JFrame(); 
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //this is your screen size
	    f.setUndecorated(true); //removes the surrounding border

	    ImageIcon image = new ImageIcon(img); 
	    JLabel lbl = new JLabel(image); 
	    f.getContentPane().add(lbl); 
	    f.setSize(image.getIconWidth(), image.getIconHeight()); 
	    int x = (screenSize.width - f.getSize().width)/2; //These two lines are the dimensions
	    int y = (screenSize.height - f.getSize().height)/2;//of the center of the screen

	    f.setLocation(x, y); 
	    f.setVisible(true); 
	}
}
