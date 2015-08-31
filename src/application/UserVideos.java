package application;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

public class UserVideos {
	private static final String API_KEY = "AIzaSyBB16aIP-SlnWAsD3JFCI1aKBRBbdWF0sc";
	private static YouTube youtube;
	
	public static void Main(){
		Channel channel;
		
		try {
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-etho-search").build();
			
			YouTube.Channels.List search = youtube.channels().list("id,snippet,contentDetails");
			
			search.setKey(API_KEY);			
			search.setForUsername("EthosLab");
			
			ChannelListResponse searchResponse = search.execute();
			List<Channel> results = searchResponse.getItems();
			if(results != null){
				prettyPrint(results);
			}
			
			
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
		String uploadsID = results.get(0).getContentDetails().getRelatedPlaylists().getUploads();
		
        System.out.println(uploadsID);
    }
}
