package com.starupFX.startup;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FenetreRechercheStock {

	public FenetreRechercheStock() {
		// TODO Auto-generated constructor stub
		Stage stage=new Stage();
		BorderPane root=new BorderPane();
		
		
		
		try {
			FadeTransition fade=new FadeTransition(Duration.seconds(2),root);
			fade.setFromValue(0.4);
			fade.setToValue(1);
			fade.play();
			Scene scene = new Scene(root,900,800);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			
			} catch(Exception e) {
					e.printStackTrace();
				}
	}

}
