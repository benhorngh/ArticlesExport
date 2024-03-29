import java.util.ArrayList;
import java.util.Date;
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


			sleep(1000);

			try{

				driver.get(url);
			}catch(WebDriverException e){
				e.printStackTrace();
				System.out.println("Invaild url "+url);

				driver = startOver(driver);
				continue;
			}

			if((i+1)%10 == 0){
				try{
					System.out.println("START OVER***");
					driver =startOver(driver);
				}catch(Exception e){e.printStackTrace();}
			}sleep(2000);



			//			if(i==0){
			//				try{
			//					signIn();
			//					driver.navigate().refresh();
			//				}
			//				catch(Exception e){e.printStackTrace();System.err.println("can't login");}
			//			}
			ArticlesRow ar = urlHandler(url, false);

			if(ar != null){
				try{
					Date arD = stringToDate(ar.date);

					Date datefrom = stringToDate(this.site.fromDate);
					Date dateto = stringToDate(this.site.toDate);

//					if(datefrom != null && dateto!= null && arD != null && datefrom.after(arD) && dateto.before(arD)){
					if((datefrom != null &&  arD != null && datefrom.after(arD) )|| (dateto != null &&  arD != null && dateto.before(arD))){
						ArticlesRow.counter--;
					}
					else reports.add(ar);
				}catch(Exception e){ArticlesRow.counter--; ar = null;}
			}

			System.out.println("finish URL");
			mainScreen.addToLog("finish url ."+(i+1));

		}
		try{
			driver = killDriver(driver);
		}catch(Exception e){}
		//		driver.close();
		//		driver.quit();
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

			String headline="", subheadline="",publishDate="",reporter="",body="";
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



			//body
			try{
				body = getBody();
			}
			catch(Exception e){System.err.println("cant get body");}

			if(headline == null)
				headline = "";
			if(subheadline == null)
				subheadline = "";
			if(publishDate == null)
				publishDate = "";
			if(reporter == null)
				reporter = "";
			if(body == null)
				body = "";


			ar.body = body;
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
