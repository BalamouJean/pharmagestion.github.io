package com.starupFX.startup;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javafx.scene.transform.Scale;

public class pdfGeneraterCommande 
{
	
	float[] columnWidths = {0.3f, 5, 1, 0.6f, 1, 1, 1, 1};
	PdfPTable pdfPTable= new PdfPTable(columnWidths); 
	Document document;
	FileOutputStream outputStream;
	
	public pdfGeneraterCommande(String NumbonCom, String structure,String Adressera,String urgent)
	{
		try {
		      //Create Document instance.
			  document = new Document();
			 
			 //Create OutputStream instance.
			 	//outputStream = new FileOutputStream(new File("D:\\"+NumbonCom+".pdf"));
			  
			  
			  File directory=new File("C:\\pharmacieDOC\\pharmaPDF\\Commandes");
			  
			  if (!directory.exists())
			  		{
				  		directory.mkdir();
			  		}
			  else
				 	outputStream = new FileOutputStream(new File("C:\\pharmacieDOC\\pharmaPDF\\Commandes\\COMMANDE_"+NumbonCom+".pdf"));
			  
			   //boolean succes= (new File("/home/balamou/Documents/LesCommande")).mkdir();  // creer un nouvo dossier pour les commdes
			  
			  
			//Create PDFWriter instance.
		        PdfWriter.getInstance(document, outputStream);
		 
		    //Open the document.
		        document.open();
		 
		        
		    //Add content to the document.
		        
		        Font font = new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD | Font.UNDERLINE | Font.BOLD);
		        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN,10,Font.BOLD | Font.NORMAL | Font.BOLD);
		        Font font3 = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD | Font.NORMAL | Font.BOLD);
		        
		        Paragraph parag=new Paragraph("BON DE COMMANDE", font);
		        parag.setAlignment(Element.ALIGN_CENTER);
		        parag.setSpacingAfter(15);
		        document.add(parag);
		 
		        document.add(new Paragraph("Numero du bon de commande : " + NumbonCom,font3));
		        document.add(new Paragraph("Structure : " + structure,font3));
		        document.add(new Paragraph("Adressé a : " + Adressera,font3));
		        Paragraph paragCom=new Paragraph("Commande Urgente : " + urgent,font3);
		        paragCom.setSpacingAfter(15);
		        document.add(paragCom);
		        
		        // creating table
		       // float[] columnWidths = {1, 4, 1, 1, 1, 1, 1, 1};
		       // pdfPTable = new PdfPTable(columnWidths);
		        pdfPTable.setWidthPercentage(100);
		        pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		        
		 
		        //Create header
		        PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("N",font2));
		        PdfPCell pdfPCell2 = new PdfPCell(new Paragraph("Désignation",font2));
		        PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("Unite",font2));
		        PdfPCell pdfPCell4 = new PdfPCell(new Paragraph("SDU",font2));
		        PdfPCell pdfPCell5 = new PdfPCell(new Paragraph("Besoin ou Stock max",font2));
		        PdfPCell pdfPCell6 = new PdfPCell(new Paragraph("Quantite commandé",font2));
		        PdfPCell pdfPCell7 = new PdfPCell(new Paragraph("Prix unitaire",font2));
		        PdfPCell pdfPCell8 = new PdfPCell(new Paragraph("Prix Total",font2));
		        
		        //Add cells to table
		        pdfPTable.addCell(pdfPCell1);
		        pdfPTable.addCell(pdfPCell2);
		        pdfPTable.addCell(pdfPCell3);
		        pdfPTable.addCell(pdfPCell4);
		        pdfPTable.addCell(pdfPCell5);
		        pdfPTable.addCell(pdfPCell6);
		        pdfPTable.addCell(pdfPCell7);
		        pdfPTable.addCell(pdfPCell8);
		        
		        
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		
		
	 
	}
public void lesProduits(int num,String designation,String unite,long sdu, long besoin,long quantiteComande,long prixunitai,Object prixTotal) 
	{
	Font fontCell = new Font(Font.FontFamily.TIMES_ROMAN,10,Font.ITALIC | Font.ITALIC | Font.ITALIC);
	Paragraph lenum=new Paragraph(""+num,fontCell);
	Paragraph laDesigna=new Paragraph(designation,fontCell);
	Paragraph lunit=new Paragraph(unite,fontCell);
	Paragraph lesdu=new Paragraph(""+sdu,fontCell);
	Paragraph lebesoin=new Paragraph(""+besoin,fontCell);
	Paragraph quantCommand=new Paragraph(""+quantiteComande,fontCell);
	Paragraph prixUnit=new Paragraph(""+prixunitai,fontCell);
	Paragraph prixtotal=new Paragraph(""+prixTotal,fontCell);
	
	 PdfPCell pdfPCell1 = new PdfPCell(lenum);
     PdfPCell pdfPCell2 = new PdfPCell(laDesigna);
     PdfPCell pdfPCell3 = new PdfPCell(lunit);
     PdfPCell pdfPCell4 = new PdfPCell(lesdu);
     PdfPCell pdfPCell5 = new PdfPCell(lebesoin);
     PdfPCell pdfPCell6 = new PdfPCell(quantCommand);
     PdfPCell pdfPCell7 = new PdfPCell(prixUnit);
     PdfPCell pdfPCell8 = new PdfPCell(prixtotal);
	
	 
  	   		pdfPTable.addCell(pdfPCell1);
	        pdfPTable.addCell(pdfPCell2);
	        pdfPTable.addCell(pdfPCell3);
	        pdfPTable.addCell(pdfPCell4);
	        pdfPTable.addCell(pdfPCell5);
	        pdfPTable.addCell(pdfPCell6);
	        pdfPTable.addCell(pdfPCell7);
	        pdfPTable.addCell(pdfPCell8);
     
	}


public void theFooter(String leMontant,String CFpar, String Sign,String date,String APpar, String sign2,String sde2)
{
	try {
	      
		 Font fontST = new Font(Font.FontFamily.TIMES_ROMAN,9,Font.ITALIC | Font.ITALIC | Font.ITALIC);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN,7,Font.ITALIC | Font.ITALIC | Font.ITALIC);
        Font fontT = new Font(Font.FontFamily.TIMES_ROMAN,8,Font.BOLD | Font.ITALIC | Font.BOLD);
	        
	        Paragraph parag=new Paragraph("Montant total (FG) : " + leMontant, fontST);
	       // parag.setAlignment(Element.ALIGN_CENTER);
	        parag.setSpacingBefore(10);
	        document.add(parag);
	 
	        LineSeparator line=new LineSeparator();
	        line.setLineWidth(1);
	        line.setLineColor(BaseColor.BLACK);
	        
	        Paragraph para1=new Paragraph("COMMANDE FAITE PAR : "+ CFpar,fontT);
	        para1.setSpacingAfter(0.5f);
	        Paragraph para2=new Paragraph("DATE : "+date,font);
	        para2.setSpacingAfter(0);
	        Paragraph para3=new Paragraph("SIGNATURE : "+Sign,font);
	        para3.setSpacingAfter(1);
	        
	        Paragraph para1C=new Paragraph("COMMANDE APROUVE PAR : "+ APpar,fontT);
	        para1C.setSpacingAfter(0.5f);
	        Paragraph para2C=new Paragraph("DATE : "+ sde2,font);
	        para2C.setSpacingAfter(0);
	        Paragraph para3C=new Paragraph("SIGNATURE : "+ sign2,font);
	        
	        
	        
	        document.add(para1);
	        document.add(line);
		        document.add(para2);
		        document.add(para3);
		      
	        
	        document.add(para1C);
	        document.add(line); 
		        document.add(para2C);
		        document.add(para3C);
		       
	                      
	      //Close document and outputStream.
	        document.close();
	        outputStream.close();
	        
		}catch (Exception e) {
			e.printStackTrace();
	    }
	
	        
	}

public void AddTable() 
{
	try {
		document.add(pdfPTable);
		
	} catch (Exception e) {
		e.printStackTrace();
    }
}

public void OpenPDF(String NumbonCom) 
{
	if (Desktop.isDesktopSupported()) {
	    try {
	        File myFile = new File("C:\\pharmacieDOC\\pharmaPDF\\Commandes\\COMMANDE_"+NumbonCom+".pdf");
	        Desktop.getDesktop().open(myFile);
	        
	    } catch (IOException ex) {
	        // no application registered for PDFs
	    	JOptionPane.showMessageDialog(null, "no application registered for PDFs");
	    }
	} 
}

}
