package data;

import java.io.Serializable;
import java.util.Date;

import utility.TimeConverter;

import com.google.api.services.youtube.model.Video;

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
			Series.addToSeries(this, "Etho Plays Minecraft", true,false,false);
		} else {
		if(title.contains("Let's Play Minecraft")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Etho Plays Minecraft";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(31, placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Etho Plays Minecraft", true,false,false);
		} else {
		if(title.contains("Etho Plays Minecraft")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Etho Plays Minecraft";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(31,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Etho Plays Minecraft", true,false,false);
		} else {
		if(title.contains("Minecraft - Tutorial")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Minecraft Tutorial";
			int placeOfColon = title.indexOf(":");
			seriesNumber = -1;
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Minecraft - Tutorial", true,false,false);
		} else {
		if(title.contains("Etho MindCrack SMP")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Etho MindCrack SMP";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(29,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Etho MindCrack SMP", true,false,false);
		} else {
		if(title.contains("Canopy Carnage")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Canopy Carnage";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(25,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Canopy Carnage", true,false,true);
		} else {
		if(title.contains("RFW") || title.contains("RFTW")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Race For Wool";
			seriesNumber = -1;
			seriesEpName = title;
			Series.addToSeries(this, "Race For Wool", true,false,true);
		} else {
		if(title.contains("Legendary - Episode")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Legendary";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(20,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);	
			Series.addToSeries(this, "Legendary", true,false,true);
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
			Series.addToSeries(this, "OOGE - Spellbound Caves", true,false,true);
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
				seriesNumber = Integer.parseInt(title.substring(placeOfSeason+1,placeOfEpisode-10)) * 10000 + Integer.parseInt(title.substring(placeOfEpisode));
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
			Series.addToSeries(this, "MindCrack Ultra Hardcore", true,false,false);
		} else {
		if(title.contains("Minecraft - Mindcrack UHC")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Mindcrack Ultra Hardcore";
			int placeOfSeason = title.indexOf(" S")+1;
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfSeason+1,placeOfColon)) * 10000 + Integer.parseInt(title.substring(placeOfColon+10));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "MindCrack Ultra Hardcore", true,false,false);
		} else {
		if(title.contains("Dwarves vs Zombies")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Random Minecraft";
			seriesNumber = -1;
			seriesEpName = title;
			Series.addToSeries(this, "Random Minecraft", true,false,false);
		} else {
		if(title.contains("Hunger Games - Docm's Special")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Random Minecraft";
			seriesNumber = -1;
			seriesEpName = title;
			Series.addToSeries(this, "Random Minecraft", true,false,false);
		} else {
		if(title.contains("Etho MindCrack FTB - Episode")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Etho MindCrack FTB";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(29,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);	
			Series.addToSeries(this, "Etho MindCrack FTB", true,true,false);
		} else {
		if(title.contains("Etho PVP - Revolution: Game")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Random Minecraft";
			seriesNumber = -1;
			seriesEpName = title;
			Series.addToSeries(this, "Random Minecraft", true,false,false);
		} else {
		if(title.contains("PlayMindCrack - Episode")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Random Mincraft";
			seriesNumber = -1;
			seriesEpName = title;
			Series.addToSeries(this, "Random Minecraft", true,false,false);
		} else {
		if(title.contains("Rush - Spellbound Caves")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Spellbound Caves Rush";
			seriesNumber = Integer.parseInt(title.substring(33));
			seriesEpName = title.substring(25);
			Series.addToSeries(this, "Spellbound Caves Rush", true,false,true);
		} else {
		if(title.contains("OOGE - Kaizo Caverns")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "OOGE - KaizoCaverns";
			seriesNumber = Integer.parseInt(title.substring(30));
			seriesEpName = title.substring(22);
			Series.addToSeries(this, "OOGE - KaizoCaverns", true,false,true);
		} else {
		if(title.contains("OOGE - Vinyl Fantasy II")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "OOGE - Vinyl Fantasy II";
			seriesNumber = Integer.parseInt(title.substring(33));
			seriesEpName = title.substring(25);
			Series.addToSeries(this, "OOGE - Vinyl Fantasy II", true,false,true);
		} else {
		if(title.contains("Map Making - ")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Map making";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfColon+10));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Map making", true,false,false);
		} else {
		if(title.contains("Ruins Of The MindCrackers: Episode")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Ruins Of The MindCrackers";
			seriesNumber = Integer.parseInt(title.substring(35));
			seriesEpName = title.substring(27);
			Series.addToSeries(this, "Ruins Of The MindCrackers", true,false,true);
		} else {
		if(title.contains("Minecraft - Ruins Of The MindCrackers 2:")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Ruins Of The MindCrackers 2";
			seriesNumber = Integer.parseInt(title.substring(49));
			seriesEpName = title.substring(41);
			Series.addToSeries(this, "Ruins Of The MindCrackers 2", true,false,true);
		} else {
		if(title.contains("Minecraft Speed Challenge")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Speed Challenges";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Speed Challenges", true,false,false);
		} else {
		if(title.contains("Minecraft - Uncharted Territory:")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Uncharted Territory";
			seriesNumber = Integer.parseInt(title.substring(41));
			seriesEpName = title.substring(33);
			Series.addToSeries(this, "Uncharted Territory", true,false,true);
		} else {
		if(title.contains("Minecraft - Nail:")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Nail";
			seriesNumber = -1;
			seriesEpName = title.substring(18);
			Series.addToSeries(this, "Nail", true,false,true);
		} else {
		if(title.contains("Minecraft - Uncharted Territory 2:")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Uncharted Territory 2";
			seriesNumber = Integer.parseInt(title.substring(43));
			seriesEpName = title.substring(35);
			Series.addToSeries(this, "Uncharted Territory 2", true,false,true);
		} else {
		if(title.contains("Minecraft - Uncharted Territory 3:")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Uncharted Territory 3";
			seriesNumber = Integer.parseInt(title.substring(43));
			seriesEpName = title.substring(35);
			Series.addToSeries(this, "Uncharted Territoy 3", true,false,true);
		} else {
		if(title.contains("Minecraft MindCrack FTB S2")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "MindCrack FTB S2";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(37,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "MindCrack FTB S2", true,true,false);
		} else {
		if(title.contains("Minecraft - Simulation Protocol:")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Simulation Protocol";
			seriesNumber = Integer.parseInt(title.substring(41));
			seriesEpName = title.substring(33);
			Series.addToSeries(this, "Simulation Protocol", true,false,false);
		} else {
		if(title.contains("Minecraft TerraFirmaCraft #")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "TerraFirmaCraft";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "TerraFirmaCraft", true,true,false);
		} else {
		if(title.contains("Minecraft CrackPack #")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "CrackPack";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "CrackPack", true,true,false);
		} else {
		if(title.contains("Minecraft - Crash Landing #")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Crash Landing";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Crash Landing", true,true,true);
		} else {
		if(title.contains("Minecraft - SoF #")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Survival of the Fittest";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1,placeOfColon))+10000;
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Survival of the Fittest", true,true,true);
		} else {
		if(title.contains("Minecraft - SoF S2 #")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Survival of the Fittest";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1,placeOfColon))+20000;
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Survival of the Fittest", true,true,true);
		} else {
		if(title.contains("Minecraft - Fly Boys #")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Fly Boys";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Fly Boys", true,true,false);
		} else {
		if(title.contains("Etho's Modded Minecraft")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Etho's Modded Minecraft";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Etho's Modded Minecraft", true,true,false);
		} else {
		if(title.contains("Minecraft - Planetary Confinement #")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Planetary Confinement";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Planetary Confinement", true,false,true);
		} else {
		if(title.contains("Minecraft - HermitCraft #")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "HermitCraft";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "HermitCraft", true,false,false);
		} else {
		if(title.contains("Minecraft - HermitCraft UHC ")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "HermitCraft UHC";
			int placeOfSeason = title.indexOf(" S")+1;
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfSeason+1,placeOfColon)) * 10000 + Integer.parseInt(title.substring(placeOfColon+10));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "HermitCraft UHC", true,false,false);
		} else {
		if(title.contains("Mini Hostile #1: RageQuit Holidays")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Random Minecraft";
			seriesNumber = -1;
			seriesEpName = title;
			Series.addToSeries(this, "Random Minecraft", true,false,false);
		
		
		

			
			
			
			//----------------------- ADD MINECRAFT SERIES ------------------------------
			
		} else {
		if(title.contains("Minecraft")){
			hasSeries = true;
			isMinecraft = true;
			seriesName = "Random Minecraft";
			seriesNumber = -1;
			seriesEpName = title;
			Series.addToSeries(this, "Random Minecraft", true,false,false);
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
			Series.addToSeries(this, "Harvest Moon 64", false,false,false);
		} else {
		if(title.contains("Let's Play Terraria")){
			hasSeries = true;
			seriesName = "Terraria";
			seriesNumber = Integer.parseInt(title.substring(30,31));
			seriesEpName = title.substring(32);
			Series.addToSeries(this, "Terraria", false,false,false);
		} else {
		if(title.contains("DOTT - Episode")){
			hasSeries = true;
			seriesName = "DOTT";
			seriesNumber = Integer.parseInt(title.substring(15,16));
			seriesEpName = title.substring(17);
			Series.addToSeries(this, "DOTT", false,false,false);
		} else {
		if(title.contains("Cube World - Episode")){
			hasSeries = true;
			seriesName = "Cube World";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(21,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Cube World", false,false,false);
		} else {
		if(title.contains("Sam & Max - Episode")){
			hasSeries = true;
			seriesName = "Sam & Max";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(20,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Sam & Max", false,false,false);
		} else {
		if(title.contains("Terraria 1.2 - Episode")){
			hasSeries = true;
			seriesName = "Terraria 1.2";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(23,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Terraria 1.2", false,false,false);
		} else {
		if(title.contains("Starbound - Episode")){
			hasSeries = true;
			seriesName = "Starbound";
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(20,placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Starbound", false,false,false);
		} else {
		if(title.contains("Etho Plays - Don't Starve")){
			hasSeries = true;
			seriesName = "Don't Starve";
			seriesNumber = Integer.parseInt(title.substring(35));
			seriesEpName = title.substring(27);
			Series.addToSeries(this, "Don't Starve", false,false,false);
		} else {
		if(title.contains("Etho Plays - SOTS The Pit")){
			hasSeries = true;
			seriesName = "SOTS The Pit";
			seriesNumber = Integer.parseInt(title.substring(35));
			seriesEpName = title.substring(27);
			Series.addToSeries(this, "SOTS The Pit", false,false,false);
		} else {
		if(title.contains("Etho Plays - Pixel Piracy")){
			hasSeries = true;
			seriesName = "Pixel Piracy";
			seriesNumber = Integer.parseInt(title.substring(35));
			seriesEpName = title.substring(27);
			Series.addToSeries(this, "Pixel Piracy", false,false,false);
		} else {
		if(title.contains("Don't Starve Together")){
			hasSeries = true;
			seriesName = "Don't Starve Together";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1, placeOfColon));
			seriesEpName = title.substring(placeOfColon+2);
			Series.addToSeries(this, "Don't Starve Together", false,false,false);
		} else {
		if(title.contains("ARK Survival Evolved")){
			hasSeries = true;
			seriesName = "ARK Survival Evolved";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1, placeOfColon));
			seriesEpName = title.substring(placeOfColon+2); 
			Series.addToSeries(this, "ARK Survival Evolved", false,false,false);
		} else {
		if(title.contains("Terraria 1.3")){
			hasSeries = true;
			seriesName = "Terraria 1.3";
			int placeOfFence = title.indexOf("#");
			int placeOfColon = title.indexOf(":");
			seriesNumber = Integer.parseInt(title.substring(placeOfFence+1, placeOfColon));
			seriesEpName = title.substring(placeOfColon+2); 
			Series.addToSeries(this, "Terraria 1.3", false,false,false);
		
		
		

		
			
			
			
			//------------------------- ADD OTHER SERIES -----------------------
		
		}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}
		if(hasSeries == false){
			hasSeries = true;
			seriesName = "Random Video";
			seriesNumber = -1;
			seriesEpName = title;
			Series.addToSeries(this, "Random Video", false,false,false);
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
