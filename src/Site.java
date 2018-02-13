import java.util.ArrayList;
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
	String fromDate;
	String toDate;
	String textToSearch;
	String textToCompare;
	int numOfArticles;
	int maxSearch = 200;
	SearchState state;

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
		List<String> articles = findLinks();


		if(articles!=null && !articles.isEmpty()){
			System.out.println("find "+ articles.size() +" articles in "+this.page.SiteName);
			return page.linksToList(articles);
		}
		else System.err.println(this.page.SiteName+" fail");
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
	 * this function get the body of the report from link, and compare to attached string
	 * @param link -link to the report
	 * @return true if the body contains the text, otherwise false
	 */
	public boolean bodyState(String link) {
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
				ArticlesRow.counter--;
				getLink = false;
			}
			else System.err.println("okey!!");
			page.driver.close();
			sleep(10000);
		}
		catch(Exception e){return false;}
		return getLink;
	}





	/**
	 * 
	 * @param link -link to the article
	 * @param title - headline of article
	 * @return true if standing on condition by the state, false otherwise.
	 */
	public boolean stateHandle(String link, String title) {
		if(state==SearchState.regular){
			return true;
		}
		if(state==SearchState.headline){
			return contain(title, textToCompare);
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
	 * this function get the comments of the report in link, and compare to attached string
	 * @param link -link to the report
	 * @return true if the comments contains the text, otherwise false
	 */
	public boolean commentState(String link) {
		boolean getLink=true;
		try{
			page.driver= startWebDriver(link);
			try{
				signIn();
				driver.navigate().to(link);
			}
			catch(Exception e){System.err.println("can't login");}
			String comments=CommentRow.wireAllComments(page.getComments());
			if(!contain(comments, textToCompare)){
				System.err.println("not Found.");
				getLink = false;
			}
			else System.err.println("okey!!");
			page.driver.close();
			sleep(10000);
		}
		catch(Exception e){return false;}
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






}
