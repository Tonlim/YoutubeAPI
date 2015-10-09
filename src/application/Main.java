package application;
	
import iohandling.UploadedVideosDatabase;

import java.util.List;

import utility.TimeConverter;
import data.CustomVideo;
import data.Series;
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
	public static ScrollPane scrollpane;
	
	/*
	 * main: setup of gui
	 */
	@Override
	public void start(Stage primaryStage) {		
		try {
			BorderPane root = new BorderPane();
			
			//top: menu with buttons
			HBox top = new HBox();
			root.setTop(top);
			Button updateDatabase = new Button("Update Database");
			top.getChildren().add(updateDatabase);
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
			
			Button showFullDatabase = new Button("Show database");
			top.getChildren().add(showFullDatabase);
			showFullDatabase.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					setupDatabaseDisplay(centerData);
				}
			});
			
			Button showSeries = new Button("Show Series");
			top.getChildren().add(showSeries);
			showSeries.setOnAction(new EventHandler<ActionEvent> (){
				@Override
				public void handle(ActionEvent event){
					setUpSeriesOverview(centerData);
				}
			});
			
			//center: data
			scrollpane = new ScrollPane();
			root.setCenter(scrollpane);
			centerData = new HBox();
			scrollpane.setContent(centerData);
	
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
		scrollpane.setVvalue(0.0);
		
	}
	
	private void setUpSeriesOverview(HBox box){
		box.getChildren().clear();
		VBox actualBox = new VBox();
		
		
		List<Series> series = UploadedVideosDatabase.getSeries();
		for(int i=0;i<series.size();i++){
			Button seriesButton = new Button(series.get(i).getName());
			final int j = i;
			seriesButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					setUpSeriesDetails(series.get(j),box);
				}
			});
			actualBox.getChildren().add(seriesButton);
		}
		box.getChildren().add(actualBox);
		scrollpane.setVvalue(0.0);
	}
	
	private void setUpSeriesDetails(Series series, HBox box){
		box.getChildren().clear();
		BorderPane canvas = new BorderPane();
		box.getChildren().add(canvas);
		//name, description, other info in top
		VBox top = new VBox();
		canvas.setTop(top);
		Text nameNode = new Text(series.getName());
		top.getChildren().add(nameNode);
		Text isMinecraftNode = new Text(series.isMinecraft()?"Minecraft":"");
		top.getChildren().add(isMinecraftNode);
		Text descriptionNode = new Text(series.getDescription());
		top.getChildren().add(descriptionNode);
		
		List<CustomVideo> vids = series.getEpisodes();
		HBox dataBox = new HBox();
		canvas.setCenter(dataBox);
		//episodes in HBox like main overview
		fillHBox(dataBox,vids);
	}
	
	private void fillHBox(HBox box, List<CustomVideo> vids){
		VBox title = new VBox();
		VBox date = new VBox();
		VBox duration = new VBox();
		for(CustomVideo vid : vids){			
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
		}
		box.getChildren().clear();
		box.getChildren().addAll(title,date,duration);
		
		box.setPadding(new Insets(10));
		date.setPadding(new Insets(0,10,0,10));
		scrollpane.setVvalue(0.0);
	}
	
	
	
	
	
	
	
	
	


	
	
}
