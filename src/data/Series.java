package data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Series implements Serializable {
	/**
	 * default ID so Eclipse stops bothering me with warnings.
	 */
	private static final long serialVersionUID = 1L;

	private static LinkedList<Series> list = new LinkedList<Series>();
	
	private String name;
	private boolean minecraft;
	private String description;
	private boolean collab;
	private String collabNames;
	private LinkedList<CustomVideo> episodes;
	private Series previousSeries;
	private Series nextSeries;
	private boolean modded;
	private boolean survivalMap;
	private boolean pvp;
	
	
	private Series(String inName, boolean inMinecraft){
		name = inName;
		minecraft = inMinecraft;
		modded = false;
		survivalMap = false;
		episodes = new LinkedList<CustomVideo>();
	}
	
	private Series(String inName, boolean inMinecraft, boolean inModded, boolean map){
		this(inName, inMinecraft);
		modded = inModded;
		survivalMap = map;
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
		episodes.addFirst(in);
	}
	
	//---------------- public methods start here ----------------
	
	public static void addToSeries(CustomVideo in, String inName, boolean inMinecraft, boolean isModded, boolean isMap){
		if(!alreadyPresent(inName)){
			Series newSeries = new Series(inName, inMinecraft, isModded, isMap);
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
	
	public static List<Series> getSeries(){
		return list;
	}
	
	public String getName(){
		return name;
	}
	
	public List<CustomVideo> getEpisodes(){
		return episodes;
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
	
	public void setPreviousSeries(Series in){
		previousSeries = in;
	}
	
	public Series getPreviousSeries(){
		return previousSeries;
	}
	
	public void setNextSeries(Series in){
		nextSeries = in;
	}
	
	public Series getNextSeries(){
		return nextSeries;
	}
	
	public void setModded(boolean in){
		modded = in;
	}
	
	public boolean isModded(){
		return modded;
	}
	
	public void setSurvivalMap(boolean in){
		survivalMap = in;
	}
	
	public boolean isSurvivalMap(){
		return survivalMap;
	}
	
	public void setPvp(boolean in){
		pvp = in;
	}
	
	public boolean isPvp(){
		return pvp;
	}


	
	
	
	
}
