package com.rnctech.nrdata.utils;
/* 
* @Author Zilin Chen
* @Date 2020/09/05
*/
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
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
	
	public static BufferedImage merge(String basefolder, int[] cards) throws Exception {
		
		File f = new File(basefolder+cards[0]+".jpg");
		BufferedImage bi = ImageIO.read(f);
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		BufferedImage[] imgs = new BufferedImage[cards.length];

		for(int i=1; i<cards.length;i++) {
			File f1 = new File(basefolder+cards[i]+".jpg");
			imgs[i] = ImageIO.read(f1);
		}
		
		width = 13 * width;
		//height = 13 * height;
		BufferedImage target=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics g = target.getGraphics();
		
		int x = 0;
		for(int i=0; i<imgs.length;i++) {
	        g.drawImage(imgs[i], x, 0, null);
	        x += width;
		}
		
		ImageIO.write(target,"jpg",new File(basefolder+"h1.jpg"));
		return target;
		/*
		 * Graphics2D targetGraphics=target.createImage();
		 * targetGraphics.drawImage(bi1,0,0,null);//draws the first image onto it
		 * 
		 * int[] pixels2=((DataBufferInt) bi2.getRaster().getDataBuffer()).getData();
		 * int[] pixelsTgt=((DataBufferInt)
		 * target.getRaster().getDataBuffer()).getData(); for(int
		 * a=0;a<pixels2.length;a++) { pixelsTgt[a]+=pixels2[a];//this adds the pixels
		 * together }
		 */
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
		int[] a = {1,11,21,31,41,51,3,13,23,33,43,6,9};
		String bf = "C:\\Users\\zilin\\git\\NRData\\src\\main\\resources\\imgs\\";
		
		BufferedImage bi = merge(bf, a);
		showImage(bi);
		
		String[] srcfiles = new String[a.length];
		for(int i=0;i<a.length;i++) {
			srcfiles[i] = bf+a[i]+".jpg";
		}
		
       // doTiling(srcfiles, "result.jpg", 2);
        System.out.println("done");
        System.exit(1);
        

	}
	
	public static void genQRCode(String s) throws Exception {
		BufferedImage img = QRGenBarcodeGenerator.generateQRCodeImage(s);
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
