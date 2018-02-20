import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * https://edition.cnn.com/
 * @author benho
 */

public class CNN extends Site{

	public CNN(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="http://edition.cnn.com/";
		this.window = WindowState.Invisible;
		this.DateRange = false;
		this.page = new CNNPage(window);
	}

	@Override
	public boolean search() {
		driver = startWebDriver(url);
		//		driver.get(url);

		try{
			WebElement opens = driver.findElement(By.xpath("//*[@id='search-button']"));
			moveTo(driver,opens );
			sleep(1000);
			clickInvisible(driver, opens);
			sleep(1000);
			WebElement ssection = driver.findElement(By.xpath("//*[@class='search__form-fields']"));
			WebElement field = ssection.findElement(By.xpath(".//input"));
			//			moveTo(driver, field);
			//			field.click();
			clickInvisible(driver, field);
			//			clickInvisible(driver, field);
			sleep(1000);
			//			field.clear();
			//			field.sendKeys(this.textToSearch);
			sendKeysInvisible(driver,field );
			sleep(1000);

			WebElement button = ssection.findElement(By.xpath(".//button"));
			//			button.click();
			clickInvisible(driver, button);
			sleep(2000);

		}catch(Exception e){e.printStackTrace();sleep(4000);return false;}

		driver.get(driver.getCurrentUrl());

		try{
			WebElement stories = driver.findElement(By.xpath("//*[@class='facet_list']//*[@for='collection_article']"));
			stories.click();
			sleep(3000);
		}catch(Exception e){e.printStackTrace();return false;}
		//		driver.get(driver.getCurrentUrl());

		return true;
	}

	@Override
	public void resultsPage(List<String> urls) {
		//		cnn-search__results-list



		List<WebElement> resutls = driver.findElements(By.xpath("//*[@class='cnn-search__results-list']/div"));


		System.out.println(resutls.size());
		if(resutls == null || resutls.size() == 0){
			System.err.println("no results");
			return;
		}

		int found = 0;
		String link="", title="", date="";
		int i=0;
		int  checks = 0;
		boolean addLink=false;
		try{
			while(found < numOfArticles){

				link=""; title=""; date="";


				WebElement ttl = resutls.get(i).findElement(By.xpath(".//*[@class='cnn-search__result-headline']/*"));
				link = ttl.getAttribute("href");

				System.out.println(link);

				title = ttl.getText();
				System.out.println(title);



				try{
					WebElement dt = resutls.get(i).findElement(By.className("cnn-search__result-publish-date"));
					date = dt.getText();
					String arr[] = date.split(" ");
					date = arr[1].substring(0, arr[1].length()-1)+"."+monthToInt(arr[0])+"."+arr[2];

				}catch(Exception e){e.printStackTrace();}

				try{
					addLink = stateHandle(link, title, date);
				}catch(Exception e){e.printStackTrace();addLink=false;}

				if(addLink){
					urls.add(link);
					found++;
				}
				addLink=false;

				i++;
				checks++;

				if(i>=resutls.size()){
					i=0;
					WebElement pagination = driver.findElement(By.xpath("//*[@class='pagination-bar']//*[@class='pagination-arrow pagination-arrow-right cnnSearchPageLink text-active']"));
					moveTo(driver, pagination);
					sleep(1000);


					pagination.click();

					sleep(1000);

					resutls = driver.findElements(By.xpath("//*[@class='cnn-search__results-list']/div"));

				}
				if(checks == maxSearch)
					return;
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

}
