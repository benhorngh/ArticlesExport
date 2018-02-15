import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BloombergPage extends Page{

	public BloombergPage(Site site,WindowState window){
		super(site);
		this.window = window;
		this.SiteName = "Bloomberg";
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
		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath(s+"//*[@class='article-title copy-width']"));
				if(!ttl.getText().isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}
		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath("//*[@class='article-title copy-width']"));
				if(!ttl.getText().isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}


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
			try{
				author =  driver.findElement(By.xpath(s+"//*[@class='lede-text-only__byline']"));
				if(!author.getText().isEmpty())
					ok = true;
			}catch(Exception e){}
		}
		if(!ok){
			try{
				author =  driver.findElement(By.xpath(s+"//*[@class='lede-large-content__byline']"));
				if(!author.getText().isEmpty())
					ok = true;
			}catch(Exception e){}
		}
		if(!ok){
			try{
				author =  driver.findElement(By.xpath("//*[@class='video-metadata__seriesname-link']"));
				if(!author.getText().isEmpty())
					ok = true;
			}catch(Exception e){}
		}
		if(!ok){
			try{
				author =  driver.findElement(By.xpath("//*[@class='article-title copy-width']"));
				if(!author.getText().isEmpty())
					ok = true;
			}catch(Exception e){}
		}
		if(!ok){
			try{
				author =  driver.findElement(By.xpath("//*[@class='bydek copy-width']"));
				if(!author.getText().isEmpty())
					ok = true;
			}catch(Exception e){}
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
		String tmp = "";
		for(int i=0; i<pgr.size(); i++){
			tmp  = pgr.get(i).getText();
			body += tmp;

		}
		if(body.isEmpty()){

			WebElement bd =  driver.findElement(By.xpath("//*[@class='copy-block copy-width']"));
			body = bd.getText();
		}


		return body;
	}



	@Override
	public ArrayList<CommentRow> commentSecction() {
		WebElement cmm = null;
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
				sleep(2000);
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
		if(path.size()==0)
			path.add("//*[@id='posts']");
		ArrayList<WebElement> cmmts=null;
		try{
			cmmts= (ArrayList<WebElement>) driver.findElements(By.xpath(pts()+"/ul/li"));
		}catch(Exception e){return;}
		//				if(cmmts == null || cmmts.size() == 0) return;

		numbers.add(0);
		for(WebElement cmmt: cmmts){
			count();
			commentList.add(getComment(cmmt, listToArray(numbers)));
			path.add(cmmt.getAttribute("id"));
			readComments(commentList);
		}
		numbers.remove(numbers.size()-1);
		path.remove(path.size()-1);
	}


	private void count() {
		int x= numbers.get(numbers.size()-1);
		x++;
		numbers.remove(numbers.size()-1);
		numbers.add(x);
	}

	private int[] listToArray(ArrayList<Integer> IntArrList) {

		int[] arr = new int[IntArrList.size()];

		for(int i =0; i<arr.length; i++){
			arr[i]  = IntArrList.get(i);
		}
		return arr;
	}

	private ArrayList<Integer> numbers = new ArrayList<Integer> ();
	private ArrayList<String> path = new ArrayList<String>();

	private String pts(){
		String str = path.get(0);
		for(int i=1; i<path.size(); i++){
			if(path.get(i).isEmpty())
				break;
			str += "//*[@id='"+path.get(i)+"']";
		}
		return str;
	}
	private String ArrToStr(int[] num){
		String s="";
		s = num[0]+"";
		for(int i=1; i<num.length;i++){
			s+= "."+num[i];
		}
		return s;
	}


	public CommentRow getComment(WebElement we, int[] num) {
		String tkbk="", date="",body="";
		try{
			WebElement name = we.findElement(By.className("post-byline")).findElements(By.tagName("span")).get(0);
			tkbk = name.getText();
		}catch(Exception e){}

		try{
			WebElement comment = we.findElement(By.className("post-body-inner"));
			body = comment.getText();
		}catch(Exception e){}

		try{
			WebElement time = we.findElement(By.className("post-meta"));
			date = time.findElement(By.tagName("a")).getAttribute("title");
			date = toDate(date);
		}
		catch(Exception e){}

		boolean org = false;
		if(num[0]==1)
			org = true;
		CommentRow cr = new CommentRow("Bloomberg", tkbk, date, "", body, ArrToStr(num), org);
		return cr;
	}


	private static final String mail = "benhorenn@gmail.com";
	private static final String password = "Bb4546662/";


	@Override
	public void signIn(){
		try{
			Bloomberg b = (Bloomberg) this.site;
			b.closeAd(driver);
			sleep(2000);

			WebElement si = driver.findElement(By.className("bb-nav-touts__link"));
			si.click();

			sleep(3000);

			driver.switchTo().frame("reg-ui-client__iframe");
			sleep(2000);

			WebElement mailbox = driver.findElement(By.className("form-element__email"));
			mailbox.clear();
			mailbox.click();
			mailbox.sendKeys(mail);

			WebElement pass = driver.findElement(By.className("form-element__password"));
			pass.clear();
			pass.click();
			pass.sendKeys(password);

			sleep(3000);
			WebElement button = driver.findElement(By.className("login-register__submit"));
			button.click();

			sleep(3000);
		}
		catch(Exception e) {e.printStackTrace();}

		driver.get(driver.getCurrentUrl());
	}



	private String toDate(String date) {
		if(date.isEmpty() || date == null) return "";
		String arr[] = date.split(" ");
		if(arr == null || arr.length<5) return "";
		String monthStr = arr[1];
		int month= monthToInt(monthStr);
		String day = arr[2].substring(0, arr[2].length()-1);
		String year = arr[3];
		return day+"."+month+"."+year;
	}



	/*
	 * a mehod I build, that do the same as the regular "readComments" 
	 * just not recursive
	 */
	public void readCommentsWithoutRec(ArrayList<CommentRow> commentList){


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
	private String strPath(String[] path){
		String s ="";
		s+=path[0];
		for(int i=1; i<path.length; i++){
			if(!path[i].isEmpty())
				s+= "//*[@id='"+path[i]+"']";
		}
		return s;
	}


}
