package com.starupFX.startup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ForSpreadSheet {

	public ForSpreadSheet() {
	       //Create blank workbook
	       XSSFWorkbook workbook = new XSSFWorkbook(); 

	       //Create a blank sheet
	       XSSFSheet spreadsheet = workbook.createSheet(" Employee Info ");

	       //Create row object
	       XSSFRow row;

	       //This data needs to be written (Object[])
	       Map < String, Object[] > empinfo = 
	       new TreeMap < String, Object[] >();
	       empinfo.put( "1", new Object[] { "EMP ID", "EMP NAME", "DESIGNATION" });
	       empinfo.put( "2", new Object[] { "tp01", "Gopal", "Technical Manager" });
	       empinfo.put( "3", new Object[] { "tp02", "Manisha", "Proof Reader" });
	       empinfo.put( "4", new Object[] { "tp03", "Masthan", "Technical Writer" });
	       empinfo.put( "5", new Object[] { "tp04", "Satish", "Technical Writer" });
	       empinfo.put( "6", new Object[] { "tp05", "Krishna", "Technical Writer" });
	       
	       //Iterate over data and write to sheet
	       Set < String > keyid = empinfo.keySet();
	       int rowid = 0;

	       for (String key : keyid) {
	          row = spreadsheet.createRow(rowid++);
	          Object [] objectArr = empinfo.get(key);
	          int cellid = 0;

	          for (Object obj : objectArr) {
	             Cell cell = row.createCell(cellid++);
	             cell.setCellValue((String)obj);
	          }
	       }

	       //Write the workbook in file system
	       FileOutputStream out;
		try {
			out = new FileOutputStream(new File("C:/Writesheet.xlsx"));
			 workbook.write(out);
		       out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	    
		

		
		/*
		 XSSFWorkbook workbook = new XSSFWorkbook(); 
	      XSSFSheet spreadsheet = workbook.createSheet("Print Area");

	      //set print area with indexes
	      workbook.setPrintArea(
	         0, //sheet index
	         0, //start column
	         5, //end column
	         0, //start row
	         5 //end row
	      );
	      
	      //set paper size
	      spreadsheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
	     
	      //set display grid lines or not
	      spreadsheet.setDisplayGridlines(false);
	      
	      //set print grid lines or not
	      spreadsheet.setPrintGridlines(true);
	      
	      FileOutputStream out;
		try {
			out = new FileOutputStream(new File("C:/printarea.xlsx"));
			 workbook.write(out);
		      out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	      System.out.println("printarea.xlsx written successfully");
	      
	      
		 */
	        
	}

}
