import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class lmain {

	public static void main(String[] args) {
		String ArticleDate="";
		ArticleDate = "18.2.2018";

		
		
		String dt = ArticleDate;  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, -1);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		

	}

}
