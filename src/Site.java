import java.util.List;

import org.openqa.selenium.WebDriver;


/**
 * @author Ben Horn
 * @since 1/2018
 *
 */

public abstract class Site extends Funcs {

	static WebDriver driver;
	Page page;
	
	
	
	/**
	 * the Main function for Site. start the search and build the List of reports.
	 * @param textToSearch -text to the search field
	 * @param textToCompare -text to search inside the report
	 * @param numOfArticles -number of the article that needed.
	 * @param state -type of search. the types are specified in Enum state javaDoc
	 * @return List with all the Articles that found.
	 */
	public List<ArticlesRow> Start(String textToSearch,String textToCompare, int numOfArticles, state state){
		List<String> articles = findLinks(textToSearch, textToCompare,numOfArticles, state);
		driver.quit();
		if(articles!=null){
			System.out.println("find "+ articles.size() +" articles.");
			return page.linksToList(articles);
		}
		else System.err.println("Faild");
		return null;
	}

	 
	/**
	 * @param textToSearch -text to the search field
	 * @param textToCompare -text to search inside the article
	 * @param numOfArticles -number of needed reports
	 * @param state -state of search. regular, search in title, body or in the comments. 
	 * @return List of all links to the reports
	 */
	public abstract List<String> findLinks(String textToSearch,String textToCompare, int numOfArticles, state state);

	/**
	 * this function get the body of the report from link, and compare to attached string
	 * @param link -link to the report
	 * @param textToCompare -text to compare with
	 * @return true if the body contains the text, otherwise false
	 */
	public abstract boolean bodyState(String link, String textToCompare);

	/**
	 * this function get the title of the report from link, and compare to attached string
	 * @param link -link to the report
	 * @param textToCompare -text to compare with
	 * @return true if the title contains the text, otherwise false
	 */
	public abstract boolean headlineState(String link, String textToCompare);

	/**
	 * this function get the comments of the report in link, and compare to attached string
	 * @param link -link to the report
	 * @param textToCompare -text to compare with
	 * @return true if the comments contains the text, otherwise false
	 */
	public abstract boolean commentState(String link, String textToCompare);
	
	
	

	/**
	 * check if the headline contains the keys.
	 * if there ar words surrounded by " " the finction will relate them like one word.
	 * @param headLine
	 * @param text
	 * @return true if contains, false otherwise
	 */
	public static boolean contain(String headLine, String text) {

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

		//		System.out.println(Arrays.toString(words));
		//		System.out.println(Arrays.toString(grs));

		String[] keys = new String[words.length+ grs.length];

		for(int i=0; i<grs.length; i++){
			keys[i]=grs[i].trim();
		}
		for(int i=0; i<words.length; i++){
			keys[i+grs.length]=words[i].trim();
		}

		for(int i=0; i<keys.length; i++){
			if(!headLine.contains(keys[i]))
				return false;
		}

		return true;
	}

}
