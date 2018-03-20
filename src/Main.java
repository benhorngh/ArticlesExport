import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author benho
 * @since 1/2018
 *
 */
public class Main {


	static final String fileName = "excelFile";
	static final String commentFolder = "comments";
	static String folderName ="output";

	public static void main(String[] args) {
		// replaced with GUI 


		String textToSearch = "טראמפ";
		String textToSearchEnglish = "teva";
		String textToCompare = "";
		String textToCompareEnglish = "";
		SearchState stat= SearchState.regular; 
		int numOfArticles = 10000;
		boolean ynet = false;
		boolean TM = false;
		boolean blmbrg = false;
		boolean rtrs = false;
		boolean glbs = false;
		boolean CNN = false;
		boolean BBC = false;
		boolean USAt = false;
		boolean NYT = true;
		boolean BusIn = true;
		boolean Altrnt = true;
		boolean dlyml = true;
		boolean fox = true;
		boolean IBT = true;
		boolean[] players={
				ynet
				,TM
				,blmbrg
				,rtrs
				,glbs
				,CNN
				,BBC
				,USAt
				,NYT
				,BusIn
				,Altrnt
				,dlyml
				,fox
				,IBT
		};

		String startDate="1.1.2006"; 
		String endDate="1.1.2018";

		boolean toFile = false;
		boolean fair = false;

		System.setProperty("webdriver.chrome.logfile", "chromedriver.log");

		starter(textToSearch,textToSearchEnglish,textToCompare,textToCompareEnglish
				,stat,startDate,endDate,numOfArticles, players, toFile, fair);
	}

	/**
	 * @param textToSearch -text to the search field
	 * @param textToSearchEnglish -text to the search field in english (for websites in english.)
	 * @param textToCompare -text to search inside the article
	 * @param textToCompareEnglish -text to search inside the article in english (for websites in english.)
	 * @param stat -state of search. regular, search in title, body or in the comments. 
	 * @param startDate -starting date
	 * @param endDate -ending date
	 * @param numOfArticles -number of needed reports
	 * @param players -true/false for each site.
	 * @param toFile -true if include txt file of each article in output folder.
	 */
	public static void starter(String textToSearch
			,String textToSearchEnglish
			,String textToCompare
			,String textToCompareEnglish
			,SearchState stat
			,String startDate
			,String endDate
			,int numOfArticles
			,boolean[] players
			,boolean toTxt
			,boolean fairSplit
			){


		Site.fairSplit = fairSplit;





		boolean ok =false;

		for(int i=0; i<players.length; i++)
			ok = ok || players[i];
		if(!ok){
			mainScreen.addToLog("error: no site selected.");
			return;
		}




		if(textToSearch.isEmpty() && textToSearchEnglish.isEmpty()){
			mainScreen.addToLog("error: 'text to search' is empty");
			return;
		}
		if(numOfArticles<=0){
			mainScreen.addToLog("error: Invaild 'Number of reports' ");
			return;
		}
		if(numOfArticles<=0){
			mainScreen.addToLog("error: Invaild 'Number of reports' ");
			return;
		}
		if(!startDate.isEmpty()){
			Date sd;
			sd = Funcs.stringToDate(startDate);
			if(sd == null){
				mainScreen.addToLog("error: Invaild date");
				return;
			}

			String[] arr = startDate.split("\\.");
			try{
				if(Integer.parseInt(arr[0])>32 || Integer.parseInt(arr[1])>12){
					mainScreen.addToLog("error: Invaild start date");
					return;
				}
			}catch(Exception e){mainScreen.addToLog("error: Invaild start date");
			return;}
		}



		if(!endDate.isEmpty()){
			Date ed;
			ed = Funcs.stringToDate(endDate);
			if(ed == null){
				mainScreen.addToLog("error: Invaild date");
				return;
			}

			String[] arr = endDate.split("\\.");
			try{
				if(Integer.parseInt(arr[0])>32 || Integer.parseInt(arr[1])>12){
					mainScreen.addToLog("error: Invaild end date");
					return;
				}
			}catch(Exception e){mainScreen.addToLog("error: Invaild end date");
			return;}
		}






		if(endDate.isEmpty() &&  (!startDate.isEmpty())){
			if(fairSplit)
				endDate = "1.1.2018";
			else endDate = Funcs.todayString();
		}

		if(startDate.isEmpty() && (!endDate.isEmpty())){
			startDate = "1.1.1980";
		}

		if(Funcs.stringToDate(startDate)==null)
			startDate ="";

		if(Funcs.stringToDate(endDate)==null)
			endDate ="";


		//		System.out.println("s "+startDate);
		//		System.out.println("e "+endDate);
		//		

		ok =false;
		int i=1;

		File directory = new File(folderName);
		if (! directory.exists()){
			directory.mkdir();
			ok=true;
		}
		i++;

		while(!ok){
			directory = new File(folderName+"-"+i);
			if (! directory.exists()){
				directory.mkdir();
				ok=true;
				folderName = folderName+"-"+i;
			}
			i++;
		}



		File commentsFold = new File(folderName+"/comments");
		if (! commentsFold.exists())
			commentsFold.mkdir();





		mainScreen.addToLog("starting..");

		System.out.println();
		System.out.println("tts: "+textToSearch +" || "+textToSearchEnglish);
		System.out.println("ttc: "+textToCompare+" || " + textToCompareEnglish);
		System.out.println("stat: "+stat);
		System.out.println("start d: "+startDate);
		System.out.println("end d: "+endDate);
		System.out.println("noa: "+numOfArticles);
		System.out.println("totxt: "+toTxt);
		System.out.println("fair splt: "+fairSplit);
		System.out.print("players: ");
		if(players[0]) System.out.print("Ynet ");
		if(players[1]) System.out.print("TheMarker ");
		if(players[2]) System.out.print("Bloomberg ");
		if(players[3]) System.out.print("Reuters ");
		if(players[4]) System.out.print("Globes ");
		if(players[5]) System.out.print("CNN ");
		if(players[6]) System.out.print("BBC ");
		if(players[7]) System.out.print("USAtoday ");
		if(players[8]) System.out.print("NewYorkTimes ");
		if(players[9]) System.out.print("BusinessInsider ");
		if(players[10]) System.out.print("Alternet ");
		if(players[11]) System.out.print("DailyMail ");
		System.out.println();
		System.out.println();

		Site[] sites = init(textToSearch,textToSearchEnglish, textToCompare, textToCompareEnglish,
				stat, numOfArticles, startDate,endDate);

		try{
			play(sites, players, toTxt);
		}catch(Exception e){}
		System.out.println();
		System.out.println("Done.");
		mainScreen.addToLog("Done."+'\n');
	}



	/**
	 * @return initialized Sites array.
	 */
	private static Site[] init(String tts, String etts, String ttc, String ettc, 
			SearchState stat, int noa, String sd,String ed){
		Site[] sites = new Site[14];
		sites[0]=new Ynet     (tts, ttc, noa, stat, sd,ed);
		sites[1]=new TheMarker(tts, ttc, noa, stat, sd,ed);
		sites[2]=new Bloomberg(etts, ettc, noa, stat, sd,ed);
		sites[3]=new Reuters(etts, ettc, noa, stat, sd,ed);
		sites[4]=new Globes(tts, ttc, noa, stat, sd,ed);
		sites[5]=new CNN(etts, ettc, noa, stat, sd,ed);
		sites[6]=new BBC(etts, ettc, noa, stat, sd,ed);
		sites[7]=new USAtoday(etts, ettc, noa, stat, sd,ed);
		sites[8]=new NewYorkTimes(etts, ettc, noa, stat, sd,ed);
		sites[9]=new BusinessInsider(etts, ettc, noa, stat, sd,ed);
		sites[10]=new Alternet(etts, ettc, noa, stat, sd,ed);
		sites[11]=new Dailymail(etts, ettc, noa, stat, sd,ed);
		sites[12]=new FoxNews(etts, ettc, noa, stat, sd,ed);
		sites[13]=new IBtimes(etts, ettc, noa, stat, sd,ed);

		return sites;
	}

	private static void play(Site[] sites, boolean[] players, boolean totxt) {

		/*
		 * !!warnning!!
		 * use only in strong computer. high CPU usege. unchecked deeply.
		 * !!warnning!!
		 */
		boolean useThreads = false;


		startWriters();

		File directory;
		directory = new File(folderName+"/"+fileName+".xlsx");
		if (!directory.exists()){
			try {
				outputStream = new FileOutputStream(folderName+"/"+fileName+".xlsx");
			} catch (FileNotFoundException e) {}
		}

		else{
			boolean ok = false;
			int j=1;
			while(!ok){
				directory = new File(folderName+"/"+fileName+"-"+j+".xlsx");
				if (!directory.exists()){
					try {
						outputStream = new FileOutputStream(folderName+"/"+fileName+"-"+j+".xlsx");
						ok =true;
					} catch (FileNotFoundException e) {}
				}
				j++;
			}
		}


		Thread[] threads = new  Thread[sites.length];
		for(int i=0; i<threads.length; i++){
			threads[i] = new Thread(sites[i]);
		}


		for(int i=0; i<sites.length; i++){
			if(players[i]){
				try{
					if(useThreads)
						threads[i].start();
					else {
						sites[i].run();
					}
				}
				catch(Exception e){System.err.println("failed!");
				e.printStackTrace();}
			}
		}

		if(useThreads){
			try {
				Thread.currentThread();
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.err.println("join!");
			try{
				for(int i=0; i<threads.length; i++){
					threads[i].join();
				}
			}catch(Exception e){};
		}

		mainScreen.addToLog("start exporting to excel..");
		for(int i=0; i<sites.length; i++){
			if(players[i]==true)
				if(sites[i].articles!=null && sites[i].articles.size()!=0)
					ArticlesRow.WriteToFile(sites[i].articles,  totxt);
		}

		closeWriters();
		closeAllChrome();
	}

	private static void closeAllChrome() {
		try {
			Runtime.
			getRuntime().
			//			   exec("cmd /c start \"\" closeOpenChromeD.bat");
			exec("taskkill /im chromedriver.exe /f");

		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {e.printStackTrace();}
	}


	static XSSFWorkbook workbook;
	static FileOutputStream outputStream;

	public static void startWriters(){
		workbook = new XSSFWorkbook();

		XSSFSheet ArticlesSheet = workbook.createSheet("Articles");
		XSSFSheet CommentsSheet = workbook.createSheet("Comments");

		//		ArticlesSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);
		//		CommentsSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);

		String[] arhl={"num.","site","link","date","reporter","number of words","headline", "subtitle","body","","","comments"};
		Funcs.StringArrToLastRow(arhl, ArticlesSheet);

		String[] cmhl={"report num.","website","num.","is Original","name","date","title","comment"};
		Funcs.StringArrToLastRow(cmhl, CommentsSheet);
	}



	public static void closeWriters(){
		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
