import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ReutersPage extends Page {

	public ReutersPage(WindowState window){
		super();
		this.window = window;
		this.SiteName = "Reuters";
	}

	private final String s = "//*[@class='ArticlePage_container_2aGp_']/div[1]";

	@Override
	public String getTitle() {

		String str="";
		boolean ok = false;
		try{
			WebElement head = driver.findElement(By.xpath(s+"//*[contains(@class,'ArticleHeader_headline')]"));
			str = head.getText();
			ok = true;
		}catch(Exception e){}

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
			ok=true;
		}
		catch(Exception e){}
		if(!ok){
			try{
				WebElement reporter = driver.findElement(By.xpath(s+"//*[contains(@class,'BylineBar_byline')]"));
				str = reporter.getText();
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
			ok=true;
		}
		catch(Exception e){}
		return str;
	}

	@Override
	public String getBody() {
		String str = "";
		boolean ok = false;
		try{
			ArrayList<WebElement> body = (ArrayList<WebElement>) driver.findElements(By.xpath(s+"//*[@class='StandardArticleBody_body_1gnLA']/p"));
			for(int i=0; i<body.size(); i++){
				str+=body.get(i).getText();
			}
			ok =true;
		}
		catch(Exception e){}

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
