package application;
	
import iohandling.UploadedVideosDatabase;

import java.util.List;

import data.CustomVideo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Main extends Application {
	public static HBox centerData;
	public static Text statusMessage;
	
	@Override
	public void start(Stage primaryStage) {

		
		
		
		
		//javafx generated code
		try {
			BorderPane root = new BorderPane();
			
			//top: menu with buttons
			BorderPane top = new BorderPane();
			root.setTop(top);
			Button updateDatabase = new Button("Update Database");
			top.setLeft(updateDatabase);
			updateDatabase.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Task<Void> task = new Task<Void>(){
						@Override
						protected Void call() throws Exception {
							UserVideos.updateDatabase();
							Platform.runLater(new Runnable(){
								@Override
								public void run() {
									setupDatabaseDisplay(centerData);
									
								}			
							});
							return null;
						}			
					};
					new Thread(task).start();
					
				}
			});
			
			//center: data
			ScrollPane center = new ScrollPane();
			root.setCenter(center);
			centerData = new HBox();
			center.setContent(centerData);
			/*
			//colum1: title
			VBox title = new VBox();
			center.getChildren().add(title);
			Text title1 = new Text(UploadedVideosDatabase.getVideos().get(0).getTitle());
			title.getChildren().add(title1);
			*/
			
			setupDatabaseDisplay(centerData);
			
			//TODO: loop through all the data and add it to the thingy
			//TODO: add a status bar at the bottom
			
			//bottom: status message
			statusMessage = new Text("");
			root.setBottom(statusMessage);
			
			
			Scene scene = new Scene(root,1600,820);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void setupDatabaseDisplay(HBox box){
		VBox title = new VBox();
		VBox date = new VBox();
		VBox duration = new VBox();
		List<CustomVideo> vids = UploadedVideosDatabase.getVideos();
		for(CustomVideo vid : vids){
			Text vidTitle = new Text(vid.getTitle());
			title.getChildren().add(vidTitle);
			Text vidDate = new Text(""+vid.getDate());
			date.getChildren().add(vidDate);
			Text vidDuration = new Text(""+vid.getDuration());
			duration.getChildren().add(vidDuration);
		}
		box.getChildren().clear();
		box.getChildren().addAll(title,date,duration);
	}
	
	
	
	
	
	
	
	
	


	
	
}
