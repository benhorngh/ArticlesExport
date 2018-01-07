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


/**
 * @author Ben Horn and Sefi Erlich
 * @since 1/2018
 *
 */
public class Ynet {

	static WebDriver driver;

	static final String url="http://www.Ynet.co.il";

	public static void main(String[] args) {

		String text="טראמפ";
		int numOfArticles=4;
		state state1 = state.body;  //0 to search only by headline
		YnetSearcher(text, numOfArticles, state1);

	}

	public static void YnetSearcher(String text, int numOfArticles, state state) {
		

		List<String> articles = findLinks( text, numOfArticles, state);

		if(articles!=null){
			System.out.println("find "+ articles.size() +" articles.");
			driver.quit();
			YnetPage.linksToCsv(articles);
		}
		else System.err.println("Faild");
	}

	private static List<String> findLinks( String text, int numOfArticles, state state) {

		//open web
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		driver.get(url);


		//find searchField section and open search window
		try {
			WebElement search = driver.findElements(By.cssSelector("#mainSrchBoxInput")).get(0);
			JavascriptExecutor jse2 = (JavascriptExecutor)driver;
			jse2.executeScript("arguments[0].scrollIntoView()", search); 
			search.click();




			//search

			driver.switchTo().frame("su_iframe");
			
			Funcs.sleep(1000);

			WebElement textField = driver.findElements(By.cssSelector("#su_w_s_search_input")).get(0);
			textField.sendKeys(text);

		}catch (NullPointerException e) {System.err.println(e); return null;}


		//get results

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement lis=null;
		try {
			lis = driver.findElements(By.cssSelector("#su_w_s_search_content_list")).get(0);
		}catch (NullPointerException e) {System.err.println(e); return null;}

		List<String> urls = new ArrayList<String>();

		boolean getLink=false;
		if(state==state.body) getLink=true;


		int found=0, i=0;
		while(found<numOfArticles){

			System.out.println();
			System.out.println(i+" /"+found);
			WebElement res;
			try { 
				res=lis.findElements(By.cssSelector("#search_result_id_"+i)).get(0);

				if(i>70 && i+2%10==0){

					Funcs.sleep(5000);
				}
				Funcs.sleep(2000);


				if(state==state.headline){
					if((i+2)%10==0){
						Actions actions = new Actions(driver);
						actions.moveToElement(res).perform();
					}

					String headLine= res.findElement(By.className("su_results_t_name")).getText();
					if(contain(headLine, text)){
						getLink=true;

					}
					else getLink=false;
				}

			} catch (Exception e){ System.err.println(e); break;}
			try{
				if(getLink){
					found++;
					Actions actions = new Actions(driver);
					actions.moveToElement(res).click().perform();


					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					WebElement cli = res.findElements(By.className("su_btn_link")).get(0);
					String link=cli.getAttribute("href");
					System.out.println(link);
					urls.add(link);
				}


			} catch (Exception e){ System.err.println(e); }
			i++;
		}
		return urls;
	}

	
	private static boolean contain(String headLine, String text) {
		String[] words = text.split(" ");

		for(int i=0; i<words.length; i++){
			if(!headLine.contains(words[i]))
				return false;
		}

		return true;
	}

}
