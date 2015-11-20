package com.zk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import com.zk.kullanici.Kullanici;
import com.zk.util.ImageProcessor;
import com.zk.yonetici_giris.GirisController;
import com.zk.yuz_bul.Yuz;
import com.zk.yuz_bul.YuzDAO;
import com.zk.yuz_bul.YuzTableModel;

import org.opencv.core.*;
public class App {
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
	}
	
	public static JDialog dialog;
	private static JLabel imageView = new JLabel();;
	private static CascadeClassifier faceDetector;
	public static YuzTableModel yuzTableModel;
	public static JTable yuzTable;
	public static int secilenGoruntuId;
	public static JPanel adKaydetPanel = new JPanel();
	public static JLabel tamAdi = new JLabel();
	public static JButton kaydetButton = new JButton("Kaydet");
	public static JScrollPane yuzScrollPane;

	public static YuzDAO yuzDAO;
	
	public static Kullanici kullanici;
	
	public static final String APP_NAME = "Yüz Tanıma";
	public static final String APP_VERSION = "1.0";	

	public static void main(String[] args) {
		kullaniciGirisi();
		GUIYuzBulma();
		loadCascade();
		kameraAc(args);
	}
	
	private static void loadCascade() {
		String cascadePath = "src/main/resources/cascades/haarcascade_frontalface_alt.xml";
	    faceDetector = new CascadeClassifier(cascadePath);
	}	
	
    private static void kullaniciGirisi(){
    	GirisController giris = new GirisController();
    	giris.kimlikSor();
    }
            
	public static void GUIYuzBulma() {
		JFrame sahipsiz = null;
		dialog = new JDialog(sahipsiz,"Yüz Bul", true);  
		dialog.setLayout(new GridBagLayout());
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  		
		
		dialog.setSize(400,400);  
		
		setupImage();
		//setupButtons();
		//yuzGoruntuleriAyarla();
		silEgitButtonlarAyarla();     
	}

	public static void GUIYuzTanima() {
		JFrame sahipsiz = null;
		dialog = new JDialog(sahipsiz,"Yüz Tanıma", true);  
		dialog.setLayout(new GridBagLayout());
		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
		dialog.setSize(400,400);  
		setupImage();
	}
	
	private static void setupImage() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		dialog.add(imageView,c);
	}
	
	public static void setupButtons() {
		GridBagLayout gridBagRowLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill=GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx=0;
		
		try{
			tamAdi.setText(kullanici.adAl()+' '+kullanici.soyadAl());
		}catch(NullPointerException ex){
			System.out.println(ex.getMessage());
		}
		
		tamAdi.setAlignmentX(Component.LEFT_ALIGNMENT);
		adKaydetPanel.add(tamAdi, gbc);		
		
		kaydetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				goruntuKaydet();
			}
		});
		
		gbc.ipadx=0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        //gbc.weightx = 100;
        //gbc.weighty = 20;	
        //gbc.ipadx=50;
		adKaydetPanel.add(kaydetButton, gbc);		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		dialog.add(adKaydetPanel,c);	
	}	
	
	public static void yuzGoruntuleriAyarla() {			
		try{
			System.out.println(kullanici);
			//yuzDAO = new YuzDAO(kullanici);
			yuzTableModel = new YuzTableModel(kullanici);
			System.out.println("Ad:" + yuzTableModel.getColumnName(2));
			yuzTable = new JTable(yuzTableModel);
			yuzTable.setBackground(Color.LIGHT_GRAY);
			yuzTable.getColumnModel().getColumn(0).setPreferredWidth(20);
			yuzTable.getColumnModel().getColumn(1).setPreferredWidth(274);
			yuzTable.setRowHeight(279);
			yuzTable.setFillsViewportHeight(true);
			yuzTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			yuzScrollPane = new JScrollPane(yuzTable);
			yuzScrollPane.setPreferredSize(new Dimension(330, 480));
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = 0;
			dialog.add(yuzScrollPane,c);	
			
			yuzTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		        public void valueChanged(ListSelectionEvent event) {
		            secilenGoruntuId = (int) (yuzTable.getValueAt(yuzTable.getSelectedRow(), 0));
		            System.out.println(secilenGoruntuId);
		            //print first column value from selected row
		            //System.out.println(yuzTable.getValueAt(yuzTable.getSelectedRow(), 0).toString());
		        }
		    });			
			
		}catch(NullPointerException ex){
			
		}
	}		

	public static void silEgitButtonlarAyarla() {			
		JButton silButton = new JButton("Sil");
		silButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				goruntuSil();
			}
		});
		silButton.setAlignmentX(Component.RIGHT_ALIGNMENT);		

		JButton ogretButton = new JButton("Eğit");
		ogretButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//sil();
			}
		});
		ogretButton.setAlignmentX(Component.RIGHT_ALIGNMENT);		
		
		GridLayout gridRowLayout = new GridLayout(1,0);
		gridRowLayout.setVgap(5);
		JPanel buttonsPanel = new JPanel(gridRowLayout);

		buttonsPanel.add(silButton);
		buttonsPanel.add(ogretButton);	
		
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 1;
		c.gridy = 1;
		dialog.add(buttonsPanel,c);
	}			
	
	public static void taniButtonAyarla() {			
		JButton taniButton = new JButton("Yüz Tanı");
		taniButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				goruntuSil();
			}
		});
		taniButton.setAlignmentX(Component.CENTER_ALIGNMENT);		
		
		GridLayout gridRowLayout = new GridLayout(1,0);
		gridRowLayout.setVgap(5);
		JPanel buttonsPanel = new JPanel(gridRowLayout);

		buttonsPanel.add(taniButton);
		
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 1;
		dialog.add(buttonsPanel,c);
	}			
	
	
	private static void kameraAc(String[] args) {
		ImageProcessor imageProcessor = new ImageProcessor();
		Mat webcamMatImage = new Mat();  
		Image tempImage;  
		VideoCapture capture = new VideoCapture(0);
		capture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH,640);
		capture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT,480);
		if( capture.isOpened()){  
			while (true){  
				capture.read(webcamMatImage);  
				if( !webcamMatImage.empty() ){  
					yuzBul(webcamMatImage);
					tempImage= imageProcessor.toBufferedImage(webcamMatImage);					
					ImageIcon imageIcon = new ImageIcon(tempImage, "Captured video");
					imageView.setIcon(imageIcon);
					dialog.pack();  
				}  
				else{  
					System.out.println("-- Frame not captured -- Break!"); 
					break;  
				}
			}  
		}
		else{
			System.out.println("Couldn't open capture.");
		}		
	}
	
	private static void yuzBul(Mat image) {
	    MatOfRect faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(	image, faceDetections, 1.1, 7,0,new Size(250,40),new Size());
	    Rect rectCrop=null;
	    		
	    for (Rect rect : faceDetections.toArray()) {
	        Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
	        rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
	    }
	    if(rectCrop != null){
	    	//System.out.println("not null");
	    	Mat image_roi = new Mat(image,rectCrop);
	    	Imgcodecs.imwrite("src/main/resources/yuz_goruntu.jpg",image_roi);
	    }
	}
	
	private static void goruntuKaydet(){
		File file = new File("src/main/resources/yuz_goruntu.jpg");
        byte[] ikiliDosya = new byte[(int) file.length()];
        
        try {
	     FileInputStream fileInputStream = new FileInputStream(file);
	     	fileInputStream.read(ikiliDosya);
	     	fileInputStream.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }        
        Yuz yuz = new Yuz();
        yuz.goruntuVer(ikiliDosya);
        yuzDAO.ekle(yuz);        
        yuzTableModel.yenile();
        yuzTableModel.fireTableDataChanged();
	}
        
	private static void goruntuSil(){
		yuzDAO.sil(secilenGoruntuId);
        yuzTableModel.yenile();
        yuzTableModel.fireTableRowsDeleted(secilenGoruntuId, secilenGoruntuId);
	}
}
