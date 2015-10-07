package application;
	
import iohandling.UploadedVideosDatabase;

import java.util.List;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import data.CustomVideo;
import data.utility.TimeConverter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
	
	/*
	 * main: setup of gui
	 */
	@Override
	public void start(Stage primaryStage) {		
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
							VideoHandleLogic.updateDatabase();
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
	
			setupDatabaseDisplay(centerData);
			

			
			//bottom: status message
			BorderPane bottom = new BorderPane();
			statusMessage = new Text("");
			root.setBottom(bottom);
			bottom.setBottom(statusMessage);
			bottom.setPadding(new Insets(5));
			
			
			
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
	
	/*
	 * reads the data from the database and puts it in the middle of the GUI
	 */
	private void setupDatabaseDisplay(HBox box){
		VBox title = new VBox();
		VBox date = new VBox();
		VBox duration = new VBox();
		VBox series = new VBox();
		List<CustomVideo> vids = UploadedVideosDatabase.getVideos();
		for(CustomVideo vid : vids){
			System.out.println(vid.getTitle());
			Text vidTitle = new Text(vid.getTitle());
			title.getChildren().add(vidTitle);
			Text vidDate = new Text(""+vid.getDate());
			date.getChildren().add(vidDate);
			int[] dayHourMinSec = TimeConverter.SecToFull(vid.getDuration());
			Text vidDuration;
			
			if(dayHourMinSec[1] == 0){	//hour = 0
				if(dayHourMinSec[3]<10){	//extra 0 needed for seconds
					vidDuration = new Text(""+dayHourMinSec[2]+":0"+dayHourMinSec[3]);
				} else {
					vidDuration = new Text(""+dayHourMinSec[2]+":"+dayHourMinSec[3]);
				}
			} else {	//hour > 0
				if(dayHourMinSec[2]<10){	//extra 0 needed for minutes
					if(dayHourMinSec[3]<10){	//extra 0 needed for seconds
						vidDuration = new Text(""+dayHourMinSec[1]+":0"+dayHourMinSec[2]+":0"+dayHourMinSec[3]);
					} else {
						vidDuration = new Text(""+dayHourMinSec[1]+":0"+dayHourMinSec[2]+":"+dayHourMinSec[3]);
					}
				} else {
					if(dayHourMinSec[3]<10){	//extra 0 needed for seconds
						vidDuration = new Text(""+dayHourMinSec[1]+":"+dayHourMinSec[2]+":0"+dayHourMinSec[3]);
					} else {
						vidDuration = new Text(""+dayHourMinSec[1]+":"+dayHourMinSec[2]+":"+dayHourMinSec[3]);
					}
				}
			}
			
			duration.getChildren().add(vidDuration);
			if(vid.getHasSeries()){
				Text vidSeries = new Text(vid.isMinecraft()+"  "+vid.getSeriesName()+"    |    "+vid.getSeriesNumber()+ "    |    "+vid.getSeriesEpName());
				series.getChildren().add(vidSeries);
			} else {
				Text vidSeries = new Text(vid.isMinecraft()+"");
				series.getChildren().add(vidSeries);
			}
		}
		box.getChildren().clear();
		box.getChildren().addAll(title,date,duration,series);
		
		box.setPadding(new Insets(10));
		date.setPadding(new Insets(0,10,0,10));
	}
	
	
	
	
	
	
	
	
	


	
	
}
