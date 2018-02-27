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

		System.out.println("srefrfeferf");
		changeTime();

		sleep(3000);

		String link="", title="";
		int i=0;
		int  checks = 0;
		boolean addLink=false;

		System.out.println("srefrfeferf");
		try{
			ArrayList<WebElement> results = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='search-result']"));
			
			while(urls.size() < numOfArticles){

				if(i >= results.size()){

					try{
						WebElement nextButton = driver.findElement(By.className("content-next-link"));

						moveTo2(driver, nextButton);
						nextButton.click();

					}catch(Exception e){
						e.printStackTrace();
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

				try{
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
							String s = toDate;
							addLink = stateHandle(link, title, "");

							if(!s.equals(toDate)){
								changeTime(); i=results.size()+1;
							}


						}catch(Exception e){e.printStackTrace();addLink=false;}
					}
				}catch(Exception e){e.printStackTrace();addLink=false;}

				if(addLink){

					urls.add(link);
					removeDuplicate(urls);
					mainScreen.addToLog(urls.size()+"/"+this.numOfArticles);
				}
				addLink=false;

				i++;
				checks++;


				if(checks == maxSearch)
					return;
			}

		}catch(Exception e){e.printStackTrace();}

	}

	private void changeTime(){

		sleep(2000);
		
		if(!this.toDate.isEmpty()){
			try{
				WebElement nextButton = driver.findElement(By.className("content-next-link"));

				moveTo2(driver, nextButton);
				nextButton.click();

			}catch(Exception e){e.printStackTrace();}


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


			try{
				int pindex =newUrl.lastIndexOf("=");
				//				String p = newUrl.substring(pindex+1, newUrl.length());
				//				p = ""+ (Integer.parseInt(p)-1);
				newUrl = newUrl.substring(0, pindex+1);
				newUrl = newUrl+"1";
			}catch(Exception e){e.printStackTrace();}

			System.out.println(curl);
			System.out.println(newUrl);

			driver.get(newUrl);
		}

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
