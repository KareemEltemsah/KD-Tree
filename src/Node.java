import java.util.ArrayList;

public class Node {
	static int K;
	int[] values;
	String Name = "";
	Node left;
	Node right;
	
	public Node () {
		values = new int[K];
		left = null;
		right = null;
	}
	public Node (String[] strValues) {
		values = new int[K];
		for (int i=0 ; i<K ; i++) {
			values[i] = Integer.parseInt(strValues[i]);
		}
		left = null;
		right = null;
	}

	public String toString() {
		String output = Name + "(";
		for (int i = 0; i < K - 1; i++)
			output += values[i] + ",";
		output += values[K-1] + ")";
		return output;
	}
}
