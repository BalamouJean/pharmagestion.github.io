package com.starupFX.startup;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AlerterSuccesWorning {

	public void Succes(String message){
		Image img=new Image("icon/securityhigh72.png");
 		Notifications notifu=Notifications.create().title("      SUCCES").text(message).graphic(new ImageView(img)).hideAfter(Duration.seconds(7)).position(Pos.CENTER);
 		notifu.darkStyle();
 		notifu.hideCloseButton();
 		notifu.show();
	}
	
	public void Worning(String message){
		Image img=new Image("icon/securitymedium72.png");
 		Notifications notifu=Notifications.create().title("  AVERTISSEMENT").text(message).graphic(new ImageView(img)).hideAfter(Duration.seconds(8)).position(Pos.CENTER);
 		notifu.darkStyle();
 		notifu.show();
		
	}
}
