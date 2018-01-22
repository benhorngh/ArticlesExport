import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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

		String textToSearch = "טראמפ מזרח התיכון";
		String textToCompare = "טראמפ "+'"'+"נשיא אר"+'"';
		state state1= state.body;  
		int numOfArticles = 7;
		boolean ynet = true;
		
		startWriters();
		List<ArticlesRow> YnetReports=null;
		if(ynet)
			YnetReports = Ynet.Main(textToSearch, textToCompare,  numOfArticles, state1);
		
		if(YnetReports!=null)
			ArticlesRow.WriteToFile(YnetReports);
		String name = "excelFile";
		closeWriters(name);
	}

	
	
	
	
	public static void startWriters(){

		 workbook = new XSSFWorkbook();

		XSSFSheet ArticlesSheet = workbook.createSheet("Articles");
		XSSFSheet CommentsSheet = workbook.createSheet("comments");

//		ArticlesSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);
//		CommentsSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);

		String[] arhl={"num.","site","date","reporter","number of words","headline", "subtitle","body","comments"};
		Funcs.StringArrToLastRow(arhl, ArticlesSheet);

		String[] cmhl={"report num.","website","num.","name","date","title","comment"};
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
