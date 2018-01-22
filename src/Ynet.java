import java.io.IOException;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;


/**
 * @author Ben Horn
 * @since 1/2018
 *
 */
public class Ynet {

	static WebDriver driver;
	static final String url="http://www.Ynet.co.il";

	public static List<ArticlesRow> Main(String textToSearch,String textToCompare, int numOfArticles, state state) {

		List<String> articles = findLinks( textToSearch, textToCompare,numOfArticles, state);

		if(articles!=null){
			System.out.println("find "+ articles.size() +" articles.");
			driver.quit();
			return YnetPage.linksToList(articles);
		}
		else System.err.println("Faild");
		return null;

	}

	public static List<String> findLinks(String textToSearch,String textToCompare, int numOfArticles, state state) {

		int maxSearch = 50;


		//open web
		driver = Funcs.startWebDriver(url);


		//find searchField section and open search window
		try {

			WebElement search = driver.findElement(By.cssSelector("#mainSrchBoxInput"));
			Funcs.moveTo2(driver, search);
			search.click();


			//search
			driver.switchTo().frame("su_iframe");

			WebElement textField = driver.findElements(By.cssSelector("#su_w_s_search_input")).get(0);
			String searchf  = Funcs.SearchField(textToSearch);
			Funcs.moveTo(driver, textField);
			textField.click();
			textField.clear();
			textField.sendKeys(searchf);

		}catch (NullPointerException e) {e.printStackTrace(); return null;}


		//get results
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement lis=null;
		try {
			lis = driver.findElements(By.cssSelector("#su_w_s_search_content_list")).get(0);
		}catch (NullPointerException e) {System.err.println(e); return null;}

		List<String> urls = new ArrayList<String>();

		boolean getLink = true;
		boolean addLink = false;
		if(state==state.regular) {
			addLink = true;
		}
		if(state==state.headline) {
			getLink = false;
		}


		int found=0, i=0;
		String link="";
		while(found<numOfArticles){


			WebElement res;

			try { 
				res=lis.findElements(By.cssSelector("#search_result_id_"+i)).get(0);


				if(i>70 && i+2%10==0){
					Funcs.sleep(5000);
				}
				Funcs.sleep(2500);


				if(state==state.headline){
					if((i+2)%10==0){
						Funcs.moveTo(driver, res);
					}

					String headLine= res.findElement(By.className("su_results_t_name")).getText();
					if(Funcs.contain(headLine, textToCompare)){
						addLink=true;
						getLink=true;

					}
					else {
						addLink=false;
						getLink=false;
					}
				}


			} catch (Exception e){ System.err.println(e); break;}

			try{
				if(getLink){				
					Actions actions = new Actions(driver);
					actions.moveToElement(res).click().perform();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					WebElement cli = res.findElements(By.className("su_btn_link")).get(0);
					link=cli.getAttribute("href");
					System.out.println(link);					
				}

			} catch (Exception e){ System.err.println(e); }


			if(state==state.comment){
				addLink= commentState(link, textToCompare);
			}

			if(state==state.body){
				addLink= bodyState(link, textToCompare);
			}

			if(addLink){
				found++;
				urls.add(link);
			}

			System.out.println((found)+" /"+numOfArticles);
			System.out.println();
			i++;
			if(i==maxSearch)
				break;
		}
		return urls;

	}

	public static boolean bodyState(String link, String textToCompare) {
		boolean getLink=true;
		YnetPage.driver=Funcs.startWebDriver(link);
		String body="";

		body = YnetPage.urlHandler(link, true).body;

		if(!Funcs.contain(body, textToCompare)){
			System.err.println("not Found.");
			getLink = false;
		}
		else System.err.println("okey!!");
		YnetPage.driver.close();
		Funcs.sleep(10000);
		return getLink;
	}

	public static boolean headlineState(String link, String textToCompare) {
		return true;
	}

	public static boolean commentState(String link, String textToCompare) {
		boolean getLink=true;
		YnetPage.driver=Funcs.startWebDriver(link);
		String comments=CommentRow.wireAllComments(YnetPage.getComments(link, 1));
		if(!Funcs.contain(comments, textToCompare)){
			System.err.println("not Found.");
			getLink = false;
		}
		else System.err.println("okey!!");
		YnetPage.driver.close();
		Funcs.sleep(10000);
		return getLink;
	}


}
