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



	public Ynet(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url = "https://www.Ynet.co.il";
		this.window = WindowState.Background;
		this.page = new YnetPage(window);
		this.DateRange = false;
	}
	
	
	public Ynet(){
		super();
		this.url = "https://www.Ynet.co.il";
		this.window = WindowState.Invisible;
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

		String date ="";
		boolean getLink = true;
		boolean addLink = false;
		if(state==SearchState.regular) {
			addLink = true;
		}
		if(state==SearchState.headline) {
			getLink = false;
		}

		int count = 0;

		int found=0, i=0;
		String link="";
		String headLine= "";
		int again=0;
		while(found<numOfArticles){
			link="";headLine= "";

			if(again==7) i++;

			WebElement res;

			try { 
				try{
					res=lis.findElement(By.cssSelector("#search_result_id_"+i));
					count= 0;
				} catch(Exception e){

					res=lis.findElement(By.cssSelector("#search_result_id_"+(i-1)));
					moveTo2(driver, res);
					count++;

					if(count==15)
						break;
					sleep(2000); 
					continue;
				}

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
				//				Actions actions = new Actions(driver);
				//				actions.moveToElement(res).click().perform();
				moveTo2(driver, res);
				sleep(2000);
				try{
					res.click();
					again=0;
				}catch(Exception e){again++; continue;}
				


				sleep(2000);
				WebElement cli = res.findElements(By.className("su_btn_link")).get(0);
				link=cli.getAttribute("href");

				System.out.println(link);					
			} catch (Exception e){ e.printStackTrace(); }


			//*[@id='search_result_id_1']//*[@class='']
			date = res.findElement(By.className("su_results_t_description"))
					.findElement(By.tagName("h4"))
					.getAttribute("title");

			String[] arr  = date.split(" ");
			arr = arr[0].split("/");
			date = arr[0]+"."+arr[1]+"."+arr[2];
			addLink = stateHandle(link , headLine , date);
			date="";

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
