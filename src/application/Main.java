package application;
	


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	TrafficHandlerThread tht; 
	
	@Override
	public void start(Stage primaryStage) {
		try {
		//	BorderPane root = new BorderPane();
			
			GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));
	        final VBox vbox = new VBox();
	        vbox.setSpacing(5);
	        vbox.setPadding(new Insets(10, 0, 0, 10));
	        vbox.getChildren().add(grid);
	        Scene scene = new Scene(vbox,500,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			GridPane gridForGtpVbox = new GridPane();
			//gridForGtpVbox.setAlignment(Pos.CENTER);
			gridForGtpVbox.setHgap(10);
			gridForGtpVbox.setVgap(10);
			Label gtpParameters = new Label("GTP Params");
			
			VBox additionalVboxForGTP = new VBox();
			additionalVboxForGTP.setSpacing(5);
			//additionalVboxForGTP.setPadding(new Insets(10, 0, 0, 10));
			Label teidLab = new Label("TEID: ");
			TextField teidTF = new TextField();
			
			TextField throughput = new TextField();
			Label throughputLab = new Label("Throughput:");
			
			Label protocolLab = new Label("Protocol:");
			ObservableList<String> options = 
				    FXCollections.observableArrayList(
				        "UDP","GTP"
				     
				    );
				ComboBox comBox = new ComboBox();
				comBox.getItems().addAll(options);
				
				comBox.getSelectionModel().selectFirst();
				comBox.valueProperty().addListener(new ChangeListener<String>(){

					@Override
					public void changed(ObservableValue ov, String t, String t1){
						additionalVboxForGTP.getChildren().clear();
						if(t1.equals("GTP")){
							additionalVboxForGTP.getChildren().add(gridForGtpVbox);
							/*additionalVboxForGTP.getChildren().add(teidLab);
							additionalVboxForGTP.getChildren().add(teidTF);*/
							gridForGtpVbox.add(gtpParameters, 0,1);
							gridForGtpVbox.add(teidLab,0,2);
							gridForGtpVbox.add(teidTF, 1, 2);
;						}
						
					}
					});
			grid.add(protocolLab, 0, 0);
			grid.add(comBox, 1, 0);	
				
			TextField address = new TextField();
			Label addressLab = new Label("Address:");
			TextField port = new TextField();
			Label portLab = new Label("Port:");
			
			TextField packetSize = new TextField();
			Label packetSizeLab = new Label("Packet Size:");
			
			
			grid.add(address, 1, 2);
			grid.add(addressLab, 0, 2);
			grid.add(port, 1, 3);
			grid.add(portLab, 0,3 );
			grid.add(throughput, 1, 4);
			grid.add(throughputLab, 0,4 );
			grid.add(packetSize, 1, 5);
			grid.add(packetSizeLab, 0,5 );
			grid.add(additionalVboxForGTP, 0, 6);
			
			Button start = new Button("Start");
			start.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					String addressStr = address.getText();
					String portStr = port.getText();
					String throughputStr = throughput.getText();
					String packetStr = packetSize.getText();
					if(portStr.equals("")|| addressStr.equals("")){
						return;
					}
					tht = new TrafficHandlerThread(addressStr, portStr, throughputStr, packetStr);
					System.out.println("Address: "+addressStr+"\nPort: "+portStr+"\nThroughput: "+throughputStr+"\nPacketsize: "+packetStr);
					 new Thread(tht).start();
				}
				
			});
			start.setMinSize(60, 10);
			grid.add(start, 10,0);
			Button end = new Button("End");
			end.setOnAction(new EventHandler<ActionEvent>(){
				 
	            @Override
	            public void handle(ActionEvent event) {
	            	if(tht!=null){
	            		   System.out.println("Stopping server!");
	                tht.setStopped(true);
	            	}
	             
	            }
	        });
			end.setMinSize(60, 10);
			grid.add(end,10, 1);;
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
