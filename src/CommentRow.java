import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author Ben Horn
 * @since 1/2018
 *
 */
public class CommentRow {

	public int ArticleNum;
	public String site;
	public String talkbakist;
	public String date;
	public String headline;
	public String comment;
	public int convNum;



	public CommentRow(){
		ArticleNum=0;
		site="";
		talkbakist="";
		date="";
		headline="";
		comment="";
		convNum=0;
	}


	


	

	public CommentRow(String site, int artclNum, String tkbk, String date, String ttl, String cmmt, int cmmNum) {
		this.site = site;
		this.ArticleNum = artclNum;
		this.talkbakist = tkbk;
		this.date = date;
		this.headline = ttl;
		this.comment = cmmt;
		this.convNum = cmmNum;
	}





	public void WriteToFile() {
		XSSFSheet sheet = Main.workbook.getSheet("comments");
		String[] row = {ArticleNum+"", site, convNum+"" ,talkbakist,date, headline, comment};
		Funcs.StringArrToLastRow(row, sheet);
	}






	/**
	 * This functions use to write to csv file, instead of using apache POI for excel
	 * @param writer
	 */
	public void WriteToFile(FileWriter writer) {
		try {
			clean();
			writer.append(""+ArticleNum+',');
			writer.append(site+',');
			writer.append(""+convNum+',');
			writer.append(talkbakist+',');
			writer.append(headline+',');
			writer.append(comment+',');
			writer.append('\n');
			writer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void clean(){
		headline = Funcs.clean(headline);
		talkbakist = Funcs.clean(talkbakist);
		comment = Funcs.clean(comment);
	}


	public static String wireAllComments(ArrayList<CommentRow> cmmts) {
		String allc="";

		for(int i=0; i<cmmts.size(); i++){
			allc+=cmmts.get(i).headline+" : "+cmmts.get(i).comment;
		}

		return allc;
	}


	public static void WriteToFile(ArrayList<CommentRow> cmmts) {
		for(int i=0; i<cmmts.size(); i++){
			cmmts.get(i).WriteToFile();

		}

	}






}
