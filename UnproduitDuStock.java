package com.starupFX.startup;

import java.sql.Date;

public class UnproduitDuStock {


	long Code=0;
	String DCI=null,Emplacement=null,Fournisseur=null,Unite=null;
	long Quantite=0;
	long PrixAchat=0,PrixVente=0;
	double Seuilcommand=0;
	long StockMin=0,tockMax=0;
	Date DateEntrer=null,DateExp=null;
	int CMM=0;
	double QJ=0;
	
	
	public UnproduitDuStock(long code,String dci,String emplacement,
							String fournisseur,String unite,long quantite,
							long prixAchat,long prixVente,double seuilcommand,
							long stockMin,long stockMax,Date dateEntrer,
							Date dateExp,int cmm,double qj)
	{
		
		this.Code=code;
		this.DCI=dci;
		this.Fournisseur=fournisseur;
		this.Unite=unite;
		this.Quantite=quantite;
		this.PrixAchat=prixAchat;
		this.PrixVente=prixVente;
		this.Seuilcommand=seuilcommand;
		this.StockMin=stockMin;
		this.tockMax=stockMax;
		this.DateEntrer=dateEntrer;
		this.DateExp=dateExp;
		this.CMM=cmm;
		this.QJ=qj;
		this.Emplacement=emplacement;
		
		
	}
	
	public long getCode() {
        return Code;
    }

    public void setCode(long code) {
    	this.Code=code;
    }
    public String getDCI() {
        return DCI;
    }

    public void setDESIGNATION(String dci) {
    	this.DCI=dci;
    }
    public String getEmplacement() {
        return Emplacement;
    }

    public void setEmplacement(String emplacement) {
    	this.Emplacement=emplacement;
    }
  
    public String getFournisseur() {
        return Fournisseur;
    }

    public void setFournisseur(String fournisseur) {
    	this.Fournisseur=fournisseur;
    }
    public String getUnite() {
        return Unite;
    }

    public void setUnite(String unite) {
    	this.Unite=unite;
    }
    public long getQuantite() {
        return Quantite;
    }

    public void setQuantite(long quantite) {
    	this.Quantite=quantite;
    }
    public long getPrixAchat() {
        return PrixAchat;
    }

    public void setPrixAchat(long prixAchat) {
    	this.PrixAchat=prixAchat;
    }
    
    public long getPrixVente() {
        return PrixVente;
    }

    public void setPrixVente(long prixVente) {
    	this.PrixVente=prixVente;
    }
    
    public double getSeuilcommand() {
        return Seuilcommand;
    }

    public void setSeuilcommand(double seuilcommand) {
    	this.Seuilcommand=seuilcommand;
    }
    
    public long getStockMin() {
        return StockMin;
    }

    public void setStockMin(long stockMin) {
    	this.StockMin=stockMin;
    }
    
    public long gettStockMax() {
        return tockMax;
    }

    public void settStockMax(long stockMax) {
    	this.tockMax=stockMax;
    }
    
    public Date getDateEntrer() {
        return DateEntrer;
    }

    public void setDateEntrer(Date dateEntrer) {
    	this.DateEntrer=dateEntrer;
    }
    
    public Date getDateExp() {
        return DateExp;
    }

    public void setDateExp(Date dateExp) {
    	this.DateExp=dateExp;
    }
    
    public int getCMM() {
        return CMM;
    }

    public void setCMM(int cmm) {
    	this.CMM=cmm;
    }
    
    public double getQJ() {
        return QJ;
    }

    public void setQJ(double qj) {
    	this.QJ=qj;
    }


}
