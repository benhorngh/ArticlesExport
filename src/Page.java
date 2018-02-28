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
	String SiteName ="";
	Site site;

	public Page() {
	}
 
	public Page(Site site) {
		this.site = site;
	}





	/**
	 * @param urls -List with the urls of the reports. 
	 * @return List with the reports orgnaized in 'ArticlesRow' class.
	 */
	public List<ArticlesRow> linksToList(List<String> urls){
		String url="";
		mainScreen.addToLog("Start open URLs");
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
			if(i==0){
				try{
					signIn();
					driver.navigate().refresh();
				}
				catch(Exception e){e.printStackTrace();System.err.println("can't login");}
			}
			reports.add(urlHandler(url, false));
			System.out.println("finish URL");
			mainScreen.addToLog("finish url ."+(i+1));

		}
		driver.close();
		driver.quit();
		System.err.println("finish "+SiteName);

		mainScreen.addToLog("finish "+SiteName);

		return reports;
	}





	/**
	 * this function handle one article.
	 * @param url -url of the report
	 * @param bodyOnly -true if include comments, false for only body
	 * @return article in 'ArticleRow' type.
	 */
	public ArticlesRow urlHandler(String url, boolean bodyOnly) {

		ArticlesRow ar = new ArticlesRow();
		try{

			String headline="", subheadline="",publishDate="",reporter="";
			sleep(2000);

			//headline
			try{
				headline = getTitle();
			}
			catch(Exception e){e.printStackTrace(); System.err.println("cant get title");}


			//subheadline
			try{
				subheadline=getSubTitle();
			}
			catch(Exception e){System.err.println("cant get subtitle");}


			//publish date
			try{
				publishDate=getDate();
			}
			catch(Exception e){System.err.println("cant get publish date");}


			//reporter
			try{
				reporter = getReporter();
			}
			catch(Exception e){System.err.println("cant get reporter");}


			String article="";
			//body
			try{

				article = getBody();
			}
			catch(Exception e){System.err.println("cant get body");}


			ar.body = article;
			ar.site = SiteName;
			ar.URL = url;
			ar.date =publishDate;
			ar.reporter = reporter;
			ar.headLine = headline;
			ar.subHeadLine = subheadline;

			if(bodyOnly){
				return ar;
			}

			ArrayList<CommentRow> cmmts = null;
			try{
				cmmts = getComments();
			}catch(Exception e){e.printStackTrace();}

			ar.setComments(cmmts);

		}catch (Exception e){
			e.printStackTrace();
		}
		return ar;
	}


	/**
	 * @return title of the article
	 */
	public abstract String getTitle();

	/**
	 * @return subtitle of the article
	 */
	public abstract String getSubTitle();


	/**
	 * @return author of the article
	 */
	public abstract String getReporter();

	/**
	 * @return publish date of the article.
	 */
	public abstract String getDate();

	/**
	 * @return body of the article
	 */
	public abstract String getBody();



	/**
	 * this function get all the comments
	 * @param url
	 * @return all the comments connected
	 */
	public ArrayList<CommentRow> getComments(){
		ArrayList<CommentRow>  cmmt =  commentSecction();
		return cmmt;
	}


	/**
	 * this function get all the comments. 
	 * @param url
	 * @return all the comments in a list
	 */
	public  abstract  ArrayList<CommentRow>  commentSecction();


	/**
	 * add to the list of comments one page of comments.
	 * @param commentsArr -list of comments
	 */
	public abstract void readComments(ArrayList<CommentRow> commentsList);


	@Override
	protected void finalize(){
		try{
			driver.close();
			this.driver.quit();
		}catch(Exception e){}
	}
}
