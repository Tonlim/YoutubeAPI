package utility;

import java.util.LinkedList;
import java.util.List;

public class ListManipulator {
	public static <T> List<T> invertListOrder(List<T> in){
		LinkedList<T> out = new LinkedList<T>();
		for(T i : in){
			out.addFirst(i);
		}
		return out;
	}
}
