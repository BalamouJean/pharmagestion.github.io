package com.starupFX.startup;

public class produitVenteJour {
	String Designation=null;
	String UNIT=null;
	String PRIX=null;
	String QUANTITEvendu=null;
	String PRIXdetail=null;
	
	public produitVenteJour(String designation,String unite,String prix,String quantiteVendu,String prixDetail) {
		// TODO Auto-generated constructor stub
		this.Designation=designation;
		this.UNIT=unite;
		this.PRIX=prix;
		this.QUANTITEvendu=quantiteVendu;
		this.PRIXdetail=prixDetail;
	}
	
	 public String getDesignation() {
	        return Designation;
	    }

	    public void setDesignation(String designation) {
	    	Designation=designation;
	    }

	    public String getUNIT() {
	        return UNIT;
	    }

	    public void setUNIT(String unit) {
	        UNIT=unit;
	    }
	  
	    public String getPRIX() {
	        return PRIX;
	    }

	    public void setPRIX(String prix) {
	        PRIX=prix;
	    }
	    public String getQUANTITEvendu() {
	        return QUANTITEvendu;
	    }

	    public void setQUANTITE(String quantVendu) {
	    	QUANTITEvendu=quantVendu;
	    }

	    public String getPRIXdetail() {
	        return PRIXdetail;
	    }

	    public void setPRIXdetail(String prixDetail) {
	    	PRIXdetail=prixDetail;
	    }

}
