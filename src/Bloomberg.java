import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * https://www.bloomberg.com
 * @author benho
 *
 */
public class Bloomberg extends Site{


	public Bloomberg(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="https://www.bloomberg.com";
		this.window = WindowState.Invisible;
		this.DateRange = true;
		this.page = new BloombergPage((Site)this, window);
	}



	/*
	 * (non-Javadoc)
	 * account info for log-in:
	 * benhorenn@gmail.com
	 * Bb4546662/
	 */
	private static final String mail = "benhorenn@gmail.com";
	private static final String password = "Bb4546662/";




	@Override
	public boolean search() {

		driver = startWebDriver(url);
		sleep(2000);
		driver.get(url);

		sleep(10000);

		try{
			closeAd(driver);
			sleep(2000);

			WebElement search = driver.findElement(By.xpath("//*[@class='bb-nav-search__icon']"));

			clickInvisible(driver , search);

			WebElement input = driver.findElement(By.xpath("//*[@class='bb-nav-search__input']"));
			input.click();
			input.clear();
			input.sendKeys(textToSearch);
			sleep(2000);
			input.sendKeys(Keys.RETURN);
		}
		catch (NullPointerException e) {e.printStackTrace(); return false;}
		catch (NoSuchFrameException e) {e.printStackTrace(); return false ;}
		catch (NoSuchElementException e) {e.printStackTrace(); return false ;}

		sleep(5000);

		return true;
	}

	@Override
	public void resultsPage(List<String> urls) {


		if(!this.toDate.isEmpty()){
			try{
				WebElement nextButton = driver.findElement(By.className("content-next-link"));

				moveTo2(driver, nextButton);
				nextButton.click();

			}catch(Exception e){}


			String curl  = driver.getCurrentUrl();
			int index = curl.indexOf("end");
			int indexSh = index +7;
			String date = curl.substring(indexSh+1, indexSh+11);
			String[] todate= toDate.split("\\.");

			if(todate[0].length()==1)
				todate[0] = "0"+todate[0];  
			if(todate[1].length()==1)
				todate[1] = "0"+todate[1];  

			String newUrl = curl.replace(date, todate[2]+"-"+todate[1]+"-"+todate[0]);
			System.out.println(curl);
			System.out.println(newUrl);
			driver.get(newUrl);
		}


		int found = 0;
		String link="", title="";
		int i=0;
		int  checks = 0;
		boolean addLink=false;
		ArrayList<WebElement> results = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='search-result']"));
		try{
			while(found < numOfArticles){

				if(i == results.size()){

					try{
						WebElement nextButton = driver.findElement(By.className("content-next-link"));

						moveTo2(driver, nextButton);
						nextButton.click();

					}catch(Exception e){
						String pageurl = driver.getCurrentUrl();
						int x = Integer.parseInt(pageurl.substring(pageurl.length()-3, pageurl.length()));
						x++;
						pageurl = pageurl.substring(0 , pageurl.length()-2) + x;
						driver.get(pageurl);
					}

					sleep(3000);
					results = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='search-result']"));

					i=0;
				}
				if(!results.get(i).findElement(By.tagName("article")).getAttribute("class") 
						.equals("search-result-story type-video")&&
						!results.get(i).findElement(By.tagName("article")).getAttribute("class") 
						.equals("search-result-story type-audio")){
					WebElement ttl = results.get(i).findElement(By.className("search-result-story__headline"));

					WebElement titl = ttl.findElement(By.tagName("a"));
					link = titl.getAttribute("href");

					title = ttl.getText();

					System.out.println(link);
					System.out.println(title);

					try{
						addLink = stateHandle(link, title, "");
					}catch(Exception e){e.printStackTrace();addLink=false;}
					
					
					if(addLink){
						urls.add(link);
						found++;
					}
					addLink=false;
				}
				i++;
				checks++;


				if(checks == maxSearch)
					return;
			}

		}catch(Exception e){e.printStackTrace();return;}
	}

	
	/**
	 * close the annoying ad about register.
	 */
	public void closeAd(WebDriver driver){
		boolean closed = false;
		int tryies = 0;
		while(!closed){
			try{

				WebElement ad = driver.findElement(By.xpath("//*[@id='chromeTout']"));
				if(!ad.getAttribute("style").equals("display: block;")){
					System.out.println("isClosed");
					closed = true;
					continue;
				}
				sleep(3000);
				WebElement x = driver.findElement(By.xpath("//*[@id='chromeTout']/button"));
				x.click();
				closed =  true;
			}
			catch(Exception e){
				System.out.println("adf");
				sleep(2000);
				tryies++;
				if(tryies == 7)
					break;}
		}
	}


}
