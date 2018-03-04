import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


public class USAtodayPage extends Page{

	public USAtodayPage(WindowState window, Site site){
		super(site);
		this.window = window;
		this.SiteName = "USAtoday";
	}



	@Override
	public String getTitle() {

		closeAd();

		String str="";
		boolean ok = false;

		if(!ok){
			try{
				WebElement title = driver.findElement(By.xpath("//h1[contains(class,asset-headline)]"));
				str=   title.getText();
				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}


		return str;
	}

	@Override
	public String getSubTitle() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getReporter() {

		String str="";
		boolean ok = false;

		if(!ok){
			try{
				WebElement rptr = driver.findElement(
						By.xpath("//*[contains(class,asset-metabar)]//span[@class='asset-metabar-author asset-metabar-item']"))
						.findElement(By.tagName("a"));

				str=   rptr.getText();
				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement rptr = driver.findElement(
						By.xpath("//*[contains(class,asset-metabar)]//span[@class='asset-metabar-author asset-metabar-item']"));

				str=   rptr.getText();
				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}

		if(!ok){
			try{
				ArrayList<WebElement> rptr = (ArrayList<WebElement>)driver.findElements(
						By.xpath("//*[@slot='bylineInfo']"));

				for(WebElement rp : rptr)
					str=   rp.getText();
				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}





		return str;
	}

	@Override
	public String getDate() {
		String str="";
		boolean ok = false;

		if(!ok){
			try{
				WebElement dt = driver.findElement(
						By.xpath("//*[contains(class,asset-metabar)]//span[@class='asset-metabar-time asset-metabar-item nobyline']"));

				str=   dt.getText();

				String[] arr = str.split(" ");
				arr[4] = arr[4].substring(0, arr[4].length()-1);
				arr[5] = arr[5].substring(0, arr[5].length()-1);

				arr[4] = ""+ monthToInt(arr[4]);
				str = arr[5]+"."+arr[4]+"."+arr[6];
				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}

		if(str.split("\\.")[1].equals("0"))
			ok= false;

		if(!ok){
			try{
				String link = driver.getCurrentUrl();
				String[] arr = link.split("/");
				int index = -1;

				for (int i = 0; i < arr.length; i++) {
					if((arr[i].length()==4)&&(arr[i].contains("20"))){
						index = i;
						break;
					}
				}
				str = arr[index+2]+"."+arr[index+1]+"."+arr[index];
				if(!str.isEmpty())
					ok=true;

			}catch(Exception e){}

		}




		return str;
	}

	@Override
	public String getBody() {

		//article[@class='story primary-content text body-5 sports row']/p

		String str="";
		boolean ok = false;

		if(!ok){
			try{
				ArrayList<WebElement> bdy =(ArrayList<WebElement>) driver.findElements(
						By.xpath("//*[@itemprop='articleBody']/p"));

				for(WebElement p : bdy){
					try{
						p.findElement(By.className("exclude-from-newsgate"));
					}
					catch(Exception e){
						try{
							if(!p.getText().isEmpty())
								str = str + p.getText();

							else {
								clickInvisible(driver, p);
								str = str + p.getText();
							}
						}catch(Exception e2){}
					}
				}

				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}

		if(!ok){
			try{
				ArrayList<WebElement> bdy =(ArrayList<WebElement>) driver.findElements(
						By.xpath("//article[contains(class,primary-content)]/p"));

				for(WebElement p : bdy){
					try{
						p.findElement(By.className("exclude-from-newsgate"));
					}
					catch(Exception e){
						try{
							if(!p.getText().isEmpty())
								str = str + p.getText();

							else {
								clickInvisible(driver, p);
								str = str + p.getText();
							}


						}catch(Exception e2){}
					}
				}

				if(!str.isEmpty())
					ok=true;
			}
			catch(Exception e){}
		}
		return str;
	}

	private void closeAd(){
		try{
			//*[@id='bx-close-inside-649524']
			//*[@class='bx-close bx-close-link bx-close-inside']
			ArrayList<WebElement> x = (ArrayList<WebElement>)
					driver.findElements(By.xpath("//*[@class='bx-close bx-close-link bx-close-inside']"));

			for(WebElement exit : x){
				try{
					exit.click();
					System.out.println("exiit");
				}catch(Exception e){}
			}
		}catch(Exception e){e.printStackTrace();}

	}

	@Override
	public ArrayList<CommentRow> commentSecction() {

		ArrayList<CommentRow> cmmts = new ArrayList<CommentRow>();

		if(true)
			return cmmts;

		try{
			WebElement sec = null;
			try{
				sec = driver.findElement(By.xpath("//*[@data-share-method='comments']"));

			}catch(NoSuchElementException e){
				sec = driver.findElement(By.xpath("//*[@class='utility-bar']//div[contains(@class,'bar-module-comments')]"));
			}
			moveTo2(driver, sec);
			sleep(1500);
			sec.click();

			WebElement frame = driver.findElement(By.xpath("//*[@title='Facebook Social Plugin']"));

			driver.switchTo().frame(frame.getAttribute("name"));

		}catch(Exception e){e.printStackTrace();}
		//title="Facebook Social Plugin"






		return cmmts;
	}

	@Override
	public void readComments(ArrayList<CommentRow> commentsList) {
		// TODO Auto-generated method stub

	}




}
