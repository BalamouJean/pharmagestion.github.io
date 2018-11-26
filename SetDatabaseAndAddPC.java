package com.starupFX.startup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SetDatabaseAndAddPC {
	static Connection connection = null;
	static Statement statement = null;
	
public void setSDatabase()
	{	PreparedStatement pstmt2=null;
		PreparedStatement pstmt=null;
		GestionDate gestionDate=new GestionDate();
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/","balamou","jean");
			statement = connection.createStatement();
		 	statement.executeUpdate("CREATE DATABASE dbproduits");
		 	connection.close();
		 	
		 	connection = DriverManager.getConnection("jdbc:mysql://localhost/dbproduits?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "balamou", "jean");
		 	statement = connection.createStatement();
		 	
		 	statement.executeUpdate("CREATE TABLE mesproduits ( Code BIGINT NOT NULL, DCI VARCHAR(100), Emplacement VARCHAR(200), Founisseur VARCHAR(100), Unite VARCHAR(50), Quantite BIGINT NOT NULL, PrixAchat BIGINT NOT NULL, PrixVente BIGINT NOT NULL, Seuilcommand DOUBLE,StockMin BIGINT, StockMax BIGINT, DateEntre DATE, DAteSortie DATE, CMM DOUBLE, QJ DOUBLE, PRIMARY KEY (Code))");
		 	
		 	statement.executeUpdate("CREATE TABLE Fournisseurs ( Code VARCHAR(10), Nom VARCHAR(70), Adresse VARCHAR(100), Mail VARCHAR(70), Tel BIGINT, PRIMARY KEY (Nom))");
		 	
			statement.executeUpdate("CREATE TABLE HistoriqueDesVente (Date DATE, NombreProdVendu INT NOT NULL, VALEUR BIGINT, Utilisateur TEXT,PRIMARY KEY (Date))");
			pstmt= connection.prepareStatement("INSERT INTO HistoriqueDesVente VALUES(?,0,0,' '");
			pstmt.setDate(1,gestionDate.curentDate());
			pstmt.executeUpdate();
			
			statement.executeUpdate("CREATE TABLE DepotRetrait (Date DATE, MontantRetirer DOUBLE, MontantDeposer DOUBLE)");
			
			statement.executeUpdate("CREATE TABLE DatePourCMM (DateDebut Date, Fait BIT, DateDebutPredef Date, IntervalEntreDeuxAprovis INT, DelaitDeLivraison INT)");
			pstmt2= connection.prepareStatement("INSERT INTO DatePourCMM VALUES(?,0,?,1,1)");
			pstmt2.setDate(1,gestionDate.curentDate());
			pstmt2.setDate(2,gestionDate.curentDate());
			pstmt2.executeUpdate();
			pstmt2.close();
			
			statement.executeUpdate("CREATE TABLE Caisse (Total DOUBLE)");
			statement.executeUpdate("INSERT INTO Caisse VALUES(0)");

			statement.executeUpdate("CREATE TABLE LogControl (nom VARCHAR(70), pass VARCHAR(15), NombreDeJour VARCHAR(8), Salaire VARCHAR(30), Remarque VARCHAR(200), venteProduit BIT,ajoutProduit BIT,faireInventaire BIT,editionProduit BIT,approvision BIT,supression BIT,faireCommande BIT,AccesParametre BIT,accesStatitique BIT,RetounerPro BIT,AjouterUser BIT,AjouterOrdy BIT)");
			statement.executeUpdate("INSERT INTO LogControl VALUES('Balamou','jean',' ',' ','Developeur',0,0,0,0,0,0,0,0,0,0,1,1)");
			
			statement.executeUpdate("CREATE TABLE InfoStructure (Nom VARCHAR(100),Mail VARCHAR(50),Adress VARCHAR(100))");
			statement.executeUpdate("INSERT INTO InfoStructure VALUES('One Vision Corporation','onevision2018@gmail.com','Conakry-Aeroport')");
			
			statement.executeUpdate("CREATE TABLE TheDataBase (DCI VARCHAR(100),FormDose VARCHAR(150),Classe VARCHAR(250),Traitement VARCHAR(250))");
			
			Alert alert=new Alert(AlertType.INFORMATION);
	 		alert.setTitle("Base de donnée");
	 		alert.setHeaderText("INFORMATION");
	 		alert.setContentText("Veuiller redemarer l'application");
	 		alert.show();
	 	}catch(SQLException se){
	 			new Alerter(se.getMessage());
	 		}catch(Exception e){
	 			
	 		}finally{
	 				//finally block used to close resources
	 				try{
	 					if(statement != null)
	 						statement.close();
	 					if(pstmt2 != null)
	 						pstmt2.close();
	 					}catch(SQLException se2){}// nothing we can do
	 				try{
	 					if(connection != null)
	 						connection.close();
	 				}catch(SQLException se){
	 					//se. printStackTrace();
	 				}//end finally try
	 		}
}
public void addPC(String url,String IPclien){
	try {
			connection = DriverManager.getConnection(url,"balamou","jean");
			statement = connection.createStatement();
			String sql="GRANT ALL PRIVILEGES ON dbproduits.* TO 'balamou'@'"+IPclien+"' IDENTIFIED BY 'jean'";// WITH GRANT OPTION
			statement.executeQuery(sql);
			new AlerterSuccesWorning().Worning("Utilisateur ajouté");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
		new AlerterSuccesWorning().Worning(e.getMessage());
	}
}
}
