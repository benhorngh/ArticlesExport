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
		this.window = WindowState.visible;
		this.DateRange = true;
		this.page = new BloombergPage(window, this);
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

			WebElement search = driver.findElement(By.xpath("//*[contains(@class,'search')]"));
			search.click();
//			clickInvisible(driver , search);
			sleep(2000);

			WebElement input = driver.findElement(By.xpath("//*[contains(@class,'input')]"));
//			input.click();
			clickInvisible(driver, input);
//			input.clear();
			input.sendKeys(textToSearch);
			sendKeysInvisible(driver, input, textToSearch);
			sleep(2000);
			input.sendKeys(Keys.RETURN);
//			sendKeysInvisible(driver, input, Keys.RETURN);
		}
		catch (NullPointerException e) {e.printStackTrace(); return false;}
		catch (NoSuchFrameException e) {e.printStackTrace(); return false ;}
		catch (NoSuchElementException e) {e.printStackTrace(); return false ;}

		sleep(5000);

		return true;
	}




	/*
	 * (non-Javadoc)
	 * @see Site#resultsPage(java.util.List)
	 * results in separated pages.
	 */



	@Override
	public void resultsPage(List<String> urls) {

		changeTime();

		sleep(3000);

		int res= 0;
		String date="",link="", title="";
		int i=0;
		int checks = 0;
		boolean addLink=false;

		try{
			ArrayList<WebElement> results = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='search-result']"));

			while(urls.size() < numOfArticles){
				date=""; link=""; title="";
				addLink=false;
				checks++;
				if(checks == maxSearch)
					return;


				if(i >= results.size()){

					try{
						WebElement nextButton = driver.findElement(By.className("content-next-link"));

						moveTo2(driver, nextButton);
						nextButton.click();

					}catch(NoSuchElementException e){

						String pageurl = driver.getCurrentUrl();
						int x = Integer.parseInt(pageurl.substring(pageurl.lastIndexOf("=")+1, pageurl.length()));
						x++;
						pageurl = pageurl.substring(0 ,pageurl.lastIndexOf("=")+1) + x;
						System.err.println("no next" + x);
						driver.get(pageurl);
					}catch(Exception e){
						e.printStackTrace();
					}

					sleep(3000);
					results = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='search-result']"));

					if(results.size() == 0)
						res++;
					else res = 0;

					if(res >= 10){
						String s = this.toDate;
						updateToDate(true);
						if(s.equals(this.toDate))
							break;
						changeTime(); i = results.size();
						res = 0;
					}


					i=0;
				}





				if(i<results.size()){
					try{
						if(!results.get(i).findElement(By.tagName("article")).getAttribute("class") 
								.equals("search-result-story type-video")&&
								!results.get(i).findElement(By.tagName("article")).getAttribute("class") 
								.equals("search-result-story type-audio")){

							WebElement ttl = results.get(i).findElement(By.className("search-result-story__headline"));
							WebElement titl = ttl.findElement(By.tagName("a"));
							link = titl.getAttribute("href");

							title = ttl.getText();

							try{
								WebElement dt = results.get(i).findElement(By.className("published-at"));
								String[] arr = dt.getText().split(" ");
								arr[0] = ""+ monthToInt(arr[0]);
								arr[1] = arr[1].substring(0, arr[1].length()-1);
								date = arr[1]+"."+arr[0]+"."+arr[2];

							}catch(Exception e){e.printStackTrace();}

							System.out.println(link);
							System.out.println(title);

							try{
								String s = toDate;
								addLink = stateHandle(link, title, date);

								if(!s.equals(toDate)){
									changeTime(); i = results.size();
								}

							}catch(Exception e){e.printStackTrace();addLink=false;}

							if(addLink){

								urls.add(link);
								removeDuplicate(urls);
								mainScreen.addToLog(urls.size()+"/"+this.numOfArticles);
							}


						}
					}catch(Exception e){e.printStackTrace();}	
					i++;
				}
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
		if(true) return;
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
