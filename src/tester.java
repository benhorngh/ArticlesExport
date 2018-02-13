import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class tester {


	@Test
	public void testContinFunc() {
		Site funcs= new Ynet();

		assertTrue(funcs.contain("", ""));

		assertFalse(funcs.contain("", "word"));

		assertTrue(funcs.contain("word", ""));


		String bigText = "big text in here, long big text. is it gonna work? maybe. hope so.";
		String text = "text in";
		assertTrue(funcs.contain(bigText, text));

		text = "text long big";
		assertTrue(funcs.contain(bigText, text));

		text = "text long big nope";
		assertFalse(funcs.contain(bigText, text));

		text = '"'+"is it"+'"';		//"is it"
		assertTrue(funcs.contain(bigText, text));

		text = "text "+'"'+"big text"+'"';		//text "big text"
		assertTrue(funcs.contain(bigText, text));

		text = '"'+"is it"+'"'+" gonna";		//"is it" gonna
		assertTrue(funcs.contain(bigText, text));

		text = '"'+"long text"+'"';				//"long text"
		assertFalse(funcs.contain(bigText, text));

		text = '"'+"gonna work"+'"'+" is text "+'"'+"hope so"+'"';	//"gonna work" is text "hope so"
		assertTrue(funcs.contain(bigText, text));
	}



	@Test
	public void testRemoveDuplicate() {
		Site funcs= new Ynet();
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("http://abcd.co.il");
		urls.add("http://abc.co.il");
		urls.add("https://abc.co.il");
		urls.add("http://abcde.co.il");
		
		funcs.removeDuplicate(urls);
		if(urls.size()!=3)
			fail("nothing remove");
	}

}
