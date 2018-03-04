import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebElement;

/**
 * https://www.reuters.com/
 * @author benho
 */
public class Reuters extends Site {

	public Reuters(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="https://www.reuters.com/";
		this.window = WindowState.Invisible;
		this.DateRange = false;
		this.page = new ReutersPage(window ,this);
	}

	@Override
	public boolean search() {

		if(this.state == SearchState.comment) 
			return false;

		driver = startWebDriver(url);
		driver.get(url);
		sleep(10000);

		try{
			WebElement serchbutton = driver.findElement(By.className("search-icon"));
			serchbutton.click();

			sleep(1000);
			WebElement field = driver.findElement(By.className("search-field"));
			field.click();
			field.clear();
			field.sendKeys(this.textToSearch);

			sleep(1000);
			WebElement enter = driver.findElement(By.className("search-submit-button"));
			enter.click();
		}
		catch (NullPointerException e) {e.printStackTrace(); return false;}
		catch (NoSuchFrameException e) {e.printStackTrace(); return false ;}
		catch (NoSuchElementException e) {e.printStackTrace(); return false ;}


		return true;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see Site#resultsPage(java.util.List)
	 * long result page.
	 */
	
	@Override
	public void resultsPage(List<String> urls) {


		
		String link="", title="", date="";
		int i=0;
		int checks = 0;
		boolean addLink=false;
		ArrayList<WebElement> results = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='search-result-indiv']"));
		int res=0;
		try{
			WebElement head = null;
			while(urls.size() < numOfArticles){
				link=""; title=""; date="";
				addLink=false;

				checks++;
				if(checks == maxSearch)
					return;


				if(i < results.size()){
					try{
						head = results.get(i)
								.findElement(By.className("search-result-title")).findElement(By.tagName("a"));

						title = head.getText();
						link = head.getAttribute("href");

						System.out.println(link);
						System.out.println(title);
					}catch(Exception e){}


					try {//September 27, 2017 12:23pm EDT
						WebElement dt = results.get(i)
								.findElement(By.className("search-result-timestamp"));

						date = dt.getText();

						String[] arr = date.split(" ");
						arr[1] = arr[1].substring(0, arr[1].length()-1);
						arr[0] = ""+monthToInt(arr[0]);

						date = arr[1]+"."+arr[0]+"."+arr[2];

					}catch(Exception e){e.printStackTrace();}


					try{
						String s = this.toDate;
						addLink = stateHandle(link, title, date);
						if(!s.equals(this.toDate))
							i=0;
						
					}catch(Exception e){e.printStackTrace();addLink=false;}

					if(addLink){
						urls.add(link);
						removeDuplicate(urls);
						mainScreen.addToLog(urls.size()+"/"+this.numOfArticles);

					}
					i++;

				}
				if(i>=results.size()){
					int tmp = results.size();

					clickLoadMore();
					results = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='search-result-indiv']"));

					if(tmp == results.size()) {
						res++;
						sleep(2000);
					}
					else res = 0;

					if(res >= 10){
						String s = this.toDate;
						updateToDate(true);
						if(s.equals(this.toDate))
							break;
						res = 0;
						i=0;
					}
				}


			}
		}catch(Exception e){e.printStackTrace();return;}

	}

	private void clickLoadMore() {
		for(int i=0; i<4; i++){
			try{
				WebElement loadMore = null;
				loadMore = driver.findElement(By.xpath("//*[@class='search-result-more-txt']"));
				moveTo2(driver,loadMore);
				sleep(3000);
				loadMore = driver.findElement(By.xpath("//*[@class='search-result-more-txt']"));
				loadMore.click();
				sleep(2000);
				break;
			}catch(Exception e){sleep(2000);}
		}
	}

}
