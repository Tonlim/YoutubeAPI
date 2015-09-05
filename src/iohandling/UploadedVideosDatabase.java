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

public class UploadedVideosDatabase {
	private static List<CustomVideo> data;
	private static final String filename = "uploaded_videos_database.dat";
	
	//opens the database
	@SuppressWarnings("unchecked")
	public static synchronized void open(){
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
			data = (List<CustomVideo>) in.readObject();
			in.close();
		} catch (FileNotFoundException e1){
			firstTimeSetUp();
			open();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//makes an empty map to start off
	private static void firstTimeSetUp(){
		data = new LinkedList<CustomVideo>();
		save();
	}
	
	//saves all changes to the database file
	public static synchronized void save(){
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
			out.writeObject(data);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//dummy name for save()
	public static synchronized void close(){
		save();
	}
	
	//returns a shallow copy of the database
	public static List<CustomVideo> getVideos() {
		if(data == null){
			open();
		}
		return data;
	}
	
	public static void setVideos(List<CustomVideo> vids){
		data = vids;
	}
	
	public static void reset(){
		firstTimeSetUp();
	}
	
	
}
