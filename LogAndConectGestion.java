package com.starupFX.startup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogAndConectGestion 
{
	static Connection connection = null;
	static Statement statement = null;
	
	private boolean existe=false;
	private boolean vente=false;
	private boolean ajoute=false;
	private boolean inventaire=false;
	private boolean edition=false;
	private boolean aprovision=false;
	private boolean supression=false;
	private boolean faiteCommande=false;
	private boolean accesParametre=false;
	private boolean voirStatisrique=false;
	private boolean retournerProd=false;
	private boolean ajoutUser=false;
	private boolean ajouterOrdy=false;
	
	
	
	
	
	public boolean userExiste(String url,String nom,String pass)
		{
			try {
				connection = DriverManager.getConnection(url,"balamou","jean");
				statement = connection.createStatement();
				ResultSet RESULTSETDCI= statement.executeQuery("SELECT * FROM LogControl");
				while(RESULTSETDCI.next())
				{
					if(nom.equals(RESULTSETDCI.getString("nom")) && pass.equals(RESULTSETDCI.getString("pass")))
						{
							existe=true;
							vente=RESULTSETDCI.getBoolean("venteProduit");
							ajoute=RESULTSETDCI.getBoolean("ajoutProduit");
							inventaire=RESULTSETDCI.getBoolean("faireInventaire");
							edition=RESULTSETDCI.getBoolean("editionProduit");
							aprovision=RESULTSETDCI.getBoolean("approvision");
							supression=RESULTSETDCI.getBoolean("supression");
							faiteCommande=RESULTSETDCI.getBoolean("faireCommande");
							accesParametre=RESULTSETDCI.getBoolean("AccesParametre");
							voirStatisrique=RESULTSETDCI.getBoolean("accesStatitique");
							retournerProd=RESULTSETDCI.getBoolean("RetounerPro");
							ajoutUser=RESULTSETDCI.getBoolean("AjouterUser");
							ajouterOrdy=RESULTSETDCI.getBoolean("AjouterOrdy");
							break;
						}
				}
			RESULTSETDCI.close();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return existe;
		}
	
public boolean VenteAlow(){
		return vente;
	}
public boolean AjouteAlow(){
	return ajoute;
}
public boolean EditionAlow(){
	return edition;
}
public boolean AprovisionAlow(){
	return aprovision;
}
public boolean SupressionAlow(){
	return supression;
}
public boolean FaireInventaire(){
	return inventaire;
}
public boolean FaireCommandeAlow(){
	return faiteCommande;
}
public boolean FaireLivraisonAlow(){
	return accesParametre;
}
public boolean VoirStatistiqAlow(){
	return voirStatisrique;
}
public boolean RetourneProdAlow(){
	return retournerProd;
}
public boolean AjoutUserAlow(){
	return ajoutUser;
}
public boolean AjoutOrdyAlow(){
	return ajouterOrdy;
}
}

