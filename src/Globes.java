import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * http://www.globes.co.il/
 * @author benho
 *
 */
public class Globes extends Site{
	public Globes(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="http://www.globes.co.il/";
		this.window = WindowState.visible;
		this.DateRange = false;
		this.page = new GlobesPage(window);
	}

	@Override
	public boolean search() {
		String sch = "http://www.globes.co.il/news/search.aspx?author=&all=&exact=%u05DB%u05DF&any=&nowords=&ling=False&time=last%203%20month&start=2017%2f11%2f14&end=2018%2f2%2f14&count=10&nadlan=&accuracy=false&type=articles&page=0&cx=partner-pub-3457903570625953:1632854301&cof=FORID:10&ie=UTF-8&q=%D7%9B%D7%9F&sa=Search";
		driver = startWebDriver(sch);
		driver.get(sch);
		sleep(10000);
		try{
			WebElement serachField  = driver.findElement(By.xpath("//*[@id='search_buttons']/input[@type='text']"));
			serachField.click();

			driver = closeOthers(driver);

			driver.get(sch);
			serachField  = driver.findElement(By.xpath("//*[@id='search_buttons']/input[@type='text']"));
			serachField.click();
			serachField.clear();
			serachField.sendKeys(this.textToSearch);

			WebElement enter  = driver.findElement(By.xpath("//*[@id='search_buttons']/input[@type='button']"));
			enter.click();


			/*
			 * open regular search. invail search (not contains only reports)
			 */
			
			//			WebElement serachIcon  = driver.findElement(By.xpath("//*[@class='navWmainI search']"));
			//			serachIcon.click();
			//
			//
			//			sleep(2000);
			//			driver = closeOthers(driver);
			//			sleep(2000);
			//			driver.get(url);
			//			sleep(2000);
			//			serachIcon  = driver.findElement(By.xpath("//*[@class='navWmainI search']"));
			//			serachIcon.click();
			//
			//			WebElement serachField  = driver.findElement(By.xpath("//*[@id='searchbox']/input[@id='query_for_site']"));
			//			serachField.click();
			//			serachField.clear();
			//			serachField.sendKeys(this.textToSearch);
			//
			//			WebElement enter  = driver.findElement(By.xpath("//*[@id='siteBtnSearch']"));
			//			enter.click();
		}
		catch(Exception e){ e.printStackTrace();return false;}

		return true;
	}

	@Override
	public void resultsPage(List<String> urls) {

		sleep(1000);


		for(int i=10; i<this.numOfArticles; i=i+10){
			System.out.println("click");
			clickLoadMore();
		}

		ArrayList<WebElement> results  = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='results_list']//div[@class='item']"));

		int found = 0;
		String link="", title="";
		int i=0;
		int  checks = 0;
		boolean addLink=false;
		System.out.println(results.size());
		try{
			while(found < numOfArticles){

				WebElement head = results.get(i)
						.findElement(By.className("left_side")).findElement(By.tagName("a"));

				title = head.getText();
				link = head.getAttribute("href");

				System.out.println(link);
				System.out.println(title);

				addLink = stateHandle(link, title);
				if(addLink){
					urls.add(link);
					found++;

					addLink=false;
				}
				i++;
				checks++;

				if(i>=results.size()){
					clickLoadMore();
					results  = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='results_list']//div[@class='item']"));		
				}

				if(checks == maxSearch)
					return;
			}
		}catch(Exception e){ e.printStackTrace();return;}


	}

	private void clickLoadMore() {
		WebElement loadMore  = driver.findElement(By.xpath("//*[@class='results_list']//*[@class='btn btnProp']"));
		moveTo2(driver, loadMore);
		sleep(2000);
		loadMore.click();
		sleep(2000);
	}

}
