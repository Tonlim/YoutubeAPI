package data;

import java.io.Serializable;
import java.util.Date;

import com.google.api.services.youtube.model.Video;

import data.utility.TimeConverter;

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
	
	public CustomVideo(Video in){
		id = in.getId();
		title = in.getSnippet().getTitle();
		description = in.getSnippet().getDescription();
		date = new Date(in.getSnippet().getPublishedAt().getValue());
		duration = TimeConverter.StringToSec(in.getContentDetails().getDuration());
		views = in.getStatistics().getViewCount().longValue();
		likes = in.getStatistics().getLikeCount().longValue();
		dislikes = in.getStatistics().getDislikeCount().longValue();
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
	
}
