import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * http://www.globes.co.il/
 * @author benho
 *
 */
public class Globes extends Site{

	public Globes(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="http://www.globes.co.il/";
		this.window = WindowState.Invisible;
		this.DateRange = true;
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

		if(!this.fromDate.isEmpty())
			selectTime();
		return true;
	}




	@Override
	public void resultsPage(List<String> urls) {

		driver.get(driver.getCurrentUrl());

		sleep(1000);



		for(int i=10; i<this.numOfArticles; i=i+10){
			clickLoadMore();
		}

		ArrayList<WebElement> results  = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='results_list']//div[@class='item']"));

		int found = 0;
		String link="", title="";
		int i=0;
		int  checks = 0;
		boolean addLink=false;
		try{
			while(found < numOfArticles){

				if(i<results.size()){
					WebElement head = results.get(i)
							.findElement(By.className("left_side")).findElement(By.tagName("a"));

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
						mainScreen.addToLog(found+"/"+this.numOfArticles);

						addLink=false;
					}
					i++;
				}
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


	private void selectTime() {

		System.out.println("F "+fromDate);
		System.out.println("T "+toDate);

		String [] start = this.fromDate.split("\\.");
		String [] end = this.toDate.split("\\.");

		try{
			WebElement form = driver.findElement(By.xpath("//*[@class='searcherFormTbl']"));

			WebElement sday = form.findElement(By.xpath(".//*[@id='since.date']"));
			WebElement smonth = form.findElement(By.xpath(".//*[@id='since.month']"));
			WebElement syear = form.findElement(By.xpath(".//*[@id='since.year']"));

			WebElement eday = form.findElement(By.xpath(".//*[@id='until.date']"));
			WebElement emonth = form.findElement(By.xpath(".//*[@id='until.month']"));
			WebElement eyear = form.findElement(By.xpath(".//*[@id='until.year']"));


			System.out.println(Arrays.toString(start));
			System.out.println(Arrays.toString(end));

			Select sd = new Select(sday);
			sd.selectByValue(start[0]);

			Select sm = new Select(smonth);
			sm.selectByValue(start[1]);

			Select sy = new Select(syear);
			sy.selectByValue(start[2]);


			Select ed = new Select(eday);
			ed.selectByValue(end[0]);

			Select em = new Select(emonth);
			em.selectByValue(end[1]);

			Select ey = new Select(eyear);
			ey.selectByValue(end[2]);


			WebElement button = driver.findElement(By.xpath("//*[@class='btn btnProp ibSearch']"));
			sleep(1500);

			try{
				moveTo2(driver, button);
			}catch(Exception e){}

			sleep(1500);
			button.click();

		}catch(Exception e){e.printStackTrace();}
	}

	private void clickLoadMore() {
		WebElement loadMore  = driver.findElement(By.xpath("//*[@class='results_list']//*[@class='btn btnProp']"));
		moveTo2(driver, loadMore);
		sleep(2000);
		loadMore.click();
		sleep(2000);
	}

}
