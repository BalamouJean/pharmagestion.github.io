package com.starupFX.startup;

public class ProduitParetoRegle {

	long Code=0;
	String Designation="";
	double QJ=0;
	double cumul=0;
	int poucentage=0;
	
	public ProduitParetoRegle(long code,String designation,double qj,double cumul,int poucentage)
	{
		
		this.Code=code;
		this.Designation=designation;
		this.QJ=qj;
		this.cumul=cumul;
		this.poucentage=poucentage;
	}
	
	public long getCode() {
        return Code;
    }

    public void setCode(long code) {
    	this.Code=code;
    }
    public String getDESIGNATION() {
        return Designation;
    }

    public void setDESIGNATION(String designation) {
    	this.Designation=designation;
    }
    
    public double getQJ() {
        return QJ;
    }

    public void setQJ(double qj) {
    	this.QJ=qj;
    }
    
    public double getCumul() {
        return cumul;
    }

    public void setCumul(double cumul) {
    	this.cumul=cumul;
    }
    public int getpoucentage() {
        return poucentage;
    }

    public void setpoucentage(int poucentage) {
    	this.poucentage=poucentage;
    }




}
