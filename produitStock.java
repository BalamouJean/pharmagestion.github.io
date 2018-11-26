package com.starupFX.startup;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class produitStock extends RecursiveTreeObject<produitStock>{

	int Code=0;
	String DCI=null;
	String Uniter=null;
	long Quantite=0;
	String DateEntre=null;
	String DAteSortie=null;
	long CMM=0;
	String Fournisseur=null;
	String NombreMoiStock=null;
	
	
	public produitStock(int code, String dci, String unite, long quantite, String dateEntrer, String dateSortie,long cmm,String fournisseur, String nbMoiStock) 
	{
		// TODO Auto-generated constructor stub
		this.Code=code;
		this.DCI=dci;
		this.Uniter=unite;
		this.Quantite=quantite;
		this.DateEntre=dateEntrer;
		this.DAteSortie=dateSortie;
		this.CMM=cmm;
		this.Fournisseur=fournisseur;
		this.NombreMoiStock=nbMoiStock;
		
	}
	
	  public int getCode() {
	        return Code;
	    }

	    public void setCode(int code) {
	    	this.Code=code;
	    }
	  public String getDCI() {
	        return DCI;
	    }

	    public void setDCI(String dci) {
	    	this.DCI=dci;
	    }
	    
	    public String getUniter() {
	        return Uniter;
	    }

	    public void setUniter(String uniter) {
	    	this.Uniter=uniter;
	    }
	    public long getQuantite() {
	        return Quantite;
	    }

	    public void setQuantite(long quantite) {
	    	this.Quantite=quantite;
	    }
	    public String getDateEntre() {
	        return DateEntre;
	    }

	    public void setDateEntre(String dateEntre) {
	    	this.DateEntre=dateEntre;
	    }
	    public String getDAteSortie() {
	        return DAteSortie;
	    }

	    public void setDAteSortie(String dAteSortie) {
	    	this.DAteSortie=dAteSortie;
	    }
	    public long getCMM() {
	        return CMM;
	    }

	    public void setCMM(long cmm) {
	    	this.CMM=cmm;
	    }
	    public String getFournisseur() {
	        return Fournisseur;
	    }

	    public void setFournisseur(String fournisseur) {
	    	this.Fournisseur=fournisseur;
	    }
	    public String getNombreMoiStock() {
	        return NombreMoiStock;
	    }

	    public void setNombreMoiStock(String nombreMoiStock) {
	    	this.NombreMoiStock=nombreMoiStock;
	    }

}
