package com.starupFX.startup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Period;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class gestionContenus {
	static Connection connection = null;
	static Statement statement = null;
	PreparedStatement pstmt=null;
	
	ObservableList<String> dataDC=FXCollections.observableArrayList();
	ObservableList<String> dataDCINP=FXCollections.observableArrayList();
	ObservableList<produitStock> dataStock=FXCollections.observableArrayList();
	ObservableList<produitDataBase> dataFromTheBase=FXCollections.observableArrayList();
	ObservableList<ProduitParetoRegle> ParetoData=FXCollections.observableArrayList();
	ObservableList<historiqueVenteData> historiqueData=FXCollections.observableArrayList();
	String url="",Mail="",Adress="";
	gestionContenus(){
				verifierCoflicConnection confliConnect=new verifierCoflicConnection();
				url=confliConnect.verifierConfliConection();
		}
	public ObservableList<String> DCIVenteContenu()
	{
		try {
			dataDC.clear();
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT DISTINCT DCI FROM mesproduits ORDER BY DCI");
			//classApp.ComboDCI.getItems().clear();
			while(RESULTSETDCI.next())
				{
					dataDC.add(RESULTSETDCI.getString("DCI"));
				}
			RESULTSETDCI.close();
		} catch (SQLException e) 
			{
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
		return dataDC;
	}
	
	public String infoStructure()
	{	String nom=null;
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT * FROM infostructure");
			//classApp.ComboDCI.getItems().clear();
			while(RESULTSETDCI.next())
				{
					nom=RESULTSETDCI.getString("Nom");
					Mail=RESULTSETDCI.getString("Mail");
					Adress=RESULTSETDCI.getString("Adress");
				}
			RESULTSETDCI.close();
		} catch (SQLException e) 
			{
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
		return nom;
	}
	public String infoMail()
	{	
		return Mail;
	}
	public String infoAdress()
	{	
		return Adress;
	}
	
	public ObservableList<String> SelectFromMesProduit(String sql,int option)
	{
		dataDC.clear();
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet RESULTSETDCI= statement.executeQuery(sql);
			//classApp.ComboDCI.getItems().clear();
			switch(option){
			case 1:{
					while(RESULTSETDCI.next())
						{
							dataDC.add(RESULTSETDCI.getString("DCI"));
						}
					break;
				}
			case 2:{
				while(RESULTSETDCI.next())
					{
						dataDC.add(RESULTSETDCI.getString("DateEntre"));
					}
				break;
			}
			case 3:{
				while(RESULTSETDCI.next())
					{
						dataDC.add(RESULTSETDCI.getString("DAteSortie"));
					}
				break;
			}
			
			}
			RESULTSETDCI.close();
		} catch (SQLException e) 
			{
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
		return dataDC;
	}
	
	// data for DCI new Product
	
	public ObservableList<String> DCInewProductContenu()
	{
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT DISTINCT DCI FROM TheDataBase ORDER BY DCI ASC");
			//classApp.ComboDCI.getItems().clear();
			while(RESULTSETDCI.next())
				{
					dataDCINP.add(RESULTSETDCI.getString("DCI"));
				}
			RESULTSETDCI.close();
		} catch (SQLException e) 
			{
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
		return dataDCINP;
	}
	
	public ObservableList<produitStock> TabStockContenu()
	{
		double leDouble=0;
		int entier=0;
		String nbmoiStock="";
		
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT Code, DCI, Emplacement, Founisseur, Unite, Quantite, DateEntre, DAteSortie, CMM FROM mesproduits ORDER BY DCI ASC");
			dataStock.clear();
			while(RESULTSETDCI.next())
				{
				if(RESULTSETDCI.getDouble("CMM")!=0.0)
					{
							leDouble=RESULTSETDCI.getDouble("Quantite")/RESULTSETDCI.getDouble("CMM");
							entier=(int)leDouble;
							if((double)entier==leDouble){
								nbmoiStock=""+entier+" mois";
							}else
								nbmoiStock=entier+" Mois "+(int)((leDouble-entier)*30)+" jours environ";
							
					}else
						nbmoiStock="Pour biento";
					
					dataStock.add(new produitStock(RESULTSETDCI.getInt("Code"),RESULTSETDCI.getString("DCI"),RESULTSETDCI.getString("Unite"),RESULTSETDCI.getLong("Quantite"),RESULTSETDCI.getString("DateEntre"),RESULTSETDCI.getString("DAteSortie"),RESULTSETDCI.getLong("CMM"),RESULTSETDCI.getString("Founisseur"),nbmoiStock));
					
				}
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
		
		return dataStock;
	}
	
	// enregistrer nouveau produit
	
	public void saveNewProduit(long code,String dci,String emplacement,
														String fournisseur,
														String uniter,long quantiter,
														long prixA,long prixV,double seuilcommand,
														long stocMin,long stockMax,
														Date dateEntrer,java.sql.Date datePeremt,
														double cmm,double qj)
	{
		
		boolean existe=false;
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT DCI FROM mesproduits");
			while(RESULTSETDCI.next())
				{
				if(dci.equals(RESULTSETDCI.getString("DCI")))
					{
						existe=true;
						break;
					}
				}
					
			
			if(existe==false){
				pstmt=connection.prepareStatement("INSERT INTO mesproduits VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setLong(1, code);
				pstmt.setString(2, dci);
				pstmt.setString(3, emplacement);
				pstmt.setString(4, fournisseur);
				pstmt.setString(5, uniter);
				pstmt.setLong(6, quantiter);
				pstmt.setLong(7, prixA);
				pstmt.setLong(8, prixV);
				pstmt.setDouble(9, seuilcommand);
				pstmt.setLong(10, stocMin);
				pstmt.setLong(11, stockMax);
				pstmt.setDate(12, dateEntrer);
				pstmt.setDate(13, datePeremt);
				pstmt.setDouble(14, cmm);
				pstmt.setDouble(15, qj);
				pstmt.execute();
				new AlerterSuccesWorning().Succes("Produit enregistré avec succes");
			}else
				new Alerter("ERROR: Produit existant dans le stock");
			
			RESULTSETDCI.close();
		} catch (SQLException e) {
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(pstmt != null)
	    		pstmt. close();
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
	
	public ObservableList<produitDataBase> SELECTfromTheDataBase()
	{
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT * FROM TheDataBase");
		
					while(RESULTSETDCI.next())
					{
						dataFromTheBase.add(new produitDataBase(RESULTSETDCI.getString("DCI"),RESULTSETDCI.getString("FormDose"),RESULTSETDCI.getString("Classe"),RESULTSETDCI.getString("Traitement")));
					}
			
			RESULTSETDCI.close();
		} catch (SQLException e) 
			{
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
		
		return dataFromTheBase;
		
	}
	
	public ObservableList<String> getFormeDose(String dci)
	{
		ObservableList<String> lesFormeDose=FXCollections.observableArrayList();
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			pstmt= connection.prepareStatement("SELECT Emplacement FROM mesproduits WHERE DCI=?");
			pstmt.setString(1,dci);
			ResultSet RESULTSETDCI = pstmt.executeQuery();
					while(RESULTSETDCI.next())
					{
						lesFormeDose.add(RESULTSETDCI.getString("formeDose"));							
					}
			RESULTSETDCI.close();
		} catch (SQLException e) 
			{
				new Alerter(e.getMessage());
			}finally {
		    	//finally block used to close resources
		    	try{
		    	if(statement != null)
		    		statement. close();
		    	if(pstmt != null)
		    		pstmt. close();
		    	}catch(SQLException se2){
		    	}// nothing we can do
		    	
		      if (connection != null) {
		        try {
		          connection.close();
		        } catch (SQLException e) {
		        } // nothing we can do
		      }
		    }
		
		return lesFormeDose;
		
	}
	
	public UnproduitDuStock AproductFromMesproduits(String dci)
	{
		UnproduitDuStock leProduits=null;
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			pstmt= connection.prepareStatement("SELECT * FROM mesproduits WHERE DCI=?");
			pstmt.setString(1,dci);
			ResultSet RESULTSETDCI= pstmt.executeQuery();
					while(RESULTSETDCI.next())
					{
						leProduits=new UnproduitDuStock(RESULTSETDCI.getLong("Code"),RESULTSETDCI.getString("DCI"),
														RESULTSETDCI.getString("Emplacement"),RESULTSETDCI.getString("Founisseur"),
														RESULTSETDCI.getString("Unite"),RESULTSETDCI.getLong("Quantite"),
														RESULTSETDCI.getLong("PrixAchat"),RESULTSETDCI.getLong("PrixVente"),
														RESULTSETDCI.getDouble("Seuilcommand"),RESULTSETDCI.getLong("StockMin"),
														RESULTSETDCI.getLong("StockMax"),RESULTSETDCI.getDate("DateEntre"),
														RESULTSETDCI.getDate("DateSortie"),RESULTSETDCI.getInt("CMM"),
														RESULTSETDCI.getDouble("QJ"));
					}
			
			RESULTSETDCI.close();
		} catch (SQLException e) 
			{
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
		
		return leProduits;
		
	}
	
	
	public ObservableList<ProduitParetoRegle> ParetoProduit(double total)
	{		 double QJcumul=0;
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT Code, DCI, QJ FROM mesproduits ORDER BY QJ DESC");
			RESULTSETDCI.first();
			QJcumul=RESULTSETDCI.getDouble("QJ");
			ParetoData.add(new ProduitParetoRegle(RESULTSETDCI.getLong("Code"),
					RESULTSETDCI.getString("DCI"),
					RESULTSETDCI.getDouble("QJ"),QJcumul,(int)((QJcumul/total)*100)));
			
					while(RESULTSETDCI.next())
					{
						QJcumul+=RESULTSETDCI.getDouble("QJ");
						ParetoData.add(new ProduitParetoRegle(RESULTSETDCI.getLong("Code"),
								RESULTSETDCI.getString("DCI"),
								RESULTSETDCI.getDouble("QJ"),QJcumul,(int)((QJcumul/total)*100)));
					}
			
			RESULTSETDCI.close();
		} catch (SQLException e) 
			{
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
		
		return ParetoData;
		
	}
	
public ObservableList<historiqueVenteData> AddLineToHistoriqueTable(String user){

	GestionDate gestionDAte=new GestionDate();
	Period diff = null;String oldUser="";
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT Date, Utilisateur FROM historiquedesvente");
			RESULTSETDCI.last();
			diff=gestionDAte.compareDate(RESULTSETDCI.getDate("Date").toLocalDate(), gestionDAte.curentDate().toLocalDate());
			oldUser=RESULTSETDCI.getString("Utilisateur");
			
			if(RESULTSETDCI.getDate("Date").toString().equals(gestionDAte.curentDate().toString()))
				{
					pstmt=connection.prepareStatement("UPDATE historiquedesvente SET Utilisateur=? WHERE Date=?");
					pstmt.setString(1,oldUser+"/"+user);
					pstmt.setDate(2,gestionDAte.curentDate());
					pstmt.execute();
				
				}else{
						pstmt=connection.prepareStatement("INSERT INTO historiquedesvente VALUE(?,0,0,?)");
						pstmt.setDate(1,gestionDAte.curentDate());
						pstmt.setString(2,user);
						pstmt.execute();
					}
			RESULTSETDCI.close();
		} catch (SQLException e) 
			{
				new Alerter(e.getMessage());
			}finally {
		        try {
		        	if (connection != null) 
		        			connection.close();
		        	if(statement != null)
			    		statement. close();
		        	if(pstmt != null)
		        		pstmt. close();
		        } catch (SQLException e) {
		        } // nothing we can do
		      
		    }
		return historiqueData;
		
		}
public void setSeuilleCommade(int intevalApprov,int delaiLiv)
	{ double leSeuil=0;
	try {
		connection = DriverManager.getConnection(url,"balamou","jean");
		statement = connection.createStatement();
		
		ResultSet RESULTSETDCI= statement.executeQuery("SELECT DCI, StockMin, StockMax FROM mesproduits");
		pstmt=connection.prepareStatement("UPDATE mesproduits SET Seuilcommand=? WHERE DCI=?");
		
		while(RESULTSETDCI.next())
				{
					leSeuil=(RESULTSETDCI.getDouble("StockMax")/intevalApprov)*delaiLiv+RESULTSETDCI.getDouble("StockMin");
					pstmt.setDouble(1, leSeuil);
					pstmt.setString(2, RESULTSETDCI.getString("DCI"));
					pstmt.executeUpdate();
				}
		RESULTSETDCI.close();
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(statement != null)
	    		statement. close();
	    	if(pstmt != null)
	    		pstmt.close();
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
public void CMMupdater(int IntervalAprovision)
	{ 	boolean fait=false;
		GestionDate gestiondate=new GestionDate();
		Period diff=null,nombreMoi=null;
		double cmm=0;int entier=0;
		try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT DateDebut,DateDebutPredef, Fait FROM DatePourCMM");
			
			PreparedStatement stmtp = connection.prepareStatement("SELECT DCI, formeDose, StockMin, StockMax, QJ FROM mesproduits");
			PreparedStatement pstmt=connection.prepareStatement("UPDATE mesproduits SET CMM=?, StockMax=? WHERE DCI=?");
			PreparedStatement pstmtCMM=connection.prepareStatement("UPDATE datepourcmm SET DateDebut=?, Fait=?");
			
			while(RESULTSETDCI.next())
					{
						fait = RESULTSETDCI.getBoolean("Fait");
						diff=gestiondate.compareDate(RESULTSETDCI.getDate("DateDebut").toLocalDate(), gestiondate.curentDate().toLocalDate());
						nombreMoi=gestiondate.compareDate(RESULTSETDCI.getDate("DateDebutPredef").toLocalDate(), gestiondate.curentDate().toLocalDate());
					}
			
			if(fait==false && diff.getMonths()>=1)
			{
			      ResultSet rs2 = stmtp.executeQuery();
			      while(rs2.next())
			      		{
						    cmm=rs2.getDouble("QJ")/nombreMoi.getMonths();
						    entier=(int)cmm;
						    if(cmm!=entier)
						    	cmm=entier+1;
						    pstmt.setDouble(1,cmm);
						    pstmt.setDouble(2,rs2.getDouble("StockMin")+IntervalAprovision*cmm);
			    	  		pstmt.setString(3,(String)rs2.getString("DCI"));
			    	  		pstmt.executeUpdate();
			      		} 
		   // Il faut metre a jour les valeurs de dateDebutpredet fait apres Chaque  mois
		        pstmtCMM.setDate(1,gestiondate.curentDate());
		        pstmtCMM.setBoolean(2,true);
		        pstmtCMM.executeUpdate();
		  	}
			
			RESULTSETDCI.close();
		} catch (SQLException e) 
			{
				new Alerter(e.getMessage());
			}finally {
		    	//finally block used to close resources
		    	try{
		    	if(statement != null)
		    		statement. close();
		    	if(pstmt != null)
		    		pstmt.close();
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
public void saveParametre(int intevalApro,int DelaiLiv,String nom,String Mail,String Adress)
{
	
		try {
			if(nom != null && Mail != null && Adress != null && intevalApro !=0 && DelaiLiv !=0){
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			
			pstmt=connection.prepareStatement("UPDATE datepourcmm SET IntervalEntreDeuxAprovis=?, DelaitDeLivraison=?");
			pstmt.setInt(1, intevalApro);
			pstmt.setInt(2, DelaiLiv);
			pstmt.executeUpdate();
			
			pstmt=connection.prepareStatement("UPDATE infostructure SET Nom=?, Mail=?, Adress=?");
			pstmt.setString(1,nom);
			pstmt.setString(2,Mail);
			pstmt.setString(3,Adress);
			pstmt.executeUpdate();
			
			setSeuilleCommade(intevalApro*30,DelaiLiv);
				
			Alert alert=new Alert(AlertType.INFORMATION);
	 		alert.setTitle("Valider");
	 		alert.setHeaderText("INFORMATION");
	 		alert.setContentText("Parametre enregistré avec succès");
	 		alert.show();
	 		
	 		}
		} catch (SQLException e) 
			{
				new Alerter(e.getMessage());
			}finally {
		    	//finally block used to close resources
		    	try{
		    	if(pstmt != null)
		    		pstmt.close();
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
public ObservableList<historiqueVenteData> HistoriqueDATA(){

	
	try {
		
		connection = DriverManager.getConnection(url,"balamou","jean");
		statement = connection.createStatement();
		ResultSet RESULTSETDCI= statement.executeQuery("SELECT * FROM historiquedesvente");
		while(RESULTSETDCI.next())
			{
				historiqueData.add(new historiqueVenteData(RESULTSETDCI.getString("Date"),RESULTSETDCI.getString("NombreProdVendu"),RESULTSETDCI.getString("VALEUR"),RESULTSETDCI.getString("Utilisateur")));
			}	
		RESULTSETDCI.close();
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	        try {
	        	if (connection != null) 
	        			connection.close();
	        	if(statement != null)
		    		statement. close();
	        } catch (SQLException e) {
	        } // nothing we can do
	      
	    }
	return historiqueData;
	
	}

public int getCode(String dci)
{
	int code=0;
			try {
				connection = DriverManager.getConnection(url,"balamou","jean");
				pstmt= connection.prepareStatement("SELECT Code FROM mesproduits WHERE DCI=?");
				ResultSet RESULTSETDCI=pstmt.executeQuery();
				
				while(RESULTSETDCI.next())
					{
						code=RESULTSETDCI.getInt("Code");
					}
						
			} catch (SQLException e) 
			{
				new Alerter(e.getMessage());
			}finally {
		    	//finally block used to close resources
		    	try{
		    	if(pstmt != null)
		    		pstmt.close();
		    	}catch(SQLException se2){
		    	}// nothing we can do
		    	
		      if (connection != null) {
		        try {
		          connection.close();
		        } catch (SQLException e) {
		        } // nothing we can do
		      }
		    }
			return code;	
	}

public void Approvision(long quantite,java.sql.Date Exp,String DCI,Date curentDate,String Origine){
	
	gestionFicheStock gestionFichStock=new gestionFicheStock();
	int code=0;
	long quantiter=0,sum=0;
	try {
		connection = DriverManager.getConnection(url,"balamou","jean");
		
		pstmt= connection.prepareStatement("SELECT Code, Quantite FROM mesproduits WHERE DCI=?");
		pstmt.setString(1, DCI);
		ResultSet RESULTSETDCI=pstmt.executeQuery();
		while(RESULTSETDCI.next())
			{
				code=RESULTSETDCI.getInt("Code");
				quantiter=RESULTSETDCI.getInt("Quantite");
			}
		RESULTSETDCI.close();
		sum=quantite+quantiter;
		if(quantite != 0 && Exp !=null && DCI !=null)
		{
			pstmt=connection.prepareStatement("UPDATE mesproduits SET Quantite=?, DAteSortie=? WHERE DCI=?");
			pstmt.setLong(1,sum);
			pstmt.setDate(2, Exp);
			pstmt.setString(3, DCI);
			pstmt.executeUpdate();
			
			gestionFichStock.remplirFicheStock(code,curentDate+","+Origine+","+quantite+","+"...."+","+sum+","+"....");
			Alert alert=new Alert(AlertType.INFORMATION);
	 		alert.setTitle("Approvision");
	 		alert.setHeaderText("INFORMATION");
	 		alert.setContentText("Approvision validé avec succès");
	 		alert.show();
 		
 		}
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(pstmt != null)
	    		pstmt.close();
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

public void ModificationSave(String dci,String dci2,String emplacement,
		String fournisseur,String unite,long prixAchat,long prixVente,
		long stockMin,long stockMax,Date dateEntrer,
		Date dateExp)
	{

	try {
		connection = DriverManager.getConnection(url,"balamou","jean");
		if(dci != null && emplacement !=null && fournisseur !=null)
		{
			pstmt=connection.prepareStatement("UPDATE mesproduits SET DCI=?, Emplacement=?, Founisseur=?, Unite=?, PrixAchat=?, PrixVente=?, StockMin=?, StockMax=?, DateEntre=?, DAteSortie=? WHERE DCI=?");
			pstmt.setString(1,dci2);
			pstmt.setString(2, emplacement);
			pstmt.setString(3, fournisseur);
			pstmt.setString(4,unite);
			pstmt.setLong(5, prixAchat);
			pstmt.setLong(6, prixVente);
			pstmt.setLong(7, stockMin);
			pstmt.setLong(8,stockMax);
			pstmt.setDate(9, dateEntrer);
			pstmt.setDate(10, dateExp);
			pstmt.setString(11, dci);
			pstmt.executeUpdate();
			
			Alert alert=new Alert(AlertType.INFORMATION);
	 		alert.setTitle("Modification");
	 		alert.setHeaderText("INFORMATION");
	 		alert.setContentText("Modification enregistrée");
	 		alert.show();
 		
 		}
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(pstmt != null)
	    		pstmt.close();
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

public void InventaireSave(int quantite,String DCI,Date curentDate,String remarque)
{
	gestionFicheStock gestionFichStock=new gestionFicheStock();
	int code=0;
	try {
		connection = DriverManager.getConnection(url,"balamou","jean");
		pstmt= connection.prepareStatement("SELECT Code FROM mesproduits WHERE DCI=?");
		pstmt.setString(1, DCI);
		ResultSet RESULTSETDCI=pstmt.executeQuery();
		while(RESULTSETDCI.next())
			{
				code=RESULTSETDCI.getInt("Code");
			}
		RESULTSETDCI.close();
		
		if(quantite != 0 && DCI !=null)
		{
			pstmt=connection.prepareStatement("UPDATE mesproduits SET Quantite=? WHERE DCI=?");
			pstmt.setInt(1, quantite);
			pstmt.setString(2, DCI);
			pstmt.executeUpdate();
			
			gestionFichStock.remplirFicheStock(code,curentDate+",Inventaire,...,...,"+quantite+","+remarque);
			
			Alert alert=new Alert(AlertType.INFORMATION);
	 		alert.setTitle("Inventaire");
	 		alert.setHeaderText("INFORMATION");
	 		alert.setContentText("Stock ajusté avec succès");
	 		alert.show();
 		
 		}
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(pstmt != null)
	    		pstmt.close();
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

public ObservableList<String> ListeUser()
{
	ObservableList<String> users=FXCollections.observableArrayList();
	try {

		connection = DriverManager.getConnection(url,"balamou","jean");
		statement = connection.createStatement();
		ResultSet RESULTSETDCI= statement.executeQuery("SELECT nom,NombreDeJour,Salaire,Remarque FROM logcontrol");
		users.add("Nom......jours de travail.....Salaire......Remarque");
		while(RESULTSETDCI.next())
			{
				users.add(RESULTSETDCI.getString("nom")+"......"+RESULTSETDCI.getString("NombreDeJour")+"......"+RESULTSETDCI.getString("Salaire")+"......"+RESULTSETDCI.getString("Remarque"));
			}
		RESULTSETDCI.close();
		
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(statement != null)
	    		statement.close();
	    	}catch(SQLException se2){
	    	}// nothing we can do
	    	
	      if (connection != null) {
	        try {
	          connection.close();
	        } catch (SQLException e) {
	        } // nothing we can do
	      }
	    }
	return users;
	
}

public void deleteUser(String noms)
{
	try {
		connection = DriverManager.getConnection(url,"balamou","jean");
		pstmt= connection.prepareStatement("DELETE FROM logcontrol WHERE nom=?");
		pstmt.setString(1,noms);
		pstmt.execute();
		
		new AlerterSuccesWorning().Succes("Utilisateur suprimé avec succès");
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(pstmt != null)
	    		pstmt.close();
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


public void saveNewUser(String param1,String param2,String param3,String param4,
						String param5,boolean param6,boolean param7,boolean param8,
						boolean param9,boolean param10,boolean param11,boolean param12,
						boolean param13,boolean param14,boolean param15,boolean param16,boolean param17)
{
	boolean existe=false;
	try {

		connection = DriverManager.getConnection(url,"balamou","jean");
		statement = connection.createStatement();
		ResultSet RESULTSETDCI= statement.executeQuery("SELECT nom FROM logcontrol");
		while(RESULTSETDCI.next())
			{
				if(RESULTSETDCI.getString("nom").equals(param1))
					{
						existe=true;
						break;
					}
			}
		
		if(existe==false)
		{
				pstmt= connection.prepareStatement("INSERT INTO  logcontrol VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1, param1);
				pstmt.setString(2, param2);
				pstmt.setString(3, param3);
				pstmt.setString(4, param4);
				pstmt.setString(5, param5);
				pstmt.setBoolean(6, param6);
				pstmt.setBoolean(7, param7);
				pstmt.setBoolean(8, param8);
				pstmt.setBoolean(9, param9);
				pstmt.setBoolean(10, param10);
				pstmt.setBoolean(11, param11);
				pstmt.setBoolean(12, param12);
				pstmt.setBoolean(13, param13);
				pstmt.setBoolean(14, param14);
				pstmt.setBoolean(15, param15);
				pstmt.setBoolean(16, param16);
				pstmt.setBoolean(17, param17);
				pstmt.execute();
				
				new AlerterSuccesWorning().Succes("Utilisateur Enregistrer avec succès");
		}else
			{
				new Alerter("Utulisateur existant");
			}
		RESULTSETDCI.close();
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(statement != null)
	    		statement.close();
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


public void updateUser(String param1,String param2,String param3,String param4,
						String param5,boolean param6,boolean param7,boolean param8,
						boolean param9,boolean param10,boolean param11,boolean param12,
						boolean param13,boolean param14,boolean param15,boolean param16,boolean param17)
{
	try{	connection = DriverManager.getConnection(url,"balamou","jean");
			if(param1 != null){
				pstmt= connection.prepareStatement("UPDATE  logcontrol SET nom=?,pass=?,NombreDeJour=?,salaire=?,Remarque=?,venteProduit=?,ajoutProduit=?,faireInventaire=?,editionProduit=?,approvision=?,supression=?,faireCommande=?,AccesParametre=?,accesStatitique=?,RetounerPro=?,AjouterUser=?,AjouterOrdy=? WHERE nom=?");
				pstmt.setString(1, param1);
				pstmt.setString(2, param2);
				pstmt.setString(3, param3);
				pstmt.setString(4, param4);
				pstmt.setString(5, param5);
				pstmt.setBoolean(6, param6);
				pstmt.setBoolean(7, param7);
				pstmt.setBoolean(8, param8);
				pstmt.setBoolean(9, param9);
				pstmt.setBoolean(10, param10);
				pstmt.setBoolean(11, param11);
				pstmt.setBoolean(12, param12);
				pstmt.setBoolean(13, param13);
				pstmt.setBoolean(14, param14);
				pstmt.setBoolean(15, param15);
				pstmt.setBoolean(16, param16);
				pstmt.setBoolean(17, param17);
				pstmt.setString(18, param1);
				pstmt.execute();
				
				new AlerterSuccesWorning().Succes("Modification Enregistrer avec succès");
			}
		
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(statement != null)
	    		statement.close();
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


public String CaisseTotal()
{
	String Total="";
	try {
		connection = DriverManager.getConnection(url,"balamou","jean");
		statement = connection.createStatement();
		ResultSet RESULTSETDCI= statement.executeQuery("SELECT Total FROM caisse");
		while(RESULTSETDCI.next())
			{
				Total=RESULTSETDCI.getString("Total");
			}
		RESULTSETDCI.close();
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(statement != null)
	    		statement.close();
	    	}catch(SQLException se2){
	    	}// nothing we can do
	    	
	      if (connection != null) {
	        try {
	          connection.close();
	        } catch (SQLException e) {
	        } // nothing we can do
	      }
	    }
	return Total;
}


public void DepotRetrait(int nombre,java.sql.Date curentDate,int option)
{	double Total=0;
	try{	
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			ResultSet RESULTSETDCI= statement.executeQuery("SELECT Total FROM caisse");
			while(RESULTSETDCI.next())
				{
					Total=RESULTSETDCI.getDouble("Total");
				}
			RESULTSETDCI.close();
			
			switch(option){
			case 1:{
						if( 0 < nombre){
							pstmt= connection.prepareStatement("UPDATE  caisse SET Total=?");
							pstmt.setDouble(1, Total+nombre);
							pstmt.execute();
							
							pstmt= connection.prepareStatement("INSERT INTO depotretrait  VALUE(?,?,?)");
							pstmt.setDate(1,curentDate);
							pstmt.setDouble(2,0);
							pstmt.setDouble(3,nombre);
							pstmt.execute();
							
							new AlerterSuccesWorning().Succes("Dépot effectué");
						}
					break;
					}
				case 2:{
						if( 0 < nombre){
							pstmt= connection.prepareStatement("UPDATE  caisse SET Total=?");
							pstmt.setDouble(1, Total-nombre);
							pstmt.execute();
							
							pstmt= connection.prepareStatement("INSERT INTO depotretrait  VALUE(?,?,?)");
							pstmt.setDate(1,curentDate);
							pstmt.setDouble(2,nombre);
							pstmt.setDouble(3,0);
							pstmt.execute();
							
							new AlerterSuccesWorning().Succes("Retrait effectué");
						}
						break;
				}
			
			}
			
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(statement != null)
	    		statement.close();
	    	if(pstmt != null)
	    		pstmt.close();
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


public ObservableList<String> CaissHistoq()
{
	ObservableList<String> historiq=FXCollections.observableArrayList();
	try {

		connection = DriverManager.getConnection(url,"balamou","jean");
		statement = connection.createStatement();
		ResultSet RESULTSETDCI= statement.executeQuery("SELECT * FROM depotretrait");
		historiq.add("   Date    Retrait    Depot");
		while(RESULTSETDCI.next())
			{
				historiq.add(RESULTSETDCI.getString("Date")+"     "+RESULTSETDCI.getString("MontantRetirer")+"     "+RESULTSETDCI.getString("MontantDeposer"));
			}
		RESULTSETDCI.close();
		
	} catch (SQLException e) 
		{
			new Alerter(e.getMessage());
		}finally {
	    	//finally block used to close resources
	    	try{
	    	if(statement != null)
	    		statement.close();
	    	}catch(SQLException se2){
	    	}// nothing we can do
	    	
	      if (connection != null) {
	        try {
	          connection.close();
	        } catch (SQLException e) {
	        } // nothing we can do
	      }
	    }
	return historiq;
	
}

}
