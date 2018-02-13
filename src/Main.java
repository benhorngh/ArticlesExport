import java.io.FileNotFoundException;


import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Ben Horn
 * @since 1/2018
 *
 */
public class Main {

	public static XSSFWorkbook workbook;

	public static void main(String[] args) {
		int numOfArticles = 10;
		String textToSearch = "ביטקוין";
		String textToCompare = "US";
		state stat= state.regular;  
		boolean ynet = false;
		boolean blmbrg = true;
		boolean TM = false;

		String startDate="1/1/2017";
		String endDate="1/1/2018";

		Site[] sites = init(textToSearch, textToCompare, stat, numOfArticles, startDate,endDate);
		boolean[] players = {ynet, TM, blmbrg};
		//Ynet , TheMarker, Bloomberg
		play(sites, players);

		System.out.println();
		System.out.println("Done.");

	}

	private static Site[] init(String tts, String ttc, state stat, int noa, String sd,String ed){
		Site[] sites = new Site[3];
		sites[0]=new Ynet     (tts, ttc, noa, stat, sd,ed);
		sites[1]=new TheMarker(tts, ttc, noa, stat, sd,ed);
		sites[2]=new Bloomberg("Bitcoin", ttc, noa, stat, sd,ed);

		return sites;

	}

	private static void play(Site[] sites, boolean[] players) {

		String fileName = "excelFile";

		startWriters();

		for(int i=0; i<sites.length; i++){
			if(players[i]){
				try{
					sites[i].run();
				}
				catch(Exception e){System.err.println("Ynet Faild ");
				e.printStackTrace();}
			}
		}
	
		
		for(int i=0; i<sites.length; i++){
			if(sites[i].articles!=null)
				ArticlesRow.WriteToFile(sites[i].articles);
		}

		closeWriters(fileName);

	}





	public static void startWriters(){
		workbook = new XSSFWorkbook();

		XSSFSheet ArticlesSheet = workbook.createSheet("Articles");
		XSSFSheet CommentsSheet = workbook.createSheet("comments");

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
