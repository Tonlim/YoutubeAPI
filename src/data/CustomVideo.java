package data;

import java.io.Serializable;
import java.util.Date;

import com.google.api.services.youtube.model.Video;

import data.utility.TimeConverter;

/*
 * data class for saved videos
 */
@SuppressWarnings("serial")
public class CustomVideo implements Serializable {
	private String id;
	private String title;
	private String description;
	private Date date;	
	private long duration;	//duration in sec
	private long views;
	private long likes;
	private long dislikes;
	
	private boolean hasSeries;
	private String seriesName;
	private int seriesNumber;
	private String seriesEpName;
	private boolean isMinecraft;
	
	public CustomVideo(Video in){
		id = in.getId();
		title = in.getSnippet().getTitle();
		description = in.getSnippet().getDescription();
		date = new Date(in.getSnippet().getPublishedAt().getValue());
		duration = TimeConverter.StringToSec(in.getContentDetails().getDuration());
		views = in.getStatistics().getViewCount().longValue();
		likes = in.getStatistics().getLikeCount().longValue();
		dislikes = in.getStatistics().getDislikeCount().longValue();
		extractSeriesInfo();
	}
	
	/*
	 * gets series info (series title, ep number, ep name) from the video title
	 */
	private void extractSeriesInfo(){
		hasSeries = false;
		seriesNumber = 0;
		isMinecraft = false;
		if(title.contains("Let's Play Minecraft - Ep.")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Etho Plays Minecraft";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(26,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
		} else {
		if(title.contains("Let's Play Minecraft")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Etho Plays Minecraft";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(31, placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
		} else {
		if(title.contains("Etho Plays Minecraft")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Etho Plays Minecraft";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(31,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
		} else {
		if(title.contains("Minecraft - Tutorial")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Minecraft Tutorial";
			int placeOfColon = title.indexOf(":");
			seriesNumber = -1;
			seriesEpName = title.substring(placeOfColon+2);
		} else {
		if(title.contains("Etho MindCrack SMP")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Etho MindCrack SMP";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(29,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
		} else {
		if(title.contains("Canopy Carnage")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Canopy Carnage";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(25,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
		} else {
		if(title.contains("RFW") || title.contains("RFTW")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Race For Wool";
			seriesNumber = -1;
			seriesEpName = title;
		} else {
		if(title.contains("Legendary - Episode")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Legendary";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(20,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);	
		} else {
		if(title.contains("OOGE - Spellbound Caves")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "OOGE - Spellbound Caves";
			if(title.contains("Episode 1 & 2")){
				seriesNumber = 1;
				seriesEpName = "Episode 1 & 2";
			} else {
				int placeOfColon = title.indexOf(":");
				seriesNumber = Integer.parseInt(title.substring(33));
				seriesEpName = title.substring(placeOfColon+2);
			}
		} else {
		if(title.contains("Mindcrack Ultra Hardcore")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Mindcrack Ultra Hardcore";
			int placeOfColon = title.indexOf(":");
			int placeOfSeason = title.indexOf(" - S")+3;
			int placeOfEpisode = title.indexOf("E", placeOfSeason);
			if(title.contains("Season") && title.contains("Episode")){
				placeOfSeason = title.indexOf("Season ")+6;
				placeOfEpisode = title.indexOf("ode ",placeOfSeason)+4;
				System.out.println(placeOfSeason);
				System.out.println(placeOfEpisode);
				System.out.println(placeOfColon);
				seriesNumber = Integer.parseInt(title.substring(placeOfSeason+1,placeOfEpisode-10)) * 10000 + Integer.parseInt(title.substring(placeOfEpisode+1,placeOfColon));
				seriesEpName = title.substring(placeOfColon+2);
			} else {
				if(title.contains("S4bE")){
					seriesNumber = 45000 + Integer.parseInt(title.substring(placeOfEpisode+1,placeOfColon));
					seriesEpName = title.substring(placeOfColon+2);
				} else {
					seriesNumber = Integer.parseInt(title.substring(placeOfSeason+1,placeOfEpisode)) * 10000 + Integer.parseInt(title.substring(placeOfEpisode+1,placeOfColon));
					seriesEpName = title.substring(placeOfColon+2);
				}
			}
			
			
			//----------------------- ADD MINECRAFT SERIES ------------------------------
			
		} else {
		if(title.contains("Minecraft")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Random Minecraft";
			seriesNumber = -1;
			seriesEpName = title;
		} else {
		if(title.contains("Harvest Moon 64")){
			hasSeries = true;
			seriesName = "Harvest Moon 64";
			int placeOfColon = title.indexOf(":");
			if(title.contains("Day 001")){
				seriesNumber = 1;
				seriesEpName = "1/1/03";
			} else {
				if(title.contains("Day 002")){
					seriesNumber = 2;
					seriesEpName = "Canadian Eh?";
				} else {
					seriesNumber = Integer.parseInt(title.substring(26,placeOfColon));
					seriesEpName = title.substring(placeOfColon+2);
				}
			}
		} else {
		if(title.contains("Let's Play Terraria")){
			hasSeries = true;
			seriesName = "Terraria";
			seriesNumber = Integer.parseInt(title.substring(30,31));
			seriesEpName = title.substring(32);
		} else {
		if(title.contains("DOTT - Episode")){
			hasSeries = true;
			seriesName = "DOTT";
			seriesNumber = Integer.parseInt(title.substring(15,16));
			seriesEpName = title.substring(17);
		
		
			
			
			
			//------------------------- ADD OTHER SERIES -----------------------
		
		} else {
			if(title.contains(":")){
				hasSeries = true;
				int placeOfDash = title.indexOf("-");
				int placeOfFence = title.indexOf("#");
				int placeOfColon = title.indexOf(":");
				if(placeOfDash > 0){
					seriesName = title.substring(0, placeOfDash-1);
				} else if(placeOfFence > 0) {
					seriesName = title.substring(0, placeOfFence-1);
				}
				if(placeOfFence > 0){
					try{
						seriesNumber = Integer.parseInt(title.substring(placeOfFence+1, placeOfColon));
					} catch(Exception e){
						System.out.println(title);
					}
				} else {	//track word "episode"
					int placeOfEpisode = title.indexOf("Episode");
					try {
						seriesNumber = Integer.parseInt(title.substring(placeOfEpisode+8));
					} catch(Exception e){
						System.out.println(title);
					}
				}
				
				seriesEpName = title.substring(placeOfColon+2);
				
			}
		}}}}}}}}}}}}}}
		if(seriesNumber == 0){
			System.out.println(title + "  || add to 'other'");
		}
	}
	
	public String getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Date getDate(){
		return date;
	}
	
	@SuppressWarnings("deprecation")
	public int getYear(){
		return date.getYear();
	}
	
	@SuppressWarnings("deprecation")
	public int getMonth(){
		return date.getMonth();
	}
	
	@SuppressWarnings("deprecation")
	public int getDay(){
		return date.getDay();
	}
	
	public long getDuration(){
		return duration;
	}
	
	public long getViews(){
		return views;
	}
	
	public long getLikes(){
		return likes;
	}
	
	public long getDislikes(){
		return dislikes;
	}
	
	public boolean getHasSeries(){
		return hasSeries;
	}
	
	public String getSeriesName(){
		return seriesName;
	}
	
	public int getSeriesNumber(){
		return seriesNumber;
	}
	
	public String getSeriesEpName(){
		return seriesEpName;
	}
	
	public boolean isMinecraft(){
		return isMinecraft;
	}
	
}
