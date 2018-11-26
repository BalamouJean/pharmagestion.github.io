package com.starupFX.startup;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class ficheStockData extends RecursiveTreeObject<ficheStockData>{

	String DATE="";
	String ORIGINE="";
	String ENTRER="";
	String SORTIE="";
	String LESTOCK="";
	String REMARQUE="";
	
	
	public ficheStockData(String date, String origin, String entre, String sortie, String stock,String remarque) 
	{
		// TODO Auto-generated constructor stub
		this.DATE=date;
		this.ORIGINE=origin;
		this.ENTRER=entre;
		this.SORTIE=sortie;
		this.LESTOCK=stock;
		this.REMARQUE=remarque;
		
	}
	public void setDate(String date){
		this.DATE=date;
	}
	 public String getDATE() {
	        return DATE;
	 }
	 
	 public void setORIGINE(String origin){
			this.ORIGINE=origin;
		}
		 public String getORIGINE() {
		        return ORIGINE;
		 }
		 
		 public void setENTRER(String entrer){
				this.ENTRER=entrer;
			}
			 public String getENTRER() {
			        return ENTRER;
			 }
			 
			 public void setSORTIE(String sortie){
					this.SORTIE=sortie;
				}
				 public String getSORTIE() {
				        return SORTIE;
				 }
				 
				 public void setLESTOCK(String stock){
						this.LESTOCK=stock;
					}
					 public String getLESTOCK() {
					        return LESTOCK;
					 }
					 public void setREMARQUE(String remarque){
							this.REMARQUE=remarque;
						}
						 public String getREMARQUE() {
						        return REMARQUE;
						 }
	 
	 
	 
	 
}
