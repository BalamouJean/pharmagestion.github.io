package com.starupFX.startup;

public interface interfacePDFGestion {
	public void pdfGeneraterCommande(String NumbonCom, String structure,String Adressera,String urgent);
	public void pdfGeneraterLivraison(String Num, String NumFacture,String NomAdressClien,String refBonCom);
	public void lesProduits(int num,String designation,String unite,String sdu, String besoin,String quantiteComande,String prixunitai,String prixTotal);
	public void lesProduitsLiv(int num,String designation,String unite,String quantite, String NumLot,String datePeremp);
	public void theFooter(String leMontant,String CFpar, String Sign,String date,String APpar, String sign2,String sde2);
	public void theFooterLiv(String leMontant,String CFpar, String Sign,String date,String APpar, String sign2,String sde2);
	public void AddTable();
	public void AddTableLiv();
	public void OpenPDF(String NumbonCom);
	
}
