package application;

import iohandling.UploadedVideosDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import data.CustomVideo;

public class UserVideos {
	private static final String API_KEY = "AIzaSyBB16aIP-SlnWAsD3JFCI1aKBRBbdWF0sc";
	private static YouTube youtube;
	private static final String CHANNELNAME = "EthosLab";
	private static int numberOfVideos;
	
	public static void Main(){


	}
	
	//loads all etho's videos into the database
	public static void updateDatabase(){
		Main.statusMessage.setText("Starting database update...");
		UploadedVideosDatabase.open();
		
		youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("youtube-etho-search").build();
		
		UploadedVideosDatabase.reset();
		//get the IDs of the videos
		List<String> videoIDs = getUploadedVideoIDs(CHANNELNAME);
				
		//get the corresponding videos
		List<CustomVideo> videos = getVideosByID(videoIDs);
		UploadedVideosDatabase.setVideos(videos);
		UploadedVideosDatabase.close();
		Main.statusMessage.setText("Database updated");	
	}
	
	//gets the video IDs of all the uploaded videos of the given user
	private static List<String> getUploadedVideoIDs(String channelname){
		List<String> videoIDs = new ArrayList<String>();
		Main.statusMessage.setText("Gathering uploaded videos");

		try{
			//get Etho's uploaded videos playlist ID
			YouTube.Channels.List search = youtube.channels().list("id,snippet,contentDetails");
			
			search.setKey(API_KEY);			
			search.setForUsername(channelname);
			
			ChannelListResponse searchResponse = search.execute();
			List<Channel> results = searchResponse.getItems();
			String uploadsID = results.get(0).getContentDetails().getRelatedPlaylists().getUploads();

			//get videoIDs in playlist
			//get first page
			YouTube.PlaylistItems.List playlistitems = youtube.playlistItems().list("id,snippet,contentDetails");
			playlistitems.setKey(API_KEY);
			playlistitems.setPlaylistId(uploadsID);
			playlistitems.setMaxResults((long) 50);
			
			PlaylistItemListResponse playlistResponse = playlistitems.execute();
			List<PlaylistItem> items = playlistResponse.getItems();
			
			for(PlaylistItem i : items){
				videoIDs.add(i.getContentDetails().getVideoId());
			}
			
			//loop for other pages
			while(playlistResponse.getNextPageToken() != null){
				String token = playlistResponse.getNextPageToken();
				playlistitems.setPageToken(token);
				playlistResponse = playlistitems.execute();
				items = playlistResponse.getItems();
				for(PlaylistItem i : items){
					videoIDs.add(i.getContentDetails().getVideoId());
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		numberOfVideos = videoIDs.size();
		Main.statusMessage.setText(numberOfVideos+" uploaded videos found");
		return videoIDs;
	}
	
	private static List<CustomVideo> getVideosByID(List<String> videoIDs){
		List<CustomVideo> videos = new LinkedList<CustomVideo>();
		int j=1;
		for(String i : videoIDs){
			CustomVideo vid = getVideoByID(i);
			videos.add(vid);
			Main.statusMessage.setText(j+"/"+numberOfVideos+" video details downloaded");
			j++;
		}
		return videos;
	}
	
	private static CustomVideo getVideoByID(String id){
		CustomVideo vid = null;
		try{
			YouTube.Videos.List vidSearch = youtube.videos().list("id,snippet,contentDetails,statistics");
			vidSearch.setKey(API_KEY);
			vidSearch.setId(id);
			
			VideoListResponse vidResponse = vidSearch.execute();
			List<Video> vidList = vidResponse.getItems();
			Video rawVid = vidList.get(0);
			
			vid = new CustomVideo(rawVid);
			
			/*
			System.out.println(rawVid.getSnippet().getTitle());
			System.out.println(rawVid.getSnippet().getDescription());
			System.out.println(rawVid.getSnippet().getPublishedAt());
			System.out.println(TimeConverter.StringToSec(rawVid.getContentDetails().getDuration()));
			System.out.println(rawVid.getStatistics().getViewCount());
			System.out.println(rawVid.getStatistics().getLikeCount());
			System.out.println(rawVid.getStatistics().getDislikeCount());
			*/

			
		} catch(Exception e){
			e.printStackTrace();
		}
		return vid;
	}

}
