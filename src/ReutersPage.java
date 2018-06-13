import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ReutersPage extends Page {

	public ReutersPage(WindowState window, Site site){
		super(site);
		this.window = window;
		this.SiteName = "Reuters";
	}

	private final String s = "//*[contains(@class,'inner-container')]";

	@Override
	public String getTitle() {

		String str=""; 
		boolean ok = false;
		try{
			WebElement head = driver.findElement(By.xpath(s+"//*[contains(@class,'ArticleHeader_headline')]"));
			str = head.getText();

			if(!str.isEmpty())
				ok = true;
		}catch(Exception e){}

		if(!ok){
			try{
				WebElement head = driver.findElement(By.xpath("//*[@class='columnRight grid8']/h2"));
				str = head.getText();

				if(!str.isEmpty())
					ok = true;
			}catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement head = driver.findElement(By.xpath(s+"//*[contains(@class,'headline')]"));
				str = head.getText();

				if(!str.isEmpty())
					ok = true;
			}catch(Exception e){}
		}


		return str;
	}

	@Override
	public String getSubTitle() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getReporter() {
		String str="";
		boolean ok = false;

		try{
			WebElement reporter = driver.findElement(By.xpath(s+"//*[contains(@class,'Attribution_container')]"));
			str = reporter.getText();
			if(!str.isEmpty())
				ok=true;
		}
		catch(Exception e){}
		if(!ok){
			try{
				WebElement reporter = driver.findElement(By.xpath(s+"//*[contains(@class,'BylineBar_byline')]"));
				str = reporter.getText();
				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement reporter = driver.findElement(By.xpath(s+"//*[contains(@class,'byline')]//*[contains(@class,'byline')]"));
				str = reporter.getText();
				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement reporter = driver.findElement(By.xpath("//*[@id='preamble']"));
				str = reporter.getText();
				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement reporter = driver.findElement(By.xpath(s+"//*[contains(@class,'byline')]"));
				str = reporter.getText();
				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}
		return str;
	}

	@Override
	public String getDate() {
		String str="";
		boolean ok = false;

		try{
			WebElement reporter = driver.findElement(By.xpath(s+"//*[contains(@class,'ArticleHeader_date')]"));
			str = reporter.getText();
			String[] arr = str.split(" ");
			int month = monthToInt(arr[0]);
			String day = arr[1].substring(0, arr[1].length()-1);
			String year = arr[2];
			str = day+"."+month+"."+year;
			if(!str.isEmpty())
				ok=true;

		}
		catch(Exception e){}

		if(!ok){
			try{
				WebElement reporter = driver.findElement(By.xpath("//*[@class='timestamp']"));
				str = reporter.getText();
				String[] arr = str.split(" ");
				int month = monthToInt(arr[0]);
				String day = arr[1].substring(0, arr[1].length()-1);
				String year = arr[2];
				str = day+"."+month+"."+year;
				ok=true;
			}
			catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement reporter = driver.findElement(By.xpath("//*[contains(@class,'inner-container')]//*[contains(@class,'date_V9eGk')]"));
				str = reporter.getText();
				String[] arr = str.split(" ");
				int month = monthToInt(arr[0]);
				String day = arr[1].substring(0, arr[1].length()-1);
				String year = arr[2];
				str = day+"."+month+"."+year;
				ok=true;
			}
			catch(Exception e){}
		}
		
		
		return str;
	}

	@Override
	public String getBody() {
		String str = "";
		boolean ok = false;
		try{
			ArrayList<WebElement> body = (ArrayList<WebElement>) driver.findElements(By.xpath(s+"//*[conatins(@class,'StandardArticleBody_body')]/p"));
			for(int i=0; i<body.size(); i++){
				str+=body.get(i).getText();
			}
			if(!str.isEmpty())
				ok =true;
		}
		catch(Exception e){}
		
		try{
			WebElement arti = driver.findElement(By.xpath("//*[contains(@class,'inner-container')]"));
			ArrayList<WebElement> body = (ArrayList<WebElement>) arti.findElements(By.xpath(".//*[contains(@class,'body_1gnLA')]/p"));
			for(int i=0; i<body.size(); i++){
				str+=body.get(i).getText();
			}
			if(!str.isEmpty())
				ok =true;
		}
		catch(Exception e){}

		if(!ok){
			try{
				ArrayList<WebElement> body = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@id='postcontent']/p[@data-ektron-preserve='true']"));
				for(int i=0; i<body.size(); i++){
					str+=body.get(i).getText();
				}
				if(!str.isEmpty())
					ok =true;
			}
			catch(Exception e){}
		}
		if(!ok){
			try{
				ArrayList<WebElement> body = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@class',body_1gnLA')]"));
				for(int i=0; i<body.size(); i++){
					str+=body.get(i).getText();
				}
				if(!str.isEmpty())
					ok =true;
			}
			catch(Exception e){}
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
