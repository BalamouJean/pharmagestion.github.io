package com.starupFX.startup;

public class produitAcommander {

	int NUM=0;
	
	String DESIGNATION=null;
	String UNIT=null;
	
	String SDU=null;
	String BESOIN=null;
	String QCOMMANDER=null;
	String PrixUnitaire=null;
	String PrixTotal=null;
	
	
	public produitAcommander(int num,String desination,
							String unite,String sdu,String Besoin,
							String Qcommander,String Punitaire,String Ptotal)
	{
		
		this.NUM=num;
		this.DESIGNATION=desination;
		this.UNIT=unite;
		this.SDU=sdu;
		this.BESOIN=Besoin;
		this.QCOMMANDER=Qcommander;
		this.PrixUnitaire=Punitaire;
		this.PrixTotal=Ptotal;
		
		
		
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
    public String getSDU() {
        return SDU;
    }

    public void setSDU(String sdu) {
    	this.SDU=sdu;
    }
    public String getBESOIN() {
        return BESOIN;
    }

    public void setBESOIN(String besoin) {
    	this.BESOIN=besoin;
    }
    public String getQCOMMANDER() {
        return QCOMMANDER;
    }

    public void setQCOMMANDER(String qcomander) {
    	this.QCOMMANDER=qcomander;
    }
    
    public String getPrixUnitaire() {
        return PrixUnitaire;
    }

    public void setPrixUnitaire(String prixUnitaire) {
    	this.PrixUnitaire=prixUnitaire;
    }
    
    public String getPrixTotal() {
        return PrixTotal;
    }

    public void setPrixTotal(String prixtotal) {
    	this.PrixTotal=prixtotal;
    }

}
