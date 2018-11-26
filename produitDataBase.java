package com.starupFX.startup;

public class produitDataBase {


	String DCI=null;
	String FormeDose=null;
	String Classe=null;
	String Traitememt=null;
	
	
	public produitDataBase(String DCI, String FormeDose, String Classe, String Traitement) 
	{
		// TODO Auto-generated constructor stub
		this.DCI=DCI;
		this.FormeDose=FormeDose;
		this.Classe=Classe;
		this.Traitememt=Traitement;
		
	}
	
	 
	  public String getDCI() {
	        return DCI;
	    }

	    public void setDCI(String dci) {
	    	this.DCI=dci;
	    }
	    public String getFormeDose() {
	        return FormeDose;
	    }

	    public void setformeDose(String formedose) {
	    	this.FormeDose=formedose;
	    }
	    public String getClasse() {
	        return Classe;
	    }

	    public void setClasse(String Classe) {
	    	this.Classe=Classe;
	    }
	    public String getTraitememt() {
	        return Traitememt;
	    }

	    public void setTraitememt(String traitement) {
	    	this.Traitememt=traitement;
	    }

}
