import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * @author Ben Horn
 * @since 1/2018
 *
 */

public abstract class Page extends Funcs{
	

	public WebDriver driver;
	
	
	public List<ArticlesRow> linksToList(List<String> urls){
		String url="";
		driver = startWebDriver("http://google.com");
		List<ArticlesRow> reports = new ArrayList<ArticlesRow>();
		for(int i=0; i<urls.size(); i++){
			url=urls.get(i);
			try{
				driver.navigate().to(url);
			}
			catch(WebDriverException e){
				System.out.println("Invaild url "+url);
				continue;
			}
			reports.add(urlHandler(url, false));
		}
		driver.quit();
		System.out.println("finish Ynet");
		return reports;
	}

	
	
	public abstract ArticlesRow urlHandler(String url, boolean bodyOnly);
	
	/**
	 * this function get all the comments
	 * @param url
	 * @param articleNum
	 * @return all the comments connected
	 */
	public abstract ArrayList<CommentRow> getComments(String url, int articleNum);
	
	public  abstract  ArrayList<CommentRow>  commentSecction(String url, int articleNum);
	
	public abstract void readComments(ArrayList<CommentRow> commentsArr, int articleNum);
}
