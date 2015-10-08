package iohandling;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import data.CustomVideo;
import data.Series;

public class UploadedVideosDatabase {
	private static List<CustomVideo> data;
	private static List<Series> series;
	private static final String filename = "uploaded_videos_database.dat";
	
	/*
	 * opens the database
	 */
	@SuppressWarnings("unchecked")
	public static synchronized void open(){
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
			data = (List<CustomVideo>) in.readObject();
			series = (List<Series>) in.readObject();
			in.close();
		} catch (FileNotFoundException e1){
			firstTimeSetUp();
			open();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * makes an empty list to start filling
	 */
	private static void firstTimeSetUp(){
		data = new LinkedList<CustomVideo>();
		series = new LinkedList<Series>();
		save();
	}
	
	/*
	 * saves all changes to the database file
	 */
	public static synchronized void save(){
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
			out.writeObject(data);
			out.writeObject(series);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * dummy name for save()
	 */
	public static synchronized void close(){
		save();
	}
	
	/*
	 * gets the database in a List<CustomVideo> format (shallow copy)
	 */
	public static List<CustomVideo> getVideos() {
		if(data == null){
			open();
		}
		return data;
	}
	
	/*
	 * puts the given videos into the database, overrides
	 */
	public static void setVideos(List<CustomVideo> vids){
		data = vids;
	}
	
	/*
	 * gets the series in a List<Series> format (shallow copy)
	 */
	public static List<Series> getSeries(){
		if(series == null){
			open();
		}
		return series;
	}
	
	/*
	 * puts the given series into the database, overrides
	 */
	public static void setSeries(List<Series> input){
		series = input;
	}
	
	/*
	 * resets the database, deleting everything in the process
	 */
	public static void reset(){
		firstTimeSetUp();
	}
	
	
}
