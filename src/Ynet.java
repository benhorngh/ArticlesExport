import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;



/**
 * @author Ben Horn
 * @since 1/2018
 *
 */
public class Ynet extends  Site {
	
	static final String url="https://www.Ynet.co.il";
	
	
	
	
	public Ynet(){
		super();
		this.window = WindowState.Background;
		this.page = new YnetPage(window);
	}
	
	
	@Override
	public List<String> findLinks(String textToSearch,String textToCompare, int numOfArticles, state state) {

		int maxSearch = 50;

		//open web
		driver = startWebDriver(url);


		//find searchField section and open search window
		try {

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

		}catch (NullPointerException e) {e.printStackTrace(); return null;}
		catch (NoSuchFrameException e) {e.printStackTrace(); return null;}
		catch (NoSuchElementException e) {e.printStackTrace(); return null;}
		

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
					sleep(5000);
				}
				sleep(2500);


				if(state==state.headline){
					if((i+2)%10==0){
						moveTo(driver, res);
					}

					String headLine= res.findElement(By.className("su_results_t_name")).getText();
					if(contain(headLine, textToCompare)){
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

	

	@Override
	public boolean headlineState(String link, String textToCompare) {
		return true;
	}

	


}
