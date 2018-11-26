package com.starupFX.startup;

public class produitAvendre {

	String DCI=null;
	String UNIT=null;
	long STOCKrestant=0;
	long PRIX=0;
	String QUANTITE=null;
	String DESTINATION=null;
	long PRIXdetail=0;
	String Emplacement=null;
	
	
	public produitAvendre(String dci,String unite,long stockRestant,long prix,String quantite,String destination,long prixDetail,String emplacement) 
	{
		// TODO Auto-generated constructor stub
		DCI=dci;
		UNIT=unite;
		PRIX=prix;
		QUANTITE=quantite;
		DESTINATION=destination;
		STOCKrestant=stockRestant;
		PRIXdetail=prixDetail;
		Emplacement=emplacement;
	}
	

    public String getDCI() {
        return DCI;
    }

    public void setDCI(String dci) {
    	DCI=dci;
    }

    public String getUNIT() {
        return UNIT;
    }

    public void setUNIT(String unit) {
        UNIT=unit;
    }
    public long getSTOCKrestant() {
        return STOCKrestant;
    }

    public void setSTOCKrestant(long stockRestant) {
    	STOCKrestant=stockRestant;
    }
    public long getPRIX() {
        return PRIX;
    }

    public void setPRIX(long prix) {
        PRIX=prix;
    }
    public String getQUANTITE() {
        return QUANTITE;
    }

    public void setQUANTITE(String quant) {
    	QUANTITE=quant;
    }

    public String getDESTINATION() {
        return DESTINATION;
    }

    public void setDESTINATION(String destination) {
    	DESTINATION=destination;
    }
    public long getPRIXdetail() {
        return PRIXdetail;
    }

    public void setPRIXdetail(long prixDetail) {
    	PRIXdetail=prixDetail;
    }
    
    public String getEmplacement() {
        return Emplacement;
    }

    public void setEmplacement(String emplacement) {
    	Emplacement=emplacement;
    }
    
}
