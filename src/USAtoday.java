import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class USAtoday extends Site{

	public USAtoday(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url=" https://www.usatoday.com/";
		this.window = WindowState.Invisible;
		this.DateRange = false;
		this.page = new USAtodayPage(window);
	}


	@Override
	public boolean search() {

		driver = startWebDriver(url);
		try{

			WebElement search = driver.findElement(By.xpath("//*[@class='site-nav-inner-wrap']"));
			search.click();

			//			
			//			Actions action = new Actions(webdriver);
			//			WebElement we = webdriver.findElement(By.xpath("html/body/div[13]/ul/li[4]/a"));
			//			action.moveToElement(we).moveToElement(webdriver.findElement(By.xpath("/expression-here"))).click().build().perform();

			sleep(3000);
			WebElement inp = driver.findElement(By.xpath("//*[@class='site-masthead-search-form-input']"));
			//			inp.click();
			//			inp.clear();
			sendKeysInvisible(driver, inp,this.textToSearch);
			//			inp.sendKeys(this.textToSearch);

			sleep(1000);
			WebElement enter = driver.findElement(By.xpath("//*[@class='site-masthead-search-form-submit']"));
			//			enter.click();
			clickInvisible(driver, enter);


		}catch(Exception e){ e.printStackTrace();  return false;}
		sleep(2000);

		driver.get(driver.getCurrentUrl());
		try{
//			sleep(1000);
			WebElement listview = driver.findElement(By.xpath("//*[@class='ui-btn list-btn']"));
//			listview.click();
			clickInvisible(driver, listview);
			
		}catch(Exception e){e.printStackTrace(); return false;}
		sleep(2000);

		return true;
	}

	@Override
	public void resultsPage(List<String> urls) {


		sleep(2000);
		ArrayList<WebElement> results  = (ArrayList<WebElement>) 
				driver.findElements(By.xpath("//*[@class='clearfix search-results-list']/li"));

		String link="", title="" , date="";
		int i=0;
		int  checks = 0;
		boolean addLink=false;
		try{
			while(urls.size() < numOfArticles){
				link=""; title=""; date="";
				addLink=false;

				if(i<results.size()){
					WebElement a = null;
					try{
						a = results.get(i)
								.findElement(By.tagName("a"));


						link = a.getAttribute("href");

						WebElement head = results.get(i)
								.findElement(By.xpath(".//div[@class='front']/h3"));


						title = head.getText();


						WebElement dt = results.get(i)
								.findElement(By.xpath(".//*[@class='meta']/span"));

						date = dt.getText();
						String[] arr = date.split(" ");
						String month = ""+monthToInt(arr[0]);
						arr[1]=arr[1].substring(0, arr[1].length()-1);
						date = arr[1]+"."+month+"."+arr[2];

						System.out.println(link);
						System.out.println(title);

					}catch(Exception e){i++; continue;}


					String type = results.get(i).getAttribute("class");

					if((!type.contains("video"))
							&&(!type.contains("gallery"))
							&&(!link.contains("reviewed"))
							&&(!link.contains("marketsblog"))
							&&(!link.contains("americasmarkets"))
							) {
						try{
							addLink = stateHandle(link, title, date);
						}catch(Exception e){e.printStackTrace();addLink=false;}
					}
					else addLink=false;
					if(addLink){
						urls.add(link);
						removeDuplicate(urls);
						mainScreen.addToLog(urls.size()+"/"+this.numOfArticles);
					}
					i++;
				}
				checks++;

				if(i+3>=results.size()){

					int tmp = results.size();
					
					moveTo2(driver, results.get(results.size()-3));
					sleep(2000);
					results  = (ArrayList<WebElement>) 
							driver.findElements(By.xpath("//*[@class='clearfix search-results-list']/li"));

					
					if(results.size()==tmp)
						break;

				}

				if(checks == maxSearch)
					return;
			}
		}catch(Exception e){ e.printStackTrace();return;}

	}


	

}
