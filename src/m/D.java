package manoj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

class Vertex implements Comparable<Vertex>
{
	float min_distance;
	int index;
	
	Vertex(float dist,int position)
	{
		this.min_distance=dist;
		this.index=position;
	}
	
	public int compareTo(Vertex v) {		
		return (int) (min_distance - v.min_distance);
    }
}


class compute
{
	int size;
	float[][] weights;
	float[] distance;
	String[] paths;
	int source_index;
	int destn_index;
	ArrayList<String> cities = new ArrayList<String>();
	Vertex v= null;
	int[] track_visited;
	PriorityQueue<Vertex> queue=new PriorityQueue<Vertex>();
	
	
	public void get_cities_data() throws IOException
	{
		File fp = new File("cities.txt");
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);
		
		String temp = "";
		
		while((temp = br.readLine()) != null)
		{
			cities.add(temp);
		}
		
		System.out.println("size :" + cities.size());
		
	}
	
	public void init_variables()
	{
		size = cities.size();
		weights = new float[size][size];
		paths = new String[size];
		track_visited = new int[size];
		distance = new float[size];
		
		for(int i=0;i<size;i++)
		{
			track_visited[i] = 0;
			distance[i] = (float) 9999.0;
		}
	}
	
	public void get_weights_data() throws IOException
	{
		File fp = new File("Matrix.txt");
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);
		
		int i = -1;
		
		String temp = "";
		
		while((temp = br.readLine()) != null)
		{
			i++;
			String[] t = temp.split("\\t");
			
			for(int h=0;h<t.length;h++)
			{
					weights[i][h] = Float.parseFloat(t[h]);
			}
			
			for(int g=0;g<t.length;g++)
			{
				if(i!=g && weights[i][g] == 0.0)
					weights[i][g]=(float)99999.99;

			}
		}				
	}
	
	public void dijkstra(int src)
	{
		this.v=new Vertex(distance[src],src);
		queue.add(v);
		this.track_visited[src]=1;
		
		while(!queue.isEmpty())
		{
			
			Vertex temp=queue.remove();
			
			int s=temp.index;
			
			for(int i=0;i<cities.size();i++)
			{	
				if(weights[s][i]>0 && s!=i && track_visited[i]==0)
				{
					if(distance[i]>(weights[s][i]+distance[s]))
					{
						distance[i]=weights[s][i]+distance[s];
						
						paths[i]=paths[s]+"->"+cities.get(i);
						
						Vertex t=new Vertex(distance[i],i);
						
						queue.add(t);
					}
				}
			}
		}
		
	}
	
	public int[][] next;
	boolean found = false;
	int distance1 = 0;
	
	public void floyd_warshall()
	{
		next = new int[size][size];
		
		for(int k=0;k<size;k++)
		{
			for(int i=0;i<size;i++)
			{
				for(int j=0;j<size;j++)
				{
					if(weights[i][k] + weights[k][j] < weights[i][j] )
					{
						found = true;
						weights[i][j] = weights[i][k] + weights[k][j];
						next[i][j] = k;
					//	distance1 = (int) (distance1 + weights[i][j]); 
					}
					
					//System.out.println("weoghts : " + weights[i][j]);
				}
			}
		
		}
		
		/*for(int ii=0;ii<size;ii++)
		{
			for(int jj=0;jj<size;jj++)
			{
				System.out.print(next[ii][jj] + "  ");

			}
			System.out.println();
		}*/
				 
	}
	
	
	public String floyd_path(int i,int j)
	{
		/*for(int ii=0;ii<size;ii++)
		{
			for(int jj=0;jj<size;jj++)
			{
				System.out.print(next[ii][jj] + "  ");

			}
			System.out.println();
		}*/
		
		//System.out.println("i :" + i + " j :" + j);
		if(weights[i][j] == 9999.0)
		{
			return "no path";
		}
		
		int intermediate = next[i][j];
		
		//System.out.println("intermediate"  + intermediate);
		
		if(intermediate == 0)	
			return "";
		else
			return floyd_path(i,intermediate) + cities.get(intermediate) + "->" + floyd_path(intermediate,j);
	
	}
	
	/*public float floyd_dist(int i,int j)
	{
		for(int ii=0;ii<size;ii++)
		{
			for(int jj=0;jj<size;jj++)
			{
				System.out.print(next[ii][jj] + "  ");

			}
			System.out.println();
		}
		
		System.out.println("weights : " + weights[i][j]);
		//System.out.println("i :" + i + " j :" + j);
		if(weights[i][j] == 9999.0)
		{
			return 0;
		}
		
	
		
		
		int intermediate = next[i][j];
	//	System.out.println("intermediate"  + intermediate);
		
		if(intermediate == 0)	
			return 0;
		else
			return weights[i][j] + floyd_dist(i,intermediate) + floyd_dist(j,intermediate);
	
	}
}
*/
	
}
	
class D
{
	public static void main(String[] args) throws Exception
	{
		
		Scanner sc = new Scanner(System.in);
		compute cc = new compute();
		cc.get_cities_data();
		cc.init_variables();
		cc.get_weights_data();
	
		int n = cc.cities.size();
		
		System.out.print("City 1: ");
		String source=sc.next();
		
		System.out.print("City 2: ");
		String destination=sc.next();
		
		for(int i=0;i<n;i++)
		{
			if((cc.cities.get(i).equalsIgnoreCase(source)))
				cc.source_index=i;
			if((cc.cities.get(i).equalsIgnoreCase(destination)))
				cc.destn_index=i;
		}
		
		cc.distance[cc.source_index]=0;
		cc.paths[cc.source_index]=""+cc.cities.get(cc.source_index);
		
		cc.dijkstra(cc.source_index);
		
		System.out.println("Minimum distance : " + cc.distance[cc.destn_index]);
		System.out.println("Route : " +cc.paths[cc.destn_index]);
		
		cc.floyd_warshall();
		
		//System.out.println(cc.cities.get(cc.source_index) + "source : " + cc.source_index );
		//System.out.println(cc.cities.get(cc.destn_index) + "destn : " + cc.destn_index );
		System.out.println(cc.floyd_path(cc.source_index, cc.destn_index));
		
		if(cc.found == false)
			System.out.println(cc.cities.get(cc.source_index) + "->" + cc.cities.get(cc.destn_index));

		

		float d = cc.next[cc.source_index][cc.destn_index];
		System.out.println("Minimum Distance with Floyd : " + cc.weights[cc.source_index][cc.destn_index]);
		
	}
}



