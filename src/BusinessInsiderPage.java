import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class BusinessInsiderPage extends Page{


	public BusinessInsiderPage(WindowState window, Site site){
		super(site);
		this.window = window;
		this.SiteName = "BusinessInsider";

	}


	@Override
	public String getTitle() {
		String str="";
		boolean ok = false;

		try{
			WebElement ttl = driver.findElement(By.xpath("//*[@class='post-headline']"));
			str = ttl.getText();

			if(!str.isEmpty())
				ok = true;

		}catch(Exception e){}

		if(!ok){
			try{
				WebElement ttl = driver.findElement(By.xpath("//*[@class='sl-layout-post']/h1"));
				str = ttl.getText();

				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}


		return str;
	}

	@Override
	public String getSubTitle() {

		String str = "";
		boolean ok = false;

		try{
			ArrayList<WebElement> sttl = (ArrayList<WebElement>)
					driver.findElements(By.xpath("//section[contains(@class,'post-content')]//ul/li"));

			for(int i=0; i<sttl.size(); i++)
				str += sttl.get(i).getText();

			if(!str.isEmpty())
				ok = true;
		}catch(Exception e){}

		return str;
	}

	@Override
	public String getReporter() {

		String str = "";
		boolean ok = false;

		try{
			WebElement rptr = driver.findElement(By.xpath("//*[@class='byline-link byline-author-name']"));
			str = rptr.getText();

			if(!str.isEmpty())
				ok = true;

		}catch(Exception e){}

		if(!ok){
			try{
				WebElement rptr = driver.findElement(By.xpath("//*[@class='byline-author']"));
				str = rptr.getText();

				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement rptr = driver.findElement(By.xpath("//*[@class='flex byline']//*[contains(@class,'author')]"));
				str = rptr.getText();

				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}



		return str;
	}

	@Override
	public String getDate() {

		String str = "";
		boolean ok = false;

		try{//Nov. 29, 2017, 11:14 AM
			WebElement dt = driver.findElement(By.xpath("//*[@class='byline-timestamp']"));
			str = dt.getText();
			str = str.trim();

			String[] arr = str.split(" ");
			arr[0] = arr[0].substring(0, arr[0].length()-1);
			arr[0] = ""+ monthToInt(arr[0]);
			arr[1] = arr[1].substring(0, arr[1].length()-1);
			arr[2] = arr[2].substring(0, arr[2].length()-1);

			str = arr[1]+"."+arr[0]+"."+arr[2];

			if(!str.isEmpty())
				ok = true;

		}catch(Exception e){}

		try{//May 18, 2017,  6:35 AM
			WebElement dt = driver.findElement(By.xpath("//*[@class='flex byline']//*[@data-bi-format='date']"));
			str = dt.getText();
			str = str.trim();

			String[] arr = str.split(" ");
			arr[0] = ""+ monthToInt(arr[0]);
			arr[1] = arr[1].substring(0, arr[1].length()-1);
			if(arr[2].trim().length()>4)
				arr[2] = arr[2].substring(0, arr[2].length()-1);

			str = arr[1]+"."+arr[0]+"."+arr[2];

			if(!str.isEmpty())
				ok = true;

		}catch(Exception e){}



		return str;
	}

	@Override
	public String getBody() {

		String str = "";
		boolean ok = false;

		try{
			ArrayList<WebElement> bdy = (ArrayList<WebElement>)
					driver.findElements(By.xpath("//section[contains(@class,'post-content')]//p"));

			for(int i=0; i<bdy.size(); i++)
				str += bdy.get(i).getText();

			if(!str.isEmpty())
				ok = true;
		}catch(Exception e){}

		if(!ok){
			try{
				ArrayList<WebElement> bdy = (ArrayList<WebElement>)
						driver.findElements(By.xpath("//*[contains(@class,'post-content')]/p"));

				for(int i=0; i<bdy.size(); i++)
					str += bdy.get(i).getText();

				if(!str.isEmpty())
					ok = true;
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
