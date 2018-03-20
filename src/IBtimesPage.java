import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class IBtimesPage extends Page{

	public IBtimesPage(WindowState window, Site site){
		super(site);
		this.window = window;
		this.SiteName = "International Business Times";
	}

	@Override
	public String getTitle() {

		String str = "";
		boolean ok = false;
		WebElement ttl;

		try{
			ttl = driver.findElement(By.xpath("//*[@class='article-header']/*[@itemprop='headline']"));
			str = ttl.getText();

			if(!str.isEmpty())
				ok = true;
		}catch(Exception e){}

		return str;
	}

	@Override
	public String getSubTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReporter() {

		String str = "";
		boolean ok = false;
		WebElement rptr;

		try{
			rptr = driver.findElement(By.xpath("//*[@class='author']")); 
			str = rptr.getText();

			if(!str.isEmpty())
				ok = true;
		}catch(Exception e){}

		return str;
	}

	@Override
	public String getDate() {

		String str = "";
		boolean ok = false;
		WebElement dt;

		try{//08/15/17 AT 3:35 PM
			dt = driver.findElement(By.xpath("//*[@itemprop='datePublished']")); 
			str = dt.getText();

			String[] arr = str.trim().split("/");
			arr[2] = arr[2].split(" ")[0];
		
			str = arr[1]+"."+arr[0]+".20"+arr[2];
			
			if(!str.isEmpty())
				ok = true;
		}catch(Exception e){}

		return str;
	}

	@Override
	public String getBody() {
		String str = "";
		boolean ok = false;
		ArrayList<WebElement> bd;
		
		try{
			
			bd = (ArrayList<WebElement>) 
					driver.findElements(By.xpath("//*[@class='article-body']/p"));
			
			for(WebElement p : bd){
				try{
					str += p.getText();
				}catch(Exception e){}
			}
				
			if(!str.isEmpty())
				ok = true;
			
		}catch(Exception e){}
		
		
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
