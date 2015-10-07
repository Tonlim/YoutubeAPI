package data.utility;

import java.time.Duration;

/*
 * helping class to convert between certain time formats
 */
public class TimeConverter {
	/*
	 * converts length string into seconds
	 */
	public static long StringToSec(String in){
		return Duration.parse(in).getSeconds();
	}
	
	/*
	 * converts length in seconds into length in day/hour/minute/second
	 */
	public static int[] SecToFull(long in){
		int[] result = new int[4];
		double input = in;
		result[0] = (int) Math.floor(input/86400);			//days
		result[1] = ((int) Math.floor(input/3600))%24;		//hours
		result[2] = ((int) Math.floor(input/60))%60;		//min
		result[3] = (int) (in % 60);						//sec
		return result;
	}
}
