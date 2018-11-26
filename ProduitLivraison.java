package com.starupFX.startup;

public class ProduitLivraison {
	


	int NUM=0;
	
	String DESIGNATION=null;
	String UNIT=null;
	
	String QUANTITE=null;
	String NUMlot=null;
	String DATEPEREMP=null;
	
	
	public ProduitLivraison(int num,String desination,String unite,String quantite,String numLot,String datePeremption)
	{
		
		this.NUM=num;
		this.DESIGNATION=desination;
		this.UNIT=unite;
		this.QUANTITE=quantite;
		this.NUMlot=numLot;
		this.DATEPEREMP=datePeremption;
		
	}
	
	public int getNUM() {
        return NUM;
    }

    public void setNUM(int num) {
    	this.NUM=num;
    }
    public String getDESIGNATION() {
        return DESIGNATION;
    }

    public void setDESIGNATION(String desination) {
    	this.DESIGNATION=desination;
    }
    public String getUNIT() {
        return UNIT;
    }

    public void setUNIT(String unite) {
    	this.UNIT=unite;
    }
   
    public String getQUANTITE() {
        return QUANTITE;
    }

    public void setQUANTITE(String quantite) {
    	this.QUANTITE=quantite;
    }
    
    public String getNUMlot() {
        return NUMlot;
    }

    public void setNUMlot(String numlot) {
    	this.NUMlot=numlot;
    }
    
    public String getDATEPEREMP() {
        return DATEPEREMP;
    }

    public void setDATEPEREMP(String dateperemp) {
    	this.DATEPEREMP=dateperemp;
    }

}
