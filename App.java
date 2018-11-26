package com.starupFX.startup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.controlsfx.control.StatusBar;
import org.controlsfx.control.table.TableFilter;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.dialog.ProgressDialog;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * Hello world!
 *
 */
public class App extends Application
{
	static LogAndConectGestion logControl=null;
	ObservableList<produitAvendre> data=FXCollections.observableArrayList();
	ObservableList<produitAvendre> dataEcheck=FXCollections.observableArrayList();
	ObservableList<produitStock> dataStock=FXCollections.observableArrayList();
	ObservableList<produitVenteJour> dataDetailVente=FXCollections.observableArrayList();
	ObservableList<ProduitLivraison> dataLivaison=FXCollections.observableArrayList();
	ObservableList<produitAcommander> dataCommande=FXCollections.observableArrayList();
	ObservableList<String> dataDCI=FXCollections.observableArrayList();
	//****************For result surveillant***************
	ListView<String> listView1=new ListView<String>();
	ListView<String> listView2=new ListView<String>();
	ListView<String> listView3=new ListView<String>();
	ListView<String> listView4=new ListView<String>();
	ListView<String> listView5=new ListView<String>();
	
	ObservableList<produitDataBase> queryDataListView=FXCollections.observableArrayList();
	int queryOption=1;
	ObservableList<produitAcommander> queryFD=FXCollections.observableArrayList();
	
	JFXComboBox<String> ComboDCI=new JFXComboBox<String>();
	Label leprix;
	Text Cj=new Text();
	Text goodstate;
	Label resultatAnalyse;
	gestionContenus gestionContenu=new gestionContenus();
	CommandLivreData dataCommand=null;//new CommandLivreData();
     
	static Connection connection = null;
	static Connection TransactionConnection = null;
	static Statement statement = null;
	PreparedStatement pstmt=null;
	PreparedStatement pstmtForTransaction=null;
	PreparedStatement pstmtUPDATEhistorique=null;
	PreparedStatement pstmtUPDATEcaisse=null;
	java.sql.Date curentDate=null;
	long diferenceDate=0;
	LocalDate curentDate2=null;
	
	static String url="",Information="";
	static boolean serveur=false;
	produitAvendre prodDevente;
	long sum=0,sumTotalventeJour=0,sumTotalValeur=0;
	double sumTotal=0;
	int validResult=0,sumTotalVente=0,TVComanLivra=0,oldIndextCV=-1,indexChecker=-1,
			curentIndex=0,indexCheckerEdit=-1,oldIndextFS=-1,indexCheckerApro=-1;
	boolean enCourdeVente=false,Rupture=false,surstock=false,FaireComande=false,SeuilDeComande=false,PerimeBiento=false;
	
	
	
	
	
	// for splash
	BorderPane root =new BorderPane();
	private Stage MainStage;
	Stage progressStage=new Stage();
	HBox hbPI=new HBox();
	ImageView fitnessHome=null;

    private VBox splashLayout;
    private ProgressBar loadProgress;
    private ProgressIndicator loadProgressTop;
    private Label progressText;
    private Stage mainStage;
    private static final int SPLASH_WIDTH = 676;
    private static final int SPLASH_HEIGHT = 227;
    
    Stage firstStage = new Stage();
    BorderPane rootAccueille= new BorderPane();
    
    StatusBar statuBar = new StatusBar();
    
    public static void main( String[] args )
    {
    	verifierCoflicConnection confliConnect=new verifierCoflicConnection();
 		url=confliConnect.verifierConfliConection();
 		Information=confliConnect.Information();
 		serveur=confliConnect.serveur();
    	 Application.launch(args);
    	
    }
    
    public void init() {
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(585);//SPLASH_WIDTH - 20
        progressText = new Label("Chargement des donnés . . .");
        progressText.setStyle("-fx-text-fill: white");
        splashLayout = new VBox();splashLayout.setSpacing(2);
        splashLayout.getChildren().addAll(progressText,loadProgress);
        
        loadProgressTop=new ProgressIndicator();
        hbPI.setAlignment(Pos.CENTER);
        hbPI.setStyle("-fx-background-color: #009688");
        loadProgressTop.setStyle("-fx-progress-color: white");
    	hbPI.getChildren().add(loadProgressTop);
    	progressStage.initStyle(StageStyle.UNDECORATED);
    	progressStage.initModality(Modality.WINDOW_MODAL);	
    	Cj.setStyle("-fx-fill: yellow;-fx-font-size: 17");
    	statuBar.setText("");
    }
    
    
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		
		
//**************************************shedule service for surveillant**************************************
		
		curentDate2=LocalDate.now(ZoneId.of("Africa/Conakry"));
		ScheduledService<Void> svc = new ScheduledService<Void>() {
		     protected Task<Void> createTask() {
		         return new Task<Void>() {
		             protected Void call() {
		           Platform.runLater(new Runnable(){
			           public void run(){
		            	  
		            	int T=dataCommand.intervallAprov()*30;
		            	listView1.getItems().clear();listView2.getItems().clear();
		            	listView3.getItems().clear();listView4.getItems().clear();
		            	listView5.getItems().clear();
		            	
						try {
							connection = DriverManager.getConnection(url,"balamou","jean");
							statement = connection.createStatement();
							ResultSet rsstock = statement.executeQuery("SELECT DCI, StockMin, Quantite, Seuilcommand, StockMax, DAteSortie FROM mesproduits");
							
								while(rsstock.next())
					      		{
				    	  			diferenceDate=ChronoUnit.MONTHS.between(rsstock.getDate("DAteSortie").toLocalDate(),curentDate2);
				    	  			if(diferenceDate<=6)
					    	  			{
					    	  				PerimeBiento=true;
					    	  				listView3.getItems().add(rsstock.getString("DCI"));
					    	  			}
					    	  		if(rsstock.getInt("StockMax")<rsstock.getInt("Quantite"))
						    	  		{
						    	  			surstock=true;
						    	  			listView1.getItems().add(rsstock.getString("DCI"));
						    	  		}
					    	  		if(rsstock.getInt("Quantite")==0)
						    	  		{
						    	  			Rupture=true;
						    	  			listView5.getItems().add(rsstock.getString("DCI"));
						    	  		}
					    	  		if(rsstock.getInt("StockMin")>rsstock.getInt("Quantite"))
						    	  		{
						    	  			FaireComande=true;	
						    	  			listView2.getItems().add(rsstock.getString("DCI"));
						    	  		}
					    	  		if(rsstock.getDouble("Seuilcommand")>=rsstock.getInt("Quantite"))
					    	  			{
					    	  				SeuilDeComande=true;
					    	  				listView4.getItems().add(rsstock.getString("DCI"));
					    	  			}
					    	  	}
					      rsstock.close();
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
		            	
		            			
								if(Rupture==true || FaireComande==true || SeuilDeComande==true)
									{
										resultatAnalyse.setStyle("-fx-text-fill: white;-fx-background-color: red;-fx-font-size: 22;");
										goodstate.setStyle("-fx-fill: red;-fx-font-size: 40");
										resultatAnalyse.setText(" Stock en mauvais état ");
										goodstate.setText("Stock en mauvais état ");
										try{fitnessHome.setImage(new Image("icon/securityLow.png"));}catch(Exception ex){}
										informDialog(" Stock en mauvais état ");
									}
								else
									{
										if(PerimeBiento==true || surstock==true)
											{
												resultatAnalyse.setStyle("-fx-text-fill: white;-fx-background-color: yellow;-fx-font-size: 22;");
												goodstate.setStyle("-fx-fill: yellow;-fx-font-size: 40");
												resultatAnalyse.setText(" Stock en MOYEN état ");
												goodstate.setText(" Stock en MOYEN état ");
												try{fitnessHome.setImage(new Image("icon/securityWorning.png"));}catch(Exception ex){}
												informDialog("Stock en MOYEN état ");
											}
										else
											resultatAnalyse.setText(null);
									}
								
		            		}
		            	});
						
		                 return null;
		             }
		         };
		     }
		 };
		 svc.setPeriod(Duration.minutes(4));
		 svc.setDelay(Duration.seconds(0));
		 svc.setRestartOnFailure(true);
		 
		 FadeTransition AnimSurv = new FadeTransition(Duration.millis(3000), resultatAnalyse);
		 AnimSurv.setFromValue(1.0);
		 AnimSurv.setToValue(1);
		 AnimSurv.setCycleCount(Timeline.INDEFINITE);
		 AnimSurv.setAutoReverse(true);
		 AnimSurv.play();
		 
//*************************************	shedule end **********************
		 
		 
		final Task<ObservableList<String>> friendTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {
		/**
		 * The root Pane
		 *
		 */
        dataCommand=new CommandLivreData();
        //gestionContenu.setSeuilleCommade(dataCommand.intervallAprov()*30,dataCommand.delaiLivrais());
      
        
        
        
        loadProgressTop.getStyleClass().add("myProgressBar");
        updateMessage("Loading data . . .");
		curentDate=new GestionDate().curentDate();
	//	BorderPane root =new BorderPane();
		BorderPane rootTop =new BorderPane();
		BorderPane rootCEnter1 =new BorderPane();rootCEnter1.setStyle("-fx-background-color: radial-gradient(focus-angle 45deg,focus-distance 20%,center 50% 25%,radius 50%, reflect,#01579b,#0277bd 60%,#29b6f6);");
		BorderPane rootCEnter2 =borderPaneVente();
		rootCEnter2.setStyle("-fx-background-color: #009688");
		BorderPane rootCEnter3=borderPaneStock();
		rootCEnter3.setStyle("-fx-background-color: #009688");
		//BorderPane rootCEnter4=new BorderPane();rootCEnter4.setStyle("-fx-background-color: #1b5e20");
		BorderPane rootCEnter4=borderPanelivraison();
		rootCEnter4.setStyle("-fx-background-color: #444444");
	//	BorderPane rootCEnter5=new BorderPane();rootCEnter5.setStyle("-fx-background-color: green");
		BorderPane rootCEnter5=borderPaneCommande();
		rootCEnter5.setStyle("-fx-background-color: green");
		BorderPane rootCEnter6=new BorderPane();
		rootCEnter6.setStyle("-fx-background-color: #009688");
		rootCEnter6.setCenter(StatistiquePane());
		BorderPane bpUser=borderPaneUser();
		
		BorderPane rootTopBottom =new BorderPane();
					rootTopBottom.setStyle("-fx-background-color: white");
		
				HBox hBox1=new HBox();
				HBox hBox2=new HBox();
				VBox hBox3=new VBox();hBox3.setSpacing(1);hBox3.setAlignment(Pos.TOP_LEFT);
				hBox3.setFocusTraversable(true);
				hBox3.setMaxWidth(170);
				hBox3.setPrefWidth(170);
				hBox2.setStyle("-fx-background-color: #283593");
				hBox3.setStyle("-fx-background-color: #E0E0E0");
				
				JFXButton close=new JFXButton();
				close.setStyle("-fx-text-fill: white;-fx-font-size: 18;");
				close.getStyleClass().add("close");
				JFXButton Newuser=new JFXButton("Utilisateur");
				Newuser.setStyle("-fx-text-fill: white;-fx-font-size: 18;-fx-background-color: #0d47a1;");
				close.setOnAction(new EventHandler<ActionEvent>(){
									@Override
									public void handle(ActionEvent arg0) {
											System.exit(1);
										}
									});
				PopOver popoveruser=new PopOver(bpUser);
				popoveruser.setArrowLocation(ArrowLocation.RIGHT_TOP);
				popoveruser.setTitle("Nouveau utilisateur");
				popoveruser.setDetached(true);
				
			   
			    
				Newuser.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						popoveruser.setDetached(true);
						popoveruser.show(Newuser);
					}
					 
				 });
				
				
				JFXButton Home=new JFXButton("Accueille  ");
				Home.setMaxSize(hBox3.getMaxWidth(), 100);
				Home.setPrefSize(hBox3.getMaxWidth(), 100);
				Home.setStyle("-fx-background-color: #03a9f4;-fx-text-fill: white;-fx-font-size: 20;");
				Home.getStyleClass().add("Home");
				JFXButton Vente=new JFXButton("Vente      ");
				Vente.setMaxSize(hBox3.getMaxWidth(), 100);
				Vente.setPrefSize(hBox3.getMaxWidth(), 100);
				Vente.setMaxWidth(hBox3.getMaxWidth());
				Vente.setStyle("-fx-text-fill: black;-fx-font-size: 20;");
				Vente.getStyleClass().add("Vente");
				JFXButton Stock=new JFXButton("Stock        ");
				Stock.setMaxSize(hBox3.getMaxWidth(), 100);
				Stock.setPrefSize(hBox3.getMaxWidth(), 100);
				Stock.setMaxWidth(hBox3.getMaxWidth());
				Stock.setStyle("-fx-text-fill: black;-fx-font-size: 20;");
				Stock.getStyleClass().add("Stock");
				JFXButton Livraison=new JFXButton("Parametre  ");
				Livraison.setMaxSize(hBox3.getMaxWidth(), 100);
				Livraison.setPrefSize(hBox3.getMaxWidth(), 100);
				Livraison.setMaxWidth(hBox3.getMaxWidth());
				Livraison.setStyle("-fx-text-fill: black;-fx-font-size: 20;");
				Livraison.getStyleClass().add("Livraison");
				JFXButton Commande=new JFXButton("Commande");
				Commande.setMaxSize(hBox3.getMaxWidth(), 100);
				Commande.setPrefSize(hBox3.getMaxWidth(), 100);
				Commande.setMaxWidth(hBox3.getMaxWidth());
				Commande.setStyle("-fx-text-fill: black;-fx-font-size: 18;");
				Commande.getStyleClass().add("Commande");
				JFXButton Statistique=new JFXButton("Statistique");
				Statistique.setMaxSize(hBox3.getMaxWidth(), 100);
				Statistique.setPrefSize(hBox3.getMaxWidth(), 100);
				Statistique.setMaxWidth(hBox3.getMaxWidth());
				Statistique.setStyle("-fx-text-fill: black;-fx-font-size: 20;");
				Statistique.getStyleClass().add("Statistique");
				
				Text oneVision=new Text("One Vision Corporation");
				oneVision.setStyle("-fx-fill: white;-fx-font-size: 20;");
				ImageView imgVew=new ImageView();
				ImageView imgVew2=new ImageView();
				ImageView imgVew3=new ImageView();
				ImageView imgVew4=new ImageView();
				
				ImageView navButon1=new ImageView();
				ImageView navButon2=new ImageView();
				ImageView navButon3=new ImageView();
				ImageView navButon4=new ImageView();
				ImageView navButon5=new ImageView();
				ImageView navButon6=new ImageView();
				ImageView oeuil=new ImageView();
				fitnessHome=new ImageView();
				
				try{
				Image img=new Image("icon/safety.png");
				Image img2=new Image("icon/user18.png");
				Image img3=new Image("icon/close18.png");
				Image img4=new Image("icon/logo.png");
				Image imgOeuil=new Image("icon/voir36dp.png");
				
				Image navImg1=new Image("icon/homeW.png");
				Image navImg2=new Image("icon/vente24.png");
				Image navImg3=new Image("icon/stock24.png");
				Image navImg4=new Image("icon/setting.png");
				Image navImg5=new Image("icon/comande24.png");
				Image navImg6=new Image("icon/chart24.png");
				Image fitneshome=new Image("icon/securityHigh.png");
				
				
				imgVew.setImage(img);
				imgVew2.setImage(img2);
				imgVew3.setImage(img3);
				imgVew4.setImage(img4);
				oeuil.setImage(imgOeuil);
				Newuser.setGraphic(imgVew2);
				close.setGraphic(imgVew3);
				
				
				navButon1.setImage(navImg1);
				navButon2.setImage(navImg2);
				navButon3.setImage(navImg3);
				navButon4.setImage(navImg4);
				navButon5.setImage(navImg5);
				navButon6.setImage(navImg6);
				fitnessHome.setImage(fitneshome);
				
				Home.setGraphic(navButon1);
				Vente.setGraphic(navButon2);
				Stock.setGraphic(navButon3);
				Livraison.setGraphic(navButon4);
				Commande.setGraphic(navButon5);
				Statistique.setGraphic(navButon6);
				
				} catch(Exception e) {
					System.out.print(e);;
				}
				VBox vbdateTime=new VBox();vbdateTime.setAlignment(Pos.CENTER);vbdateTime.setSpacing(0);
						Label DateLabel=new Label(LocalDate.now()+"");
						DateLabel.setStyle("-fx-text-fill: blue;-fx-font-size: 25");
						Label timeLabel=new Label();
						timeLabel.setStyle("-fx-text-fill: blue;-fx-font-size: 20");
						Timeline clock=new Timeline(new KeyFrame(Duration.ZERO, e -> {
							timeLabel.setText(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute()+":"+LocalDateTime.now().getSecond());
						}),new KeyFrame(Duration.seconds(1)));
						clock.setCycleCount(Animation.INDEFINITE);
						clock.play();
						vbdateTime.getChildren().addAll(DateLabel,timeLabel);
				
				
						HBox hbrootTopBottomCenter=new HBox();
									hbrootTopBottomCenter.setAlignment(Pos.CENTER);
									Text nomPharmacy=new Text(gestionContenu.infoStructure());
									nomPharmacy.setStyle("-fx-fill: blue;-fx-font-size: 25");
									VBox vbMailAdres=new VBox();vbMailAdres.setAlignment(Pos.CENTER_LEFT);
											Text mailText=new Text(" "+gestionContenu.infoMail());
											mailText.setStyle("-fx-fill: blue;-fx-font-size: 18");
											Text AdressText=new Text(" "+gestionContenu.infoAdress());
											AdressText.setStyle("-fx-fill: blue;-fx-font-size: 18");
											vbMailAdres.getChildren().addAll(mailText,AdressText);
											Label labTiret=new Label();labTiret.setPrefWidth(3);labTiret.setPrefHeight(45);
											labTiret.setStyle("-fx-background-color: red;");
						hbrootTopBottomCenter.getChildren().addAll(nomPharmacy,labTiret,vbMailAdres);
						
					//Animation 1
				        double sceneWidth1 = hbrootTopBottomCenter.getWidth();
				        double textWidth1 = nomPharmacy.getLayoutBounds().getWidth();
				       // Define the Durations
				        Duration startDuration1 = Duration.seconds(4);
				        Duration endDuration1 = Duration.seconds(5);
				        // Create the start and end Key Frames
				        KeyValue startKeyValue1 = new KeyValue(nomPharmacy.translateXProperty(), sceneWidth1);
				        KeyFrame startKeyFrame1 = new KeyFrame(startDuration1, startKeyValue1);
				        KeyValue endKeyValue1 = new KeyValue(nomPharmacy.translateXProperty(), -0.5*textWidth1);
				        KeyFrame endKeyFrame1 = new KeyFrame(endDuration1, endKeyValue1);
				        // Create a Timeline1
				        Timeline timeline1 = new Timeline(startKeyFrame1, endKeyFrame1);      
				        timeline1.setAutoReverse(true);
				        timeline1.setCycleCount(2);
				        timeline1.play();
						
				     // animation 2
				        double hbheight = hbrootTopBottomCenter.getHeight();
				        Duration startDuration2 = Duration.ZERO;
				        Duration endDuration2 = Duration.seconds(2);
				        
				        double textHeight = mailText.getLayoutBounds().getHeight();
				        // Create the start and end Key Frames
				        KeyValue startKeyValue2 = new KeyValue(mailText.translateYProperty(), hbheight);
				        KeyFrame startKeyFrame2 = new KeyFrame(startDuration2, startKeyValue2);
				        KeyValue endKeyValue2 = new KeyValue(mailText.translateYProperty(), -0.5*textHeight);
				        KeyFrame endKeyFrame2 = new KeyFrame(endDuration2, endKeyValue2);
				        // Create a Timeline1
				        Timeline timeline2 = new Timeline(startKeyFrame2, endKeyFrame2);   
				        timeline2.setAutoReverse(true);
				        timeline2.setRate(5);
				        timeline2.setCycleCount(2);
				        
						
				     // animation 3
				        double textHeight3 = AdressText.getLayoutBounds().getHeight();
				        // Create the start and end Key Frames
				        KeyValue startKeyValue3 = new KeyValue(AdressText.translateYProperty(), hbheight);
				        KeyFrame startKeyFrame3 = new KeyFrame(startDuration2, startKeyValue3);
				        KeyValue endKeyValue3 = new KeyValue(AdressText.translateYProperty(), 1.1*textHeight3);
				        KeyFrame endKeyFrame3 = new KeyFrame(endDuration2, endKeyValue3);
				        // Create a Timeline1
				        Timeline timeline3 = new Timeline(startKeyFrame3, endKeyFrame3);   
				        timeline3.setAutoReverse(true);
				        timeline3.setRate(5);
				        timeline3.setCycleCount(2);
				        
				        timeline1.setOnFinished(new EventHandler<ActionEvent>(){
				        	public void handle(ActionEvent arg0) 
				        	{
				        		timeline2.play();
				        		timeline3.play();
							}
				        });
				        
				      
							
				hBox1.getChildren().addAll(imgVew,oneVision);
				hBox2.getChildren().addAll(Newuser,close);
				hBox3.getChildren().addAll(Home,Vente,Stock,Commande,Statistique,Livraison);
				rootTop.setLeft(hBox1);
				HBox hbResult=new HBox();hbResult.setAlignment(Pos.CENTER_RIGHT);
				resultatAnalyse=new Label();
				resultatAnalyse.setStyle("-fx-text-fill: white;-fx-background-color: red;-fx-font-size: 22;");
				hbResult.getChildren().add(resultatAnalyse);
				rootTop.setCenter(resultatAnalyse);
				rootTop.setRight(hBox2);
				
				//Animation 4
				Screen screanWidth=Screen.getPrimary();
				double survWidth = screanWidth.getVisualBounds().getWidth();
		        double leGraphWidth = survWidth/2;
		       // Define the Durations
		        Duration startDurationE = Duration.seconds(3);
		        Duration endDuration1E = Duration.seconds(5);
		        // Create the start and end Key Frames
		        KeyValue startKeyValueE = new KeyValue(resultatAnalyse.translateXProperty(), survWidth);
		        KeyFrame startKeyFrameE = new KeyFrame(startDurationE, startKeyValueE);
		        KeyValue endKeyValueE = new KeyValue(resultatAnalyse.translateXProperty(), leGraphWidth);
		        KeyFrame endKeyFrameE = new KeyFrame(endDuration1E, endKeyValueE);
		        // Create a Timeline1
		        Timeline timelineE = new Timeline(startKeyFrameE, endKeyFrameE);  
		        timelineE.setCycleCount(Timeline.INDEFINITE);
		        timelineE.setRate(-0.5);
		        timelineE.setAutoReverse(true);
		        timelineE.play();
				
				rootTopBottom.setLeft(imgVew4);
				rootTopBottom.setCenter(hbrootTopBottomCenter);
				rootTopBottom.setRight(vbdateTime);
				rootTop.setBottom(rootTopBottom);
				
				HBox hboxHome=new HBox();hboxHome.setSpacing(8);hboxHome.setAlignment(Pos.TOP_CENTER);
				Accordion accordion=new Accordion();
				accordion.setPrefWidth(330);
				accordion.setMaxWidth(460);
				
				JFXButton Gestion=new JFXButton(" Bilan-Annalise");
				Gestion.setStyle("-fx-text-fill: white;-fx-font-size: 14;");
				Gestion.setGraphic(oeuil);
				
				BorderPane bpAcPopover=new BorderPane();
				bpAcPopover.setPrefHeight(350);
				bpAcPopover.setPrefWidth(450);
				
				PopOver popoverAccueil=new PopOver(bpAcPopover);
				popoverAccueil.setDetachable(true);
				popoverAccueil.setArrowLocation(ArrowLocation.TOP_CENTER);
				popoverAccueil.setTitle("Bilan de l'analyse");
				popoverAccueil.setDetachable(true);
				
				
				TitledPane titledPane1=new TitledPane();
				titledPane1.setText("Sur stock");
				titledPane1.setStyle("-fx-font-size: 16");
				titledPane1.setExpanded(false);
				titledPane1.setContent(listView1);
				TitledPane titledPane2=new TitledPane();
				titledPane2.setText("Sous stock");
				titledPane2.setStyle("-fx-font-size: 16");
				titledPane2.setExpanded(false);
				titledPane2.setContent(listView2);
				TitledPane titledPane3=new TitledPane();
				titledPane3.setText("Péremption dans 6 mois");
				titledPane3.setStyle("-fx-font-size: 16");
				titledPane3.setExpanded(false);
				titledPane3.setContent(listView3);
				TitledPane titledPane4=new TitledPane();
				titledPane4.setText("Commande urgente");
				titledPane4.setStyle("-fx-font-size: 16");
				titledPane4.setExpanded(false);
				titledPane4.setContent(listView4);
				TitledPane titledPane5=new TitledPane();
				titledPane5.setText("Produits en rupture");
				titledPane5.setStyle("-fx-font-size: 16");
				titledPane5.setExpanded(false);
				titledPane5.setContent(listView5);
				accordion.getPanes().addAll(titledPane1,titledPane2,titledPane3,titledPane4,titledPane5);
				bpAcPopover.setCenter(accordion);
				
				Gestion.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						popoverAccueil.setDetached(true);
						popoverAccueil.show(Gestion);
					}
					 
				 });
				
				
				hboxHome.getChildren().add(Gestion);
				VBox vboxHome=new VBox();vboxHome.setAlignment(Pos.CENTER);vboxHome.setSpacing(2);
				
				goodstate=new Text("Stock en bon état");
				goodstate.setStyle("-fx-fill: white;-fx-font-size: 40");
				
				vboxHome.getChildren().addAll(fitnessHome,goodstate);
				rootCEnter1.setCenter(vboxHome);
				rootCEnter1.setTop(hboxHome);
				
				// Get the Width of the Scene and the Text
				        double sceneWidth = rootCEnter1.getWidth();
				        double textWidth = fitnessHome.getLayoutBounds().getWidth();
				       // Define the Durations
				        Duration startDuration = Duration.ZERO;
				        Duration endDuration = Duration.seconds(3);
				        // Create the start and end Key Frames
				        KeyValue startKeyValue = new KeyValue(fitnessHome.translateXProperty(), sceneWidth);
				        KeyFrame startKeyFrame = new KeyFrame(startDuration, startKeyValue);
				        KeyValue endKeyValue = new KeyValue(fitnessHome.translateYProperty(), -0.5*textWidth);
				        KeyFrame endKeyFrame = new KeyFrame(endDuration, endKeyValue);
				        // Create a Timeline
				        Timeline timeline = new Timeline(startKeyFrame, endKeyFrame);      
				        timeline.setAutoReverse(true);
				        timeline.setCycleCount(Timeline.INDEFINITE);
				       // timeline.play();
				        
						       JFXButton printButon1=new JFXButton("Print");
						       printButon1.setOnAction(new EventHandler<ActionEvent>(){
									@Override
									public void handle(ActionEvent arg0) {
										// TODO Auto-generated method stub
										
										
										
										//WritableImage image = tableStockView.snapshot(new SnapshotParameters(), null);
									    
									    // TODO: probably use a file chooser here
									   /* File file = new File("C:/chart.png");
									    
									    try {
									        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
									    } catch (IOException e) {
									        // TODO: handle exception here
									    }*/
									    
									    
									}
									 
								 });
						       
						      
				 
							
							 
							// statuBar.progressProperty().bind(new Task().progressProperty());
				
							root.setTop(rootTop);
							root.setLeft(hBox3);
							root.setBottom(statuBar);
							root.setStyle("-fx-background-color: #007ea7");
							root.setCenter(rootCEnter1);
		
		
		/**
		 * The Action Event
		 *
		 */
							 Home.setOnAction(new EventHandler<ActionEvent>(){
								@Override
								public void handle(ActionEvent arg0) {
									// TODO Auto-generated method stub
									root.setCenter(rootCEnter1);
									Home.setStyle("-fx-background-color: #03a9f4;-fx-text-fill: white;-fx-font-size: 20;");
									Livraison.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
									Commande.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
									Statistique.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
									Stock.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
									Vente.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
									
									Home.setGraphic(navButon1);
									if(logControl.VenteAlow())Vente.setGraphic(navButon2);
									Stock.setGraphic(navButon3);
									if(logControl.FaireCommandeAlow())Commande.setGraphic(navButon5);
									if(logControl.VoirStatistiqAlow())Statistique.setGraphic(navButon6);
								
								}
								 
							 });
							 
							 if(!logControl.VenteAlow())
							 {
								 Vente.setGraphic(new ImageView(new Image("icon/lockRed1.png")));
								 Vente.setOnAction(new EventHandler<ActionEvent>(){
										@Override
										public void handle(ActionEvent arg0) {
											new Alerter(" Action non autorisée",1);
										}});
							 }else{
							 Vente.setOnAction(new EventHandler<ActionEvent>(){
									@Override
									public void handle(ActionEvent arg0) {
										// TODO Auto-generated method stub
										root.setCenter(rootCEnter2);
										Vente.setStyle("-fx-background-color: #009688;-fx-text-fill: white;-fx-font-size: 20;");
										
										Home.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Livraison.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Commande.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Statistique.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Stock.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Home.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										
										Home.setGraphic(new ImageView(new Image("icon/home24.png")));
										Vente.setGraphic(new ImageView(new Image("icon/vente24W.png")));
										Stock.setGraphic(navButon3);
										if(logControl.FaireCommandeAlow())Commande.setGraphic(navButon5);
										if(logControl.VoirStatistiqAlow())Statistique.setGraphic(navButon6);
										
									}
									 
								 });
							 }
							 
							 
							 Stock.setOnAction(new EventHandler<ActionEvent>(){
									@Override
									public void handle(ActionEvent arg0) {
										// TODO Auto-generated method stub
										root.setCenter(rootCEnter3);
										Stock.setStyle("-fx-background-color: #009688;-fx-text-fill: white;-fx-font-size: 20;");
										Home.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Vente.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Livraison.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Commande.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Statistique.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										
										Home.setGraphic(new ImageView(new Image("icon/home24.png")));
										if(logControl.VenteAlow())Vente.setGraphic(navButon2);
										Stock.setGraphic(new ImageView(new Image("icon/stockwhite.png")));
										if(logControl.FaireCommandeAlow())Commande.setGraphic(navButon5);
										if(logControl.VoirStatistiqAlow())Statistique.setGraphic(navButon6);
									}
								 });
            
							 if(!logControl.FaireLivraisonAlow())
							 {
								 Livraison.setGraphic(new ImageView(new Image("icon/lockRed1.png")));
								 Livraison.setOnAction(new EventHandler<ActionEvent>(){
										@Override
										public void handle(ActionEvent arg0) {
											new Alerter(" Action non autorisée",1);
										}});
							 }else{
							 Livraison.setOnAction(new EventHandler<ActionEvent>(){
									@Override
									public void handle(ActionEvent arg0) {
										// TODO Auto-generated method stub
										root.setCenter(rootCEnter4);
										Livraison.setStyle("-fx-background-color: #444444;-fx-text-fill: white;-fx-font-size: 20;");
										
										Home.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Vente.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Commande.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Statistique.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Stock.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										
										Home.setGraphic(new ImageView(new Image("icon/home24.png")));
										Stock.setGraphic(navButon3);
										if(logControl.VenteAlow())Vente.setGraphic(navButon2);
										if(logControl.FaireCommandeAlow())Commande.setGraphic(navButon5);
										if(logControl.VoirStatistiqAlow())Statistique.setGraphic(navButon6);
										
										
										
									}
									 
								 });
							 }
							 
							 if(!logControl.FaireCommandeAlow())
							 {
								 Commande.setGraphic(new ImageView(new Image("icon/lockRed1.png")));
								 Commande.setOnAction(new EventHandler<ActionEvent>(){
										@Override
										public void handle(ActionEvent arg0) {
											new Alerter(" Action non autorisée",1);
										}});
							 }else{
							 Commande.setOnAction(new EventHandler<ActionEvent>(){
									@Override
									public void handle(ActionEvent arg0) {
										// TODO Auto-generated method stub
										root.setCenter(rootCEnter5);
										Commande.setStyle("-fx-background-color: green;-fx-text-fill: white;-fx-font-size: 20;");
										Home.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Stock.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Vente.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Livraison.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										Statistique.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
										
										Home.setGraphic(new ImageView(new Image("icon/home24.png")));
										Stock.setGraphic(navButon3);
										if(logControl.VenteAlow())Vente.setGraphic(navButon2);
										if(logControl.VoirStatistiqAlow())Statistique.setGraphic(navButon6);
										Commande.setGraphic(new ImageView(new Image("icon/commandeW.png")));
									}
									 
								 });
							 }
							 
							 if(!logControl.VoirStatistiqAlow())
								 {
								 	 Statistique.setGraphic(new ImageView(new Image("icon/lockRed1.png")));
									 Statistique.setOnAction(new EventHandler<ActionEvent>(){
											@Override
											public void handle(ActionEvent arg0) {
												new Alerter(" Action non autorisée",1);
											}});
								 }else{
									 Statistique.setOnAction(new EventHandler<ActionEvent>(){
											@Override
											public void handle(ActionEvent arg0) {
												// TODO Auto-generated method stub
												root.setCenter(rootCEnter6);
												Statistique.setStyle("-fx-background-color: #009688;-fx-text-fill: white;-fx-font-size: 20;");
												
												Home.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
												Stock.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
												Vente.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
												Livraison.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
												Commande.setStyle("-fx-background-color: #E0E0E0;-fx-font-size: 20;");
												
												Home.setGraphic(new ImageView(new Image("icon/home24.png")));
												Stock.setGraphic(navButon3);
												if(logControl.VenteAlow())Vente.setGraphic(navButon2);
												if(logControl.FaireCommandeAlow())Commande.setGraphic(navButon5);
												Statistique.setGraphic(new ImageView(new Image("icon/chartW.png")));
											}
											 
										 });
								 	}
							
		
		try {
			FadeTransition fade=new FadeTransition(Duration.seconds(4),root);
			fade.setFromValue(0.1);
			fade.setToValue(1);
			fade.play();
			
			} catch(Exception e) {
					e.printStackTrace();
				}
		
		svc.restart();
		//for splash
		return null;
			}
		};

		
		rootAccueille.setPadding(new Insets(1,5,0,5));
		rootAccueille.setStyle("-fx-background-color: #004d40");
		VBox left=new VBox();
				left.setAlignment(Pos.CENTER);
				Image imgAcc=null;
				Image imgAcc3=null;
				Image imgAcc4=null;
				
				try{  imgAcc=new Image("icon/healthcare1.png");
					  imgAcc3=new Image("icon/user18.png");
					  imgAcc4=new Image("icon/lock27.png");
				}catch(Exception e){}
				ImageView imageAccuel=new ImageView(imgAcc);
				ImageView imageAccuel3=new ImageView(imgAcc3);
				ImageView imageAccuel4=new ImageView(imgAcc4);
				
				JFXButton imageAccuel1=new JFXButton();
				try{imageAccuel1.setGraphic(new ImageView(new Image("icon/pga.png")));}catch(Exception ex){}
				
				Text welcom=new Text("WellCom \n to");
				welcom.setTextAlignment(TextAlignment.CENTER);
				welcom.setStyle("-fx-fill: white;-fx-font-size: 15;");
				left.getChildren().addAll(imageAccuel,welcom,imageAccuel1);
				
				VBox hbAcc=new VBox();hbAcc.setSpacing(7);
					hbAcc.getChildren().addAll(imageAccuel3,imageAccuel4);
				VBox hbAcc1=new VBox();hbAcc1.setSpacing(7);
						JFXTextField user=new JFXTextField();
							user.setUnFocusColor(Paint.valueOf("#9e9e9e"));
							user.setPromptText("Utilisateur");
							user.setFocusColor(Paint.valueOf("white"));
							user.setStyle("-fx-text-fill: white;-fx-font-size: 14;-fx-prompt-text-fill: white;");
							
						JFXPasswordField passWord=new JFXPasswordField();
							passWord.setPromptText("Mot de pass");
							passWord.setUnFocusColor(Paint.valueOf("#9e9e9e"));
							passWord.setFocusColor(Paint.valueOf("white"));
							passWord.setStyle("-fx-text-fill: white;-fx-font-size: 14;-fx-prompt-text-fill: white;");
						hbAcc1.getChildren().addAll(user,passWord);
				HBox hbAcceuil=new HBox();hbAcceuil.setSpacing(5);hbAcceuil.setPadding(new Insets(5));
				hbAcceuil.getChildren().addAll(hbAcc,hbAcc1);
				
				JFXButton LogButon=new JFXButton();LogButon.setStyle("-fx-text-fill: white;-fx-font-size: 12;");
				ImageView imageAccuel2=new ImageView();
				try{
					LogButon.setGraphic(new ImageView(new Image("icon/next1.png")));
					imageAccuel2.setImage(new Image("icon/lock126.png"));
					
				}catch(Exception e){}
				
				TextField textInform=new TextField();
				LogButon.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						logControl=new LogAndConectGestion();
				 		if(logControl.userExiste(url, user.getText(), passWord.getText()))
						{
							showSplash(primaryStage, friendTask,() -> showMainStage(friendTask.valueProperty()) );
					       new Thread(friendTask).start();
					       gestionContenu.AddLineToHistoriqueTable(user.getText());
					        //svc.restart();
						}else{
								new Alerter("Utilisateur ou Mot de pass incorrect");
							}
						
					}
				 });
				textInform.setText(Information);
				textInform.setStyle("-fx-background-color: #004d40;-fx-text-fill: yellow");
		VBox right=new VBox();right.setPrefWidth(200);right.setSpacing(20);
		right.setStyle("-fx-border-width: 0 0 0 2;-fx-border-color: #9e9e9e;");
		right.setAlignment(Pos.CENTER);
		right.getChildren().addAll(imageAccuel2,hbAcceuil,LogButon,textInform);
		right.setAlignment(Pos.TOP_CENTER);
		
		
		FadeTransition ft = new FadeTransition(Duration.millis(2500), imageAccuel);
	     ft.setFromValue(1.0);
	     ft.setToValue(0.2);
	     ft.setCycleCount(Timeline.INDEFINITE);
	     ft.setAutoReverse(true);
	     ft.play();
	     
	    /* RotateTransition rt = new RotateTransition();
	      rt.setNode(imageAccuel2);
	      rt.setFromAngle(90);
	      rt.setToAngle(45);
	      rt.setInterpolator(Interpolator.LINEAR);
	      rt.setCycleCount(Timeline.INDEFINITE);
	      rt.setDuration(new Duration(3000));
	      rt.play();*/
	     BorderPane AccueilTop=new BorderPane();
	     Accordion accordionAccuiel=new Accordion();
				JFXButton menuAccueil=new JFXButton();
				try{menuAccueil.setGraphic(new ImageView(new Image("icon/menu18.png")));}catch(Exception ex){}
				AccueilTop.setLeft(menuAccueil);
				BorderPane bpAcPopover=new BorderPane();
				bpAcPopover.setPrefHeight(80);
				bpAcPopover.setPrefWidth(200);
				
				PopOver popoverAccueil=new PopOver(bpAcPopover);
				popoverAccueil.setArrowLocation(ArrowLocation.TOP_LEFT);
				popoverAccueil.setDetachable(false);
				
				VBox vbAc1=new VBox();vbAc1.setAlignment(Pos.CENTER);vbAc1.setSpacing(15);
				VBox vbAc2=new VBox();vbAc2.setAlignment(Pos.CENTER);vbAc2.setSpacing(15);
				VBox vbAc3=new VBox();vbAc3.setAlignment(Pos.CENTER);vbAc3.setSpacing(15);
				
				JFXTextField ipText=new JFXTextField();
				ipText.setPromptText("IP adress");
				JFXButton ValidIP=new JFXButton();
				try{ValidIP.setGraphic(new ImageView(new Image("icon/ok.png")));}catch(Exception ex){}
				ValidIP.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						 FileWriter file;
						 File directory=new File("C:\\IPadress");
						 try {
								 if (!directory.exists())
							  		{
									 	directory.mkdir();
							  		} 
								file=new FileWriter(new File("C:\\IPadress\\password.dat"));
								PrintWriter fichier=new PrintWriter(file);
								fichier.print(ipText.getText());
								fichier.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				 });
				vbAc1.getChildren().addAll(ipText,ValidIP);
				
				JFXTextField PassText=new JFXTextField();
				PassText.setPromptText("Password");
				JFXButton ValidDatabase=new JFXButton();
				try{ValidDatabase.setGraphic(new ImageView(new Image("icon/ok.png")));}catch(Exception ex){}
				vbAc2.getChildren().addAll(PassText,ValidDatabase);
				
				ValidDatabase.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) 
					{
						if(serveur==true){
							SetDatabaseAndAddPC addPc=new SetDatabaseAndAddPC();
							addPc.setSDatabase();
						}else{
							Alert alert=new Alert(AlertType.INFORMATION);
					 		alert.setTitle("Base de donnée");
					 		alert.setHeaderText("INFORMATION");
					 		alert.setContentText("Only server side allow");
					 		alert.show();
						}
					}
					 
				 });
				JFXTextField addPC=new JFXTextField();
				addPC.setPromptText("IP de la machine cliente");
				JFXButton ValidaddPC=new JFXButton();
				try{ValidaddPC.setGraphic(new ImageView(new Image("icon/ok.png")));}catch(Exception ex){}
				vbAc3.getChildren().addAll(addPC,ValidaddPC);
				ValidaddPC.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) 
					{
						if(serveur==true){
							SetDatabaseAndAddPC addPc=new SetDatabaseAndAddPC();
							addPc.addPC(url, addPC.getText());
						}else{
							Alert alert=new Alert(AlertType.INFORMATION);
					 		alert.setTitle("Base de donnée");
					 		alert.setHeaderText("INFORMATION");
					 		alert.setContentText("Only server side allow");
					 		alert.show();
						}
					}
					 
				 });
				
				
				JFXButton close=new JFXButton();
				try{close.setGraphic(new ImageView(new Image("icon/close2.png")));}catch(Exception ex){}
				close.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						System.exit(1);
					}
					 
				 });
				AccueilTop.setRight(close);
				
				TitledPane titledPane1=new TitledPane();
				titledPane1.setText("Set server IP");
				titledPane1.setStyle("-fx-font-size: 16");
				titledPane1.setExpanded(false);
				titledPane1.setContent(vbAc1);
				TitledPane titledPane2=new TitledPane();
				titledPane2.setText("Set database");
				titledPane2.setStyle("-fx-font-size: 16");
				titledPane2.setExpanded(false);
				titledPane2.setContent(vbAc2);
				TitledPane titledPane3=new TitledPane();
				titledPane3.setText("Ajouter un PC");
				titledPane3.setStyle("-fx-font-size: 16");
				titledPane3.setExpanded(false);
				titledPane3.setContent(vbAc3);
				
				accordionAccuiel.getPanes().addAll(titledPane1,titledPane2,titledPane3);
				bpAcPopover.setCenter(accordionAccuiel);
				
				menuAccueil.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						popoverAccueil.show(menuAccueil);
					}
					 
				 });
	     		
	    rootAccueille.setTop(AccueilTop);
		rootAccueille.setCenter(left);
		rootAccueille.setRight(right);
		Scene scene1 = new Scene(rootAccueille,600,450);
		
		firstStage.setScene(scene1);
		firstStage.initStyle(StageStyle.UNDECORATED);
		firstStage.setAlwaysOnTop(true);
		firstStage.show();
		
		
		//showSplash(primaryStage, friendTask,() -> showMainStage(friendTask.valueProperty()) );
        //new Thread(friendTask).start();
		
		Scene splashScene = new Scene(hbPI,100,100);
		progressStage.setScene(splashScene);
		progressStage.initOwner(firstStage);
		progressStage.setAlwaysOnTop(true);
		
	}
public int nombrePositif(String string)
	{
	int nombre=0;
		try{
			 nombre=Integer.parseInt(string);
			 if(nombre<0)
			 	{
			 		new Alerter("Nombre inferieur a zéro");
			 		nombre=0;
			 	}
			}catch(NumberFormatException ex){
				new Alerter("Nombre Invalide");
			}
		return nombre;
	}
public void changed(ObservableValue<? extends Toggle> Observable,Toggle oldBut,Toggle newBut)
	{
		String selectedLabel="none";
		if(newBut!=null)
		{
			
		}
		
	}

private void pageSetup(Node node, Stage owner)
 {
     // Create the PrinterJob
	  Node bp=node;
	  Printer printer = Printer.getDefaultPrinter();
	 PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
      double scaleX = 0.5;//pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
     double scaleY = 0.5;//pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
      bp.getTransforms().add(new Scale(scaleX, scaleY));
     PrinterJob job = PrinterJob.createPrinterJob();
    

     if (job == null)
     {
         return;
     }
     // Show the page setup dialog
     boolean proceed = job.showPageSetupDialog(owner);
     if (proceed)
     {
         print(job, bp);
     }
 }

private void print(PrinterJob job, Node node)
   {
       // Set the Job Status Message
       //jobStatus.textProperty().bind(job.jobStatusProperty().asString());
       // Print the node
       boolean printed = job.printPage(node);
       if (printed)
       {
           job.endJob();
       }
   }

//for splash


private void showMainStage(ReadOnlyObjectProperty<ObservableList<String>> friends)
{
	Screen screan=Screen.getPrimary();
	try{
	Scene scene = new Scene(root,screan.getVisualBounds().getWidth(),screan.getVisualBounds().getHeight());
	scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());
	MainStage = new Stage();
	MainStage.setScene(scene);
	MainStage.initStyle(StageStyle.UNDECORATED);
	MainStage.setFullScreen(true);
	MainStage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
}

private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler ) 
{
	//loadProgress
	rootAccueille.setBottom(splashLayout);
    progressText.textProperty().bind(task.messageProperty());
    loadProgress.progressProperty().bind(task.progressProperty());
    task.stateProperty().addListener((observableValue, oldState, newState) -> {
        if (newState == Worker.State.SUCCEEDED) {
            loadProgress.progressProperty().unbind();
            loadProgress.setProgress(1);
            FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), rootAccueille);
            fadeSplash.setFromValue(1.0);
            fadeSplash.setToValue(0.0);
            fadeSplash.setOnFinished(actionEvent -> firstStage.hide());
            fadeSplash.play();
           
            initCompletionHandler.complete();
        } // todo add code to gracefully handle other task states.
    });

}


public void showWorking(Service<Void> serviceQuery) 
{
	
		progressStage.show();
		loadProgressTop.progressProperty().bind(serviceQuery.progressProperty());
		serviceQuery.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
            	loadProgressTop.progressProperty().unbind();
            	loadProgressTop.setProgress(1);
            	progressStage.hide();
            } // todo add code to gracefully handle other task states.
        });

}


private void showValiCompletAlert(ReadOnlyObjectProperty<ObservableList<String>> friends)
{
	
	new AlerterSuccesWorning().Succes("Validé avec Succès");
	ComboDCI.setValue(null);
}

private void showValidProgres(Task<?> task, InitCompletionHandler2 initCompletionHandler ) 
{
	
	
	
    progressText.textProperty().bind(task.messageProperty());
    statuBar.progressProperty().bind(task.progressProperty());
    task.stateProperty().addListener((observableValue, oldState, newState) -> {
        if (newState == Worker.State.SUCCEEDED) {
        	statuBar.progressProperty().unbind();
        	statuBar.setProgress(1);
            initCompletionHandler.complete();
        } // todo add code to gracefully handle other task states.
    });
    
}

public interface InitCompletionHandler {
    void complete();
}
public interface InitCompletionHandler2 {
    void complete();
}
public boolean testEgalite(int para1, int para2){
	if(para1==para2)
		return true;
	else
		return false;
}
public void informDialog(String message){
	Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("INFORMATION");
		alert.setHeaderText(message);
		alert.setContentText("Consulter l'accueille pour plus de détails");
		alert.show();
}

//*********************
public BorderPane borderPaneUser(){
	BorderPane borederpaneVente=new BorderPane();borederpaneVente.setStyle("-fx-background-color: #444444");
	borederpaneVente.setPadding(new Insets(5));
	borederpaneVente.setMinHeight(750);
	borederpaneVente.setPrefHeight(750);
	borederpaneVente.setPrefWidth(800);
	borederpaneVente.setMaxWidth(800);
	VBox vbUser=new VBox();vbUser.setAlignment(Pos.CENTER);vbUser.setSpacing(10);
			Image userImage=null;
			try{ userImage=new Image("icon/userimage.png");}catch(Exception e){}
			ImageView imageView=new ImageView(userImage);
	
			JFXComboBox<String> names=new JFXComboBox<String>();
			names.setPadding(new Insets(0));
			names.setPrefWidth(200);
			names.setMaxWidth(200);
			names.setEditable(true);
			names.setPromptText("Nom et Prénom");
			names.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
			names.setFocusColor(Paint.valueOf("white"));
			names.setUnFocusColor(Paint.valueOf("#B7B7B7"));
			names.setItems(gestionContenu.ListeUser());
			TextFields.bindAutoCompletion(names.getEditor(),(Collection<String>) names.getItems());
			
			
			JFXTextField pass=new JFXTextField();
			pass.setPadding(new Insets(0));
			pass.setPrefWidth(200);
			pass.setMaxWidth(200);	
			pass.setPromptText("Pass");
			pass.setAlignment(Pos.BASELINE_LEFT);
			pass.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
			pass.setFocusColor(Paint.valueOf("white"));
			pass.setUnFocusColor(Paint.valueOf("#B7B7B7"));
			
			JFXTextField nombreDejour=new JFXTextField();
			nombreDejour.setPadding(new Insets(0));
			nombreDejour.setPromptText("nombre de jours de travail");
			nombreDejour.setPrefWidth(200);
			nombreDejour.setMaxWidth(200);
			nombreDejour.setAlignment(Pos.BASELINE_LEFT);
			nombreDejour.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
			nombreDejour.setFocusColor(Paint.valueOf("white"));
			nombreDejour.setUnFocusColor(Paint.valueOf("#B7B7B7"));
			
			JFXTextField salaire=new JFXTextField();
			salaire.setPadding(new Insets(0));
			salaire.setPromptText("Salaire");
			salaire.setPrefWidth(200);
			salaire.setMaxWidth(200);
			salaire.setAlignment(Pos.BASELINE_LEFT);
			salaire.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
			salaire.setFocusColor(Paint.valueOf("white"));
			salaire.setUnFocusColor(Paint.valueOf("#B7B7B7"));
			
			JFXTextField detail=new JFXTextField();
			detail.setPadding(new Insets(0));
			detail.setPrefWidth(200); 
			detail.setMaxWidth(200);
			detail.setPromptText("Détails");
			detail.setAlignment(Pos.BASELINE_LEFT);
			detail.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
			detail.setFocusColor(Paint.valueOf("white"));
			detail.setUnFocusColor(Paint.valueOf("#B7B7B7"));
			
			vbUser.getChildren().addAll(imageView,names,pass,nombreDejour,salaire,detail);
			borederpaneVente.setTop(vbUser);
	HBox user2=new HBox();user2.setSpacing(6);user2.setAlignment(Pos.BASELINE_LEFT);
	VBox userSous1=new VBox();
			JFXToggleButton permit1=new JFXToggleButton();
		 	permit1.setText("Vente de produits");
		 	permit1.setStyle("-fx-text-fill: white;"); 
		    JFXToggleButton permit2=new JFXToggleButton();
		    permit2.setText("Ajout de produits ");
		    permit2.setStyle("-fx-text-fill: white;");
		    JFXToggleButton permit3=new JFXToggleButton();
		    permit3.setText("Faire inventaire ");
		    permit3.setStyle("-fx-text-fill: white;");
		    JFXToggleButton permit4=new JFXToggleButton();
		    permit4.setText("Editer un produits ");
		    permit4.setStyle("-fx-text-fill: white;");
		    userSous1.getChildren().addAll(permit1,permit2,permit3,permit4);
	 VBox userSous2=new VBox();
		    JFXToggleButton permit5=new JFXToggleButton();
		    permit5.setText("Approvisuin du stock ");
		    permit5.setStyle("-fx-text-fill: white;");
		    JFXToggleButton permit6=new JFXToggleButton();
		    permit6.setText("Supression de produit ");
		    permit6.setStyle("-fx-text-fill: white;");
		    JFXToggleButton permit7=new JFXToggleButton();
		    permit7.setText("Faire commande ");
		    permit7.setStyle("-fx-text-fill: white;");
		    JFXToggleButton permit8=new JFXToggleButton();
		    permit8.setText("Accès au parametre ");
		    permit8.setStyle("-fx-text-fill: white;");
		    userSous2.getChildren().addAll(permit5,permit6,permit7,permit8);
	VBox userSous3=new VBox();
		    JFXToggleButton permit9=new JFXToggleButton();
		    permit9.setText("Accès au statistique ");
		    permit9.setStyle("-fx-text-fill: white;");
		    JFXToggleButton permit10=new JFXToggleButton();
		    permit10.setText("retourner un produit");
		    permit10.setStyle("-fx-text-fill: white;");
		    JFXToggleButton permit11=new JFXToggleButton();
		    permit11.setText("Ajouter un utilisateur ");
		    permit11.setStyle("-fx-text-fill: white;");
		    JFXToggleButton permit12=new JFXToggleButton();
		    permit12.setText("Ajouter un ordinateur ");
		    permit12.setStyle("-fx-text-fill: white;");
		    userSous3.getChildren().addAll(permit9,permit10,permit11,permit12);
		    user2.getChildren().addAll(userSous1,userSous2,userSous3);
		    borederpaneVente.setCenter(user2);
	VBox user3=new VBox();user3.setSpacing(5);user3.setAlignment(Pos.CENTER);
	JFXButton save=new JFXButton("Enregistrer");
				save.setStyle("-fx-background-color: black;-fx-text-fill: white;-fx-font-size: 14;");
	JFXButton edit=new JFXButton("Editer utilisateur");
				edit.setStyle("-fx-background-color: black;-fx-text-fill: white;-fx-font-size: 14;");
	JFXButton liste=new JFXButton("Liste des utilisateurs");
				liste.setStyle("-fx-background-color: black;-fx-text-fill: white;-fx-font-size: 14;");
	JFXButton suprimer=new JFXButton("Suprimer un utilisateur");
				suprimer.setStyle("-fx-background-color: black;-fx-text-fill: white;-fx-font-size: 14;");
				JFXButton suprimeInPop=new JFXButton("Suprimer");
				suprimeInPop.setStyle("-fx-background-color: white;-fx-text-fill: black;-fx-font-size: 12;");
	
				save.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {// 
							gestionContenu.saveNewUser(names.getValue(), pass.getText(), nombreDejour.getText(), salaire.getText(),
									detail.getText(), permit1.isSelected(), permit2.isSelected(), permit3.isSelected(), permit4.isSelected(), 
									permit5.isSelected(), permit6.isSelected(), permit7.isSelected(), permit8.isSelected(),
									permit9.isSelected(), permit10.isSelected(), permit11.isSelected(), permit12.isSelected());
					}
					 
				 });
			//******************the POp POVER************
				edit.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						
						gestionContenu.updateUser(names.getItems().get(names.getSelectionModel().getSelectedIndex()), pass.getText(), nombreDejour.getText(), salaire.getText(),
								detail.getText(), permit1.isSelected(), permit2.isSelected(), permit3.isSelected(), permit4.isSelected(), 
								permit5.isSelected(), permit6.isSelected(), permit7.isSelected(), permit8.isSelected(),
								permit9.isSelected(), permit10.isSelected(), permit11.isSelected(), permit12.isSelected());
						}
					 
				 });
				
				VBox hbSuprimer=new VBox();hbSuprimer.setStyle("-fx-background-color: black;");
				hbSuprimer.setSpacing(4);
				JFXComboBox<String> SuprimCB=new JFXComboBox<String>();
				SuprimCB.setFocusColor(Paint.valueOf("#FFFFFF"));
				SuprimCB.setUnFocusColor(Paint.valueOf("#eeeeee"));
				SuprimCB.setEditable(true);
				SuprimCB.setPromptText("Nom et prenom");
				SuprimCB.setMaxWidth(250);
				SuprimCB.setPrefWidth(250);
				SuprimCB.setItems(gestionContenu.ListeUser());
				TextFields.bindAutoCompletion(SuprimCB.getEditor(),(Collection<String>) SuprimCB.getItems());
				hbSuprimer.getChildren().addAll(SuprimCB,suprimeInPop);
				
				PopOver popoverSuprimer=new PopOver(hbSuprimer);
				popoverSuprimer.setDetached(false);
				popoverSuprimer.setArrowLocation(ArrowLocation.RIGHT_TOP);
				popoverSuprimer.setTitle("Suprimer un Utilisateur");
				
				suprimer.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						popoverSuprimer.show(suprimer);
					}
					 
				 });
				suprimeInPop.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						gestionContenu.deleteUser(SuprimCB.getValue());
					}
					 
				 });
				//***********
				BorderPane hbList=new BorderPane();
				hbList.setPrefHeight(300);
				hbList.setPrefWidth(500);
				ListView<String> listeV=new ListView<String>();
				hbList.setCenter(listeV);
				
				PopOver popoverList=new PopOver(hbList);
				popoverList.setArrowLocation(ArrowLocation.BOTTOM_RIGHT);
				popoverList.setTitle("Liste des utilisateurs");
				
				liste.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						listeV.setItems(gestionContenu.ListeUser());
						popoverList.show(liste);
					}
					 
				 });
				
				
				
				
				
				
				
				
				
				
				
				
				
			user3.getChildren().addAll(save,edit,liste,suprimer);
			borederpaneVente.setBottom(user3);
	return borederpaneVente;
}
public BorderPane borderPaneVente(){
	BorderPane borederpaneVente=new BorderPane();
	
	 TabPane tabPaneVente=new 	TabPane();
     tabPaneVente.setPadding(new Insets(-5));
		        Tab vente=new Tab("Vente de produits");
		        		vente.setClosable(false);
		        		BorderPane bPaneVente=new BorderPane();
		        		bPaneVente.setStyle("-fx-background-color: #009688");
		        		bPaneVente.setPadding(new Insets(20));
		        				BorderPane forHboxVente=new BorderPane();
		        				HBox hboxVente=new HBox(); hboxVente.setSpacing(20);
		        				hboxVente.setPadding(new Insets(0 ,0 ,5 ,0));
		        											ComboDCI.setFocusColor(Paint.valueOf("#FFFFFF"));
		        											ComboDCI.setUnFocusColor(Paint.valueOf("#eeeeee"));
		        											ComboDCI.setEditable(true);
		        											ComboDCI.setPromptText(" D.C.I");
		        											ComboDCI.setMaxWidth(500);
		        											ComboDCI.setPrefWidth(500);
		        											ComboDCI.setPrefHeight(30);
		        											dataDCI=gestionContenu.DCIVenteContenu();
		        											ComboDCI.setItems(gestionContenu.DCIVenteContenu());
		        											TextFields.bindAutoCompletion(ComboDCI.getEditor(),(Collection<String>) ComboDCI.getItems());
		        							
		        					Text prixTotal=new Text();
		        					prixTotal.setStyle("-fx-fill: white;-fx-font-size: 30;");
		        				hboxVente.getChildren().addAll(ComboDCI);
		        				forHboxVente.setLeft(hboxVente);
		        				forHboxVente.setRight(prixTotal);
		        				
		        				TableView<produitAvendre> tableViewVente=new TableView<produitAvendre>();
		        				TableView<produitVenteJour> TableVenteJour=new TableView<produitVenteJour>();
		        				tableViewVente.setItems(data);
		        				tableViewVente.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		        				tableViewVente.setEditable(true);
		        				
		        				 
		        				ComboDCI.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		        					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        					
		        						if(!testEgalite(oldIndextCV,ComboDCI.getSelectionModel().getSelectedIndex())){

			        						if(ComboDCI.getValue()!=null)
			        							{
			        								sum=0;
			        								enCourdeVente=false;
			        								
			        								for(int i=0;i<data.size();i++)
					        						{
					        							if(data.get(i).getDCI().equals(ComboDCI.getValue()))
							        							{
							        								enCourdeVente=true;
							        								break;
							        							}
					        						}
			        								
			        								if(enCourdeVente==false)
			        								{
			        								
			        								try{
			        									connection = DriverManager.getConnection(url,"balamou","jean");
			        									pstmt= connection.prepareStatement("SELECT DCI, Emplacement, Unite, Quantite, PrixVente FROM mesproduits WHERE DCI=?");
			        									pstmt.setString(1, ComboDCI.getValue());
			        									ResultSet rs2 = pstmt.executeQuery();
			        									while(rs2.next())
			        						      		{
			        										prodDevente=new produitAvendre(ComboDCI.getValue(),rs2.getString("Unite"),rs2.getLong("Quantite"),rs2.getLong("PrixVente"),"1","Un particulier",rs2.getLong("PrixVente"),rs2.getString("Emplacement"));
			        										
			        						      		}
			        										//prodDevente=new produitAvendre("dci","FD","Uniter","StockRestant","200","50","Destina");
			        									if(prodDevente.getSTOCKrestant()!=0)
			        										{	
			        											data.add(prodDevente);
			        											for(int i=0;i<data.size();i++)
						        								{
						        									sum=sum+data.get(i).getPRIX()*nombrePositif(data.get(i).getQUANTITE());
						        								}
						        								prixTotal.setText(""+sum+" FG");
						        								ComboDCI.setValue(null);
						        								
			        										}else
			        											new AlerterSuccesWorning().Worning("Ce produit est Fini du Stock");
			        									
			        									rs2.close();
			        								}catch(SQLException e){
			        									new Alerter(e.getMessage());
			        								}finally{
					        							try {
					        								if(connection!=null)
					        									connection.close();
					        								if(pstmt!=null)
					        									pstmt.close();
					        							} catch (SQLException e) {
					        								//e.printStackTrace();
					        								new Alerter("Couldn't close pstmt");
					        							}
					        						}
			        								
			        								}else
			        									new AlerterSuccesWorning().Worning("Produit en cours de vente");
			        								
			        							}
			        					
										
		        							oldIndextCV=ComboDCI.getSelectionModel().getSelectedIndex();
		        						//	ComboDCI.getSelectionModel().clearSelection(curentIndex);
		        							
		        						}
		        						
		        					}
		        				});
		        				
		        			
		     	
				        		
		        	//******************************creation tableView**************************
		        				
		        				
		        				TableColumn<produitAvendre,String> TventeCol1=new TableColumn<produitAvendre, String>();
		        				TventeCol1.setText("D.C.I");
		        				TventeCol1.setPrefWidth(450);
		        				TventeCol1.setCellValueFactory(new PropertyValueFactory<produitAvendre,String>("DCI"));
		        				
		        				TableColumn<produitAvendre,String> TventeCol3=new TableColumn<produitAvendre, String>();
		        				TventeCol3.setText("Unité");
		        				TventeCol3.setCellValueFactory(new PropertyValueFactory<produitAvendre,String>("UNIT"));
		        				
		        				TableColumn<produitAvendre,Long> TventeCol4=new TableColumn<produitAvendre, Long>();
		        				TventeCol4.setText("Stock");
		        				TventeCol4.setCellValueFactory(new PropertyValueFactory<produitAvendre,Long>("STOCKrestant"));
		        				TventeCol4.setStyle("-fx-alignment: center");
		        				
		        				TableColumn<produitAvendre,Long> TventeCol5=new TableColumn<produitAvendre, Long>();
		        				TventeCol5.setText("Prix");
		        				TventeCol5.setPrefWidth(100);
		        				TventeCol5.setCellValueFactory(new PropertyValueFactory<produitAvendre,Long>("PRIX"));
		        				TventeCol5.setStyle("-fx-alignment: center");
		        				
		     Callback<TableColumn<produitAvendre, String>, TableCell<produitAvendre, String>> cellFactoryString = ( TableColumn<produitAvendre, String> p) -> new EditingCellString();
		        				 TableColumn<produitAvendre,String> TventeCol6=new TableColumn<produitAvendre, String>();
		        	             TventeCol6.setText("Q.Vendre");
		        	             TventeCol6.setStyle("-fx-alignment: center");
		        				TventeCol6.setCellValueFactory(new PropertyValueFactory<produitAvendre,String>("QUANTITE"));
		        				TventeCol6.setCellFactory(cellFactoryString);
		        				TventeCol6.setOnEditCommit((CellEditEvent<produitAvendre, String> t) ->
		        				{
		        						int position=t.getTablePosition().getRow();
		        						if(nombrePositif(t.getNewValue())<=data.get(position).getSTOCKrestant())
		        						{
			        		                ((produitAvendre) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQUANTITE(t.getNewValue());
			        		                
			        		                data.get(position).setPRIXdetail(data.get(position).getPRIX()*nombrePositif(data.get(position).getQUANTITE()));
			        		               
			        		                sum=0;
			        		                for(int i=0;i<data.size();i++)
			        						{
			        							sum=sum+nombrePositif(""+data.get(i).getPRIX())*nombrePositif(data.get(i).getQUANTITE());
			        						}
			        						prixTotal.setText(""+sum+" FG");
			        						tableViewVente.refresh();
		        						}else
			        						{
		        								((produitAvendre) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQUANTITE("0");
				        		                data.get(position).setPRIXdetail(data.get(position).getPRIX()*nombrePositif(data.get(position).getQUANTITE()));
				        		                sum=0;
				        		                for(int i=0;i<data.size();i++)
				        						{
				        							sum=sum+nombrePositif(""+data.get(i).getPRIX())*nombrePositif(data.get(i).getQUANTITE());
				        						}
				        						prixTotal.setText(""+sum+" FG");
		        								new AlerterSuccesWorning().Worning("Quantité supérieur au stock");
		        								tableViewVente.refresh();
			        						}
		        		                
		        		        });
		        				 TventeCol6.setEditable(true);
			        				
		        				 
		        				TableColumn<produitAvendre,String> TventeCol7=new TableColumn<produitAvendre, String>();
		        				TventeCol7.setText("Destination");
		        				TventeCol7.setPrefWidth(150);
		        				TventeCol7.setCellValueFactory(new PropertyValueFactory<produitAvendre,String>("DESTINATION"));
		        				TventeCol7.setCellFactory(cellFactoryString);
		        				TventeCol7.setOnEditCommit(new EventHandler<CellEditEvent<produitAvendre, String>>() {
		        					@Override
		        					public void handle(CellEditEvent<produitAvendre, String> event) {
		        						// TODO Auto-generated method stub
		        		                ((produitAvendre) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDESTINATION(event.getNewValue());
		        					}
		        			    });
		        				TventeCol7.setEditable(true);
		        				TableColumn<produitAvendre,String> TventeCol8=new TableColumn<produitAvendre, String>();
		        				TventeCol8.setText("Prix a payé");
		        				TventeCol8.setPrefWidth(100);
		        				TventeCol8.setCellValueFactory(new PropertyValueFactory<produitAvendre,String>("PRIXdetail"));
		        		
		        				TableColumn<produitAvendre,Long> TventeCol9=new TableColumn<produitAvendre, Long>();
		        				TventeCol9.setText("Emplacement");
		        				TventeCol9.setPrefWidth(100);
		        				TventeCol9.setCellValueFactory(new PropertyValueFactory<produitAvendre,Long>("Emplacement"));
		        				
		        				tableViewVente.getColumns().addAll(TventeCol1,TventeCol3,TventeCol4,TventeCol5,TventeCol6,TventeCol7,TventeCol8,TventeCol9);
		        		
		        				HBox hboxVenteBotom=new HBox();
		        				HBox hboxVenteBotom2=new HBox();
		        				hboxVenteBotom.setSpacing(20);
		        				hboxVenteBotom.setPadding(new Insets(8,0,0,0));
		        				hboxVenteBotom.setAlignment(Pos.CENTER);
		        				hboxVenteBotom2.setAlignment(Pos.CENTER);
		        				JFXButton anuler=new JFXButton("Annuler");
		        				anuler.getStyleClass().add("Valider");
		        				anuler.setOnAction(new EventHandler<ActionEvent>()
			        				{
										@Override
										public void handle(ActionEvent arg0) 
											{
												data.removeAll(tableViewVente.getSelectionModel().getSelectedItems());
												 sum=0;
					        		                for(int i=0;i<data.size();i++)
					        						{
					        							sum=sum+data.get(i).getPRIX()*nombrePositif(data.get(i).getQUANTITE());
						        						
					        						}
					        						prixTotal.setText(""+sum+" FG");
												tableViewVente.refresh();
													
											}
			        				});
		        				
		        				
		        				
		        				
		        				anuler.setRipplerFill(Paint.valueOf("red"));
		        				JFXButton valider=new JFXButton("Valider");
		        				Text labTotal=new Text("......");
		        				valider.getStyleClass().add("Valider");
		        			
		        				
		        		gestionFicheStock gestionFicheStock=new gestionFicheStock();
		        		gestionFicheStock.createFicheDetailJour(""+curentDate);
		        		String curendateString=curentDate.toString();
		        		class taskServise extends Service<Void> {
		        			@Override
		        			protected Task<Void> createTask()
		        			{
		        				Task<Void> task=new Task<Void>(){

									@Override
									protected Void call() throws Exception 
									{
										// TODO Auto-generated method stub
						
										dataEcheck.clear();
										try {
											TransactionConnection = DriverManager.getConnection(url,"balamou","jean");
											TransactionConnection.setAutoCommit(false);
											//updater
											pstmtForTransaction=TransactionConnection.prepareStatement("UPDATE mesproduits SET Quantite=?, QJ=? WHERE (((DCI IS NULL AND @DCIInitial IS NULL) OR (DCI=?) ) AND ((Quantite IS NULL AND @QuantiteInitial IS NULL) OR (Quantite=?)))");
											pstmtUPDATEhistorique= TransactionConnection.prepareStatement("UPDATE HistoriqueDesVente SET NombreProdVendu=?, VALEUR=? WHERE Date=?");
											pstmtUPDATEcaisse= TransactionConnection.prepareStatement("UPDATE Caisse SET Total=?");
											
											//Select Quantite and QJ from database
											pstmt= TransactionConnection.prepareStatement("SELECT Code, Quantite, QJ FROM mesproduits WHERE DCI=?");
											//Select total caisse and valeur jour
											statement = TransactionConnection.createStatement();
											ResultSet ResultTotal= statement.executeQuery("SELECT Total FROM Caisse");
											ResultTotal.first();
											sumTotal=ResultTotal.getDouble("Total");
											ResultSet ResultJourTotal= statement.executeQuery("SELECT NombreProdVendu,VALEUR FROM HistoriqueDesVente");
											ResultJourTotal.last();
											sumTotalValeur=ResultJourTotal.getLong("VALEUR");
											sumTotalVente=ResultJourTotal.getInt("NombreProdVendu");
											
											for(int i=0;i<data.size();i++)
				        						{
												 
				        									pstmt.setString(1,data.get(i).getDCI());
				        									ResultSet rs2 = pstmt.executeQuery();
				        									while(rs2.next())
				        						      		{
				        										
				        										pstmtForTransaction.setLong(1,rs2.getLong("Quantite")-nombrePositif(data.get(i).getQUANTITE()));
				        										pstmtForTransaction.setLong(2,rs2.getLong("QJ")+nombrePositif(data.get(i).getQUANTITE()));
				        										pstmtForTransaction.setString(3, data.get(i).getDCI());
				        										
				        										if(rs2.getLong("Quantite")>=nombrePositif(data.get(i).getQUANTITE()) && nombrePositif(data.get(i).getQUANTITE())!=0)
		        													{
		        														pstmtForTransaction.setLong(4, rs2.getLong("Quantite"));
		        														validResult = pstmtForTransaction.executeUpdate();
		        														
		        														dataDetailVente.add(new produitVenteJour(data.get(i).getDCI(),data.get(i).getUNIT(),""+data.get(i).getPRIX(),""+data.get(i).getQUANTITE(),""+data.get(i).getPRIXdetail()));
									        							sumTotalventeJour=sumTotalventeJour+data.get(i).getPRIXdetail();
									        							labTotal.setText(sumTotalventeJour+" FG");
									        							sumTotalVente += nombrePositif(data.get(i).getQUANTITE());
									        							sumTotalValeur += data.get(i).getPRIXdetail();
									        							sumTotal += data.get(i).getPRIXdetail();
									        							gestionFicheStock.remplirFicheStock(rs2.getInt("Code"),curentDate+","+data.get(i).getDESTINATION()+","+"...."+","+data.get(i).getQUANTITE()+","+data.get(i).getSTOCKrestant()+","+"....");
									        							gestionFicheStock.remplirFicheSDetailJour(curendateString, data.get(i).getDCI()+","+data.get(i).getQUANTITE());
									        							//ClientSocket cs=new ClientSocket(""+(new Date(System.currentTimeMillis()))+","+(String)data.get(i).getDESTINATION()+","+" "+","+"5"+","+"10"+",Pas de remarque",rs2.getLong("Code"),"169.254.162.102");
																		
		        													}else{
				        											if(rs2.getLong("Quantite")==0)
				        												{
					        												new Alerter("Le produit "+data.get(i).getDCI()+" est fini du stock avant validation");
					        												data.get(i).setSTOCKrestant(0);
					        												dataEcheck.add(data.get(i));
				        												}else
				        													{
				        														if(nombrePositif(data.get(i).getQUANTITE())==0){
				        															new AlerterSuccesWorning().Worning("Vente(s) non effectués(s)");
				        															dataEcheck.add(data.get(i));
				        															}else{
				        																new AlerterSuccesWorning().Worning("Le stock du Produit "+data.get(i).getDCI()+" est inferieur a la quantité vendue");
						        														data.get(i).setSTOCKrestant(rs2.getLong("Quantite"));
						        														dataEcheck.add(data.get(i));
					        														}
				        													}
				        										}
			        										}
		        								}		
											pstmtUPDATEhistorique.setInt(1, sumTotalVente);
											pstmtUPDATEhistorique.setLong(2, sumTotalValeur);
											pstmtUPDATEhistorique.setDate(3,curentDate);
											pstmtUPDATEcaisse.setDouble(1,sumTotal);
											pstmtUPDATEhistorique.executeUpdate();
											pstmtUPDATEcaisse.executeUpdate();
											TransactionConnection.commit();
											data.clear();
											for(int i=0;i<dataEcheck.size();i++)
			        						{
												data.add(dataEcheck.get(i));
			        						}
											tableViewVente.refresh();
											TableVenteJour.refresh();
											
											if(data.size()!=0)
												{
													 	sum=0;
														 for(int i=0;i<data.size();i++)
							        						{
								        						sum=sum+data.get(i).getPRIXdetail();
									        					prixTotal.setText(""+sum+" FG");
							        						}
												}
											
											
											
							 
										} catch (SQLException e) {
											// if error
											 if (TransactionConnection != null) {
										            try {
										            	e.printStackTrace();
										            	new AlerterSuccesWorning().Worning(e.getMessage());
										                TransactionConnection. rollback();
										            } catch(SQLException excep) {
										                
										            }
										        }
										}finally {
											 try {
											        if (pstmtForTransaction != null) { pstmtForTransaction.close(); }
											        if (pstmt != null) {
											        	pstmt.close();
											        }
													TransactionConnection.setAutoCommit(true);
													 if (TransactionConnection != null) {
														 TransactionConnection.close();
													         }
											 } catch (SQLException e) {
												// TODO Auto-generated catch block
												 new AlerterSuccesWorning().Worning(e.getMessage());
											}
									    }
										
										//***************************
										
										return null;
									}	
		        				};
								return task;
		        			}
		        		}
		        				
		        				
		        		valider.setOnAction(new EventHandler<ActionEvent>()
			     					{
										@Override
										public void handle(ActionEvent arg0) 
											{
											//statuBar.setProgress(0);
											Service<Void> setvice=new taskServise();
											statuBar.progressProperty().bind(setvice.progressProperty());
											setvice.stateProperty().addListener((observableValue, oldState, newState) -> {
									            if (newState == Worker.State.SUCCEEDED) {
									            	statuBar.progressProperty().unbind();
									            	statuBar.setProgress(1);
									            	new AlerterSuccesWorning().Succes("Validé avec Succès");
									            	oldIndextCV=-1;
									            	ComboDCI.setValue(null);
									            	ComboDCI.getSelectionModel().clearSelection(ComboDCI.getSelectionModel().getSelectedIndex());
									            } // todo add code to gracefully handle other task states.
									        });
											setvice.start();
											}
			     					});		
		        				
		        				
		        				BorderPane borderPEdi=new BorderPane();
		        				borderPEdi.setStyle("-fx-background-color: linear-gradient(#232B3E,#004d40)");
		        				borderPEdi.setPrefSize(500, 500);
		        				borderPEdi.setMaxSize(500, 500);
		        				borderPEdi.setPadding(new Insets(3));
		        				Text texretour=new Text("  Retourner un produits . . . .");
		        				texretour.setStyle("-fx-fill: white;-fx-font-size: 25;");
		        				borderPEdi.setTop(texretour);
		        				JFXButton retouner=new JFXButton();
			        				PopOver popoverRetour=new PopOver(borderPEdi);
			        				popoverRetour.setArrowLocation(ArrowLocation.BOTTOM_RIGHT);
			        				popoverRetour.setDetachable(false);
			        				
		        				try{
		        						anuler.setGraphic(new ImageView(new Image("icon/delete2.png")));
		        						valider.setGraphic(new ImageView(new Image("icon/done.png")));
		        						retouner.setGraphic(new ImageView(new Image("icon/retour.png")));
		        				}catch(Exception ex){
		        					new Alerter(ex.getMessage());
		        				}
		        				BorderPane bpBotom=new BorderPane();
		        				hboxVenteBotom.getChildren().addAll(anuler,valider);
		        				hboxVenteBotom2.getChildren().addAll(retouner);
		        				bpBotom.setCenter(hboxVenteBotom);
		        				bpBotom.setRight(hboxVenteBotom2);
		        				retouner.setOnAction(new EventHandler<ActionEvent>(){
									@Override
									public void handle(ActionEvent arg0) {
										// TODO Auto-generated method stub
										popoverRetour.show(retouner);
									}
									 
								 });
		        		
		        bPaneVente.setTop(forHboxVente);	
		        bPaneVente.setCenter(tableViewVente);
		        bPaneVente.setBottom(bpBotom);
		        vente.setContent(bPaneVente);
		        
		        
			        			HBox bPaneRetourCenter=new HBox();bPaneRetourCenter.setSpacing(40);bPaneRetourCenter.setAlignment(Pos.CENTER);
					        			VBox vb1=new VBox();vb1.setSpacing(10);vb1.setAlignment(Pos.CENTER);
					        			JFXComboBox<String> comboBoxR1=new JFXComboBox<String>();
								        			comboBoxR1.setPrefWidth(300);
								        			comboBoxR1.setPromptText(" D.C.I");
								        			comboBoxR1.setEditable(true);
								        			comboBoxR1.setFocusColor(Paint.valueOf("#ffffff"));
								        			comboBoxR1.setUnFocusColor(Paint.valueOf("#DEDEDE"));
					        		
						        					vb1.getChildren().addAll(comboBoxR1);	 
 								 
					        			VBox vb2=new VBox();vb2.setSpacing(40);vb2.setAlignment(Pos.CENTER);
							        			DatePicker dateAchat=new DatePicker();
							        			TitledPane tilePane=new TitledPane();
							        			tilePane.setCollapsible(false);
							        			tilePane.setText("DATE D'ACHAT");
							        			tilePane.setContent(dateAchat);
							        			JFXTextField quantite=new JFXTextField();
							        			quantite.setAlignment(Pos.CENTER);
							        			quantite.setStyle("-fx-background-color: #232B3E");
							        			TitledPane tilePane1=new TitledPane();
							        			tilePane1.setCollapsible(false);
							        			tilePane1.setText("QUANTITE A RETOURNER");
							        			tilePane1.setContent(quantite);
							        			JFXTextField remarque=new JFXTextField();
							        			remarque.setStyle("-fx-background-color: #232B3E");
							        			TitledPane tilePane2=new TitledPane();
							        			tilePane2.setCollapsible(false);
							        			tilePane2.setText("REMARQUE");
							        			tilePane2.setContent(remarque);
							        			vb2.getChildren().addAll(tilePane,tilePane1,tilePane2);
							     bPaneRetourCenter.getChildren().addAll(vb1,vb2);
							     JFXButton validerRetour=new JFXButton();
							     			validerRetour.getStyleClass().add("Valider");	
							     			try{validerRetour.setGraphic(new ImageView(new Image("icon/turned2.png")));}catch(Exception ex){new Alerter(ex.getMessage());}
							        		HBox botomhbox=new HBox();botomhbox.setAlignment(Pos.CENTER);
							     			botomhbox.getChildren().add(validerRetour);
							 

					borderPEdi.setCenter(bPaneRetourCenter);
					borderPEdi.setBottom(botomhbox);  			
							     			
							     			
							     			
							     			
	/**
	 * Dertail des Vente
	 * **/						     			
							   	
							     			
			Tab detailVente=new Tab("Détail des ventes du jour");
			detailVente.setClosable(false);
						BorderPane bPDetail=new BorderPane();
						bPDetail.setStyle("-fx-background-color: #009688");
						bPDetail.setPadding(new Insets(15));
							HBox hbbPDetailTop=new HBox();hbbPDetailTop.setAlignment(Pos.CENTER);
							
									Text textValeur=new Text("Valeur totale réalisée:  ");
									
									textValeur.setStyle("-fx-fill: white;-fx-font-size: 30;");
									labTotal.setStyle("-fx-fill: white;-fx-font-size: 36;");
									
									hbbPDetailTop.getChildren().addAll(textValeur,labTotal);
									bPDetail.setTop(hbbPDetailTop);
									bPDetail.setBottom(Cj);
									
									
									TableVenteJour.setPadding(new Insets(10));
			        				
			        				TableColumn<produitVenteJour,String> TVenteCol1=new TableColumn<produitVenteJour, String>();
			        				TVenteCol1.setText("Désignation");
			        				TVenteCol1.setPrefWidth(450);
			        				TVenteCol1.setCellValueFactory(new PropertyValueFactory<produitVenteJour,String>("Designation"));
			        				
			        				TableColumn<produitVenteJour,String> TVenteCol2=new TableColumn<produitVenteJour, String>();
			        				TVenteCol2.setText("UNITE");
			        				TVenteCol2.setPrefWidth(160);
			        				TVenteCol2.setSortable(false);
			        				TVenteCol2.setCellValueFactory(new PropertyValueFactory<produitVenteJour,String>("UNIT"));
			        				
			        				TableColumn<produitVenteJour,String> TVenteCol3=new TableColumn<produitVenteJour, String>();
			        				TVenteCol3.setText("PRIX");
			        				TVenteCol3.setSortable(false);
			        				TVenteCol3.setPrefWidth(160);
			        				TVenteCol3.setCellValueFactory(new PropertyValueFactory<produitVenteJour,String>("PRIX"));
					        	
			        				TableColumn<produitVenteJour,String> TVenteCol4=new TableColumn<produitVenteJour, String>();
			        				TVenteCol4.setText("QUANTITE VENDUE");
			        				TVenteCol4.setStyle("-fx-alignment: center");
			        				TVenteCol4.setPrefWidth(200);
			        				TVenteCol4.setSortable(false);
			        				TVenteCol4.setCellValueFactory(new PropertyValueFactory<produitVenteJour,String>("QUANTITEvendu"));
					        	
			        				TableColumn<produitVenteJour,String> TVenteCol5=new TableColumn<produitVenteJour, String>();
			        				TVenteCol5.setText("PRI-PAYER");
			        				TVenteCol5.setPrefWidth(150);
			        				TVenteCol5.setCellValueFactory(new PropertyValueFactory<produitVenteJour,String>("PRIXdetail"));
			        				TVenteCol5.setSortable(false);
					        	
			        				TableVenteJour.getColumns().addAll(TVenteCol1,TVenteCol2,TVenteCol3,TVenteCol4,TVenteCol5);
			        				TableVenteJour.setItems(dataDetailVente);
									
									bPDetail.setCenter(TableVenteJour);
						detailVente.setContent(bPDetail);
						
				Tab TabCaisse=new Tab("CAISSE");
					BorderPane bpCaisse=new BorderPane();// bpCaisse.setStyle("-fx-background-color: #444444;");
					bpCaisse.setStyle("-fx-background-color: linear-gradient(#009688,#444444)");
					
					
						HBox hbCaiss=new HBox();hbCaiss.setAlignment(Pos.CENTER);
						hbCaiss.setPadding(new Insets(10,2,2,2));
						Text textSommT=new Text("Totale de la caisse");
						textSommT.setStyle("-fx-font-size: 50;-fx-fill: white");
						hbCaiss.getChildren().add(textSommT);
						bpCaisse.setTop(hbCaiss);
						
						VBox vbCaiss=new VBox();vbCaiss.setAlignment(Pos.CENTER);vbCaiss.setSpacing(100);
						VBox vbcais=new VBox();vbcais.setAlignment(Pos.CENTER);vbcais.setSpacing(3);
						Text textTotal=new Text("2.200.300 FG");
						textTotal.setText(gestionContenu.CaisseTotal()+" FG");
						textTotal.setStyle("-fx-font-size: 60;-fx-fill: white");
						JFXButton refresh=new JFXButton();
						try{
							refresh.setGraphic(new ImageView(new Image("icon/refresh.png")));
	    					}catch(Exception ex){}
						refresh.setOnAction(new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent arg0) {
								textTotal.setText(gestionContenu.CaisseTotal()+" FG");
							}
						 });
						vbcais.getChildren().addAll(textTotal,refresh);
						
						HBox hbCs=new HBox();hbCs.setAlignment(Pos.CENTER);hbCs.setSpacing(100);
						JFXButton Depot=new JFXButton("Mensionner un dépot");Depot.setStyle("-fx-background-Color: black;-fx-font-size: 25;-fx-text-fill: white");
						//***********
						BorderPane bpDepot=new BorderPane();
						bpDepot.setPrefHeight(200);
						bpDepot.setPrefWidth(200);
						VBox vbCaisse=new VBox();vbCaisse.setAlignment(Pos.CENTER);vbCaisse.setSpacing(25);
							JFXButton ValidDepot=new JFXButton("Valider");ValidDepot.setStyle("-fx-text-fill: white;-fx-font-size: 15;-fx-background-Color: black;");
							JFXTextField somDepot=new JFXTextField();
							somDepot.setPadding(new Insets(0));
							somDepot.setPrefWidth(150); 
							somDepot.setMaxWidth(150);
							somDepot.setPromptText("Somme du depot");
							somDepot.setAlignment(Pos.BASELINE_LEFT);
							somDepot.setStyle("-fx-text-fill: white;-fx-font-size: 15;-fx-background-Color: #444444;");
							somDepot.setFocusColor(Paint.valueOf("white"));
							somDepot.setUnFocusColor(Paint.valueOf("#B7B7B7"));
							vbCaisse.getChildren().addAll(somDepot,ValidDepot);
							bpDepot.setCenter(vbCaisse);	
						PopOver popoverDepot=new PopOver(bpDepot);
						popoverDepot.setDetachable(true);
						popoverDepot.setArrowLocation(ArrowLocation.BOTTOM_RIGHT);
						popoverDepot.setTitle("Faire dépot");
						
						Depot.setOnAction(new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent arg0) {
								popoverDepot.setDetached(true);
								popoverDepot.show(Depot);
							}
						 });
						ValidDepot.setOnAction(new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent arg0) {
								gestionContenu.DepotRetrait(nombrePositif(somDepot.getText()), curentDate, 1);
							}
						 });
						
						JFXButton Retrait=new JFXButton("Mensionner un retrait");Retrait.setStyle("-fx-background-Color: black;-fx-font-size: 25;-fx-text-fill: white");
						//***********
						BorderPane bpRetrait=new BorderPane();
						bpRetrait.setPrefHeight(200);
						bpRetrait.setPrefWidth(200);
						VBox vbCaisseR=new VBox();vbCaisseR.setAlignment(Pos.CENTER);vbCaisseR.setSpacing(25);
							JFXButton ValidRetrait=new JFXButton("Valider");ValidRetrait.setStyle("-fx-text-fill: white;-fx-font-size: 15;-fx-background-Color: black;");
							JFXTextField somRetrait=new JFXTextField();
							somRetrait.setPadding(new Insets(0));
							somRetrait.setPrefWidth(150); 
							somRetrait.setMaxWidth(150);
							somRetrait.setPromptText("Somme du retrait");
							somRetrait.setAlignment(Pos.BASELINE_LEFT);
							somRetrait.setStyle("-fx-text-fill: white;-fx-font-size: 15;-fx-background-Color: #444444;");
							somRetrait.setFocusColor(Paint.valueOf("white"));
							somRetrait.setUnFocusColor(Paint.valueOf("#B7B7B7"));
							vbCaisseR.getChildren().addAll(somRetrait,ValidRetrait);
							bpRetrait.setCenter(vbCaisseR);	
						PopOver popoverRetrait=new PopOver(bpRetrait);
						popoverRetrait.setDetachable(true);
						popoverRetrait.setArrowLocation(ArrowLocation.BOTTOM_RIGHT);
						popoverRetrait.setTitle("Faire Retrait");
						
						Retrait.setOnAction(new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent arg0) {
								popoverRetrait.setDetached(true);
								popoverRetrait.show(Retrait);
							}
						 });
						ValidRetrait.setOnAction(new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent arg0) {
								gestionContenu.DepotRetrait(nombrePositif(somRetrait.getText()), curentDate,2);
							}
						 });
						//*****************
						JFXButton CaissHistori=new JFXButton("Historique");
						CaissHistori.setStyle("-fx-background-Color: black;-fx-font-size: 25;-fx-text-fill: white");
						ListView<String> lvC=new ListView<String>();
						lvC.setItems(gestionContenu.CaissHistoq());
						lvC.setPrefWidth(400);
						
						PopOver popoverHistorique=new PopOver(lvC);
						popoverHistorique.setDetachable(true);
						popoverHistorique.setArrowLocation(ArrowLocation.BOTTOM_RIGHT);
						popoverHistorique.setTitle("Historique");
						
						CaissHistori.setOnAction(new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent arg0) {
								popoverHistorique.setDetached(true);
								popoverHistorique.show(CaissHistori);
							}
						 });
						
						hbCs.getChildren().addAll(Depot,Retrait,CaissHistori);
						vbCaiss.getChildren().addAll(vbcais,hbCs);
						bpCaisse.setCenter(vbCaiss);
						
					TabCaisse.setContent(bpCaisse);
	 tabPaneVente.setPadding(new Insets(-5));		
     tabPaneVente.getTabs().addAll(vente,detailVente,TabCaisse);
     
     JFXButton detaiVente=new JFXButton("Prix-séletion");
	 leprix=new Label();
	 leprix.setStyle("-fx-font-size: 15;-fx-text-fill: red");
	 detaiVente.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent arg0) 
				{int sum1=0;
					for(int i=0;i<tableViewVente.getSelectionModel().getSelectedItems().size();i++)
					{
						sum1=sum1+nombrePositif(""+tableViewVente.getSelectionModel().getSelectedItems().get(i).getPRIXdetail());
						//System.out.println(tableViewVente.getSelectionModel().getSelectedItems().get(0).getPRIXdetail());
						
					}
					leprix.setText("  "+sum1+" FG");	
				}
		});
	 
	 statuBar.getLeftItems().addAll(detaiVente,leprix);
     
     borederpaneVente.setCenter(tabPaneVente);
	return borederpaneVente;
}
public BorderPane borderPaneStock(){
	BorderPane stockPane=new BorderPane();
	
    TabPane tabPaneStock=new 	TabPane();
	        Tab TabLesProduits=new Tab("Le stock");TabLesProduits.setClosable(false);
	        		BorderPane bpaneStock=new BorderPane();
	        		bpaneStock.setPadding(new Insets(15));
	        		
	        		TableView<produitStock> tableStockView=new TableView<produitStock>();
	        		
    				TableColumn<produitStock,Integer> TStockCol1=new TableColumn<produitStock, Integer>();
    				TStockCol1.setText("Code");
    				TStockCol1.setPrefWidth(50);
    				TStockCol1.setCellValueFactory(new PropertyValueFactory<produitStock,Integer>("Code"));
	        	
    				TableColumn<produitStock,String> TStockCol2=new TableColumn<produitStock, String>();
    				TStockCol2.setText("D.C.I");
    				TStockCol2.setPrefWidth(450);
    				TStockCol2.setCellValueFactory(new PropertyValueFactory<produitStock,String>("DCI"));
	        	
    				
    				TableColumn<produitStock,String> TStockCol4=new TableColumn<produitStock, String>();
    				TStockCol4.setText("Unité");
    				TStockCol4.setPrefWidth(100);
    				TStockCol4.setCellValueFactory(new PropertyValueFactory<produitStock,String>("Uniter"));
	        	
    				TableColumn<produitStock,Long> TStockCol5=new TableColumn<produitStock, Long>();
    				TStockCol5.setText("Quantité");
    				TStockCol5.setStyle("-fx-alignment: center");
    				TStockCol5.setPrefWidth(90);
    				TStockCol5.setCellValueFactory(new PropertyValueFactory<produitStock,Long>("Quantite"));
    				
    				
    				
    				TableColumn<produitStock,String> TStockCol6=new TableColumn<produitStock, String>();
    				TStockCol6.setText("Date d'entré");
    				TStockCol6.setPrefWidth(100);
    				TStockCol6.setStyle("-fx-alignment: center");
    				TStockCol6.setCellValueFactory(new PropertyValueFactory<produitStock,String>("DateEntre"));
    				
    				TableColumn<produitStock,String> TStockCol7=new TableColumn<produitStock, String>();
    				TStockCol7.setText("Expire");
    				TStockCol7.setPrefWidth(100);
    				TStockCol7.setStyle("-fx-alignment: center");
    				TStockCol7.setCellValueFactory(new PropertyValueFactory<produitStock,String>("DAteSortie"));
	        	
    				TableColumn<produitStock,Long> TStockCol10=new TableColumn<produitStock, Long>();
    				TStockCol10.setText("C.M.M");
    				TStockCol10.setPrefWidth(70);
    				TStockCol10.setStyle("-fx-alignment: center");
    				TStockCol10.setCellValueFactory(new PropertyValueFactory<produitStock,Long>("CMM"));
    				
    				TableColumn<produitStock,String> TStockCol8=new TableColumn<produitStock, String>();
    				TStockCol8.setText("Mois de stock disponible");
    				TStockCol8.setPrefWidth(200);
    				TStockCol8.setCellValueFactory(new PropertyValueFactory<produitStock,String>("NombreMoiStock"));
    				
    				TableColumn<produitStock,String> TStockCol9=new TableColumn<produitStock, String>();
    				TStockCol9.setText("Fournisseur");
    				TStockCol9.setPrefWidth(100);
    				TStockCol9.setCellValueFactory(new PropertyValueFactory<produitStock,String>("Fournisseur"));
    				
    				dataStock=gestionContenu.TabStockContenu();
					tableStockView.setItems(dataStock);
    				bpaneStock.setCenter(tableStockView);
    				
    				Service<Void> servicerefresh=new Service<Void>(){
    					@Override
    					protected Task<Void> createTask() {
    						// TODO Auto-generated method stub
    						return new Task<Void>(){
    							@Override
    							protected Void call() throws Exception 
    							{
    								dataStock.clear();
    								dataStock=gestionContenu.TabStockContenu();
									return null;
    								
    							}
    							
    						};
    					}
    					
    				};
    				
    				JFXButton rafrechirStock=new JFXButton();
    				rafrechirStock.setOnAction(new EventHandler<ActionEvent>(){
						@Override
						public void handle(ActionEvent arg0) {
							ProgressDialog progDialog=new ProgressDialog(servicerefresh);
							progDialog.setTitle("Rafrechissement.....");
							progDialog.initOwner(MainStage);
							progDialog.setHeaderText("Rafrechissement en cours ......");
							progDialog.initModality(Modality.WINDOW_MODAL);
							servicerefresh.restart();
							tableStockView.setItems(dataStock);
							tableStockView.refresh();
							}
						});
    				
    				JFXButton ButApprovision=new JFXButton("Approvision");
    				try{
    					ButApprovision.setGraphic(new ImageView(new Image("icon/approvision.png")));
    					rafrechirStock.setGraphic(new ImageView(new Image("icon/refresh.png")));
    					}catch(Exception ex){}
    				ButApprovision.setStyle("-fx-text-fill: white;-fx-font-size: 20;");
    				BorderPane bpApro=new BorderPane();bpApro.setStyle("-fx-background-color: linear-gradient(rgba(35,43,62,.2),#004d40)");
					bpApro.setPrefSize(500, 500);
					bpApro.setMaxSize(500, 500);
					bpApro.setPadding(new Insets(10));
					
					
						VBox vbApro=new VBox();vbApro.setAlignment(Pos.CENTER);vbApro.setSpacing(8);
    							JFXComboBox<String> comboBoxAproDCI=new JFXComboBox<String>();
    							comboBoxAproDCI.setEditable(true);
    							comboBoxAproDCI.setPrefWidth(400);
    							comboBoxAproDCI.setPromptText(" D.C.I");
    							comboBoxAproDCI.setFocusColor(Paint.valueOf("#ffffff"));
    							comboBoxAproDCI.setUnFocusColor(Paint.valueOf("#DEDEDE"));
						        comboBoxAproDCI.setItems(ComboDCI.getItems());
								TextFields.bindAutoCompletion(comboBoxAproDCI.getEditor(),(Collection<String>) comboBoxAproDCI.getItems());
								
	        			vbApro.getChildren().addAll(comboBoxAproDCI)	;
					
					
	        			VBox vbApro1=new VBox();vbApro1.setAlignment(Pos.CENTER);vbApro1.setSpacing(15);
	        			
	        						Label labUnitApp=new Label();
	        						labUnitApp.setStyle("-fx-text-fill: yellow;-fx-font-size: 17;");
	        			
	        						JFXTextField origine=new JFXTextField();
	        						origine.setFocusColor(Paint.valueOf("#ffffff"));
	        						origine.setUnFocusColor(Paint.valueOf("#DEDEDE"));
	        						origine.setPromptText("Origine");
	        						origine.setPrefWidth(150);
	        						origine.setMaxWidth(150);
	        						
	        						JFXTextField quantiteApp=new JFXTextField();
	        						quantiteApp.setFocusColor(Paint.valueOf("#ffffff"));
	        						quantiteApp.setUnFocusColor(Paint.valueOf("#DEDEDE"));
	        						quantiteApp.setPromptText("Quantité");
	        						quantiteApp.setPrefWidth(100);
	        						quantiteApp.setMaxWidth(100);
	        						
	        						DatePicker dateExpireApp=new DatePicker();
	        						dateExpireApp.setPromptText("Date d'expiration");
	        						
	        						JFXButton volidApp=new JFXButton();
	        						try{volidApp.setGraphic(new ImageView(new Image("icon/validIc.png")));}catch(Exception ex){}
	        						HBox vbAp=new HBox();vbAp.setAlignment(Pos.CENTER);
	        						vbAp.getChildren().add(volidApp);
			        	vbApro1.getChildren().addAll(labUnitApp,origine,quantiteApp,dateExpireApp);
					
					
	        			VBox vbApro2=new VBox();vbApro2.setAlignment(Pos.CENTER);vbApro2.setSpacing(30);
	        			vbApro2.getChildren().addAll(vbApro,vbApro1);
	        			bpApro.setCenter(vbApro2);
	        			bpApro.setBottom(vbAp);
	        			
	        			volidApp.setOnAction(new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent event) {
								// TODO Auto-generated method stub
								gestionContenu.Approvision(nombrePositif(quantiteApp.getText()), java.sql.Date.valueOf(dateExpireApp.getValue()), comboBoxAproDCI.getValue(),curentDate,origine.getText());
								
							}
	        			});
	        			
	        			comboBoxAproDCI.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        					
        						if(!testEgalite(indexCheckerApro,comboBoxAproDCI.getSelectionModel().getSelectedIndex()))
    							{
	        						if(comboBoxAproDCI.getValue()!=null)
	        							{
	        								try{
	        							        connection = DriverManager.getConnection(url,"balamou","jean");
	        							       statement= connection.createStatement();
	        									ResultSet rs2 = statement.executeQuery("SELECT Unite FROM mesproduits");
	        									rs2.last();
	        									labUnitApp.setText((rs2.getString("Unite")));
	        									rs2.close();
	        								}catch(SQLException e){
	        									new Alerter(e.getMessage());
	        								}finally{
			        							try {
			        								if(connection!=null)
			        									connection.close();
			        								if(statement!=null)
			        									statement.close();
			        							} catch (SQLException e) {
			        								//e.printStackTrace();
			        								new Alerter("Couldn't close statement");
			        							}
			        						}
	        								
	        							}
	        						indexCheckerApro=comboBoxAproDCI.getSelectionModel().getSelectedIndex();
    							}
        					}
        				});
					
					
	        			
		        				PopOver popoverApprovision=new PopOver(bpApro);
		        				popoverApprovision.setArrowLocation(ArrowLocation.BOTTOM_CENTER);
		        				popoverApprovision.setDetachable(false);
		        				
    				JFXButton ButEdition=new JFXButton("Edition de produit");
    				ButEdition.setStyle("-fx-text-fill: white;-fx-font-size: 20;");
    				BorderPane bpEdi=new BorderPane();bpEdi.setStyle("-fx-background-color: linear-gradient(rgba(35,43,62,.2),#004d40)");
    							bpEdi.setPrefSize(500, 500);
    							bpEdi.setMaxSize(500, 500);
    							bpEdi.setPadding(new Insets(10));
    							
    							VBox vbEdi=new VBox();vbEdi.setAlignment(Pos.CENTER);vbEdi.setSpacing(11);
        							JFXComboBox<String> comboBoxEditDCI=new JFXComboBox<String>();
        							comboBoxEditDCI.setEditable(true);
        							comboBoxEditDCI.setPrefWidth(450);
        							comboBoxEditDCI.setPromptText(" D.C.I");
        							comboBoxEditDCI.setFocusColor(Paint.valueOf("#ffffff"));
        							comboBoxEditDCI.setUnFocusColor(Paint.valueOf("#DEDEDE"));
        							comboBoxEditDCI.setItems(ComboDCI.getItems());
									TextFields.bindAutoCompletion(comboBoxEditDCI.getEditor(),(Collection<String>) comboBoxEditDCI.getItems());
									
		        					VBox VBE1=new VBox();VBE1.setAlignment(Pos.CENTER);VBE1.setSpacing(8);
		        					VBox VBE2=new VBox();VBE2.setAlignment(Pos.CENTER);VBE2.setSpacing(8);
		        					
		        					Tooltip ttp=null;
		        					ttp=new Tooltip("Fournisseur");
		        					JFXTextField fournisseurE=new JFXTextField();
		        					fournisseurE.setFocusColor(Paint.valueOf("yellow"));
		        					fournisseurE.setTooltip(ttp);
		        					ttp=new Tooltip("Unité");
		        					JFXTextField uniteE=new JFXTextField();
		        					uniteE.setFocusColor(Paint.valueOf("yellow"));
		        					uniteE.setTooltip(ttp);
		        					ttp=new Tooltip("Prix d'achat");
		        					JFXTextField prixAxhatE=new JFXTextField();
		        					prixAxhatE.setFocusColor(Paint.valueOf("yellow"));
		        					prixAxhatE.setTooltip(ttp);
		        					ttp=new Tooltip("prix de Vente");
		        					JFXTextField prixVenteE=new JFXTextField();
		        					prixVenteE.setFocusColor(Paint.valueOf("yellow"));
		        					prixVenteE.setTooltip(ttp);
		        					ttp=new Tooltip("Emplacement");
		        					JFXTextField Emplacement=new JFXTextField();
		        					Emplacement.setFocusColor(Paint.valueOf("yellow"));
		        					Emplacement.setTooltip(ttp);
		        					Emplacement.setAlignment(Pos.CENTER);
		        					ttp=new Tooltip("Stock min");
		        					JFXTextField StockMainE=new JFXTextField();
		        					StockMainE.setFocusColor(Paint.valueOf("yellow"));
		        					StockMainE.setTooltip(ttp);
		        					ttp=new Tooltip("Stock max");
		        					JFXTextField StockMaxE=new JFXTextField();
		        					StockMaxE.setFocusColor(Paint.valueOf("yellow"));
		        					StockMaxE.setTooltip(ttp);
		        					ttp=new Tooltip("date d'entrer en stock");
		        					DatePicker DateEnterE=new DatePicker();
		        					DateEnterE.setPromptText("Entrer en stock");
		        					DateEnterE.setTooltip(ttp);
		        					ttp=new Tooltip("Exp date");
		        					DatePicker DateSortE=new DatePicker();
		        					DateSortE.setPromptText("Exp date");
		        					DateSortE.setTooltip(ttp);
		        					
		        					VBE1.getChildren().addAll(fournisseurE,uniteE,Emplacement,StockMainE,StockMaxE);
		        					VBE2.getChildren().addAll(prixAxhatE,prixVenteE,DateEnterE,DateSortE);
		        					
		        					JFXButton validEdit=new JFXButton();
	        						try{validEdit.setGraphic(new ImageView(new Image("icon/validIc.png")));}catch(Exception ex){}
	        						HBox HBVE=new HBox();HBVE.setAlignment(Pos.CENTER);
	        						HBVE.getChildren().add(validEdit);
	        						
		        					HBox HBE=new HBox();HBE.setAlignment(Pos.CENTER);HBE.setSpacing(5);
		        					HBE.getChildren().addAll(VBE1,VBE2);
		        				vbEdi.getChildren().addAll(comboBoxEditDCI,HBE);
		        				bpEdi.setCenter(vbEdi);
		        				bpEdi.setBottom(HBVE);

    							PopOver popoverEdition=new PopOver(bpEdi);
    							popoverEdition.setArrowLocation(ArrowLocation.BOTTOM_CENTER);
    							popoverEdition.setDetachable(false);
    							
    							validEdit.setOnAction(new EventHandler<ActionEvent>(){
        							@Override
        							public void handle(ActionEvent arg0) {
        								// TODO Auto-generated method stub
        								gestionContenu.ModificationSave(comboBoxEditDCI.getItems().get(comboBoxEditDCI.getSelectionModel().getSelectedIndex()),
        																comboBoxEditDCI.getValue(),Emplacement.getText(), fournisseurE.getText(), uniteE.getText(),
        																nombrePositif(prixAxhatE.getText()), nombrePositif(prixVenteE.getText()),
        																nombrePositif(StockMainE.getText()), nombrePositif(StockMaxE.getText()), 
        																java.sql.Date.valueOf(DateEnterE.getValue()), java.sql.Date.valueOf(DateSortE.getValue()));
        							}
        						});
    							
    							comboBoxEditDCI.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		        					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        						if(!testEgalite(indexCheckerEdit,comboBoxEditDCI.getSelectionModel().getSelectedIndex()))
		    							{
			        						if(comboBoxEditDCI.getValue()!=null)
			        						{
			        							UnproduitDuStock leProduit=gestionContenu.AproductFromMesproduits(comboBoxEditDCI.getValue());
			        							fournisseurE.setText(leProduit.getFournisseur());
			        							uniteE.setText(leProduit.getUnite());
			        							prixAxhatE.setText(""+leProduit.getPrixAchat());
			        							prixVenteE.setText(""+leProduit.getPrixVente());
			        							Emplacement.setText(leProduit.getEmplacement());
			        							StockMainE.setText(""+leProduit.getStockMin());
			        							StockMaxE.setText(""+leProduit.gettStockMax());
			        							DateEnterE.setValue(leProduit.getDateEntrer().toLocalDate());
			        							DateSortE.setValue(leProduit.getDateExp().toLocalDate());
			        							
			        						}
			        						indexCheckerEdit=comboBoxEditDCI.getSelectionModel().getSelectedIndex();
		    							}
		        					}
		        				});
    							
    							JFXButton inventaire=new JFXButton("Inventaire");
    							inventaire.setStyle("-fx-text-fill: white;-fx-font-size: 20;");
    		    				BorderPane bpInvent=new BorderPane();bpEdi.setStyle("-fx-background-color: linear-gradient(rgba(35,43,62,.2),#004d40)");
    		    							bpInvent.setPrefSize(500, 500);
    		    							bpInvent.setMaxSize(500, 500);
    		    							bpInvent.setPadding(new Insets(10));
    		    							
    		    							VBox vbInvent=new VBox();vbInvent.setAlignment(Pos.CENTER);vbEdi.setSpacing(50);
    		    							vbInvent.setStyle("-fx-background-color: linear-gradient(rgba(35,43,62,.2),#004d40)");
    		        							JFXComboBox<String> comboBoxInventDCI=new JFXComboBox<String>();
    		        							comboBoxInventDCI.setEditable(true);
    		        							comboBoxInventDCI.setPrefWidth(450);
    		        							comboBoxInventDCI.setPromptText(" D.C.I");
    		        							comboBoxInventDCI.setFocusColor(Paint.valueOf("#ffffff"));
    		        							comboBoxInventDCI.setUnFocusColor(Paint.valueOf("#DEDEDE"));
    		        							comboBoxInventDCI.setItems(ComboDCI.getItems());
    											TextFields.bindAutoCompletion(comboBoxInventDCI.getEditor(),(Collection<String>) comboBoxInventDCI.getItems());
    											
    											
    											VBox vbinv=new VBox();vbinv.setAlignment(Pos.CENTER);
    											Text ajuster=new Text("Ajuster le stock");
    											ajuster.setStyle("-fx-fill: white;-fx-font-size: 15;");
    											
    											JFXTextField quantiteInvent=new JFXTextField();
    											quantiteInvent.setFocusColor(Paint.valueOf("#ffffff"));
    											quantiteInvent.setUnFocusColor(Paint.valueOf("#DEDEDE"));
    											quantiteInvent.setPromptText("Quantité");
    											quantiteInvent.setAlignment(Pos.CENTER);
    											quantiteInvent.setPrefWidth(100);
    											quantiteInvent.setMaxWidth(100);
    											vbinv.getChildren().addAll(ajuster,quantiteInvent);
    											
    											JFXTextField remarque=new JFXTextField();
    											remarque.setFocusColor(Paint.valueOf("#ffffff"));
    											remarque.setUnFocusColor(Paint.valueOf("#DEDEDE"));
    											remarque.setPromptText("Remarque");
    											remarque.setPrefWidth(200);
    											remarque.setMaxWidth(200);
    											
    											JFXButton validInvent=new JFXButton();
    			        						try{validInvent.setGraphic(new ImageView(new Image("icon/validIc.png")));}catch(Exception ex){}
    			        						
    			        						vbInvent.getChildren().addAll(comboBoxInventDCI,vbinv,remarque,validInvent);
    											
    			        						PopOver popoverInvent=new PopOver(vbInvent);
    			        						popoverInvent.setArrowLocation(ArrowLocation.BOTTOM_CENTER);
    			        						popoverInvent.setDetachable(false);
    			        						
    			        						validInvent.setOnAction(new EventHandler<ActionEvent>(){
    			        							@Override
    			        							public void handle(ActionEvent arg0) {
    			        								// TODO Auto-generated method stub
    			        								gestionContenu.InventaireSave(nombrePositif(quantiteInvent.getText()),comboBoxInventDCI.getValue(),curentDate, remarque.getText());
    			        							}
    			        						});
    			        						
    			        						
    				HBox hbStock=new HBox();hbStock.setAlignment(Pos.CENTER);hbStock.setSpacing(110);hbStock.setPadding(new Insets(10,0,0,0));
    				hbStock.getChildren().addAll(ButApprovision,ButEdition,inventaire,rafrechirStock);
    				bpaneStock.setBottom(hbStock);
    				
    				try{
    					ButApprovision.setGraphic(new ImageView(new Image("icon/approvision.png")));
    					ButEdition.setGraphic(new ImageView(new Image("icon/edit.png")));
    					inventaire.setGraphic(new ImageView(new Image("icon/inventaire.png")));
    					}catch(Exception ex){}
    				
    				inventaire.setOnAction(new EventHandler<ActionEvent>(){
						@Override
						public void handle(ActionEvent arg0) {
							// TODO Auto-generated method stub
							popoverInvent.show(inventaire);
						}
    				});
    				
    				if(!logControl.AprovisionAlow())
					 {
    					ButApprovision.setGraphic(new ImageView(new Image("icon/lockRed1.png")));
    					ButApprovision.setOnAction(new EventHandler<ActionEvent>(){
								@Override
								public void handle(ActionEvent arg0) {
									new Alerter(" Action non autorisée",1);
								}});
					 }else{
	        				ButApprovision.setOnAction(new EventHandler<ActionEvent>(){
								@Override
								public void handle(ActionEvent arg0) {
									// TODO Auto-generated method stub
									popoverApprovision.show(ButApprovision);
								}
								 
							 });
					 	}
    				
    				if(!logControl.EditionAlow())
					 {
    					ButEdition.setGraphic(new ImageView(new Image("icon/lockRed1.png")));
    					ButEdition.setOnAction(new EventHandler<ActionEvent>(){
								@Override
								public void handle(ActionEvent arg0) {
									new Alerter(" Action non autorisée",1);
								}});
					 }else{
	        				ButEdition.setOnAction(new EventHandler<ActionEvent>(){
								@Override
								public void handle(ActionEvent arg0) {
									// TODO Auto-generated method stub
									popoverEdition.show(ButEdition);
								}
								 
							 });
					 	}
    				
    			tableStockView.getColumns().addAll(TStockCol1,TStockCol2,TStockCol4,TStockCol5,TStockCol6,TStockCol7,TStockCol10,TStockCol9,TStockCol8);
    			TableFilter<produitStock> filter = new TableFilter<produitStock>(tableStockView);
    			TabLesProduits.setContent(bpaneStock);
	        
	        //***************************************************** Nouveau produits **************************
			       
	        Tab TabNouveuProduit=new Tab("Nouveau produits");TabNouveuProduit.setClosable(false);
	        			BorderPane bpaneNouveau=new BorderPane();bpaneNouveau.setStyle("-fx-background-color: linear-gradient(#009688,#004d40)");
	        			bpaneNouveau.setPadding(new Insets(10,100,10,100));
	        			
			        JFXComboBox<String> comboBoxNDCI=new JFXComboBox<String>();
			        comboBoxNDCI.setEditable(true);
			        comboBoxNDCI.setPrefWidth(300);
			        comboBoxNDCI.setPromptText(" D.C.I");
			        comboBoxNDCI.setFocusColor(Paint.valueOf("#ffffff"));
			        comboBoxNDCI.setUnFocusColor(Paint.valueOf("#DEDEDE"));
			        comboBoxNDCI.setItems(gestionContenu.DCInewProductContenu());
					TextFields.bindAutoCompletion(comboBoxNDCI.getEditor(),(Collection<String>) comboBoxNDCI.getItems());
					
					
					
					
					
					Label labCode=new Label("101");
					labCode.setStyle("-fx-text-fill: red;-fx-font-size: 20;");
    				TitledPane TPN1=new TitledPane();
    				TPN1.setExpanded(false);
    				//TPN1.setCollapsible(false);
    				TPN1.setText("CODE");
    				TPN1.setContent(labCode);
    				
    				Tooltip tpTax=new Tooltip();tpTax.setText("EX: 15 ou 20");
    				JFXTextField leTax=new JFXTextField();leTax.setPromptText("%");
    				leTax.setPrefWidth(25);
    				leTax.setMaxWidth(25);
    				leTax.setFocusColor(Paint.valueOf("yellow"));
    				leTax.setUnFocusColor(Paint.valueOf("white"));
    				leTax.setTooltip(tpTax);
    				
    				JFXToggleButton taxActive=new JFXToggleButton();
    				taxActive.setStyle("-fx-font-size: 15;-fx-text-fill: white");
    				taxActive.setText("Taxé");
    				taxActive.setSelected(false);
    				VBox hbTaxe=new VBox();hbTaxe.setAlignment(Pos.CENTER);hbTaxe.setSpacing(1);
    				hbTaxe.getChildren().add(taxActive);
    				
    				Text informText=new Text("Produit non taxé");
    				informText.setStyle("-fx-fill: yellow;-fx-font-size: 13;");
    				
    				taxActive.setOnAction(new EventHandler<ActionEvent>(){
						@Override
						public void handle(ActionEvent arg0) {
							// TODO Auto-generated method stub
							if(taxActive.isSelected()){
								hbTaxe.getChildren().add(leTax);
								informText.setText("Produit taxé");
								informText.setStyle("-fx-fill: red;-fx-font-size: 13;");
							}else
		        					{
										hbTaxe.getChildren().remove(leTax);
										informText.setText("Produit non taxé");
										informText.setStyle("-fx-fill: yellow;-fx-font-size: 13;");
		        					}
							
						}
					 });
    				
    				comboBoxNDCI.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    					
    						if(indexChecker!=comboBoxNDCI.getSelectionModel().getSelectedIndex())
							{
	    						if(comboBoxNDCI.getValue()!=null)
	    							{
	    								
	    								try{
	    									connection = DriverManager.getConnection(url,"balamou","jean");
	    									statement= connection.createStatement();
	    									ResultSet rs2 = statement.executeQuery("SELECT Code FROM mesproduits");
	    									rs2.last();
	    									 labCode.setText(""+(rs2.getLong("Code")+1));
	    									 TPN1.setExpanded(true);
	    									
	    								}catch(SQLException e){
	    									new Alerter(e.getMessage());
	    								}finally{
		        							try {
		        								if(connection!=null)
		        									connection.close();
		        								if(statement!=null)
		        									statement.close();
		        							} catch (SQLException e) {
		        								//e.printStackTrace();
		        								new Alerter("Couldn't close statement");
		        							}
		        						}
	    								
	    							}
	    						indexChecker=comboBoxNDCI.getSelectionModel().getSelectedIndex();
							}
    					}
    				});
    				
					
	        				
	        				
	        				
	        				
    				JFXTextField  famille=new JFXTextField();
	        		famille.setPrefWidth(300);
	        		famille.setEditable(true);
	        		famille.setPromptText(" Emplacement");
	        		famille.setFocusColor(Paint.valueOf("white"));
	        		famille.setUnFocusColor(Paint.valueOf("#B7B7B7"));
    				
	        		JFXTextField DateEntrer=new JFXTextField(new Date(System.currentTimeMillis()).toString());
	        		DateEntrer.setFocusTraversable(false);
	        		DateEntrer.setEditable(false);
	        		DateEntrer.setPadding(new Insets(0));
	        		DateEntrer.setAlignment(Pos.BASELINE_LEFT);
	        		DateEntrer.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
	        		DateEntrer.setUnFocusColor(Paint.valueOf("#B7B7B7"));
    				
    				
    				JFXTextField Fournisseur=new JFXTextField();
    				Fournisseur.setPadding(new Insets(0));
    				Fournisseur.setPromptText(" Fournisseur ");
    				Fournisseur.setAlignment(Pos.BASELINE_LEFT);
    				Fournisseur.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
    				Fournisseur.setFocusColor(Paint.valueOf("white"));
    				Fournisseur.setUnFocusColor(Paint.valueOf("#B7B7B7"));
			        
    				
    				
    				JFXTextField unite=new JFXTextField();
    				unite.setPadding(new Insets(0));
    				unite.setPromptText(" Unité ");
    				unite.setAlignment(Pos.BASELINE_LEFT);
    				unite.setStyle("-fx-text-fill: white;-fx-font-size: 17;");
    				unite.setFocusColor(Paint.valueOf("white"));
    				unite.setUnFocusColor(Paint.valueOf("#B7B7B7"));
    				
    				
    				JFXTextField prixA=new JFXTextField();
    				prixA.setPadding(new Insets(0));
    				prixA.setPromptText(" Prix d'achat ");
    				prixA.setAlignment(Pos.BASELINE_LEFT);
    				prixA.setStyle("-fx-text-fill: white;-fx-font-size: 17;");
    				prixA.setFocusColor(Paint.valueOf("white"));
    				prixA.setUnFocusColor(Paint.valueOf("#B7B7B7"));
    				
    				
    				JFXTextField prixV=new JFXTextField();
    				prixV.setPadding(new Insets(0));
    				prixV.setPromptText(" P.V.H.T ");
    				prixV.setAlignment(Pos.BASELINE_LEFT);
    				prixV.setStyle("-fx-text-fill: white;-fx-font-size: 17;");
    				prixV.setFocusColor(Paint.valueOf("white"));
    				prixV.setUnFocusColor(Paint.valueOf("#B7B7B7"));
    				
    				
    				
    				JFXTextField quantiteN=new JFXTextField();
    				quantiteN.setPadding(new Insets(0));
    				quantiteN.setPromptText(" Quantité ");
    				quantiteN.setAlignment(Pos.BASELINE_LEFT);
    				quantiteN.setStyle("-fx-text-fill: white;-fx-font-size: 17;");
    				quantiteN.setFocusColor(Paint.valueOf("white"));
    				quantiteN.setUnFocusColor(Paint.valueOf("#B7B7B7"));
    				
    				
    				JFXTextField StockMin=new JFXTextField();
    				StockMin.setPadding(new Insets(0));
    				StockMin.setPromptText(" Stock MIN ");
    				StockMin.setAlignment(Pos.BASELINE_LEFT);
    				StockMin.setStyle("-fx-text-fill: white;-fx-font-size: 17;");
    				StockMin.setFocusColor(Paint.valueOf("white"));
    				StockMin.setUnFocusColor(Paint.valueOf("#B7B7B7"));
    				
    				
    				JFXTextField StockMax=new JFXTextField();
    				StockMax.setPadding(new Insets(0));
    				StockMax.setPromptText(" Stock MAX ");
    				StockMax.setAlignment(Pos.BASELINE_LEFT);
    				StockMax.setStyle("-fx-text-fill: white;-fx-font-size: 17;");
    				StockMax.setFocusColor(Paint.valueOf("white"));
    				StockMax.setUnFocusColor(Paint.valueOf("#B7B7B7"));
    				
	        		
    				
    				DatePicker Peremption=new DatePicker();
    				Peremption.setPrefWidth(247);
    				Peremption.setPadding(new Insets(0));
    				Peremption.setPromptText(" Date d'expiration");
    				Peremption.setStyle("-fx-text-fill: white;-fx-font-size: 15;-fx-background-color: #009688;");
    				
    				
	        		
	        		HBox hboxTOP=new HBox();hboxTOP.setAlignment(Pos.CENTER);hboxTOP.setSpacing(50);
	        		hboxTOP.setPadding(new Insets(0,0,15,0));
	        		hboxTOP.getChildren().addAll(TPN1,comboBoxNDCI,hbTaxe);
	      bpaneNouveau.setTop(hboxTOP);
	        		
	        		TabNouveuProduit.setContent(bpaneNouveau);
	        		
	        		HBox hbox2=new HBox(); hbox2.getChildren().addAll(prixA,prixV);hbox2.setSpacing(3);
	        		hbox2.setAlignment(Pos.CENTER);
	        		
	        		VBox vbN0=new VBox();
	        			 vbN0.setAlignment(Pos.CENTER);vbN0.setSpacing(10);
	        			 vbN0.getChildren().addAll(famille,DateEntrer);
	     bpaneNouveau.setLeft(vbN0);
	        		
	        		VBox vbNCenter=new VBox();
	        		vbNCenter.setPrefWidth(250);
	        		vbNCenter.setMaxWidth(250);
			        		vbNCenter.setAlignment(Pos.CENTER);vbNCenter.setSpacing(50);
					        		VBox vbN1=new VBox();
					        		vbN1.setAlignment(Pos.CENTER);vbN1.setSpacing(15);
					        		vbN1.getChildren().addAll(Fournisseur,unite,hbox2,informText);
					        			
					        		VBox vbN2=new VBox();
						        		vbN2.setAlignment(Pos.CENTER);vbN2.setSpacing(15);
						        		vbN2.getChildren().addAll(quantiteN,StockMin,StockMax,Peremption);
						      vbNCenter.getChildren().addAll(vbN1,vbN2);   		
		 bpaneNouveau.setCenter(vbNCenter);	
					 VBox vbN3=new VBox();
		        		vbN3.setAlignment(Pos.CENTER);vbN3.setPadding(new Insets(12));
		        		JFXButton butonValid=new JFXButton("Valider");
		        				try{butonValid.setGraphic(new ImageView(new Image("icon/done24.png")));}catch(Exception ex){}
		        				butonValid.setStyle("-fx-text-fill: white;-fx-font-size: 16;");
		        		vbN3.getChildren().addAll(butonValid);
		 bpaneNouveau.setRight(vbN3);
		
		 
		 if(!logControl.AjouteAlow())
		 {
			 butonValid.setGraphic(new ImageView(new Image("icon/lockRed1.png")));
			 butonValid.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						new Alerter(" Action non autorisée",1);
					}});
		 }else{
			 butonValid.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						//hbPI.getChildren().add(loadProgressTop);
						try{
						long code=Long.parseLong(labCode.getText());
						int codeint=Integer.parseInt(labCode.getText());
						long quant=Long.parseLong(quantiteN.getText());
						long prixa=Long.parseLong(prixA.getText());
						long prixv=0;
						if(taxActive.isSelected()){
							prixv=Long.parseLong(prixV.getText())+Long.parseLong(prixV.getText())*Long.parseLong(leTax.getText())/100;
						}else{
							prixv=Long.parseLong(prixV.getText());
							}
						long stockmin=Long.parseLong(StockMin.getText());
						long stockmax=Long.parseLong(StockMin.getText());
						java.sql.Date dateEntrer=java.sql.Date.valueOf(LocalDate.now());
						java.sql.Date dateexpire=java.sql.Date.valueOf(Peremption.getValue());
						
						dataStock.add(new produitStock(codeint,comboBoxNDCI.getValue(),unite.getText(),quant,dateEntrer.toString(),dateexpire.toString(),0,Fournisseur.getText(),"..."));
						
						gestionContenu.saveNewProduit(code,
										comboBoxNDCI.getValue(),famille.getText(),
										Fournisseur.getText(), unite.getText(),
										quant, prixa,
										prixv, 0,stockmin ,
										stockmax,dateEntrer,
										dateexpire, 0, 0);
					     
						}catch(NumberFormatException ex){
							new Alerter("Nombre invalide \n"+ex.getMessage());
							//ex.printStackTrace();
							}
						tableStockView.refresh();
					}
					 
				 });
		 	}
		 
		//***************************************************** Nouveau produits  Fin **************************
		       
	        
	        
	        
	        
	        
	        
	      //***************************************************** RechercheQuery **************************
	        Tab RechercheQuery=new Tab("Recherche/Query");RechercheQuery.setClosable(false);
	        			BorderPane RechercheQueryContent=new BorderPane();RechercheQueryContent.setPadding(new Insets(10));
	        			BorderPane RechercheQueryContentCenter=new BorderPane();RechercheQueryContent.setCenter(RechercheQueryContentCenter);
	        
	        			VBox vBoxRecher=new VBox();vBoxRecher.setSpacing(6);vBoxRecher.setAlignment(Pos.TOP_LEFT);
	        					vBoxRecher.setStyle("-fx-background-color: #26a69a");
	        					vBoxRecher.setPadding(new Insets(6));
			        			JFXToggleButton stock=new JFXToggleButton();
			        			stock.setStyle("-fx-font-size: 15;-fx-text-fill: white");
			        			stock.setText("Stock");
			        			
			        			
			        			HBox hBoxrech=new HBox();hBoxrech.setAlignment(Pos.CENTER);hBoxrech.setPadding(new Insets(10));
			        				hBoxrech.setSpacing(15);
			        				JFXComboBox<String> comboBoxRech=new JFXComboBox<String>();
			        									comboBoxRech.setPrefWidth(400);
			        									comboBoxRech.setPromptText(" D.C.I");
			        									comboBoxRech.setEditable(true);
			        									comboBoxRech.setFocusColor(Paint.valueOf("#ffffff"));
			        									comboBoxRech.setUnFocusColor(Paint.valueOf("#DEDEDE"));
			        									comboBoxRech.setItems(ComboDCI.getItems());
			        							TextFields.bindAutoCompletion(comboBoxRech.getEditor(),(Collection<String>) comboBoxRech.getItems());
			    					
			        				
			        			VBox hBoxrechCenter=new VBox();hBoxrechCenter.setSpacing(20);hBoxrechCenter.setAlignment(Pos.CENTER);
			        			
				        			VBox hBoxrechCenter1=new VBox();hBoxrechCenter1.setSpacing(5);
				        					hBoxrechCenter1.setPadding(new Insets(40));
					        				Label lab1=new Label();
					        				lab1.setPrefWidth(200);
						        			lab1.setAlignment(Pos.CENTER);
					        				TitledPane labPane=new TitledPane();
					        				labPane.setCollapsible(false);
					        				labPane.setText("CMM");
					        				labPane.setContent(lab1);
					        				lab1.getStyleClass().add("rechercheLab");
					        				Label lab2=new Label();
					        				lab2.setPrefWidth(200);
					        				lab2.setWrapText(true);
						        			lab2.setAlignment(Pos.CENTER);
					        				lab2.getStyleClass().add("rechercheLab");
					        				TitledPane labPane1=new TitledPane();
					        				labPane1.setCollapsible(false);
					        				labPane1.setText("FOURNISSEUR");
					        				labPane1.setContent(lab2);
					        				hBoxrechCenter1.getChildren().addAll(labPane,labPane1);
					        				hBoxrechCenter1.setPrefWidth(250);
					        				hBoxrechCenter1.setMaxWidth(250);
					        				hBoxrechCenter1.setMinWidth(250);
					        			//	hBoxrechCenter1.setStyle("-fx-background-color: linear-gradient(#80cbc4,#1e88e5)");
					        				
					        	HBox hBoxrechforVB=new HBox();hBoxrechforVB.setSpacing(70);	
					        	hBoxrechforVB.setAlignment(Pos.CENTER);
				        			VBox hBoxrechCenter2=new VBox();hBoxrechCenter2.setSpacing(5);
				        					hBoxrechCenter2.setPadding(new Insets(40));
						        			Label lab3=new Label();
						        			lab3.setPrefWidth(200);
						        			lab3.setAlignment(Pos.CENTER);
						        			Label lab4=new Label();
						        			lab4.setPrefWidth(200);
						        			lab4.setAlignment(Pos.CENTER);
						        			Label lab5=new Label();
						        			lab5.setPrefWidth(200);
						        			lab5.setAlignment(Pos.CENTER);
						        			Label labs=new Label();
						        			labs.setPrefWidth(200);
						        			labs.setAlignment(Pos.CENTER);
						        			TitledPane labPane2=new TitledPane();
					        				labPane2.setCollapsible(false);
					        				labPane2.setText("UNITE");
					        				labPane2.setContent(lab3);
					        				TitledPane labPane3=new TitledPane();
					        				labPane3.setCollapsible(false);
					        				labPane3.setText("PRIX");
					        				labPane3.setContent(lab4);
					        				TitledPane labPane4=new TitledPane();
					        				labPane4.setCollapsible(false);
					        				labPane4.setText("QUANTITE");
					        				labPane4.setContent(lab5);
					        				TitledPane labPanes=new TitledPane();
					        				labPanes.setCollapsible(false);
					        				labPanes.setText("Seuil de commande");
					        				labPanes.setContent(labs);
						        			lab3.getStyleClass().add("rechercheLab");
						        			lab4.getStyleClass().add("rechercheLab");
						        			lab5.getStyleClass().add("rechercheLab");
						        			hBoxrechCenter2.getChildren().addAll(labPane2,labPane3,labPane4,labPanes);
						        			hBoxrechCenter2.setPrefWidth(250);
					        				hBoxrechCenter2.setMaxWidth(250);
					        				hBoxrechCenter2.setMinWidth(250);
						        			//hBoxrechCenter2.setStyle("-fx-background-color: linear-gradient(#4db6ac,#1e88e5)");
				        			VBox hBoxrechCenter3=new VBox();hBoxrechCenter3.setSpacing(5);
				        					hBoxrechCenter3.setPadding(new Insets(40));
						        			Label lab6=new Label();
						        			lab6.setPrefWidth(200);
						        			lab6.setAlignment(Pos.CENTER);
						        			Label lab7=new Label();
						        			lab7.setAlignment(Pos.CENTER);
						        			lab7.setPrefWidth(200);
						        			Label lab8=new Label();
						        			lab8.setAlignment(Pos.CENTER);
						        			lab8.setPrefWidth(200);
						        			TitledPane labPane5=new TitledPane();
						        			labPane5.setCollapsible(false);
						        			labPane5.setText("STOCK MIN");
						        			labPane5.setContent(lab6);
					        				TitledPane labPane6=new TitledPane();
					        				labPane6.setCollapsible(false);
					        				labPane6.setText("STOCK MAX");
					        				labPane6.setContent(lab7);
					        				TitledPane labPane7=new TitledPane();
					        				labPane7.setCollapsible(false);
					        				labPane7.setText("PEREMPTION");
					        				labPane7.setContent(lab8);
						        			lab6.getStyleClass().add("rechercheLab");
						        			lab7.getStyleClass().add("rechercheLab");
						        			lab8.getStyleClass().add("rechercheLab");
						        			hBoxrechCenter3.getChildren().addAll(labPane5,labPane6,labPane7);
						        			hBoxrechCenter3.setPrefWidth(250);
					        				hBoxrechCenter3.setMaxWidth(250);
					        				hBoxrechCenter3.setMinWidth(250);
					        				
					        				
					        		VBox hBoxrechCenter4=new VBox();hBoxrechCenter4.setSpacing(5);
					        		hBoxrechCenter4.setPadding(new Insets(40));
						        			Label lab9=new Label();
						        			lab9.setPrefWidth(200);
						        			lab9.setAlignment(Pos.CENTER);
						        			JFXTextField lab10=new JFXTextField();
						        			lab10.setPrefWidth(200);
						        			lab10.setAlignment(Pos.CENTER);
						        			Label lab11=new Label();
						        			lab11.setPrefWidth(200);
						        			lab11.setAlignment(Pos.CENTER);
						        			TitledPane labPane8=new TitledPane();
						        			labPane8.setCollapsible(false);
						        			labPane8.setText("CODE");
						        			labPane8.setContent(lab9);
					        				TitledPane labPane9=new TitledPane();
					        				labPane9.setCollapsible(false);
					        				labPane9.setText("Emplacement");
					        				labPane9.setContent(lab10);
					        				TitledPane labPane10=new TitledPane();
					        				labPane10.setCollapsible(false);
					        				labPane10.setText("DATE-ENTRE EN STOCK");
					        				labPane10.setContent(lab11);
						        			lab9.getStyleClass().add("rechercheLab");
						        			lab10.getStyleClass().add("rechercheLab");
						        			lab11.getStyleClass().add("rechercheLab");
						        			hBoxrechCenter4.getChildren().addAll(labPane8,labPane9,labPane10);
						        			hBoxrechCenter4.setPrefWidth(250);
					        				hBoxrechCenter4.setMaxWidth(250);
					        				hBoxrechCenter4.setMinWidth(250);
					        				hBoxrechforVB.getChildren().addAll(hBoxrechCenter4,hBoxrechCenter2,hBoxrechCenter3);
				        			
					        				Label prixDachat=new Label();
					        				prixDachat.getStyleClass().add("rechercheLab");
					        				
					        				
					        		ListView<String> listeTogleStock=new ListView<String>();
					        		listeTogleStock.setPrefWidth(395);
					        		listeTogleStock.setPadding(new Insets(10,0,0,0));
					        		listeTogleStock.setStyle("-fx-font-size: 15;");
					        		hBoxrechCenter.getChildren().addAll(hBoxrechCenter1,hBoxrechforVB);
									RechercheQueryContentCenter.setBottom(prixDachat);
									prixDachat.setText("Liste des produits essenciel des du Pays");
									RechercheQueryContentCenter.setTop(hBoxrech);

									
			        				queryDataListView=gestionContenu.SELECTfromTheDataBase();
			        				
			        				TableView<produitDataBase> tableViewBaseData=new TableView<produitDataBase>();
							        tableViewBaseData.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
							        RechercheQueryContentCenter.setCenter(tableViewBaseData);
			        				
			        				TableColumn<produitDataBase,Integer> BaseColomn1=new TableColumn<produitDataBase, Integer>();
			        				BaseColomn1.setText("DCI");
			        				BaseColomn1.setPrefWidth(250);
			        				BaseColomn1.setCellValueFactory(new PropertyValueFactory<produitDataBase,Integer>("DCI"));
			        				
			        				TableColumn<produitDataBase,String> BaseColomn2=new TableColumn<produitDataBase, String>();
			        				BaseColomn2.setText("Forme-Dose");
			        				BaseColomn2.setPrefWidth(250);
			        				BaseColomn2.setCellValueFactory(new PropertyValueFactory<produitDataBase,String>("FormeDose"));
			        				
			        				TableColumn<produitDataBase,String> BaseColomn3=new TableColumn<produitDataBase, String>();
			        				BaseColomn3.setText("Classe");
			        				BaseColomn3.setPrefWidth(200);
			        				BaseColomn3.setCellValueFactory(new PropertyValueFactory<produitDataBase,String>("Classe"));
			        				
			        				TableColumn<produitDataBase,Double> BaseColomn4=new TableColumn<produitDataBase, Double>();
			        				BaseColomn4.setText("Traitement");
			        				BaseColomn4.setPrefWidth(200);
			        				BaseColomn4.setCellValueFactory(new PropertyValueFactory<produitDataBase,Double>("Traitememt"));
			        				
			        				tableViewBaseData.getColumns().addAll(BaseColomn1,BaseColomn2,BaseColomn3,BaseColomn4);
			        				tableViewBaseData.setItems(queryDataListView);
			        				TableFilter filter2=new TableFilter(tableViewBaseData);
			        				
			        				
			        			stock.setOnAction(new EventHandler<ActionEvent>(){
												@Override
												public void handle(ActionEvent arg0) {
													// TODO Auto-generated method stub
													if(stock.isSelected())
													{
														comboBoxRech.setPromptText(" D.C.I");
														hBoxrech.getChildren().addAll(comboBoxRech);
														prixDachat.setText(null);
														RechercheQueryContentCenter.setCenter(hBoxrechCenter);
								        			}else
								        				{
															hBoxrech.getChildren().removeAll(comboBoxRech);
															prixDachat.setText("Liste des produits essenciel du Pays");
															RechercheQueryContentCenter.setCenter(tableViewBaseData);
														}
													
												}
												 
											 });
			        		
			        			
			        			comboBoxRech.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		        					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        					if(comboBoxRech.getValue()!=null)
		        							{
		        							UnproduitDuStock unProduit=gestionContenu.AproductFromMesproduits(comboBoxRech.getValue());
		        							
		        										lab1.setText(""+unProduit.getCMM());
		        										lab2.setText(unProduit.getFournisseur());
		        										lab3.setText(unProduit.getUnite());
		        										lab4.setText(""+unProduit.getPrixVente());
		        										lab5.setText(""+unProduit.getQuantite());
		        										lab6.setText(""+unProduit.getStockMin());
		        										lab7.setText(""+unProduit.gettStockMax());
		        										lab8.setText(""+unProduit.getDateExp());
		        										lab9.setText(""+unProduit.getCode());
		        										lab10.setText(unProduit.getEmplacement());
		        										labs.setText(""+Math.round(unProduit.getSeuilcommand()));
		        										lab11.setText(""+unProduit.getDateEntrer());
		        										prixDachat.setText("PRIX D'ACHAT :   "+unProduit.getPrixAchat()+
		        														" FG                                  "+(int)unProduit.getQJ()+" Unités vendues");
 				        				      } 
				        						
		        					}
		        				});
			        			
			        	vBoxRecher.getChildren().addAll(stock,new Separator());
	        			RechercheQueryContent.setRight(vBoxRecher);
	        			RechercheQueryContentCenter.setPadding(new Insets(0,10,5,0));
	        			RechercheQuery.setContent(RechercheQueryContent);
	        tabPaneStock.getTabs().addAll(TabLesProduits,TabNouveuProduit,RechercheQuery);
	        stockPane.setCenter(tabPaneStock);
	     //***************************************************** RechercheQuery Fin **************************
		  return stockPane;
}
public BorderPane borderPanelivraison(){
	
BorderPane LivraosonPane=new BorderPane();
		   LivraosonPane.setPadding(new Insets(15));
    
        VBox hBLBottom=new VBox();hBLBottom.setSpacing(50);hBLBottom.setPadding(new Insets(10,0,0,0));
        VBox vbINFO=new VBox();vbINFO.setSpacing(5);
        VBox vbMODE=new VBox();vbMODE.setSpacing(5);
		        
        		Text parametre=new Text("Paramètre");parametre.setStyle("-fx-fill: white;-fx-font-size: 32;");
        		Text infoStruc=new Text("Les informations sur la Pharmacie");infoStruc.setStyle("-fx-fill: white;-fx-font-size: 22;");
		        Text NomPharmaci=new Text("Nom de la pharmacy");NomPharmaci.setStyle("-fx-fill: white;-fx-font-size: 14;");
		        	JFXTextField nomText=new JFXTextField();
				        	nomText.setPadding(new Insets(0));
				        	nomText.setAlignment(Pos.BASELINE_LEFT);
				        	nomText.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
				        	nomText.setFocusColor(Paint.valueOf("white"));
				        	nomText.setUnFocusColor(Paint.valueOf("#B7B7B7"));
		        Text Adress=new Text("Adresse");Adress.setStyle("-fx-fill: white;-fx-font-size: 14;");
		        	JFXTextField ADRESSText=new JFXTextField();
				        	ADRESSText.setPadding(new Insets(0));
				        	ADRESSText.setAlignment(Pos.BASELINE_LEFT);
				        	ADRESSText.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
				        	ADRESSText.setFocusColor(Paint.valueOf("white"));
				        	ADRESSText.setUnFocusColor(Paint.valueOf("#B7B7B7"));
		        Text Mail=new Text("Mail");Mail.setStyle("-fx-fill: white;-fx-font-size: 14;");
		        JFXTextField mailText=new JFXTextField();
					        mailText.setPadding(new Insets(0));
				        	mailText.setAlignment(Pos.BASELINE_LEFT);
				        	mailText.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
				        	mailText.setFocusColor(Paint.valueOf("white"));
				        	mailText.setUnFocusColor(Paint.valueOf("#B7B7B7"));
		        
		        Text ModeAppro=new Text("Mode d'approvision");ModeAppro.setStyle("-fx-fill: white;-fx-font-size: 22;");
		        Text interApprov=new Text("Interval entre deux Approvision");interApprov.setStyle("-fx-fill: white;-fx-font-size: 14;");
		        	JFXTextField interVText=new JFXTextField();
					        	interVText.setPadding(new Insets(0));
					        	interVText.setPromptText(" En mois >0");
					        	interVText.setAlignment(Pos.BASELINE_LEFT);
					        	interVText.setStyle("-fx-text-fill: white;-fx-font-size: 17;");
					        	interVText.setFocusColor(Paint.valueOf("white"));
					        	interVText.setUnFocusColor(Paint.valueOf("#B7B7B7"));
		        Text delaiLiv=new Text("Délait de livraison");delaiLiv.setStyle("-fx-fill: white;-fx-font-size: 14;");
		        	JFXTextField delaitLivText=new JFXTextField();
					        	delaitLivText.setPadding(new Insets(0));
					        	delaitLivText.setPromptText(" En jours >0");
					        	delaitLivText.setAlignment(Pos.BASELINE_LEFT);
					        	delaitLivText.setStyle("-fx-text-fill: white;-fx-font-size: 17;");
					        	delaitLivText.setFocusColor(Paint.valueOf("white"));
					        	delaitLivText.setUnFocusColor(Paint.valueOf("#B7B7B7"));
					        	
					        	HBox hbValid=new HBox();hbValid.setAlignment(Pos.CENTER);
				JFXButton valider=new JFXButton("Valider");	  
				valider.setStyle("-fx-text-fill: white;-fx-font-size: 16;-fx-background-color: black;");      
				hbValid.getChildren().add(valider);
				valider.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						gestionContenu.saveParametre(nombrePositif(interVText.getText()), nombrePositif(delaitLivText.getText()), nomText.getText(), mailText.getText(), ADRESSText.getText());
					}
					
				});
		vbINFO.getChildren().addAll(infoStruc,new Separator(),NomPharmaci,nomText,Adress,ADRESSText,Mail,mailText);
		vbMODE.getChildren().addAll(ModeAppro,new Separator(),interApprov,interVText,delaiLiv,delaitLivText);
        hBLBottom.getChildren().addAll(vbINFO,vbMODE,hbValid);
        
        /**
         * 
         * historique des connections
         * 
         */
        
        BorderPane BPhistorique=new BorderPane();
        BPhistorique.setPadding(new Insets(15));
        		HBox hboxHisto=new HBox();hboxHisto.setAlignment(Pos.CENTER);
	        		Text hitoriqueText=new Text("Historique des ventes");hitoriqueText.setStyle("-fx-fill: white;-fx-font-size: 22;");
	        		hboxHisto.getChildren().add(hitoriqueText);
	        		BPhistorique.setTop(hboxHisto);
	        		
		        TableView<historiqueVenteData> tableViewHistorique=new TableView<historiqueVenteData>();
		        tableViewHistorique.setEditable(true);
		        tableViewHistorique.setStyle("-fx-table-color: red;");
		        tableViewHistorique.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				
				TableColumn<historiqueVenteData,Integer> HistoColomn1=new TableColumn<historiqueVenteData, Integer>();
				HistoColomn1.setText("DATE");
				HistoColomn1.setStyle("-fx-alignment: center");
				HistoColomn1.setPrefWidth(150);
				HistoColomn1.setCellValueFactory(new PropertyValueFactory<historiqueVenteData,Integer>("DATE"));
				
				TableColumn<historiqueVenteData,String> HistoColomn2=new TableColumn<historiqueVenteData, String>();
				HistoColomn2.setText("Q.Vendue");
				HistoColomn2.setStyle("-fx-alignment: center");
				HistoColomn2.setPrefWidth(150);
				HistoColomn2.setCellValueFactory(new PropertyValueFactory<historiqueVenteData,String>("NPVensu"));
				
				TableColumn<historiqueVenteData,String> HistoColomn3=new TableColumn<historiqueVenteData, String>();
				HistoColomn3.setText("Valeur");
				HistoColomn3.setPrefWidth(150);
				HistoColomn3.setCellValueFactory(new PropertyValueFactory<historiqueVenteData,String>("VALEUR"));
				
				TableColumn<historiqueVenteData,String> HistoColomn4=new TableColumn<historiqueVenteData, String>();
				HistoColomn4.setText("Utilisateurs connectés");
				HistoColomn4.setPrefWidth(350);
				HistoColomn4.setCellValueFactory(new PropertyValueFactory<historiqueVenteData,String>("USER"));
				HistoColomn4.setCellFactory(TextFieldTableCell.<historiqueVenteData>forTableColumn());
				HistoColomn4.setOnEditCommit(new EventHandler<CellEditEvent<historiqueVenteData, String>>() {
					@Override
					public void handle(CellEditEvent<historiqueVenteData, String> event) 
					{
					}
			    });
				HistoColomn4.setEditable(true);
				tableViewHistorique.setItems(gestionContenu.HistoriqueDATA());
				tableViewHistorique.getColumns().addAll(HistoColomn1,HistoColomn2,HistoColomn3,HistoColomn4);
        
		BPhistorique.setCenter(tableViewHistorique);
        LivraosonPane.setTop(parametre); 
        LivraosonPane.setLeft(hBLBottom);
        LivraosonPane.setCenter(BPhistorique);
       
       return LivraosonPane;
    
}
public BorderPane borderPaneCommande(){
	
	BorderPane BPCommande=new BorderPane();
	BPCommande.setPadding(new Insets(5));
    BorderPane bpCTop=new BorderPane();
    
    VBox VBC=new VBox();VBC.setPadding(new Insets(0,0,10,0));
        VBC.setSpacing(3);	
        VBC.setAlignment(Pos.CENTER);
        VBC.setPrefWidth(250);
        VBC.setMaxWidth(250);
        VBC.setPrefHeight(20);
        VBC.setMaxHeight(20);
        
        JFXTextField NumBonDeComande=new JFXTextField();
        NumBonDeComande.setPadding(new Insets(0));
        NumBonDeComande.setPromptText("Numéro du bon de commande");
        NumBonDeComande.setAlignment(Pos.BASELINE_LEFT);
        NumBonDeComande.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
        NumBonDeComande.setFocusColor(Paint.valueOf("white"));
        NumBonDeComande.setUnFocusColor(Paint.valueOf("green"));
        
        JFXTextField Structure=new JFXTextField();
        Structure.setPadding(new Insets(0));
        Structure.setPromptText("Structure ");
        Structure.setAlignment(Pos.BASELINE_LEFT);
        Structure.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
        Structure.setFocusColor(Paint.valueOf("white"));
        Structure.setUnFocusColor(Paint.valueOf("green"));
        
        JFXTextField AdresserA=new JFXTextField();
        AdresserA.setPadding(new Insets(0));
        AdresserA.setPromptText("Adressé à");
        AdresserA.setAlignment(Pos.BASELINE_LEFT);
        AdresserA.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
        AdresserA.setFocusColor(Paint.valueOf("white"));
        AdresserA.setUnFocusColor(Paint.valueOf("green"));
        
        JFXTextField urgent=new JFXTextField();
        urgent.setPadding(new Insets(0));
        urgent.setPromptText("Commande urgente");
        urgent.setAlignment(Pos.BASELINE_LEFT);
        urgent.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
        urgent.setFocusColor(Paint.valueOf("white"));
        urgent.setUnFocusColor(Paint.valueOf("green"));
        
   VBC.getChildren().addAll(NumBonDeComande,Structure,AdresserA,urgent);  
   bpCTop.setLeft(VBC);
  
    HBox hBocC=new HBox();hBocC.setAlignment(Pos.CENTER);hBocC.setSpacing(4);hBocC.setPadding(new Insets(4));
   // hBocC.setStyle("-fx-background-color: green");
    
    JFXButton baseNodeListe=new JFXButton();
    try{
    		baseNodeListe.setGraphic(new ImageView(new Image("icon/menu18.png")));
    }catch(Exception e){
    	//nothing to do
    }
    JFXToggleButton CommandUrgent=new JFXToggleButton();
    CommandUrgent.setText("Commandes urgentes ");
    
    Button addCommand=new Button("Ajouter");
    Button cancel=new Button("Retirer");
    Button save=new Button("Valider"); //save.setStyle("-fx-border-radius: 80");
    Button print=new Button("Imprimer");
    
    hBocC.getChildren().addAll(addCommand,cancel,CommandUrgent,save,print);

    PopOver popoverCommand=new PopOver(hBocC);
    popoverCommand.setArrowLocation(ArrowLocation.TOP_RIGHT);
    popoverCommand.setDetachable(false);
    popoverCommand.setMinHeight(20);
    popoverCommand.setPrefHeight(20);
	
   
    
    baseNodeListe.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			popoverCommand.show(baseNodeListe);
		}
		 
	 });
    bpCTop.setRight(baseNodeListe);
    BPCommande.setTop(bpCTop);
	   
	  
Callback<TableColumn<produitAcommander, String>, TableCell<produitAcommander, String>> cellFactoryStringComand = ( TableColumn<produitAcommander, String> p) -> new EditingCellCommand();
Callback<TableColumn<produitAcommander, String>, TableCell<produitAcommander, String>> cellFactoryComboBox = ( TableColumn<produitAcommander, String> p) -> new editingComboBox();
  
    TableView<produitAcommander> tableViewCommade=new TableView<produitAcommander>();
    tableViewCommade.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    tableViewCommade.setEditable(true);
	
	TableColumn<produitAcommander,Integer> TC_Colomn1=new TableColumn<produitAcommander, Integer>();
	TC_Colomn1.setText("N");
	TC_Colomn1.setPrefWidth(50);
	TC_Colomn1.setStyle("-fx-alignment: center");
	TC_Colomn1.setCellValueFactory(new PropertyValueFactory<produitAcommander,Integer>("NUM"));
	
	TableColumn<produitAcommander,String> TC_Colomn2=new TableColumn<produitAcommander, String>();
	TC_Colomn2.setText("Désignation");
	TC_Colomn2.setPrefWidth(500);
	TC_Colomn2.setCellValueFactory(new PropertyValueFactory<produitAcommander,String>("DESIGNATION"));
	//TC_Colomn2.setCellFactory(ComboBoxTableCell.forTableColumn(ComboDCI.getItems()));
	TC_Colomn2.setCellFactory(cellFactoryComboBox);
	TC_Colomn2.setOnEditCommit(new EventHandler<CellEditEvent<produitAcommander, String>>() {
		@Override
		public void handle(CellEditEvent<produitAcommander, String> event) 
		{
			((produitAcommander) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDESIGNATION(event.getNewValue());
			tableViewCommade.refresh();
		}
    });
	
	TC_Colomn2.setEditable(true);
	TableColumn<produitAcommander,String> TC_Colomn3=new TableColumn<produitAcommander, String>();
	TC_Colomn3.setText("Unité");
	TC_Colomn3.setPrefWidth(100);
	TC_Colomn3.setCellValueFactory(new PropertyValueFactory<produitAcommander,String>("UNIT"));
	TC_Colomn3.setCellFactory(cellFactoryStringComand);
	TC_Colomn3.setOnEditCommit(new EventHandler<CellEditEvent<produitAcommander, String>>() {
		@Override
		public void handle(CellEditEvent<produitAcommander, String> event) 
		{
			((produitAcommander) event.getTableView().getItems().get(event.getTablePosition().getRow())).setUNIT(event.getNewValue());
			tableViewCommade.refresh();
		}
    });
	TC_Colomn3.setEditable(true);
	
	TableColumn<produitAcommander,String> TC_Colomn4=new TableColumn<produitAcommander, String>();
	TC_Colomn4.setText("SDU");
	TC_Colomn4.setPrefWidth(90);
	TC_Colomn4.setStyle("-fx-alignment: center");
	TC_Colomn4.setCellValueFactory(new PropertyValueFactory<produitAcommander,String>("SDU"));
	TC_Colomn4.setCellFactory(cellFactoryStringComand);
	TC_Colomn4.setOnEditCommit(new EventHandler<CellEditEvent<produitAcommander, String>>() {
		@Override
		public void handle(CellEditEvent<produitAcommander, String> event) 
		{
			((produitAcommander) event.getTableView().getItems().get(event.getTablePosition().getRow())).setSDU(""+nombrePositif(event.getNewValue()));
			tableViewCommade.refresh();
		}
    });
	TC_Colomn4.setEditable(true);
	
	TableColumn<produitAcommander,String> TC_Colomn5=new TableColumn<produitAcommander, String>();
	TC_Colomn5.setText("Stock Max");
	TC_Colomn5.setPrefWidth(100);
	TC_Colomn5.setStyle("-fx-alignment: center");
	TC_Colomn5.setCellValueFactory(new PropertyValueFactory<produitAcommander,String>("BESOIN"));
	TC_Colomn5.setCellFactory(cellFactoryStringComand);
	TC_Colomn5.setOnEditCommit(new EventHandler<CellEditEvent<produitAcommander, String>>() {
		@Override
		public void handle(CellEditEvent<produitAcommander, String> event) 
		{
			((produitAcommander) event.getTableView().getItems().get(event.getTablePosition().getRow())).setBESOIN(""+nombrePositif(event.getNewValue()));
			tableViewCommade.refresh();
		}
    });
	TC_Colomn5.setEditable(true);
	
	TableColumn<produitAcommander,String> TC_Colomn6=new TableColumn<produitAcommander, String>();
	TC_Colomn6.setText("Q.Commandée");
	TC_Colomn6.setPrefWidth(130);
	TC_Colomn6.setStyle("-fx-alignment: center");
	TC_Colomn6.setCellValueFactory(new PropertyValueFactory<produitAcommander,String>("QCOMMANDER"));
	TC_Colomn6.setCellFactory(cellFactoryStringComand);
	TC_Colomn6.setOnEditCommit(new EventHandler<CellEditEvent<produitAcommander, String>>() {
		@Override
		public void handle(CellEditEvent<produitAcommander, String> event) 
		{
			int position=event.getTablePosition().getRow();
			produitAcommander unElement=tableViewCommade.getItems().get(position);
			    ((produitAcommander) event.getTableView().getItems().get(event.getTablePosition().getRow())).setQCOMMANDER(""+nombrePositif(event.getNewValue()));
                tableViewCommade.getItems().get(position).setPrixTotal(""+nombrePositif(unElement.getPrixUnitaire())*nombrePositif(event.getNewValue()));
                tableViewCommade.refresh();
			
		}
    });
	TC_Colomn6.setEditable(true);
	
	TableColumn<produitAcommander,String> TC_Colomn7=new TableColumn<produitAcommander, String>();
	TC_Colomn7.setText("Prix Unitaire");
	TC_Colomn7.setPrefWidth(120);
	TC_Colomn7.setCellValueFactory(new PropertyValueFactory<produitAcommander,String>("PrixUnitaire"));
	TC_Colomn7.setCellFactory(cellFactoryStringComand);
	TC_Colomn7.setOnEditCommit(new EventHandler<CellEditEvent<produitAcommander, String>>() {
		@Override
		public void handle(CellEditEvent<produitAcommander, String> event) 
		{

			int position=event.getTablePosition().getRow();
			produitAcommander unElement=tableViewCommade.getItems().get(position);
			    ((produitAcommander) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrixUnitaire(""+nombrePositif(event.getNewValue()));
                tableViewCommade.getItems().get(position).setPrixTotal(""+nombrePositif(unElement.getQCOMMANDER())*nombrePositif(event.getNewValue()));
                tableViewCommade.refresh();
		}
    });
	TC_Colomn7.setEditable(true);
	
	TableColumn<produitAcommander,Double> TC_Colomn8=new TableColumn<produitAcommander, Double>();
	TC_Colomn8.setText("Prix Total");
	TC_Colomn8.setPrefWidth(120);
	TC_Colomn8.setCellValueFactory(new PropertyValueFactory<produitAcommander,Double>("PrixTotal"));

	tableViewCommade.getColumns().addAll(TC_Colomn1,TC_Colomn2,TC_Colomn3,TC_Colomn4,TC_Colomn5,TC_Colomn6,TC_Colomn7,TC_Colomn8);
	tableViewCommade.setItems(dataCommand.TabCommandContenu());
	Cj.setText("Environ "+dataCommand.ConsommationJournaliere()+" produits vendus par jour");//Ici car nb_jour se calcul ds dataCommand.TabCommandContenu()
	BPCommande.setCenter(tableViewCommade);

	 Service<Void> serveceUrgent=new Service<Void>(){
 		@Override
 		protected Task<Void> createTask() {
 			// TODO Auto-generated method stub
 			return new Task<Void>(){
 				@Override
 				protected Void call() throws Exception 
 				{
 					tableViewCommade.setItems(dataCommand.CommandeUrgente());
					tableViewCommade.refresh();
 					return null;
 				}
 			};
 		}
	 };
	 Service<Void> serveceNonUrgent=new Service<Void>(){
	 		@Override
	 		protected Task<Void> createTask() {
	 			// TODO Auto-generated method stub
	 			return new Task<Void>(){
	 				@Override
	 				protected Void call() throws Exception
	 				{
	 					tableViewCommade.setItems(dataCommand.TabCommandContenu());
						tableViewCommade.refresh();
	 					return null;
	 				}
	 			};
	 		}
		 };
	 
	cancel.setOnAction(new EventHandler<ActionEvent>()
	{
		@Override
		public void handle(ActionEvent arg0) 
			{
					tableViewCommade.getItems().removeAll(tableViewCommade.getSelectionModel().getSelectedItems());
					for(int i=0;i<tableViewCommade.getItems().size();i++)
					{
						tableViewCommade.getItems().get(i).setNUM(i);
					}
					tableViewCommade.refresh();
			}
	});
	addCommand.setOnAction(new EventHandler<ActionEvent>()
	{
		@Override
		public void handle(ActionEvent arg0) 
			{
					tableViewCommade.getItems().add(new produitAcommander(tableViewCommade.getItems().size()+1,"Nouveau","Nouveau","0","0","0","0","0"));
					for(int i=0;i<tableViewCommade.getItems().size();i++)
					{
						tableViewCommade.getItems().get(i).setNUM(i);
					}
					tableViewCommade.refresh();
					
			}
	});
	CommandUrgent.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(CommandUrgent.isSelected())
				{
	    			ProgressDialog progDialog=new ProgressDialog(serveceUrgent);
	    			progDialog.setTitle("Collection des commandes urgentes ");
	    			progDialog.initOwner(MainStage);
	    			progDialog.setHeaderText("Commande uregente ......");
	    			progDialog.initModality(Modality.WINDOW_MODAL);
	    			serveceUrgent.restart();
				}else{
					ProgressDialog progDialog=new ProgressDialog(serveceNonUrgent);
	    			progDialog.setTitle("Collection des commandes ");
	    			progDialog.initOwner(MainStage);
	    			progDialog.setHeaderText("Commandes ......");
	    			progDialog.initModality(Modality.WINDOW_MODAL);
	    			serveceNonUrgent.restart();
				}
			
		}
		 
	 });
	
	
	 VBox vbPAR=new VBox();
		 vbPAR.setSpacing(3);	
		 vbPAR.setAlignment(Pos.CENTER);
		 vbPAR.setPrefWidth(250);
         vbPAR.setMaxWidth(250);
      VBox vbPAR2=new VBox();
		 vbPAR2.setSpacing(3);	
		 vbPAR2.setAlignment(Pos.CENTER);
		 vbPAR2.setPrefWidth(250);
         vbPAR2.setMaxWidth(250);
        
         JFXTextField totalComanValeur=new JFXTextField();
         	totalComanValeur.setPadding(new Insets(0));
	        totalComanValeur.setPromptText("Montant total (FG)");
	        totalComanValeur.setAlignment(Pos.BASELINE_LEFT);
	        totalComanValeur.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
	        totalComanValeur.setFocusColor(Paint.valueOf("white"));
	        totalComanValeur.setUnFocusColor(Paint.valueOf("green"));
	        totalComanValeur.focusedProperty().addListener(
	                (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
	                	for(int i=0;i<tableViewCommade.getItems().size();i++)
						{
	                		TVComanLivra +=nombrePositif(tableViewCommade.getItems().get(i).getPrixTotal());
						}
	                	totalComanValeur.setPromptText("Le prix total est : "+TVComanLivra);
	                	TVComanLivra=0;
	                });
	        
        JFXTextField camandeFaiPar=new JFXTextField();
        camandeFaiPar.setPadding(new Insets(0));
        camandeFaiPar.setPromptText("Commande fait par");
        camandeFaiPar.setAlignment(Pos.BASELINE_LEFT);
        camandeFaiPar.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
        camandeFaiPar.setFocusColor(Paint.valueOf("white"));
        camandeFaiPar.setUnFocusColor(Paint.valueOf("green"));
        
        JFXTextField FPDate=new JFXTextField();
        FPDate.setPadding(new Insets(0));
        FPDate.setPromptText("Date ");
        FPDate.setAlignment(Pos.BASELINE_LEFT);
        FPDate.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
        FPDate.setFocusColor(Paint.valueOf("white"));
        FPDate.setUnFocusColor(Paint.valueOf("green"));
        
        JFXTextField FPsignature=new JFXTextField();
        FPsignature.setPadding(new Insets(0));
        FPsignature.setPromptText("Signature");
        FPsignature.setAlignment(Pos.BASELINE_LEFT);
        FPsignature.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
        FPsignature.setFocusColor(Paint.valueOf("white"));
        FPsignature.setUnFocusColor(Paint.valueOf("green"));
        	/*......................*/
        JFXTextField camandeAprouvPar=new JFXTextField();
        camandeAprouvPar.setPadding(new Insets(0));
        camandeAprouvPar.setPromptText("Commande approuvée par");
        camandeAprouvPar.setAlignment(Pos.BASELINE_LEFT);
        camandeAprouvPar.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
        camandeAprouvPar.setFocusColor(Paint.valueOf("white"));
        camandeAprouvPar.setUnFocusColor(Paint.valueOf("green"));
        
        JFXTextField FPDateAp=new JFXTextField();
        FPDateAp.setPadding(new Insets(0));
        FPDateAp.setPromptText("Date ");
        FPDateAp.setAlignment(Pos.BASELINE_LEFT);
        FPDateAp.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
        FPDateAp.setFocusColor(Paint.valueOf("white"));
        FPDateAp.setUnFocusColor(Paint.valueOf("green"));
        
        JFXTextField FPsignatureAp=new JFXTextField();
        FPsignatureAp.setPadding(new Insets(0));
        FPsignatureAp.setPromptText("Signature");
        FPsignatureAp.setAlignment(Pos.BASELINE_LEFT);
        FPsignatureAp.setStyle("-fx-text-fill: white;-fx-font-size: 15;");
        FPsignatureAp.setFocusColor(Paint.valueOf("white"));
        FPsignatureAp.setUnFocusColor(Paint.valueOf("green"));
        
        vbPAR.getChildren().addAll(camandeFaiPar,FPDate,FPsignature); 
        vbPAR2.getChildren().addAll(camandeAprouvPar,FPDateAp,FPsignatureAp);
        HBox hBCBottom=new HBox();hBCBottom.setSpacing(50);
        hBCBottom.getChildren().addAll(vbPAR,vbPAR2,totalComanValeur);
        BPCommande.setBottom(hBCBottom);
        
        Service<Void> serviceSave=new Service<Void>(){
    		@Override
    		protected Task<Void> createTask() {
    			// TODO Auto-generated method stub
    			return new Task<Void>(){

    				@Override
    				protected Void call() throws Exception {

    	    			// TODO Auto-generated method stub
    	    			pdfgestion gestionPDF=new pdfgestion();
    	    			gestionPDF.pdfGeneraterCommande(NumBonDeComande.getText(), Structure.getText(), AdresserA.getText(), urgent.getText());
    	    			for(int i=0;i<tableViewCommade.getItems().size();i++){
    	    				gestionPDF.lesProduits(tableViewCommade.getItems().get(i).getNUM(),
    	    										tableViewCommade.getItems().get(i).getDESIGNATION(),
    	    										tableViewCommade.getItems().get(i).getUNIT(),
    	    										tableViewCommade.getItems().get(i).getSDU(),
    	    										tableViewCommade.getItems().get(i).getBESOIN(),
    	    										tableViewCommade.getItems().get(i).getQCOMMANDER(),
    	    										tableViewCommade.getItems().get(i).getPrixUnitaire(),
    	    										tableViewCommade.getItems().get(i).getPrixTotal());
    	    				
    	    			}
    	    			gestionPDF.AddTable();
    					gestionPDF.theFooter(totalComanValeur.getText(), camandeFaiPar.getText(), FPsignature.getText(), FPDate.getText(), camandeAprouvPar.getText(), FPsignatureAp.getText(), FPDateAp.getText());
    				
    	    		
    					return null;
    					
    				}
    				
    			};
    		}
    		
    	};
    	
        save.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent arg0) {

    			ProgressDialog progDialog=new ProgressDialog(serviceSave);
    			progDialog.setTitle("Enregistrement de la Commande ");
    			progDialog.initOwner(MainStage);
    			progDialog.setHeaderText("Enregistrement ......");
    			progDialog.initModality(Modality.WINDOW_MODAL);
    			serviceSave.restart();
    			progDialog.setOnHidden(new EventHandler<DialogEvent>( ){
    				@Override
    				public void handle(DialogEvent event) {
    					// TODO Auto-generated method stub
    					new AlerterSuccesWorning().Succes("Validé avec succès");
    				}
    			});
    		
    		}
    		 
    	 });
        
        Service<Void> servicePrint=new Service<Void>(){
    		@Override
    		protected Task<Void> createTask() {
    			// TODO Auto-generated method stub
    			return new Task<Void>(){
    				@Override
    				protected Void call() throws Exception {
    					pdfgestion gestionPDF=new pdfgestion();
    					gestionPDF.OpenPDF(NumBonDeComande.getText());
						return null;
    				}
    			};
    		}
        };
        print.setOnAction(new EventHandler<ActionEvent>(){
     		@Override
     		public void handle(ActionEvent arg0) {

     			ProgressDialog progDialog=new ProgressDialog(servicePrint);
     			progDialog.setTitle("Impression ");
     			progDialog.initOwner(MainStage);
     			progDialog.setHeaderText("Impression en cours ......");
     			progDialog.initModality(Modality.WINDOW_MODAL);
     			servicePrint.restart();
     			progDialog.setOnHidden(new EventHandler<DialogEvent>( ){
     				@Override
     				public void handle(DialogEvent event) {
     					// TODO Auto-generated method stub
     					
     				}
     			});
     		
     		}
     		 
     	 });
    	
        
		return BPCommande;
  }
 @SuppressWarnings("deprecation")
public TabPane StatistiquePane(){
	 int total=0; 
	 TabPane tabPaneVente=new TabPane();
     tabPaneVente.setPadding(new Insets(-5));
		        Tab Hitorique=new Tab("Evolution de la Consommation");
		        Hitorique.setClosable(false);
		        		BorderPane bPaneHitorique=new BorderPane();
		        		bPaneHitorique.setStyle("-fx-background-color: #009688");
		        		bPaneHitorique.setPadding(new Insets(10));
		        		
		                final CategoryAxis xAxis = new CategoryAxis();
		                final NumberAxis yAxis = new NumberAxis();
		                yAxis.setLabel("Quantité de produits vendue");
		                xAxis.setLabel("Mois"); 
		                
		                
		                final LineChart<String,Number> lineChart =  new LineChart<String,Number>(xAxis,yAxis);
		               // lineChart.setTitle("Consommation");
		                                        
		                XYChart.Series<String, Number> series = new Series<String, Number>();
		                series.setName(" Courbe de consommations mensuelles");
		                
		                try {
		                	int sum=0,mois=0;
		                	String dateChart="";
		        			connection = DriverManager.getConnection(url,"balamou","jean");
		        			statement = connection.createStatement();
		        			ResultSet RESULTSETDCI= statement.executeQuery("SELECT Date, NombreProdVendu FROM HistoriqueDesVente");
		        			RESULTSETDCI.first();
		        			mois=RESULTSETDCI.getDate("Date").getMonth();
		        			dateChart=""+RESULTSETDCI.getDate("Date").toLocalDate().getYear()+"-"+RESULTSETDCI.getDate("Date").toLocalDate().getMonth();
        					sum+=RESULTSETDCI.getInt("NombreProdVendu");
        					total+=sum;
        					
		        			while(RESULTSETDCI.next())
		        					{
		        						if(RESULTSETDCI.getDate("Date").getMonth()==mois){
		        							sum+=RESULTSETDCI.getInt("NombreProdVendu");
		        						}else{
		        							series.getData().add(new XYChart.Data<String,Number>(dateChart,sum));
		        							sum=RESULTSETDCI.getInt("NombreProdVendu");
		        							mois=RESULTSETDCI.getDate("Date").getMonth();
		        							dateChart=""+RESULTSETDCI.getDate("Date").toLocalDate().getYear()+"-"+RESULTSETDCI.getDate("Date").toLocalDate().getMonth();
				        					}
		        						total+=RESULTSETDCI.getInt("NombreProdVendu");
		        					}
		        			series.getData().add(new XYChart.Data<String,Number>(dateChart,sum));
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
		                lineChart.getData().add(series);
		                bPaneHitorique.setCenter(lineChart);	
		        Hitorique.setContent(bPaneHitorique);
		        
//******************************** second Table ****************************************
		        BorderPane BPProdPareto=new BorderPane();
		        BPProdPareto.setStyle("-fx-background-color: #444444");
		        BPProdPareto.setPadding(new Insets(10));
		        BPProdPareto.setPrefHeight(600);
		        BPProdPareto.setPrefWidth(900);
		        PopOver popoverPareto=new PopOver(BPProdPareto);
		        popoverPareto.setTitle("Détail du diagramme 80-20");
		        popoverPareto.setDetachable(true);
		        popoverPareto.setArrowLocation(ArrowLocation.TOP_CENTER);
		        popoverPareto.setDetachable(false);
		    	
		        HBox hbbuttonL=new HBox();
		        hbbuttonL.setAlignment(Pos.CENTER);
		        JFXButton buttonL=new JFXButton();
		        try{buttonL.setGraphic(new ImageView(new Image("icon/menu18.png")));}catch(Exception ex){}
		        buttonL.setOnAction(new EventHandler<ActionEvent>(){
		    		@Override
		    		public void handle(ActionEvent arg0) {
		    			// TODO Auto-generated method stub
		    			popoverPareto.setDetached(true);
		    			popoverPareto.show(buttonL);
		    		}
		    		 
		    	 });
		        hbbuttonL.getChildren().add(buttonL);
		        
		        HBox hbListV=new HBox();hbListV.setSpacing(15);
		        hbListV.setAlignment(Pos.CENTER);
		        VBox Vb1=new VBox();Vb1.setSpacing(15); Vb1.setAlignment(Pos.CENTER);
		        VBox VB2=new VBox();VB2.setSpacing(15);VB2.setAlignment(Pos.CENTER);
		        
		        Text tex1=new Text("Produits représentant 80% \n de la consommation");tex1.setStyle("-fx-fill: white;-fx-font-size: 20;");
		        tex1.setTextAlignment(TextAlignment.CENTER);
		        Text tex2=new Text("Produits représentant 20% \n de la consommation");tex2.setStyle("-fx-fill: white;-fx-font-size: 20;");
		        tex2.setTextAlignment(TextAlignment.CENTER);
		        ListView<String> listevenghtPourcent=new ListView<String>();listevenghtPourcent.setPrefWidth(420);
		        listevenghtPourcent.getItems().add("             % Cumul    ------    Designation");
		        listevenghtPourcent.getItems().add(" ");
		        ListView<String> listquatreVenPourcent=new ListView<String>();listquatreVenPourcent.setPrefWidth(420);
		        listquatreVenPourcent.getItems().add("            % Cumul   ------    Designation");
		        listquatreVenPourcent.getItems().add("  ");
		        Vb1.getChildren().addAll(tex1,listevenghtPourcent);
		        VB2.getChildren().addAll(tex2,listquatreVenPourcent);
		        hbListV.getChildren().addAll(Vb1,VB2);
		        
		        Tab paretoCondom=new Tab("Statistique de conssommation");
		        paretoCondom.setClosable(false);
		        		BorderPane BPCosom=new BorderPane();
		        		BPCosom.setStyle("-fx-background-color: #009688");
		        		BPCosom.setPadding(new Insets(10));
		        		BPCosom.setTop(hbbuttonL);
		        		
		        		final CategoryAxis xAxisC = new CategoryAxis();
		                final NumberAxis yAxisC = new NumberAxis();
		                yAxisC.setLabel("Pourcentage sur la consommation total");
		                xAxisC.setLabel("Code"); 
		                
		                final LineChart<String, Number> lineChartC =  new LineChart<String,Number>(xAxisC,yAxisC);
		                XYChart.Series<String, Number> seriesC = new Series<String, Number>();
		                seriesC.setName(" Diagramme 80-20");
		                
		     		ObservableList<ProduitParetoRegle> ParetoData=gestionContenu.ParetoProduit(total);
		     		for(int i=0;i<ParetoData.size();i++)
		     		{
		     			seriesC.getData().add(new XYChart.Data<String,Number>(""+ParetoData.get(i).getCode(),ParetoData.get(i).getpoucentage()));
		     			if(ParetoData.get(i).getpoucentage()<80)
		     			{
		     				listevenghtPourcent.getItems().add(ParetoData.get(i).getpoucentage()+"%   "+ParetoData.get(i).getDESIGNATION());
			     		}else{
		     				listquatreVenPourcent.getItems().add(ParetoData.get(i).getpoucentage()+"%   "+ParetoData.get(i).getDESIGNATION());
		     			}
		     		}
		     		 lineChartC.getData().add(seriesC);
		             BPCosom.setCenter(lineChartC);
		             BPProdPareto.setCenter(hbListV);
		                
		        paretoCondom.setContent(BPCosom);
//******************************** third Table ****************************************		        
		        Tab FicheDestock=new Tab("Fiche de stock");
		        FicheDestock.setClosable(false);
				        BorderPane BPficheDeStock=new BorderPane();
				        BPficheDeStock.setStyle("-fx-background-color: #009688");
				        BPficheDeStock.setPadding(new Insets(10));
				        
				        BorderPane BP1=new BorderPane();BP1.setPadding(new Insets(15));
				        
				        VBox vb11=new VBox();
				        Text dciText=new Text();dciText.setStyle("-fx-fill: yellow;-fx-font-size: 25;");
				        vb11.getChildren().addAll(dciText);
				        BP1.setLeft(vb11);
				        
				        HBox hb1=new HBox();hb1.setStyle("-fx-border-color: white");hb1.setSpacing(10);hb1.setPadding(new Insets(2));
						        VBox vb1=new VBox();vb1.setAlignment(Pos.CENTER_LEFT);
						        Text code=new Text("Code");code.setStyle("-fx-fill: white;-fx-font-size: 14;");
						        Text cmm=new Text("CMM");cmm.setStyle("-fx-fill: white;-fx-font-size: 14;");
						        Text StockDispo=new Text("Stock");StockDispo.setStyle("-fx-fill: white;-fx-font-size: 14;");
						        vb1.getChildren().addAll(code,cmm,StockDispo);
						        
						        VBox vb2=new VBox();vb2.setAlignment(Pos.CENTER_LEFT);
						        Text stockMin=new Text("Stock min");stockMin.setStyle("-fx-fill: white;-fx-font-size: 14;");
						        Text stockMax=new Text("Stock max");stockMax.setStyle("-fx-fill: white;-fx-font-size: 14;");
						        Text seuilCommand=new Text("Seuil Com.");seuilCommand.setStyle("-fx-fill: white;-fx-font-size: 14;");
						        vb2.getChildren().addAll(stockMin,stockMax,seuilCommand);
						        hb1.getChildren().addAll(vb1,vb2);
						    BP1.setRight(hb1);        
						    BPficheDeStock.setTop(BP1);   
				        
						    TableView<ficheStockData> tableFicheStockView=new TableView<ficheStockData>();
			        		
		    				TableColumn<ficheStockData,String> TFSCol1=new TableColumn<ficheStockData, String>();
		    				TFSCol1.setText("Date");
		    				TFSCol1.setPrefWidth(200);
		    				TFSCol1.setStyle("-fx-alignment: center");
		    				TFSCol1.setCellValueFactory(new PropertyValueFactory<ficheStockData,String>("DATE"));
			        	
		    				TableColumn<ficheStockData,String> TFSCol2=new TableColumn<ficheStockData, String>();
		    				TFSCol2.setText("Origine/Destination");
		    				TFSCol2.setPrefWidth(200);
		    				TFSCol2.setStyle("-fx-alignment: center");
		    				TFSCol2.setCellValueFactory(new PropertyValueFactory<ficheStockData,String>("ORIGINE"));
			        	
		    				TableColumn<ficheStockData,String> TFSCol3=new TableColumn<ficheStockData, String>();
		    				TFSCol3.setText("Entré");
		    				TFSCol3.setPrefWidth(100);
		    				TFSCol3.setStyle("-fx-alignment: center");
		    				TFSCol3.setCellValueFactory(new PropertyValueFactory<ficheStockData,String>("ENTRER"));
			        	
		    				TableColumn<ficheStockData,String> TFSCol4=new TableColumn<ficheStockData, String>();
		    				TFSCol4.setText("Sortie");
		    				TFSCol4.setPrefWidth(100);
		    				TFSCol4.setStyle("-fx-alignment: center");
		    				TFSCol4.setCellValueFactory(new PropertyValueFactory<ficheStockData,String>("SORTIE"));
			        	
		    				TableColumn<ficheStockData,String> TFSCol5=new TableColumn<ficheStockData, String>();
		    				TFSCol5.setText("Stock");
		    				TFSCol5.setStyle("-fx-alignment: center");
		    				TFSCol5.setPrefWidth(100);
		    				TFSCol5.setCellValueFactory(new PropertyValueFactory<ficheStockData,String>("LESTOCK"));
		    				
		    				TableColumn<ficheStockData,String> TFSCol6=new TableColumn<ficheStockData, String>();
		    				TFSCol6.setText("Remarque");
		    				TFSCol6.setPrefWidth(300);
		    				TFSCol6.setStyle("-fx-alignment: center");
		    				TFSCol6.setCellValueFactory(new PropertyValueFactory<ficheStockData,String>("REMARQUE"));   
				        
		    				tableFicheStockView.getColumns().addAll(TFSCol1,TFSCol2,TFSCol3,TFSCol4,TFSCol5,TFSCol6);
			    				ObservableList<ficheStockData> datafiche=FXCollections.observableArrayList();
			    				tableFicheStockView.setItems(datafiche);
			    				BPficheDeStock.setCenter(tableFicheStockView);
				        
		    				VBox hbChange=new VBox();hbChange.setSpacing(6);
		    		        hbChange.setAlignment(Pos.CENTER);
		    		        hbChange.setStyle("-fx-background-color: #444444");
		    		        hbChange.setPadding(new Insets(10));
		    		        hbChange.setPrefHeight(200);
		    		        hbChange.setPrefWidth(400);
		    		        PopOver popoverchange=new PopOver(hbChange);
		    		        popoverchange.setArrowLocation(ArrowLocation.BOTTOM_LEFT);
		    		        popoverchange.setDetachable(true);
		    		    	
		    		        
		    		        JFXButton butChange=new JFXButton();
		    		        try{butChange.setGraphic(new ImageView(new Image("icon/menu18.png")));}catch(Exception ex){}
		    		        butChange.setOnAction(new EventHandler<ActionEvent>(){
		    		    		@Override
		    		    		public void handle(ActionEvent arg0) {
		    		    			// TODO Auto-generated method stub
		    		    			popoverchange.show(butChange);
		    		    		}
		    		    		 
		    		    	 });
		    		        BPficheDeStock.setBottom(butChange);
				        
		JFXComboBox<String> ComboDCI=new JFXComboBox<String>();
							ComboDCI.setFocusColor(Paint.valueOf("#FFFFFF"));
							ComboDCI.setUnFocusColor(Paint.valueOf("#009688"));
							ComboDCI.setEditable(true);
							ComboDCI.setPromptText(" D.C.I");
							ComboDCI.setMaxWidth(300);
							ComboDCI.setPrefWidth(300);
							ComboDCI.setItems(dataDCI);
							TextFields.bindAutoCompletion(ComboDCI.getEditor(),(Collection<String>) ComboDCI.getItems());
							
	  
		    		        
		    		        Service<Void> serviceGetInfo=new Service<Void>(){
								@Override
								protected Task<Void> createTask() {
									// TODO Auto-generated method stub
									return new Task<Void>(){
										@Override
										protected Void call() throws Exception {
											// TODO Auto-generated method stub
											Platform.runLater(new Runnable(){
												public void run(){
													datafiche.clear();
													UnproduitDuStock leProduit=gestionContenu.AproductFromMesproduits(ComboDCI.getValue());
													BufferedReader readerTF;
														dciText.setText(leProduit.getDCI());
														code.setText("Code: "+leProduit.getCode());
														cmm.setText("CMM: "+leProduit.getCMM());
														StockDispo.setText("Stock: "+leProduit.getQuantite());
														stockMin.setText("Stock min: "+leProduit.getStockMin());
														stockMax.setText("Stock max: "+leProduit.gettStockMax());
														seuilCommand.setText("Seuil comd: "+leProduit.getStockMin());
														
													try {
														readerTF = new BufferedReader(new FileReader("C:\\pharmacieDOC\\pharmaPDF\\FicheDeStocks\\"+leProduit.getCode()+".dat"));
														String curentLine=readerTF.readLine();
														while(curentLine!=null)
														{
									    		        	String[] detailLine=curentLine.split(",");
									    		        	datafiche.add(new ficheStockData(detailLine[0],detailLine[1],detailLine[2],detailLine[3],detailLine[4],detailLine[5]));
									    		        	curentLine=readerTF.readLine();
														}
										        
													} catch (FileNotFoundException e) {
														// TODO Auto-generated catch block e.printStackTrace();
														new AlerterSuccesWorning().Worning(e.getMessage());
													} catch (IOException e) {
														// TODO Auto-generated catch blocke.printStackTrace();
														new AlerterSuccesWorning().Worning(e.getMessage());
													}
												//	ComboDCI.setValue(null);
												//	tableFicheStockView.refresh();
													
												}
											});
											return null;
										}
										
									};
								}
		    		        	
		    		        };
	
		    		        serviceGetInfo.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
								@Override
								public void handle(WorkerStateEvent event) {
									// TODO Auto-generated method stub
									ComboDCI.setValue(null);
									tableFicheStockView.refresh();
								}
								
							});
		ComboDCI.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			
				if(oldIndextFS!=ComboDCI.getSelectionModel().getSelectedIndex())
				{
				if(ComboDCI.getValue()!=null)
					{
						ProgressDialog progDialog=new ProgressDialog(serviceGetInfo);
						progDialog.setTitle("Chargement des données");
						progDialog.initOwner(MainStage);
						progDialog.setHeaderText("Fiche de Stock ......");
						progDialog.initModality(Modality.WINDOW_MODAL);
						serviceGetInfo.restart();
						
						oldIndextFS=ComboDCI.getSelectionModel().getSelectedIndex();
					}
				}
			}
		});
		hbChange.getChildren().addAll(ComboDCI)	;
		FicheDestock.setContent(BPficheDeStock);
		        
		tabPaneVente.getTabs().addAll(Hitorique,paretoCondom,FicheDestock);
		        
	return tabPaneVente;
}

}
