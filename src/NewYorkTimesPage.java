import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class NewYorkTimesPage extends Page{

	public NewYorkTimesPage(WindowState window, Site site){
		super(site);
		this.window = window;
		this.SiteName = "New York Times";

	}

	@Override
	public String getTitle() {
		String str= "";
		boolean ok = false;

		try{
			WebElement ttl = driver.findElement(By.xpath("//*[@id='story-header']//*[@class='headline']"));
			str = ttl.getText();
			if(!str.isEmpty())
				ok  =true;

		}catch(Exception e){}
		
		if(!ok){
			try{
				WebElement ttl = driver.findElement(By.xpath("//*[@class='postHeader']//*[@itemprop='headline']"));
				str = ttl.getText();
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
	
		if(!ok){
			try{
				WebElement ttl = driver.findElement(By.xpath("//*[@class='articleHeadline']"));
				str = ttl.getText();
				if(!str.isEmpty())
					ok  = true;

			}catch(Exception e){}
		}
	
		if(!ok){
			try{
				WebElement ttl = driver.findElement(By.xpath("//*[@class='story-meta']//*[@itemprop='headline']"));
				str = ttl.getText();
				if(!str.isEmpty())
					ok  = true;

			}catch(Exception e){}
		}
		
		if(!ok){
			try{
				WebElement ttl = driver.findElement(By.xpath("//*[@id='story-meta']//*[@id='headline']"));
				str = ttl.getText();
				if(!str.isEmpty())
					ok  = true;

			}catch(Exception e){}
		}
		
		if(!ok){
			try{
				WebElement ttl = driver.findElement(By.xpath("//*[@class='story-meta']//*[contains(@class,'story-heading')]"));
				str = ttl.getText();
				if(!str.isEmpty())
					ok  = true;

			}catch(Exception e){}
		}
		
		
		return str;
	} 

	@Override
	public String getSubTitle() {
		String str ="";
		boolean ok = false;
		if(!ok){
			try{
				WebElement sttl = driver.findElement(By.xpath("//*[@id='story-meta']//*[@id='story-deck']"));
				str = sttl.getText();
				if(!str.isEmpty())
					ok  = true;

			}catch(Exception e){}
		}
		
		return str;
	}

	@Override
	public String getReporter() {


		String str= "";
		boolean ok = false;

		try{
			WebElement rprt = driver.findElements(By.xpath("//*[@id='story-header']//*[@class='byline-author']")).get(0);
			str = rprt.getText();
			if(!str.isEmpty())
				ok  =true;

			rprt = driver.findElements(By.xpath("//*[@id='story-header']//*[@class='byline-author']")).get(1);
			str += rprt.getText();

		}catch(Exception e){}

		if(!ok){

			try{
				WebElement rprt = driver.findElement(By.xpath("//*[@id='story-header']//*[@class='byline-column-link']"));
				str = rprt.getText();
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
		if(!ok){

			try{
				WebElement rprt = driver.findElement(By.xpath("//*[@class='postHeader']//*[@class='byline author vcard']"));
				str = rprt.getText();
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
		if(!ok){

			try{
				WebElement rprt = driver.findElement(By.xpath("//*[@itemprop='author creator']"));
				str = rprt.getText();
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
		
		if(!ok){

			try{
				WebElement rprt = driver.findElement(By.xpath("//*[@id='story-meta']//*[@class='byline-author']"));
				str = rprt.getText();
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
		
		
	
		return str;
	}

	@Override
	public String getDate() {

		String str= "";
		boolean ok = false;

		try{
			WebElement dt = driver.findElement(By.xpath("//*[@id='story-header']//*[@class='dateline']"));
			str = dt.getText();
			String[] arr = str.split(" ");
			//DEC. 27, 2017
			arr[0] = arr[0].substring(0, arr[0].length()-1);
			arr[0]=""+monthToInt(arr[0]);
			arr[1] = arr[1].substring(0, arr[1].length()-1);
			
			if(arr[2].length()>4)
				arr[2] = arr[2].substring(0, 4);
			
			str = arr[1]+"."+arr[0]+"."+arr[2];

			if(!str.isEmpty())
				ok = true;

		}catch(Exception e){}
		
		if(!ok){
			try{
				WebElement dt = driver.findElement(By.xpath("//*[@class='postHeader']//*[@class='dateline ']"));
				str = dt.getText();
				String[] arr = str.split(" ");
				//March 18, 2010 5:15 am
				arr[0]=""+monthToInt(arr[0]);
				arr[1] = arr[1].substring(0, arr[1].length()-1);
				
				if(arr[2].length()>4)
					arr[2] = arr[2].substring(0, 4);
				
				str = arr[1]+"."+arr[0]+"."+arr[2];

				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}
		
		if(!ok){
			try{
				WebElement dt = driver.findElement(By.xpath("//*[@class='dateline']"));
				str = dt.getText();
				String[] arr = str.split(" ");
				//Published: May 3, 2011
				arr[arr.length-3]=""+monthToInt(arr[arr.length-3]);
				arr[arr.length-2] = arr[arr.length-2].substring(0, arr[arr.length-2].length()-1);
				
				if(arr[arr.length-1].length()>4)
					arr[arr.length-1] = arr[arr.length-1].substring(0, 4);
				
				str = arr[arr.length-2]+"."+arr[arr.length-3]+"."+arr[arr.length-1];

				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement dt = driver.findElement(By.xpath("//*[@class='story-meta']//*[@class='dateline']"));
				str = dt.getText();
				String[] arr = str.split(" ");
				//JULY 24, 2016
				arr[0]=""+monthToInt(arr[0]);
				arr[1] = arr[1].substring(0, arr[1].length()-1);
				
				if(arr[arr.length-1].length()>4)
					arr[arr.length-1] = arr[arr.length-1].substring(0, 4);
				
				str = arr[1]+"."+arr[0]+"."+arr[2];

				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}
		
		if(!ok){
			try{
				WebElement dt = driver.findElement(By.xpath("//*[@id='story-meta']//*[@class='dateline']"));
				str = dt.getText();
				String[] arr = str.split(" ");
				//JULY 24, 2016
				arr[0]=""+monthToInt(arr[0]);
				arr[1] = arr[1].substring(0, arr[1].length()-1);
				
				if(arr[arr.length-1].length()>4)
					arr[arr.length-1] = arr[arr.length-1].substring(0, 4);
				
				str = arr[1]+"."+arr[0]+"."+arr[2];

				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}
		
		
		
		if(!ok){
			try{
				WebElement dt = driver.findElement(By.xpath("//*[@class='header-current-day']"));
				str = dt.getText();
				String[] arr = str.split(" ");
				//Feb.4, 2015
				str = arr[1];
				
				arr = arr[0].split("\\.");
				arr[0] = ""+monthToInt(arr[0]);
				arr[1] = arr[1].substring(0, arr[1].length()-1);
				
				str = arr[1]+"."+arr[0]+"."+str;
				
				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}


		if(!ok){
			try{
				WebElement dt = driver.findElement(By.xpath("//*[@class='story-meta']//time"));
				str = dt.getText();
				String[] arr = str.split(" ");
				//March 11, 2016
				
				arr[0] = ""+ monthToInt(arr[0]);
				arr[1] = arr[1].substring(0,arr[1].length()-1);
				
				str = arr[1]+"."+arr[0]+"."+arr[2];
				
				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}
		
		
		return str;
	}

	@Override
	public String getBody() {



		String str= "";
		boolean ok = false;

		try{
			ArrayList<WebElement> bdy = (ArrayList <WebElement>)
					driver.findElements(By.xpath("//p[@class='story-body-text story-content']"));

			for(int i=0; i<bdy.size(); i++)
				str += bdy.get(i).getText();
			
			if(!str.isEmpty())
				ok  =true;

		}catch(Exception e){}
		
		if(!ok){
			try{
				ArrayList<WebElement> bdy = (ArrayList <WebElement>)
						driver.findElements(By.xpath("//*[@class='story-body-text']"));

				for(int i=0; i<bdy.size(); i++)
					str += bdy.get(i).getText();
				
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
		
		if(!ok){
			try{
				ArrayList<WebElement> bdy = (ArrayList <WebElement>)
						driver.findElements(By.xpath("//*[@class='articleBody']/p[@itemprop='articleBody']"));

				for(int i=0; i<bdy.size(); i++)
					str += bdy.get(i).getText();
				
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
		
		
		if(!ok){
			try{
				ArrayList<WebElement> bdy = (ArrayList <WebElement>)
						driver.findElements(By.className("summary-text"));

				for(int i=0; i<bdy.size(); i++)
					str += bdy.get(i).getText();
				
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
		
		if(!ok){
			try{
				ArrayList<WebElement> bdy = (ArrayList <WebElement>)
						driver.findElements(By.xpath("//*[@class='story-body-text story-content']"));

				for(int i=0; i<bdy.size(); i++)
					str += bdy.get(i).getText();
				
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
		
		if(!ok){
			try{
				ArrayList<WebElement> bdy = (ArrayList <WebElement>)
						driver.findElements(By.xpath("//*[@class='story-body-text']"));

				for(int i=0; i<bdy.size(); i++)
					str += bdy.get(i).getText();
				
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
		
		if(!ok){
			try{
				ArrayList<WebElement> bdy = (ArrayList <WebElement>)
						driver.findElements(By.xpath("//*[@class='entry-content']//*[@class='story-body-text']"));

				for(int i=0; i<bdy.size(); i++)
					str += bdy.get(i).getText();
				
				if(!str.isEmpty())
					ok  =true;

			}catch(Exception e){}
		}
		
		

		return str;
	}

	@Override
	public ArrayList<CommentRow> commentSecction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readComments(ArrayList<CommentRow> commentsList) {
		// TODO Auto-generated method stub

	}

}
