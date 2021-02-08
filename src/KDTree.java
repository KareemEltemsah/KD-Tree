import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class KDTree {
	static int K = 0;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("how many dimensions: ");
		K = scanner.nextInt();
		Node.K = K;

		System.out.println("how many initial points: ");
		int initial = scanner.nextInt();

		System.out.println("enter the initial points: ");
		ArrayList<Node> initialPoints = new ArrayList<Node>();
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < initial; i++) {
			String[] temp = sc.nextLine().split(",");
			Node n = new Node(temp);
			initialPoints.add(n);
		}

		Node root = null;
		root = initializeTree(root, initialPoints);
		print(root, 0);
		while (true) {
			System.out.println("\n1-add point\t2-delete point\t3-search for point\t0-exit");
			int choice = scanner.nextInt();
			if (choice == 1) {
				String[] temp = sc.nextLine().split(",");
				Node n = new Node(temp);
				root = addPoint(root, n, 0);
			} else if (choice == 2) {
				String[] temp = sc.nextLine().split(",");
				Node n = new Node(temp);
				root = deletePoint(root, n, 0);
			} else if (choice == 3) {
				String[] temp = sc.nextLine().split(",");
				Node n = new Node(temp);
				System.out.println(searchForPoint(root, n, 0));
			} else if (choice == 0) {
				break;
			}
			print(root, 0);
		}
	}

	public static Node initializeTree(Node root, ArrayList<Node> points) {
		root = points.get(0);
		root.Name = "r";
		for (int i = 1; i < points.size(); i++)
			root = addPoint(root, points.get(i), 0);
		return root;
	}

	public static Node addPoint(Node root, Node point, int level) {
		if (root == null)
			return point;

		if (point.values[level % K] < root.values[level % K]) {
			root.left = addPoint(root.left, point, level + 1);
			root.left.Name = root.Name + "L";
		} else {
			root.right = addPoint(root.right, point, level + 1);
			root.right.Name = root.Name + "R";
		}
		return root;
	}

	public static boolean searchForPoint(Node root, Node point, int level) {
		if (root == null)
			return false;
		if (isEqual(root, point))
			return true;

		if (point.values[level % K] < root.values[level % K]) {
			return searchForPoint(root.left, point, level + 1);
		} else {
			return searchForPoint(root.right, point, level + 1);
		}
	}

	public static Node deletePoint(Node root, Node point, int level) {
		if (root == null)
			return null;
		if (isEqual(root, point)) {
			if (root.right != null) {
				Node min = findMin(root.right, level % K, level + 1);
				int[] temp = root.values;
				root.values = min.values;
				min.values = temp;
				root.right = deletePoint(root.right, min, level + 1);
				if (root.right != null)
					root.right.Name = root.Name + "R";
			} else if (root.left != null) {
				Node min = findMin(root.left, level % K, level + 1);
				int[] temp = root.values;
				root.values = min.values;
				min.values = temp;
				root.right = deletePoint(root.left, min, level + 1);
				root.right.Name = root.Name + "R";
				if (root.right != null)
					root.left = null;
			} else
				root = null;
			return root;
		}

		if (point.values[level % K] < root.values[level % K]) {
			root.left = deletePoint(root.left, point, level + 1);
		} else {
			root.right = deletePoint(root.right, point, level + 1);
		}
		return root;
	}

	public static Node findMin(Node root, int checkIndex, int level) {
		if (root == null)
			return root;
		int minValue = root.values[checkIndex];
		if (level % K == checkIndex) {
			if (root.left == null)
				return root;
			else {
				if (root.values[checkIndex] < findMin(root.left, checkIndex, level + 1).values[checkIndex])
					return root;
				else
					return findMin(root.left, checkIndex, level + 1);
			}
		} else {
			if (root.right != null && root.left != null) {
				if (findMin(root.right, checkIndex, level + 1).values[checkIndex] > minValue
						&& findMin(root.left, checkIndex, level + 1).values[checkIndex] > minValue) {

					return root;
				} else {
					if (findMin(root.right, checkIndex, level + 1).values[checkIndex] < findMin(root.left, checkIndex,
							level + 1).values[checkIndex])
						return findMin(root.right, checkIndex, level + 1);
					else
						return findMin(root.left, checkIndex, level + 1);
				}
			} else if (root.right != null) {
				if (root.values[checkIndex] < findMin(root.right, checkIndex, level + 1).values[checkIndex])
					return root;
				else
					return findMin(root.right, checkIndex, level + 1);
			} else if (root.left != null) {
				if (root.values[checkIndex] < findMin(root.left, checkIndex, level + 1).values[checkIndex])
					return root;
				else
					return findMin(root.left, checkIndex, level + 1);
			}
			return root;
		}
	}

	public static void print(Node root, int level) {
		if (root == null)
			return;
		System.out.print(root + " ");
		print(root.left, level + 1);
		print(root.right, level + 1);
	}

	public static boolean isEqual(Node n1, Node n2) {
		boolean result = true;
		for (int i = 0; i < K; i++) {
			if (n1.values[i] != n2.values[i])
				result = false;
		}
		return result;
	}
}
