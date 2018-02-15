import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author benho
 * @since 1/2018
 *
 */
public class Main {

	public static XSSFWorkbook workbook;

	public static void main(String[] args) {
		//will replace it with GUI
		int numOfArticles = 5;
		String textToSearch = "נתניהו";
		String textToSearchEnglish = "Trump";
		String textToCompare = "סטיב";
		String textToCompareEnglish = "Trump";
		SearchState stat= SearchState.regular;  
		boolean ynet = false;
		boolean TM = false;
		boolean blmbrg = false;
		boolean rtrs = false;
		boolean glbs = true;

		String startDate="1/1/2017"; //currently noe completed
		String endDate="1/1/2018";


		Site[] sites = init(textToSearch,textToSearchEnglish, textToCompare, textToCompareEnglish,
				stat, numOfArticles, startDate,endDate);
		boolean[] players = {ynet, TM, blmbrg, rtrs, glbs};
		//Ynet , TheMarker, Bloomberg, Reuters, Globes .

		play(sites, players);


		System.out.println();
		System.out.println("Done.");
	}



	/**
	 * 
	 * @param tts -text to the search field
	 * @param etts -text to the search field in english (for websites in english.)
	 * @param ttc  -text to search inside the article
	 * @param ettc -text to search inside the article in english (for websites in english.)
	 * @param stat -state of search. regular, search in title, body or in the comments. 
	 * @param noa -number of needed reports
	 * @param sd starting date
	 * @param ed ending date
	 * @return initialized Sites array.
	 */
	private static Site[] init(String tts, String etts, String ttc, String ettc, 
			SearchState stat, int noa, String sd,String ed){
		Site[] sites = new Site[5];
		sites[0]=new Ynet     (tts, ttc, noa, stat, sd,ed);
		sites[1]=new TheMarker(tts, ttc, noa, stat, sd,ed);
		sites[2]=new Bloomberg(etts, ettc, noa, stat, sd,ed);
		sites[3]=new Reuters(etts, ettc, noa, stat, sd,ed);
		sites[4]=new Globes(tts, ttc, noa, stat, sd,ed);

		return sites;
	}

	private static void play(Site[] sites, boolean[] players) {

		boolean useThreads = false;

		String fileName = "excelFile";

		startWriters();

		Thread[] threads = new  Thread[sites.length];
		for(int i=0; i<threads.length; i++){
			threads[i] = new Thread(sites[i]);
		}


		for(int i=0; i<sites.length; i++){
			if(players[i]){
				try{
					if(useThreads)
						threads[i].start();
					else sites[i].run();

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
			System.err.println("the end has come. join!");
			try{
				for(int i=0; i<threads.length; i++){
					threads[i].join();
				}
			}catch(Exception e){};
		}

		for(int i=0; i<sites.length; i++){
			if(players[i]==true)
				if(sites[i].articles!=null && sites[i].articles.size()!=0)
					ArticlesRow.WriteToFile(sites[i].articles);
		}

		closeWriters(fileName);

	}





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

	public static void closeWriters(String fileName){
		try {
			FileOutputStream outputStream = new FileOutputStream(fileName+".xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
