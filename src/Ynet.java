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
		this.page = new YnetPage(window ,this);
		this.DateRange = false;
	}


	public Ynet(){
		super();
		this.url = "https://www.Ynet.co.il";
		this.window = WindowState.Background;
		this.page = new YnetPage(window, this); 
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

		sleep(3000);

		WebElement lis=null;
		try {
			lis = driver.findElement(By.cssSelector("#su_w_s_search_content_list"));
		}catch (Exception e) {e.printStackTrace(); return;}

		String date ="";
		boolean addLink = false;

		int checks=0;
		int i=0;
		String link="";
		String title= "";
		int again=0;

		while(urls.size()<numOfArticles){
			link=""; title= ""; date="";

			checks++;
			if(checks == maxSearch)
				return;

			if(again >= 10){
				String s = this.toDate;
				updateToDate(true);
				if(s.equals(this.toDate))
					break;
				again = 0;
			}

			WebElement res = null;

			try { 

				res=lis.findElement(By.cssSelector("#search_result_id_"+i));;

				moveTo2(driver, res);

				sleep(2000); 


				if(i>70 && i+2%10==0){
					sleep(5000);
				}

				if((i+2)%10==0){
					moveTo(driver, res);
				}

				title= res.findElement(By.className("su_results_t_name")).getText();

				again = 0;
			} catch (Exception e){ e.printStackTrace(); again++; }

			if(res == null){
				again++; continue;
			}else again = 0;

			try{
				moveTo2(driver, res);
				sleep(2000);
				try{
					res.click();
				}catch(Exception e){e.printStackTrace();}


				sleep(2000);
				WebElement cli = res.findElement(By.className("su_btn_link"));
				link=cli.getAttribute("href");

				System.out.println(title);
				System.out.println(link);					
			} catch (Exception e){ e.printStackTrace(); }


			try{
				date = res.findElement(By.className("su_results_t_description"))
						.findElement(By.tagName("h4"))
						.getAttribute("title");

				String[] arr  = date.split(" ");
				arr = arr[0].split("/");
				date = arr[0]+"."+arr[1]+"."+arr[2];
			}catch(Exception e){e.printStackTrace();}

			try{
				addLink = stateHandle(link, title, date);
			}catch(Exception e){e.printStackTrace();}


			if(addLink){
				urls.add(link);
				removeDuplicate(urls);
				mainScreen.addToLog(urls.size()+"/"+this.numOfArticles);
			}

			i++;
			if(i==maxSearch)
				break;
		}
	}
}
