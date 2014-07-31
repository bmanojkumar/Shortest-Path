import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class setProxy
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


class getData
{
	getData()
	{
		 setProxy p = new setProxy();
		 p.lab();
	}
	public ArrayList<String> al = new ArrayList<String>();
	public void getData1(String ref)
	{
		 try
	      {			 
			 
			 System.out.println("Reading..." + ref);
	          URL  url  = new URL(ref); 
	          URLConnection urlC = url.openConnection();	          
	          InputStream is = url.openStream();
	          System.out.flush();
	          FileOutputStream fos=null;
	          
	          String localFile="distances.txt";             
	          fos = new FileOutputStream(localFile);                   
	          int oneChar, count=0;
	          
	          
	          while ((oneChar=is.read()) != -1)
	          {
	             fos.write(oneChar);
	             count++;
	          }
	          
	          
	          is.close();
	          fos.close();
	      }
	      catch (MalformedURLException e)
	      { 
	    	  System.err.println(e.toString()); 
	      }
	      catch (IOException e)
	      { 
	    	  System.err.println(e.toString()); 
	      }
	}
	
	
	public void readFile() throws IOException
	{
        String pattern = "<li><a class=\"content-link\" href=\"\\w+";
        Pattern p = Pattern.compile(pattern);
        
		File fp = new File("distances.txt");
		
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);
		
		String temp = "";
		
		while((temp = br.readLine()) != null)
		{
			Matcher m = p.matcher(temp);
			while (m.find()) {
				
				String h = temp.substring(m.start(), m.end());
				int count = 0;
				String city = "";
				
				for(int i=0;i<h.length();i++)
				{
					if(count == 3)
					{
						city = city + h.charAt(i);
					}
					if(h.charAt(i) == '"')
					{
						count++;
					}
										
				}
				
				al.add(city);				
											
			}
		}		
	}
}

class A
{
	public static void main(String[] args) throws IOException
	{
		
		Map m1 = new HashMap(); 
		
		
		
		getData s = new getData();
		s.getData1("http://www.mapsofindia.com/distances/");
		s.readFile();
		
		
		for(int z=0;z<s.al.size();z++)
		{
			System.out.println("----------------------------------------------------------------");
			s.getData1("http://www.mapsofindia.com/distances/"+s.al.get(z)+".html");
			//s.getData1("http://www.mapsofindia.com/distances/chandigarh.html");
			
			for(int i=0;i<s.al.size();i++)
			{
				
				String k = s.al.get(i);
				//System.out.println(k);
				
					//System.out.println("k :" + k);
					String pattern = Character.toUpperCase(k.charAt(0))+k.substring(1);
					Pattern p = Pattern.compile(pattern);
					
					String pattern1 = "\\d+";
					Pattern p1 = Pattern.compile(pattern1);
					
					String pattern2 = "<li>\\W*\\d+\\W";
					Pattern p2 = Pattern.compile(pattern2);
					
					String pattern3 = "<li>\\W*\\w+";
					Pattern p3 = Pattern.compile(pattern3);
					
					File fp = new File("distances.txt");
					
					FileReader fr = new FileReader(fp);
					BufferedReader br = new BufferedReader(fr);
					
					boolean found = false;
					
					String temp = "";
					while((temp = br.readLine()) != null )
					{
						//System.out.println("hi");
						if(temp.contains("<table  cellspacing="))
						{
							//System.out.println("found table");
							found = true;
						}
						
						if(found)
						{
							Matcher j = p.matcher(temp);
						
								if(j.find())
								{
									
									if(temp.contains("<li>"))
									{
										Matcher g = p3.matcher(temp);
										
										if(g.find())
										{
										
											System.out.println("list item...");
											String kl = "";
											while((kl = br.readLine()) != null)
											{
													Matcher ee = p2.matcher(kl);
													
													String tmp = "";
													
													if(ee.find())
													{
														//System.out.println(kl.substring(ee.start(),ee.end()));
														for(int yy=kl.length()-1;yy>=0;yy--)
														{
															kl.charAt(yy);
															if(Character.isDigit(kl.charAt(yy)))
															{
																tmp = tmp + kl.charAt(yy);
															}
														}
														
														tmp = new StringBuffer(tmp).reverse().toString();
														System.out.println(tmp);
													}
											}
											continue;
										}
									}
									
									System.out.println(temp.substring(j.start(), j.end()));
									String jj = temp.substring(j.start(), j.end());
									if(jj.equals("Delhi"))
									{
										temp = br.readLine();
									}
									temp = br.readLine(); // ""
									
									while(!temp.endsWith("</td>"))
									{
										temp = br.readLine();
									}
									
									
									int t = temp.lastIndexOf("</td>");
									String dist = "";
									
									for(int r=t-1;r>=0;r--)
									{
										if(Character.isDigit(temp.charAt(r)))
											dist = dist + temp.charAt(r);
									}
									
									dist = new StringBuffer(dist).reverse().toString();
									
									System.out.println(dist);
									
									/*while(temp.equals(""))
									{
										temp = br.readLine();
										//System.out.println(temp);
									}
									
									while(!Character.isDigit(temp.charAt(0)))
									{
										temp = br.readLine();
										//System.out.println(temp);
									}*/
									
									/*Matcher j1 = p1.matcher(temp);
									
									while(j1.find())
									{
										System.out.println(temp.substring(j1.start(),j1.end()));
									}*/
						
								
									
									/*while(!j1.find())
									{
										System.out.println("hi");
										
										temp = br.readLine();
										j1.reset();
										if(!temp.substring(j1.start(),j1.end()).equals(""))
										{
											System.out.println("hjk");
										}
									}
									
									j1.reset();*/
									
									
									/*System.out.println("temp :" + temp);
									if(temp.equals(""))
									{
										System.out.println("hi");
										temp = br.readLine(); // ""
											if(temp.equals(""))
												temp = br.readLine(); //td
									}
									temp = br.readLine();
									
									System.out.println("aagrs : " +  temp);*/
									
									
									
									/*while(j1.find())
									{
										System.out.println(temp.substring(j1.start(), j1.end()));
									}*/
								}
							}
						}
				}
			}
			
		}
		
/*		String pattern = "<table  cellspacing=";
		Pattern p = Pattern.compile(pattern);
		
		
		String pattern1 = "<TD ALIGN=\"left\" >";
		Pattern p1 = Pattern.compile(pattern1);
		
		String pattern2 = "<td ><b>CITY</b></td>";
		Pattern p2 = Pattern.compile(pattern2);
		
		String pattern3 = "<TD ALIGN=\"left\"";
		Pattern p3 = Pattern.compile(pattern3);
		
		String pattern4 = "<td>\\w+</td>";
		Pattern p4 = Pattern.compile(pattern4);
		
		String pattern5 = "<TD><a href=\"http://www.mapsofindia.com/\\w+/";
		Pattern p5 = Pattern.compile(pattern5);
		
		File fp = new File("distances.txt");
		
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);
		
		boolean found = false;
		
		String temp = "";
		while((temp = br.readLine()) != null)
		{
			
			
			
			Matcher j = p3.matcher(temp);
			Matcher j1 = p4.matcher(temp);
			Matcher j2 = p5.matcher(temp);
			
			while(j.find())
			{
				//System.out.println(temp);
				
				int i=temp.lastIndexOf("<u>");
				
				String tt = "";
				char c;
				while((c = temp.charAt(i+3)) != '<')
				{
					tt = tt + c;
					i++;
				}
					
				System.out.println(temp);
				System.out.println(tt);
				
				//System.out.println(temp.substring(j.start(), j.end()));
			}
			
			while(j1.find())
			{
				//System.out.println(temp);
				String tt = "";
				int i =0;
				char c;
				while((c = temp.charAt(i+4)) != '<')
				{
					tt = tt + c;
					i++;
				}
				
				System.out.println(temp);
				System.out.println(tt);
				//System.out.println(temp.substring(j1.start(), j1.end()));
			}
			
			while(j2.find())
			{
				
				String tt = "";
				int i = temp.lastIndexOf("</a></td>");
				char c;
				
				System.out.println("index :  " + i);
				
				while((c = temp.charAt(i-1)) != '>')
				{
					tt = tt + c;
					i--;
				}
				
				System.out.println(temp);
				System.out.println(tt);
				//System.out.println(temp);
			}*/
			
			/*
			Matcher k = p1.matcher(temp);
			while(k.find())
			{
				found = true;
			}
			Matcher m = p.matcher(temp);
			while (m.find()) {
			
				temp = br.readLine();
			}
			
			if(found)
			{
				Matcher l = p2.matcher(pattern2);
				
				while(l.find())
				{
					System.out.println(temp);
				}	
			}*/
		}
		
	
