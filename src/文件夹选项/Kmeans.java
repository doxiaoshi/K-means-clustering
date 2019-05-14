package �ļ���ѡ��;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Kmeans {
	private class Node {
		int label;// label ������¼�����ڵڼ��� cluster
		double[] attributes;

		public Node() {
			attributes = new double[100];
		}
	}

	private class NodeComparator {
		Node nodeOne;
		Node nodeTwo;
		double distance;

		public void compute() {
			double val = 0;
			for (int i = 0; i < dimension; ++i) {
				val += (this.nodeOne.attributes[i] - this.nodeTwo.attributes[i]) * 
						(this.nodeOne.attributes[i] - this.nodeTwo.attributes[i]);
			}
			this.distance = val;
		}
	}

	private ArrayList<Node> arraylist;
	private ArrayList<Node> centroidList;
	private double averageDis;
	private int dimension;
	private Queue<NodeComparator> FsQueue = 
			new PriorityQueue<NodeComparator>(150, // ����������������֮��ľ��룬�Ӵ�С��
					new Comparator<NodeComparator>() {
				public int compare(NodeComparator one, NodeComparator two) {
					if (one.distance < two.distance)
						return 1;
					else if (one.distance > two.distance)
						return -1;
					else
						return 0;
				}
			});

	public void setKmeansInput(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String str;
			String[] strArray;
			arraylist = new ArrayList<Node>();
			while ((str = br.readLine()) != null) {
				strArray = str.split(",");
				dimension = strArray.length;
				Node node = new Node();
				for (int i = 1; i < dimension; ++i) {
					node.attributes[i] = Double.valueOf(strArray[i]);
				}
				arraylist.add(node);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void computeTheK() {
		int cntTuple = 0;
		for (int i = 0; i < arraylist.size() - 1; ++i) {
			for (int j = i + 1; j < arraylist.size(); ++j) {
				NodeComparator nodecomp = new NodeComparator();
				nodecomp.nodeOne = new Node();
				nodecomp.nodeTwo = new Node();
				for (int k = 0; k < dimension; ++k) {
					nodecomp.nodeOne.attributes[k] = arraylist.get(i).attributes[k];
					nodecomp.nodeTwo.attributes[k] = arraylist.get(j).attributes[k];
				}
				nodecomp.compute();
				averageDis += nodecomp.distance;
				FsQueue.add(nodecomp);
				cntTuple++;
			}
		}
		averageDis /= cntTuple;// ����ƽ������
		chooseCentroid(FsQueue);
	}

	public double getDistance(Node one, Node two) {// ����������ŷ�Ͼ���
		double val = 0;
		for (int i = 0; i < dimension; ++i) {
			val += (one.attributes[i] - two.attributes[i]) * (one.attributes[i] - two.attributes[i]);
		}
		return val;
	}

	public void chooseCentroid(Queue<NodeComparator> queue) {
		centroidList = new ArrayList<Node>();
		boolean flag = false;
		while (!queue.isEmpty()) {
			boolean judgeOne = false;
			boolean judgeTwo = false;
			NodeComparator nc = FsQueue.poll();
			if (nc.distance < averageDis)
				break;// �����������Ԫ�飬���ڵ�����С��ƽ�����룬�򲻼�������
			if (!flag) {
				centroidList.add(nc.nodeOne);// �ȼ������е��о�����Զ��������
				centroidList.add(nc.nodeTwo);
				flag = true;
			} else {// ֮���֮ǰ�Ѽ������Զ�������㿪ʼ����������������Զ�ĵ㣬
				// �������������е��ƽ�����룬����Ϊ�ҵ����µ����ģ������϶�Ϊ����
				for (int i = 0; i < centroidList.size(); ++i) {
					Node testnode = centroidList.get(i);
					if (centroidList.contains(nc.nodeOne) || getDistance(testnode, nc.nodeOne) < averageDis) {
						judgeOne = true;
					}
					if (centroidList.contains(nc.nodeTwo) || getDistance(testnode, nc.nodeTwo) < averageDis) {
						judgeTwo = true;
					}
				}
				if (!judgeOne) {
					centroidList.add(nc.nodeOne);
				}
				if (!judgeTwo) {
					centroidList.add(nc.nodeTwo);
				}
			}
		}
	}

	public void doIteration(ArrayList<Node> centroid) {

		int cnt = 1;
		int cntEnd = 0;
		int numLabel = centroid.size();
		while (true) {// ������ֱ�����е����Ķ����仯Ϊֹ
			boolean flag = false;
			for (int i = 0; i < arraylist.size(); ++i) {
				double dis = 0x7fffffff;
				cnt = 1;
				for (int j = 0; j < centroid.size(); ++j) {
					Node node = centroid.get(j);
					if (getDistance(arraylist.get(i), node) < dis) {
						dis = getDistance(arraylist.get(i), node);
						arraylist.get(i).label = cnt;
					}
					cnt++;
				}
			}
			int j = 0;
			numLabel -= 1;
			while (j < numLabel) {
				int c = 0;
				Node node = new Node();
				for (int i = 0; i < arraylist.size(); ++i) {
					if (arraylist.get(i).label == j + 1) {
						for (int k = 0; k < dimension; ++k) {
							node.attributes[k] += arraylist.get(i).attributes[k];
						}
						c++;
					}
				}
				DecimalFormat df = new DecimalFormat("#.###");// ����С�������λ
				double[] attributelist = new double[100];
 	for (int i = 0; i < dimension; ++i) {
 		attributelist[i] = Double.parseDouble(df.format(node.attributes[i] / c));
 		if (attributelist[i] != centroid.get(j).attributes[i]) {
 			centroid.get(j).attributes[i] = attributelist[i];
 			flag = true;
 		}
 	}
 	if (!flag) {
 		cntEnd++;
 		if (cntEnd == numLabel) {// �����е����Ķ����䣬������ѭ��
 			break;
 		}
 	}
 	j++;
			}
			if (cntEnd == numLabel) {// �����е����Ķ����䣬�� success
				System.out.println("run kmeans successfully.");
				break;
			}
		}
	}

	public void printKmeansResults(String path) {
		try {
			PrintStream out = new PrintStream(path);
			computeTheK();
			doIteration(centroidList);
			out.println("There are " + centroidList.size() + " clusters!");
			for (int i = 0; i < arraylist.size(); ++i) {
				out.print("(");
				for (int j = 0; j < dimension - 1; ++j) {
					out.print(arraylist.get(i).attributes[j] + ", ");
				}
				out.print(arraylist.get(i).attributes[dimension - 1] + ") ");
				out.println("belongs to cluster " + arraylist.get(i).label);
			}
			out.close();
			System.out.println("Please check results in: " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String s;
	public static void main(String[] args) {
		Kmeans kmeans = new Kmeans();
		s=����.s;
		kmeans.setKmeansInput("E:/2018-2019����NBA������ս��.txt");
		kmeans.printKmeansResults("E:/kmeansResults.txt");
 }
}
