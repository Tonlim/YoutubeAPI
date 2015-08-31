package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

public class UserVideos {
	private static final String API_KEY = "AIzaSyBB16aIP-SlnWAsD3JFCI1aKBRBbdWF0sc";
	private static YouTube youtube;
	
	public static void Main(){
		Channel channel;
		
		try {
			//make the structure to handle the requests
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-etho-search").build();
			
			//get Etho's uploaded videos playlist ID
			YouTube.Channels.List search = youtube.channels().list("id,snippet,contentDetails");
			
			search.setKey(API_KEY);			
			search.setForUsername("EthosLab");
			
			ChannelListResponse searchResponse = search.execute();
			List<Channel> results = searchResponse.getItems();
			String uploadsID = results.get(0).getContentDetails().getRelatedPlaylists().getUploads();
			
			//get videoIDs in playlist
			YouTube.PlaylistItems.List playlistitems = youtube.playlistItems().list("id,snippet,contentDetails");
			playlistitems.setKey(API_KEY);
			playlistitems.setPlaylistId(uploadsID);
			playlistitems.setMaxResults((long) 50);
			
			PlaylistItemListResponse playlistResponse = playlistitems.execute();
			List<PlaylistItem> items = playlistResponse.getItems();
			List<String> videoIDs = new ArrayList<String>();
			for(PlaylistItem i : items){
				videoIDs.add(i.getContentDetails().getVideoId());
			}
			
			System.out.println(videoIDs.get(0));
			System.out.println(videoIDs.get(1));
			System.out.println(videoIDs.get(2));
			
			System.out.println("\n"+videoIDs.size());
			
			
			
		} catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
		
		
		

	}
	
	private static void prettyPrint(List<Channel> results) {
		System.out.println(results.get(0).getSnippet());
    }
}
