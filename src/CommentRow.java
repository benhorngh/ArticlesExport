import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author Ben Horn
 * @since 1/2018
 *
 *This class present one comment
 */
public class CommentRow {

	public int ArticleNum;
	public String site;
	public String talkbakist;
	public String date;
	public String headline;
	public String comment;
	public String convNum;
	public boolean original;


	public CommentRow(){
		ArticleNum=0;
		site="";
		original = true;
		talkbakist="";
		date="";
		headline="";
		comment="";
		convNum="0";
	}


	/**
	 * 
	 * @param site -name of the comment site
	 * @param tkbk -talkbackist name
	 * @param date -comment writen date
	 * @param ttl -title
	 * @param cmmt -body of the comment
	 * @param cmmNum -comment num.
	 * @param org -true if comment is original, false if comment on comment.
	 */
	public CommentRow(String site, String tkbk, String date, String ttl, String cmmt, String cmmNum, boolean org) {
		this.site = site;
		this.talkbakist = tkbk;
		this.date = date;
		this.original = org;
		this.headline = ttl;
		this.comment = cmmt;
		this.convNum = cmmNum;
	}




	/**
	 * write to file one comment
	 */
	public void WriteToFile() {
		XSSFSheet sheet = Main.workbook.getSheet("comments");
		String[] row = {ArticleNum+"", site, convNum+"" , ""+original,talkbakist,date, headline, comment};
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


	/**
	 * 
	 * @param cmmts List with all comments
	 * @return all comments attached
	 */
	public static String wireAllComments(ArrayList<CommentRow> cmmts) {
		String allc="";
		
		if(cmmts == null) return allc;

		for(int i=0; i<cmmts.size(); i++){
			allc+=cmmts.get(i).headline+" . "+cmmts.get(i).comment;
		}
		return allc;
	}
	public static void WriteToFile(ArrayList<CommentRow> cmmts) {
		for(int i=0; i<cmmts.size(); i++){
			cmmts.get(i).WriteToFile();
		}
	}






}
