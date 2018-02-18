import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;



/**
 * https://www.Ynet.co.il
 * @author benho
 * @since 1/2018
 */
public class Ynet extends  Site {


	public Ynet(){
		super();
		this.url = "https://www.Ynet.co.il";
		this.window = WindowState.Background;
		this.page = new YnetPage(window);
		this.DateRange = false;
	}


	public Ynet(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url = "https://www.Ynet.co.il";
		this.window = WindowState.Background;
		this.page = new YnetPage(window);
		this.DateRange = false;
	}


	@Override
	public boolean search() {

		//		return true;
		driver = startWebDriver(url);
		driver.get(url);
		sleep(10000);
		//find searchField section and open search window
		try {
			//open web

			WebElement search = driver.findElement(By.cssSelector("#mainSrchBoxInput"));
			moveTo2(driver, search);
			search.click();


			//search
			driver.switchTo().frame("su_iframe");

			WebElement textField = driver.findElements(By.cssSelector("#su_w_s_search_input")).get(0);
			String searchf  = SearchField(textToSearch);
			moveTo(driver, textField);
			textField.click();
			textField.clear();
			textField.sendKeys(searchf);

		}catch (NullPointerException e) {e.printStackTrace(); return false ;}
		catch (NoSuchFrameException e) {e.printStackTrace(); return false ;}
		catch (NoSuchElementException e) {e.printStackTrace(); return false ;}

		return true;

	}





	@Override
	public void resultsPage(List<String> urls) {
		//get results
		//		driver.get(url);
		sleep(3000);
		//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement lis=null;
		try {
			lis = driver.findElement(By.cssSelector("#su_w_s_search_content_list"));
		}catch (Exception e) {e.printStackTrace(); return;}


		boolean getLink = true;
		boolean addLink = false;
		if(state==SearchState.regular) {
			addLink = true;
		}
		if(state==SearchState.headline) {
			getLink = false;
		}

		int found=0, i=0;
		String link="";
		String headLine= "";
		while(found<numOfArticles){


			WebElement res;

			try { 
				res=lis.findElements(By.cssSelector("#search_result_id_"+i)).get(0);

				if(i>70 && i+2%10==0){
					sleep(5000);
				}
				sleep(2500);



				if((i+2)%10==0){
					moveTo(driver, res);

				}

				 headLine= res.findElement(By.className("su_results_t_name")).getText();


			} catch (Exception e){ e.printStackTrace(); break;}

			try{

				Actions actions = new Actions(driver);
				actions.moveToElement(res).click().perform();
				sleep(2000);
				WebElement cli = res.findElements(By.className("su_btn_link")).get(0);
				link=cli.getAttribute("href");

				System.out.println(link);					


			} catch (Exception e){ System.err.println(e); }


			addLink = stateHandle(link , headLine);
			if(state==SearchState.comment){
				addLink= commentState(link);
			}

			if(state==SearchState.body){
				addLink= bodyState(link);
			}

			if(addLink){
				found++;
				urls.add(link);
			}

			i++;
			if(i==maxSearch)
				break;
		}
	}
}
