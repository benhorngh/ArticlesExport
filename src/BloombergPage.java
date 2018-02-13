import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BloombergPage extends Page{

	public BloombergPage(WindowState window){
		super();
		this.window = window;
	}
	
	
	
	private final String s  = "//*[@class='transporter-item current']";
	
	@Override
	public String getTitle() {
		boolean ok = false;
		WebElement ttl = null;
		try{
			ttl =  driver.findElement(By.xpath(s+"//*[@class='lede-text-only__highlight']"));
			ok= true;
		}
		catch(Exception e){
		}
		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath(s+"//*[@class='lede-large-content__highlight']"));
				if(!ttl.getText().isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}
		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath(s+"//*[@class='full-width-image-lede-text-above__hed']"));
				if(!ttl.getText().isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}
		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath(s+"//*[@class='lede-vertical-image-text-right__hed']"));
				if(!ttl.getText().isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}
		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath("//*[@class='video-metadata__title']"));
				if(!ttl.getText().isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}
		
		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath(s+"//*[@class='lede-text-only__hed']"));
				if(!ttl.getText().isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}

		System.out.println(ttl.getText());
		return ttl.getText();		
	}

	@Override
	public String getSubTitle() {
		String subs = "";
		boolean ok =false;
		try{
			ArrayList<WebElement> rows = (ArrayList<WebElement>) driver.findElements(By.xpath(s+"//*[@class='abstract__item']"));
			for(int i=0; i<rows.size(); i++){
				subs+= rows.get(i).getText() +". ";
			}
			if(!subs.isEmpty()){
				ok=true;
			}
		}
		catch(Exception e){}
		if(!ok){
			try{
				WebElement sub = driver.findElement(By.xpath(s+"//*[@class='lede-text-only__dek']/span"));
				subs = sub.getText();
				if(!subs.isEmpty()){
					ok=true;
				}
			}
			catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement sub = driver.findElement(By.xpath(s+"//*[@class='full-width-image-lede-text-above__dek']"));
				subs = sub.getText();
				if(!subs.isEmpty()){
					ok=true;
				}

			}
			catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement sub = driver.findElement(By.xpath(s+"//*[@class='lede-large-content__dek']"));
				subs = sub.getText();
				if(!subs.isEmpty()){
					ok=true;
				}
			}
			catch(Exception e){}
		}
		if(!ok){
			try{//*[@class='lede-vertical-image-text-right__dek']
				WebElement sub = driver.findElement(By.xpath(s+"//*[@class='lede-vertical-image-text-right__dek']"));
				subs = sub.getText();
				if(!subs.isEmpty()){
					ok=true;
				}
			}
			catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement sub = driver.findElement(By.xpath(s+"//*[@class='lede-text-only__dek']"));
				subs = sub.getText();
				if(!subs.isEmpty()){
					ok=true;
				}
			}
			catch(Exception e){}
		}
		
//		
		return subs;
	}

	@Override
	public String getReporter() {
		WebElement author = null;
		boolean ok =false;
		try{
			author =  driver.findElement(By.xpath(s+"//*[@class='author']"));
			if(!author.getText().isEmpty())
				ok = true;
		}
		catch(Exception e){}
		if(!ok){
			author =  driver.findElement(By.xpath(s+"//*[@class='lede-text-only__byline']"));
			if(!author.getText().isEmpty())
				ok = true;
		}
		if(!ok){
			author =  driver.findElement(By.xpath(s+"//*[@class='lede-large-content__byline']"));
			if(!author.getText().isEmpty())
				ok = true;
		}
		if(!ok){
			author =  driver.findElement(By.xpath("//*[@class='video-metadata__seriesname-link']"));
			if(!author.getText().isEmpty())
				ok = true;
		}

		return author.getText();
	}

	@Override
	public String getDate() {
		String url = driver.getCurrentUrl();
		int index = url.indexOf("20");
		String date = url.substring(index, index+10);
		String[] arr = date.split("-");
		date="";
		date+=arr[arr.length-1]+"";
		for(int i=arr.length-2; i>=0; i--){
			date+="."+arr[i];
		}
		return date;
	}

	@Override
	public String getBody() {
		String body="";

		ArrayList<WebElement> pgr =  (ArrayList<WebElement>) driver.findElements(By.xpath(s+"//*[@class='body-copy']/p"));
		System.err.println(pgr.size());
		String tmp = "";
		for(int i=0; i<pgr.size(); i++){
			tmp  = pgr.get(i).getText();
			body += tmp;

		}
		if(body.isEmpty()){
			WebElement bd =  driver.findElement(By.xpath("//*[@class='video-metadata__summary']"));
			body = bd.getText();
		}
		return body;
	}



	@Override
	public ArrayList<CommentRow> commentSecction() {
		WebElement cmm = null;
		System.out.println("start comments");
		ArrayList<CommentRow> cmmts = new ArrayList<CommentRow>();
		try{
			cmm =  driver.findElement(By.xpath(s+"//*[@class='disqus']"));
		}
		catch(Exception e){ return cmmts;}
		moveTo2(driver, cmm);
		sleep(5000);
		cmm.click();
		sleep(5000);

		WebElement frame = driver.findElement(By.xpath("//*[contains(@id, 'dsq-app')]"));

		driver.switchTo().frame(frame);

		boolean open = false;
		WebElement dad;
		WebElement loadMore;
		while(!open){
			try{

				dad =  driver.findElement(By.xpath("//*[@class='load-more']"));
				loadMore =  driver.findElement(By.xpath("//*[@class='btn load-more__button']"));
				if(dad.getAttribute("style").equals("display: none;"))
					open = true;

				moveTo2(driver, loadMore);
				sleep(3000);
				clickInvisible(driver, loadMore);
				sleep(2000);
			}
			catch(Exception e){e.printStackTrace(); break;}
		}

		try{
			readComments(cmmts);
		}
		catch(Exception e){
			System.err.println("fuck");
			e.printStackTrace();
		}

		return cmmts;
	}

	
	@Override
	public void readComments(ArrayList<CommentRow> commentList){
		String[] path = {"//*[@id='posts']","","","","","","","","",""};
		int[] num = {1,1,1,1,1,1,1,1};
		//		int num = 0;
		ArrayList<WebElement> posts1=null, posts2=null, posts3=null, posts4=null, posts5=null
				, posts6=null, posts7=null, posts8=null;
		posts1 = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@id='posts']/ul/li"));

		for(int i1=0; i1<posts1.size(); i1++){
			commentList.add(getComment(posts1.get(i1), num));
			num[0]++;
			path[1] = posts1.get(i1).getAttribute("id");
			try{
				posts2 = (ArrayList<WebElement>) driver.findElements(By.xpath(strPath(path)+"/ul/li"));
			}catch(Exception e){System.err.println(strPath(path));};
			for(int i2=0; i2<posts2.size(); i2++){
				commentList.add(getComment(posts2.get(i2), num));
				num[1]++;
				path[2] = posts2.get(i2).getAttribute("id");
				try{
					posts3 = (ArrayList<WebElement>) driver.findElements(By.xpath(strPath(path)+"/ul/li"));
				}catch(Exception e){System.err.println(strPath(path));};

				for(int i3=0; i3<posts3.size(); i3++){
					commentList.add(getComment(posts3.get(i3), num));
					num[2]++;
					path[3] = posts3.get(i3).getAttribute("id");
					try{
						posts4 = (ArrayList<WebElement>) driver.findElements(By.xpath(strPath(path)+"/ul/li"));
					}catch(Exception e){System.err.println(strPath(path));};

					for(int i4=0; i4<posts4.size(); i4++){
						commentList.add(getComment(posts4.get(i4), num));
						num[3]++;
						path[4] = posts4.get(i4).getAttribute("id");
						try{
							posts5 = (ArrayList<WebElement>) driver.findElements(By.xpath(strPath(path)+"/ul/li"));
						}catch(Exception e){System.err.println(strPath(path));};

						for(int i5=0; i5<posts5.size(); i5++){
							commentList.add(getComment(posts5.get(i5), num));
							num[4]++;
							path[5] = posts5.get(i5).getAttribute("id");
							try{
								posts6 = (ArrayList<WebElement>) driver.findElements(By.xpath(strPath(path)+"/ul/li"));
							}catch(Exception e){System.err.println(strPath(path));};

							for(int i6=0; i6<posts6.size(); i6++){
								commentList.add(getComment(posts6.get(i6), num));
								num[5]++;
								path[6] = posts6.get(i6).getAttribute("id");
								try{
									posts7 = (ArrayList<WebElement>) driver.findElements(By.xpath(strPath(path)+"/ul/li"));
								}catch(Exception e){System.err.println(strPath(path));};


								for(int i7=0; i7<posts7.size(); i7++){
									commentList.add(getComment(posts7.get(i7), num));
									num[6]++;
									path[7] = posts7.get(i7).getAttribute("id");
									try{
										posts8 = (ArrayList<WebElement>) driver.findElements(By.xpath(strPath(path)+"/ul/li"));
									}catch(Exception e){System.err.println(strPath(path));};

									for(int i8=0; i8<posts8.size(); i8++){
										num[7]++;
										commentList.add(getComment(posts8.get(i8), num));
									}path[7] = ""; num[7]=1;
								}path[6] = ""; num[6]=1;
							}path[5] = ""; num[5]=1;
						}path[4] = ""; num[4]=1;
					}path[3] = ""; num[3]=1;
				}path[2] = ""; num[2]=1;
			}path[1] = ""; num[1]=1;
		}
	}

	public CommentRow getComment(WebElement we, int[] num) {
		WebElement name = we.findElement(By.className("post-byline")).findElements(By.tagName("span")).get(0);
//		*[@class='post-byline']/span[1]
		String tkbk = name.getText();

		WebElement comment = we.findElement(By.className("post-body-inner"));
		String body = comment.getText();

		WebElement time = we.findElement(By.className("post-meta"));
		String date= "";
		try{
			date = time.findElement(By.tagName("a")).getAttribute("title");
			date = toDate(date);
		}
		catch(Exception e){}

		boolean org = false;
		if(num[0]==1)
			org = true;
		CommentRow cr = new CommentRow("Bloomberg", tkbk, date, "", body, toInt(num), org);
		return cr;
	}


	private int toInt(int[] num){
		String s="";
		for(int i=0; i<num.length;i++){
			s+= num[i]+"";
		}
		return Integer.parseInt(s);
	}
	
	private String strPath(String[] path){
		String s ="";
		s+=path[0];
		for(int i=1; i<path.length; i++){
			if(!path[i].isEmpty())
				s+= "//*[@id='"+path[i]+"']";
		}
		return s;
	}
	private String toDate(String date) {
		if(date.isEmpty() || date == null) return "";
		String arr[] = date.split(" ");
		if(arr == null || arr.length<5) return "";
		String monthStr = arr[1];
		int month= monthToInt(monthStr);
		String day = arr[2].substring(0, arr[2].length()-1);
		String year = arr[3];
		return day+"/"+month+"/"+year;
	}

}
