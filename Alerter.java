package com.starupFX.startup;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Alerter{
	public Alerter(String message) {
/*	Alert alert=new Alert(AlertType.INFORMATION);
 		alert.setTitle("ERREUR");
 		alert.setHeaderText("INFORMATION");
 		alert.setContentText(message);
 		alert.show();
 */		Image img=null;
		try{ img=new Image("icon/securitylow72.png");}catch(Exception ex){}
 		Notifications notifu=Notifications.create().title("      ERREUR").text(message).graphic(new ImageView(img)).hideAfter(Duration.seconds(8)).position(Pos.CENTER);
 		notifu.darkStyle();
 		notifu.show();
 		
	}
	
	public Alerter(String message,int nb) {
				Image img=null;
				try{ img=new Image("icon/lockRed.png");}catch(Exception ex){}
		 		Notifications notifu=Notifications.create().title(" AVERTISSEMENT").text(message).graphic(new ImageView(img)).hideAfter(Duration.seconds(4)).position(Pos.CENTER);
		 		notifu.darkStyle();
		 		notifu.show();
		 		
			}

}
