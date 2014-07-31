import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/*
 * 
 * LOW FARE ROUTING SYSTEM WITH DIJKSTRA AND FLOYD ALGORITHMS
 * 
 * 
 * */


/* Vertex class */
class Vertex implements Comparable<Vertex> {
	float min_distance;				// minimum distance from source to this vertex
	int index;						// index position in the array

	Vertex(float dist, int position) { // Constructor to set the variables
		this.min_distance = dist;
		this.index = position;
	}

	@Override
	public int compareTo(Vertex v) {	// to compare two vertices, based on min distance, useful when placed in a queue
		return (int) (min_distance - v.min_distance);
	}
}


/* This class contains methods to calculate minimum distance using different algorithms*/
class compute {
	int size;				// no of vertices
	float[][] weights;		// weights if edges
	float[] distance;		// distance table to keep track of min distances
	String[] paths;			// to store paths
	int source_index;		// to store source index
	int destn_index;		// to store destination index
	ArrayList<String> cities = new ArrayList<String>();  // array of vertices
	Vertex v = null;		// vertex object
	int[] track_visited;	
	PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>(); // minimum priority queue

	
	/* This method is used to read the file containing cities names */
	
	public void get_cities_data() throws IOException {
		File fp = new File("cities.txt");				// file handler
		FileReader fr = new FileReader(fp);				// file reader
		BufferedReader br = new BufferedReader(fr);		// buffered reader

		String temp = "";

		while ((temp = br.readLine()) != null) {		// read until end of file
			cities.add(temp);							// add each line to array list
		}
	}

	
	/* This method is used to initialize all data which will be required to compute min distances*/
	
	public void init_variables() {
		size = cities.size();				// no of cities
		weights = new float[size][size];	// matrix to hold distances
		paths = new String[size];			// paths
		track_visited = new int[size];	
		distance = new float[size];			// distance table

		for (int i = 0; i < size; i++) {	// initially initialize distance table to infinity
			distance[i] = (float) 9999.0;
		}
	}

	
	/* This method is used to read all distance between cities */
	
	public void get_weights_data() throws IOException {
		File fp = new File("Matrix.txt");			// file handler
		FileReader fr = new FileReader(fp);			// file reader
		BufferedReader br = new BufferedReader(fr);	// buffered reader

		int i = -1;

		String temp = "";

		while ((temp = br.readLine()) != null) {  // loop until end of file
			i++;
			String[] t = temp.split("\\t");			// distance are seperated by tab

			for (int h = 0; h < t.length; h++) {		
				weights[i][h] = Float.parseFloat(t[h]); // add to matrix
			}

			for (int g = 0; g < t.length; g++) {
				if (i != g && weights[i][g] == 0.0)		// initialize inconsistent data to infinity
					weights[i][g] = (float) 99999.99;

			}
		}
	}

	/* This method implements DIJKSTRA'S Algorithm */
	
	public void dijkstra(int src) {					// takes input source index
		queue.add(new Vertex(distance[src], src));	// creates a vertex of source and adds it to the queue

		while (!queue.isEmpty()) {
			Vertex temp = queue.remove();			// pop the queue
			int s = temp.index;

			for (int i = 0; i < cities.size(); i++) {		// get all adjacent cities
				if (s != i) {		
					float t = weights[s][i] + distance[s];	// calculate distance(a) + edge(a,b)

					if (distance[i] > t) {					// if found min distance
						distance[i] = t;					// update the distance
						paths[i] = paths[s] + "->" + cities.get(i); // set paths
						queue.add(new Vertex(distance[i], i));	// add it to the queue
					}
				}
			}
		}
	}

	public int[][] next;
	boolean found = false;
	int distance1 = 0;
	
	/* This method implements all source shortest path FLOYD algorithm*/

	public void floyd_warshall() {
		next = new int[size][size];

		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (weights[i][k] + weights[k][j] < weights[i][j]) {
						found = true;
						weights[i][j] = weights[i][k] + weights[k][j];
						next[i][j] = k;
					}
				}
			}

		}
	}

	/* This method returns the path between source and destination */
	
	public String floyd_path(int i, int j) {
		if (weights[i][j] == 9999.0) {
			return "no path";
		}

		int intermediate = next[i][j];

		if (intermediate == 0)
			return "";
		else
			return floyd_path(i, intermediate) + cities.get(intermediate)
					+ "->" + floyd_path(intermediate, j);

	}

	public float floyd_dist(int i, int j) {

		System.out.println("weights : " + weights[i][j]);
		// System.out.println("i :" + i + " j :" + j);
		if (weights[i][j] == 9999.0) {
			return 0;
		}

		int intermediate = next[i][j];

		if (intermediate == 0)
			return 0;
		else
			return weights[i][j] + floyd_dist(i, intermediate)
					+ floyd_dist(j, intermediate);

	}
}

public class C extends JApplet {

	private JTextField textField;
	private JTextField textField_1;
	String[] k1 = new String[1];
	String[] k2 = new String[1];

	@Override
	public void init() {
		this.setSize(600, 400);
	}

	public C() {

		getContentPane().setLayout(null);

		JLabel lblSingleSourceShortest = new JLabel(
				"                              SINGLE SOURCE SHORTEST PATH");
		lblSingleSourceShortest.setBounds(37, 20, 309, 22);
		getContentPane().add(lblSingleSourceShortest);

		JLabel lblSource = new JLabel("Source :");
		lblSource.setBounds(55, 53, 98, 14);
		getContentPane().add(lblSource);

		JLabel lblDestination = new JLabel("Destination :");
		lblDestination.setBounds(185, 53, 107, 14);
		getContentPane().add(lblDestination);

		final JComboBox comboBox = new JComboBox();

		comboBox.setBounds(55, 112, 98, 20);
		getContentPane().add(comboBox);

		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(k2));
		comboBox_1.setBounds(185, 112, 107, 20);
		getContentPane().add(comboBox_1);

		final JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "Dijkstra",
				"Floyd Warshall" }));
		comboBox_2.setBounds(302, 78, 98, 20);
		getContentPane().add(comboBox_2);

		JLabel lblAlgorithm = new JLabel("Algorithm :");
		lblAlgorithm.setBounds(300, 53, 61, 14);
		getContentPane().add(lblAlgorithm);

		final JTextArea textArea = new JTextArea();
		textArea.setBounds(55, 188, 327, 70);
		getContentPane().add(textArea);

		final JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(55, 148, 98, 29);
		getContentPane().add(textArea_1);

		JButton btnGetRoute = new JButton("Get Route");
		btnGetRoute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				compute cc = new compute();

				try {
					cc.get_cities_data();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				cc.init_variables();

				try {
					cc.get_weights_data();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				int n = cc.cities.size();

				String source = comboBox.getSelectedItem().toString();

				String destination = comboBox_1.getSelectedItem().toString();

				for (int i = 0; i < n; i++) {
					if ((cc.cities.get(i).equalsIgnoreCase(source)))
						cc.source_index = i;
					if ((cc.cities.get(i).equalsIgnoreCase(destination)))
						cc.destn_index = i;
				}

				if (comboBox_2.getSelectedItem().equals("Dijkstra")) {
					System.out.println("1");
					cc.distance[cc.source_index] = 0;
					cc.paths[cc.source_index] = ""
							+ cc.cities.get(cc.source_index);

					cc.dijkstra(cc.source_index);

					Float a = cc.distance[cc.destn_index];
					String b = cc.paths[cc.destn_index];

					textArea_1.setText(String.valueOf(a));
					System.out.println(b);
					textArea.setText(b);
				} else {
					System.out.println("2");
					cc.floyd_warshall();

					String a = String
							.valueOf(cc.weights[cc.source_index][cc.destn_index]);
					String b = String.valueOf(source + "->"
							+ cc.floyd_path(cc.source_index, cc.destn_index)
							+ destination);

					textArea_1.setText(a);
					textArea.setText(b);
				}

			}
		});
		btnGetRoute.setBounds(182, 149, 89, 23);
		getContentPane().add(btnGetRoute);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {

				System.out.println("hi..");
				String k = textField.getText();
				String[] args = new String[1];
				args[0] = k;

				System.out.println("hi :" + args[0]);

				try {
					Tries.main(args);
				} catch (IOException e) {
					e.printStackTrace();
				}

				k1 = new String[Tries.f.size()];

				for (int i = 0; i < Tries.f.size(); i++) {
					k1[i] = Tries.f.get(i);
				}

				System.out.println(k1.length);
				comboBox.setModel(new DefaultComboBoxModel(k1));

			}
		});
		textField.setBounds(55, 78, 96, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				System.out.println("hi..");
				String k = textField_1.getText();
				String[] args = new String[1];
				args[0] = k;

				System.out.println("hi :" + args[0]);

				try {
					Tries.main(args);
				} catch (IOException ee) {
					ee.printStackTrace();
				}

				k2 = new String[Tries.f.size()];

				for (int i = 0; i < Tries.f.size(); i++) {
					k2[i] = Tries.f.get(i);
				}

				System.out.println(k1.length);
				comboBox_1.setModel(new DefaultComboBoxModel(k2));

			}
		});
		textField_1.setBounds(185, 81, 107, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

	}
}
