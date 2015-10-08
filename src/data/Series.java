package data;

import java.util.LinkedList;

public class Series {
	private static LinkedList<Series> list;
	
	private String name;
	private boolean minecraft;
	private String description;
	private boolean collab;
	private String collabNames;
	private LinkedList<CustomVideo> episodes;
	
	private Series(String inName, boolean inMinecraft){
		name = inName;
		minecraft = inMinecraft;
		episodes = new LinkedList<CustomVideo>();
	}
	
	
	private static boolean alreadyPresent(String inName){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getName().contains(inName)){
				return true;
			}
		}
		//nothing found
		return false;
	}
	
	private static int getIndex(String inName){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getName().contains(inName)){
				return i;
			}
		}
		//dummy return for the compiler
		//we should never get here if caller used 'alreadyPresent'
		return -1;
	}
	
	private void addEpisode(CustomVideo in){
		episodes.add(in);
	}
	
	//---------------- public methods start here ----------------
	
	public static void addToSeries(CustomVideo in, String inName, boolean inMinecraft){
		if(!alreadyPresent(inName)){
			Series newSeries = new Series(inName, inMinecraft);
			list.add(newSeries);
		} 
		list.get(getIndex(inName)).addEpisode(in);
		
	}
	
	//----- generic getters and setters -----
	
	public static Series getSeriesByName(String inName){
		if(!alreadyPresent(inName)){
			return null;
		} else {
			return list.get(getIndex(inName));
		}
	}
	
	public String getName(){
		return name;
	}
	
	public CustomVideo[] getEpisodes(){
		return (CustomVideo[]) episodes.toArray();
	}
	
	public boolean isMinecraft(){
		return minecraft;
	}
	
	public void setDescription(String input){
		description = input;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setCollab(boolean input){
		collab = input;
	}
	
	public boolean isCollab(){
		return collab;
	}
	
	public void setCollabNames(String input){
		collabNames = input;
	}
	
	public String getCollabNames(){
		return collabNames;
	}
	
	
}
