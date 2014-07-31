/*import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;


class Vertex
{
	public boolean visited;
	public boolean adj_visit;
	Vertex()
	{
		visited = false;
		adj_visit = false;
	}
}

class set_Proxy
{
	public void room()
	{		
		System.setProperty("http.proxyHost", "proxy.iiit.ac.in");
		System.setProperty("http.proxyPort", "8080");
	}
	
	public void lab()
	{
		final String authUser = "iiit-37";
		final String authPassword = "msit123";

		System.setProperty("http.proxyHost", "10.10.10.3");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("http.proxyUser", authUser);
		System.setProperty("http.proxyPassword", authPassword);

		Authenticator.setDefault(new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(authUser, authPassword
						.toCharArray());
			}
		});

		System.setProperty("http.proxyUser", authUser);
		System.setProperty("http.proxyPassword", authPassword);

	}
}


class get_Data
{
	ArrayList<String> al = new ArrayList<String>();
	int size;
	
	float[][] weights;
	float[][] adjmatrix;
	float[]   distance;
	Vertex[]  vertexList;
	String[]  path;
	
	
	get_Data()
	{
		 set_Proxy p = new set_Proxy();
		 p.lab();
	}
	
	public void read_cities() throws IOException
	{
		File fp = new File("cities.txt");
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);
		
		String temp = "";
		int i = -1;
		
		while((temp = br.readLine()) != null)
		{
			al.add(temp);
		}
		
		size = al.size();
	}

	public void adjmatrix()
	{
		adjmatrix = new float[al.size()][al.size()];
		
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				adjmatrix[i][j] = 1;
				//adjmatrix[j][i] = 0;
				if(i == j)
					adjmatrix[i][j] = 0;
			}
		}
		
	}
	
	public void construct_weights() throws IOException
	{
		weights = new float[size][size];
		
		File fp = new File("Matrix.txt");
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);
		
		String temp = "";
		int i = -1;
		
		while((temp = br.readLine()) != null)
		{
			i++;
			String[] t = temp.split("\\t");
			
			for(int h=0;h<t.length;h++)
			{
				//System.out.println(weights[i][h]);
				weights[i][h] = Float.parseFloat(t[h]);
			}
	
		}
		
	}
	
	public void display_weights()
	{
		
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				System.out.print(weights[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	
	void dijkstra_init()
	{
		distance = new float[size];
		vertexList = new Vertex[size];
		path = new String[size];
		
		
		for(int r=0;r<size;r++)
		{
			vertexList[r] = new Vertex();
		}
		
		for(int i=0;i<distance.length;i++)
		{
			distance[i] = -1;
		}
		
	}
	
	void dijkstra(int s)
	{
		this.dijkstra_init();
		distance[s] = 0;
		
		PriorityQueue<Integer> qu = new PriorityQueue<>(al.size(),new Comparator<Integer>()
		{
			public int compare(Integer o1, Integer o2) {
				return (int) (distance[o1] - distance[o2]);
				}
					
		});
		
		int v,w;
		
		qu.add(s);
		path[s] = al.get(s);
	//	vertexList[s].visited = true;
	
		
		while(!qu.isEmpty())
		{
			v = qu.remove();
			
			for(int h=0;h<size;h++)
			{
				vertexList[h].adj_visit = false;
			}
			
			vertexList[v].visited = true;
			
			while((w = getAdjacent(v)) != -1)
			{
					vertexList[w].adj_visit = true;
					
					int t = (int) (distance[v] + weights[v][w]);
					

					if(distance[w] == -1)
					{
						distance[w] = t;
						qu.add(w);
						
					}
			
					System.out.println("ddddddddddd : " + distance[w]);
					
					if(distance[w] > t && vertexList[w].visited)
					{
						System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
						qu.remove(w);
						distance[w] = t;
						path[w] = path[w-1]+","+al.get(w);
						System.out.println("------------" + path[w]);
						qu.add(w);
					}
			 }
			
		}
		
	}
	
	public int getAdjacent(int v)
	{
		for(int j=0;j<size;j++)
		{
			if(adjmatrix[v][j] == 1 && vertexList[j].adj_visit == false)
				return j;
		}
		return -1;
	}

	
	public void print_distancetable()
	{
		for(int i=0;i<size;i++)
		{
			System.out.println(al.get(i) + " : " + distance[i]);
		}
	}
	
	public void print_linked()
	{
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				System.out.print(adjmatrix[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
}


class B
{
	public static void main(String[] args) throws IOException
	{
		set_Proxy d = new set_Proxy();d.lab();
		get_Data  gd = new get_Data();
		gd.read_cities();
		gd.construct_weights();
		gd.display_weights();
		gd.adjmatrix();
		gd.print_linked();
		gd.dijkstra(0);
		gd.print_distancetable();
		
		Scanner sc= new Scanner(System.in);
		
		String source = "";
		String dest = "";
		System.out.println("Enter the source :");
		source = sc.nextLine();
		System.out.println("Enter the destination :");
		dest = sc.nextLine();
		
		int sIndex = 0;
		int dIndex = 0;
		
		for(int i=0;i<gd.al.size();i++)
		{
			if(gd.al.get(i).equalsIgnoreCase(source))
				sIndex = i;
			if(gd.al.get(i).equalsIgnoreCase(dest))
				dIndex = i;
		}
		
		System.out.println("Distance : " + gd.distance[dIndex]);
		System.out.println("Path : " + gd.path[dIndex]);
		
		
	}
}

*/