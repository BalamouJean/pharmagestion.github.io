package com.starupFX.startup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandLivreData {
	static Connection connection = null;
	static Statement statement = null;
	ObservableList<produitAcommander> dataCommande=FXCollections.observableArrayList();
	ObservableList<String> dataComboLivretCom=FXCollections.observableArrayList();

	private String url="",lastUser="";int intervAppro=0,delaiLIvrai=0,entierQJ=0,QC=0;
	private double consomPendLiv=0;
	private long diferenceDate=0;
	private LocalDate curentDate=null;
	LocalDate lastDate=null;
	
	// ********************for surveillant***********
	Date dateDebu=null;
	int intervalApro=0,delaiLivraison=0;
	
	CommandLivreData(){
				verifierCoflicConnection confliConnect=new verifierCoflicConnection();
				url=confliConnect.verifierConfliConection();
				curentDate= LocalDate.now(ZoneId.of("Africa/Conakry"));
				//********************
				try {
					connection = DriverManager.getConnection(url,"balamou","jean");
					statement = connection.createStatement();
					
					ResultSet RESULTSETDCI= statement.executeQuery("SELECT DCI, FormDose FROM TheDataBase");
					while(RESULTSETDCI.next())
					{
						dataComboLivretCom.add(RESULTSETDCI.getString("DCI")+" "+RESULTSETDCI.getString("FormDose"));
					}
					
					
					ResultSet rsetFromCMM= statement.executeQuery("SELECT IntervalEntreDeuxAprovis,DelaitDeLivraison FROM DatePourCMM");
					while(rsetFromCMM.next())
					{
						intervalApro=rsetFromCMM.getInt("IntervalEntreDeuxAprovis");
						delaiLivraison=rsetFromCMM.getInt("DelaitDeLivraison");
					}
					
					
					RESULTSETDCI.close();
					rsetFromCMM.close();
				} catch (SQLException e) {
					new Alerter(e.getMessage());
				}finally {
			    	//finally block used to close resources
			    	try{
			    	if(statement != null)
			    		statement. close();
			    	}catch(SQLException se2){
			    	}// nothing we can do
			    	
			      if (connection != null) {
			        try {
			          connection.close();
			        } catch (SQLException e) {
			        } // nothing we can do
			      }
			    }
				
		}
//---------------------------	
	public int intervallAprov()
	{
		
		return intervalApro;
	}
	public int delaiLivrais()
	{
		
		return delaiLivraison;
	}
	public Date dateDebu()
	{
		
		return dateDebu;
	}
	public boolean fait()
	{
		
		return false;
	}
//----------------------
	
	
	
	
	public ObservableList<produitAcommander> CommandeUrgente()
	{

		int entier=1;
		dataCommande.clear();
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet delaiInterv = statement.executeQuery("SELECT DateDebut, IntervalEntreDeuxAprovis, DelaitDeLivraison FROM DatePourCMM");
		    while(delaiInterv.next())
	      		{
		    	   intervAppro=delaiInterv.getInt("IntervalEntreDeuxAprovis");
		    	   delaiLIvrai=delaiInterv.getInt("DelaitDeLivraison");
		    	   diferenceDate=ChronoUnit.DAYS.between(delaiInterv.getDate("DateDebut").toLocalDate(),curentDate);
		    	}
		   
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT DCI, Unite, Quantite, PrixAchat, Seuilcommand, StockMin, StockMax, QJ FROM mesproduits");
			while(RESULTSETDCI.next())
			{
				if(RESULTSETDCI.getDouble("Seuilcommand")>=RESULTSETDCI.getLong("Quantite")){
					consomPendLiv = (RESULTSETDCI.getDouble("QJ")/diferenceDate)*delaiLIvrai;
					entierQJ=(int)consomPendLiv;
				    if(consomPendLiv!=(double)entierQJ)
				    	{
				    		entierQJ=entierQJ+1;
				    	}
				    QC=(RESULTSETDCI.getInt("StockMax")+entierQJ)-RESULTSETDCI.getInt("Quantite");
				    dataCommande.add(new produitAcommander(entier,RESULTSETDCI.getString("DCI"),
						RESULTSETDCI.getString("Unite"),
						""+RESULTSETDCI.getInt("Quantite"),
						RESULTSETDCI.getString("StockMax"),
						""+QC,
						""+RESULTSETDCI.getLong("PrixAchat"),""+QC*RESULTSETDCI.getLong("PrixAchat")));
				    entier+=1;
				}
			}
			
			
			delaiInterv.close();
			RESULTSETDCI.close();
		} catch (SQLException e) {
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(statement != null)
	    		statement. close();
	    	}catch(SQLException se2){
	    	}// nothing we can do
	    	
	      if (connection != null) {
	        try {
	          connection.close();
	        } catch (SQLException e) {
	        } // nothing we can do
	      }
	    }
		
		return dataCommande;
	
		
	}
	
	public ObservableList<produitAcommander> TabCommandContenu()
	{
		int entier=1;
		dataCommande.clear();
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet delaiInterv = statement.executeQuery("SELECT DateDebut, IntervalEntreDeuxAprovis, DelaitDeLivraison FROM DatePourCMM");
		    while(delaiInterv.next())
	      		{
		    	   intervAppro=delaiInterv.getInt("IntervalEntreDeuxAprovis");
		    	   delaiLIvrai=delaiInterv.getInt("DelaitDeLivraison");
		    	   diferenceDate=ChronoUnit.DAYS.between(delaiInterv.getDate("DateDebut").toLocalDate(),curentDate);
		    	}
		   
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT DCI, Unite, Quantite, PrixAchat, StockMin, StockMax, QJ FROM mesproduits");
			while(RESULTSETDCI.next())
			{
				
				consomPendLiv = (RESULTSETDCI.getDouble("QJ")/diferenceDate)*delaiLIvrai;
				entierQJ=(int)consomPendLiv;
			    if(consomPendLiv!=(double)entierQJ)
			    	{
			    		entierQJ=entierQJ+1;
			    	}
			    QC=(RESULTSETDCI.getInt("StockMax")+entierQJ)-RESULTSETDCI.getInt("Quantite");
			dataCommande.add(new produitAcommander(entier,RESULTSETDCI.getString("DCI"),
					RESULTSETDCI.getString("Unite"),
					""+RESULTSETDCI.getInt("Quantite"),
					RESULTSETDCI.getString("StockMax"),
					""+QC,
					""+RESULTSETDCI.getLong("PrixAchat"),""+QC*RESULTSETDCI.getLong("PrixAchat")));
			entier+=1;
			}
			
			
			delaiInterv.close();
			RESULTSETDCI.close();
		} catch (SQLException e) {
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(statement != null)
	    		statement. close();
	    	}catch(SQLException se2){
	    	}// nothing we can do
	    	
	      if (connection != null) {
	        try {
	          connection.close();
	        } catch (SQLException e) {
	        } // nothing we can do
	      }
	    }
		
		return dataCommande;
	}
	public ObservableList<String> dataForComboCommand()
	{
		return dataComboLivretCom;
	}
	
	public int ConsommationJournaliere()
	{
		double conJournalier=0;int count=0,somme=0;
		try {
				connection = DriverManager.getConnection(url,"balamou","jean");
				statement = connection.createStatement();
				ResultSet RESULTSETDCI = statement.executeQuery("SELECT Date, NombreProdVendu, Utilisateur FROM HistoriqueDesVente");
				RESULTSETDCI.setFetchDirection(ResultSet.FETCH_REVERSE);
				RESULTSETDCI.last();
				somme=RESULTSETDCI.getInt("NombreProdVendu");
				lastUser=RESULTSETDCI.getString("Utilisateur");
				lastDate=RESULTSETDCI.getDate("Date").toLocalDate();
				while(RESULTSETDCI.previous())
				{
					somme +=RESULTSETDCI.getInt("NombreProdVendu");
					count += 1;
					if(count==6)
						break;
				}
				conJournalier=somme/7;
				RESULTSETDCI.close();
		} catch (SQLException e) {
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(statement != null)
	    		statement. close();
	    	}catch(SQLException se2){
	    	}// nothing we can do
	    	
	      if (connection != null) {
	        try {
	          connection.close();
	        } catch (SQLException e) {
	        } // nothing we can do
	      }
	    }
		return (int)Math.round(conJournalier);
	}
	
	public LocalDate getLastConectDate(){
		return lastDate;
	}
	public String getLastConectUser(){
		return lastUser;
	}
}
