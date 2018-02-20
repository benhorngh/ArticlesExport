import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebElement;

public class Reuters extends Site {

	public Reuters(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="https://www.reuters.com/";
		this.window = WindowState.Invisible;
		this.DateRange = false;
		this.page = new ReutersPage(window);
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

	@Override
	public void resultsPage(List<String> urls) {


		for(int i=10; i<this.numOfArticles; i=i+10){
			clickLoadMore();
		}

		int found = 0;
		String link="", title="";
		int i=0;
		int  checks = 0;
		boolean addLink=false;
		ArrayList<WebElement> results = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='search-result-indiv']"));
		System.out.println(results.size());
		try{
			WebElement head = null;
			while(found < numOfArticles){

				if(i < results.size()){
					 head = results.get(i)
					.findElement(By.className("search-result-title")).findElement(By.tagName("a"));

				title = head.getText();
				link = head.getAttribute("href");

				System.out.println(link);
				System.out.println(title);

				try{
					addLink = stateHandle(link, title, "");
				}catch(Exception e){e.printStackTrace();addLink=false;}
				
				if(addLink){
					urls.add(link);
					found++;

					addLink=false;
				}
				i++;
				checks++;
				}
				if(i>=results.size()){
					clickLoadMore();
					results = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='search-result-indiv']"));
				}

				if(checks == maxSearch)
					return;
			}
		}catch(Exception e){e.printStackTrace();return;}

	}

	private void clickLoadMore() {
		WebElement loadMore = null;
		loadMore = driver.findElement(By.xpath("//*[@class='search-result-more-txt']"));
		moveTo2(driver,loadMore);
		sleep(3000);
		loadMore = driver.findElement(By.xpath("//*[@class='search-result-more-txt']"));
		loadMore.click();
		System.out.println("click");
		sleep(2000);

	}

}
