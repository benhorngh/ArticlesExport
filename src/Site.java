import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;


/**
 * @author Ben Horn
 * @since 1/2018
 *
 */

public abstract class Site extends Funcs implements Runnable {

	String url="";
	WebDriver driver;
	Page page;
	Boolean DateRange;
	String fromDate="";
	String toDate="";
	String textToSearch;
	String textToCompare;
	int numOfArticles;
	SearchState state;

	final int maxSearch = 50000;

	List<ArticlesRow> articles;

	@Override
	public void run(){
		this.articles = Start();
	}


	public Site(){}

	/**
	 * 
	 * @param textToSearch -text to the search field
	 * @param textToCompare -text to search inside the article
	 * @param numOfArticles -number of needed reports
	 * @param state -state of search. regular, search in title, body or in the comments. 
	 * @param startAt starting date
	 * @param endAt ending date
	 */
	public Site(String textToSearch, String textToCompare, int numOfArticles,
			SearchState state, String startAt, String endAt) {
		super();
		this.textToSearch = textToSearch;
		this.textToCompare = textToCompare;
		this.numOfArticles = numOfArticles;
		this.state = state;
		this.fromDate = startAt;
		this.toDate = endAt;

	}



	/**
	 * the Main function for Site. start the search and build the List of reports.
	 * @return List with all the Articles that found.
	 */
	public List<ArticlesRow> Start(){
		mainScreen.addToLog("start "+this.page.SiteName);
		List<String> articles =null;
		try{
			articles = findLinks();
		}catch(Exception e){e.printStackTrace();}

		if(articles!=null && !articles.isEmpty()){
			System.out.println("find "+ articles.size() +" articles in "+this.page.SiteName);
			mainScreen.addToLog("find "+ articles.size() +" articles in "+this.page.SiteName);
			return page.linksToList(articles);
		}
		else System.err.println(this.page.SiteName+" fail");
		mainScreen.addToLog(this.page.SiteName+" fail");
		return null;
	}


	/**
	 * @return List of all links to the reports
	 */
	public List<String> findLinks(){
		List<String> urls = new ArrayList<String>();
		try{
			boolean search = search();
			if(search) {
				resultsPage(urls);
				removeDuplicate(urls);
			}
		}
		catch(Exception e){e.printStackTrace();}
		try{
			driver.quit();
		}
		catch(Exception e){}
		return urls;
	}


	/**
	 * search end open result page
	 */
	public abstract boolean search();


	/**
	 * after the search, this method add to url the links to articles.
	 * @param urls
	 */
	public abstract void resultsPage(List<String> urls);






	/**
	 * 
	 * @param link -link to the article
	 * @param title - headline of article
	 * @param date -date of the article, if can reached from result page.
	 * @return true if standing on condition by the state, false otherwise.
	 */
	public boolean stateHandle(String link, String title, String date) {

		if(date == null) date ="";
		
		if(!this.fromDate.isEmpty()){
			return stateWithDate(link, title, date);
		}

		return stateWithoutDate(link, title);
		

	}

	/**
	 * state handler with date range.
	 * @param link
	 * @param title
	 * @param Adate
	 * @return
	 */
	private boolean stateWithDate(String link, String title, String Adate){ 

		if(this.DateRange) return true && stateWithoutDate(link, title); //built-in date range

		Date fromD = stringToDate(this.fromDate);
		Date toD = stringToDate(this.toDate);


		if(!Adate.isEmpty()){  //if can access date from result page
			Date urlD = stringToDate(Adate);
			
			if(urlD == null) stateWithDate(link, title, "");

			if(urlD.after(toD) || urlD.before(fromD)){
				System.err.println("next");
				return false;
			}
			else return true && stateWithoutDate(link, title);
		}



		//need access to article for the date

		ArticlesRow ar = null;
		String date="";
		try{
			this.page.driver= startWebDriver(link);

			try{
				this.page.signIn();
			}
			catch(Exception e){System.err.println("can't login");}
			ar = page.urlHandler(link, true);
			date = ar.date;
			ArticlesRow.counter--;
			page.driver.quit();
			sleep(4000);
		}
		catch(Exception e){e.printStackTrace();page.driver.quit();return false;}

		if(!date.isEmpty()){ 
			Date urlD = stringToDate(date);
			if(urlD.after(toD) || urlD.before(fromD)){
				System.err.println("next");
				return false;
			}
			System.err.println("okey!!");

			if(this.state == SearchState.body){
				if(!contain(ar.body, textToCompare)){
					System.err.println("not Found.");
					return false;
				}
				else {
					System.err.println("okey!!");
					return true;}

			}
			else return true && stateWithoutDate(link,title);
		}		
		return false;
	}

	/**
	 * state handle without date range.
	 * @param link
	 * @param title
	 * @return
	 */
	private boolean stateWithoutDate(String link, String title){
		if(state==SearchState.regular){
			return true;
		}
		if(state==SearchState.headline){
			return  contain(title, textToCompare);
		}
		if(state==SearchState.body){
			return bodyState(link);
		}
		if(state==SearchState.comment){
			return commentState(link);
		}
		return false;
	}



	


	/**
	 * this function get the body of the report from link, and compare to attached string
	 * @param link -link to the report
	 * @return true if the body contains the text, otherwise false
	 */
	private boolean bodyState(String link) {
		boolean getLink=true;
		try{
			this.page.driver= startWebDriver(link);

			try{
				this.page.signIn();
			}
			catch(Exception e){System.err.println("can't login");}
			String body="";
			body = page.urlHandler(link, true).body;
			if(!contain(body, textToCompare)){
				System.err.println("not Found.");
				getLink = false;
			}
			else System.err.println("okey!!");
			ArticlesRow.counter--;
			page.driver.close();
			sleep(10000);
		}
		catch(Exception e){e.printStackTrace();page.driver.quit();return false;}
		return getLink;
	}


	/**
	 * this function get the comments of the report in link, and compare to attached string
	 * @param link -link to the report
	 * @return true if the comments contains the text, otherwise false
	 */
	private boolean commentState(String link) {
		boolean getLink=true;
		try{
			page.driver= startWebDriver(link);
			try{
				this.page.signIn();
			}
			catch(Exception e){System.err.println("can't login");}

			String comments=CommentRow.wireAllComments(page.getComments());
			if(!contain(comments, textToCompare)){
				System.err.println("not Found.");
				getLink = false;
			}
			else System.err.println("okey!!");
			page.driver.quit();
			sleep(10000);
		}
		catch(Exception e){e.printStackTrace();page.driver.quit();return false;}
		return getLink;
	}




	/**
	 * check if the headline contains the keys.
	 * if there ar words surrounded by " " the finction will relate them like one word.
	 * @param big text
	 * @param text
	 * @return true if contains, false otherwise
	 */
	public boolean contain(String bigText, String text) {

		text= text.trim();
		int index =0;
		int count = 0;
		String gr = '"'+"";
		while(index!= -1){
			count ++;
			index = text.indexOf(gr ,index+1);
		}
		count = count /2;
		String[] grs = new String[count];

		for(int i=0; i<count; i++){
			int start = text.indexOf(gr);
			int end = text.indexOf(gr, start+1);
			grs[i]= text.substring(start, end+1);
			grs[i]= grs[i].replaceAll(gr, "");
			text= text.substring(0, start)+text.substring(end+1);
		}
		String[] words = text.split(" ");

		String[] keys = new String[words.length+ grs.length];

		for(int i=0; i<grs.length; i++){
			keys[i]=grs[i].trim();
		}
		for(int i=0; i<words.length; i++){
			keys[i+grs.length]=words[i].trim();
		}
		
		for (int i=0; i<keys.length; i++){
			if(keys[i].contains("''")){
				keys[i] = keys[i].replaceAll("''", '"'+"");
			}
		}

		for(int i=0; i<keys.length; i++){
			if(!bigText.contains(keys[i]))
				return false;
		}

		return true;
	}



	/**
	 * remove duplicate urls with "http" and "https". only https taken.
	 * @param urls
	 */
	public void removeDuplicate(List<String> urls){

		String url1, url2;
		for(int i=0; i<urls.size()-1; i++){
			url1 = urls.get(i);
			url2 = urls.get(i+1);
			url1 = url1.substring(url1.indexOf("://"), url1.length());
			url2 = url2.substring(url2.indexOf("://"), url2.length());
			if(url1.equals(url2)){
				if(url1.contains("https"))
					urls.remove(i+1);
				else urls.remove(i);	
			}
		}
	}

	/**
	 * close all other tabs in driver, except the current.
	 * @param driver the webdriver
	 * @return driver after changes - with one tab only.
	 */
	public WebDriver closeOthers(WebDriver driver){
		String originalHandle = driver.getWindowHandle();

		//Do something to open new tabs
		for(String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				driver.close();
			}
		}
		driver.switchTo().window(originalHandle);
		return driver;
	}



	@Override
	protected void finalize(){
		try{
			this.driver.quit();
		}catch(Exception e){}
	}
}
