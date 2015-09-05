package data.utility;

import java.time.Duration;

public class TimeConverter {
	public static long StringToSec(String in){
		return Duration.parse(in).getSeconds();
	}
}
