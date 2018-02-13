/**
 *  States of searchs that can be used
 *  <li>{@link #regular}</li>
 *  <li>{@link #headline}</li>
 *  <li>{@link #headline}</li>
 *  <li>{@link #headline}</li>
 */
public enum SearchState {
	/**
	 * take every article in the results.
	 */
	regular,

	/**
	 * take only the article that contain 'textToCompare' string in his headline.
	 */
	headline,

	/**
	 * take only the article that contain 'textToCompare' string in the body of the article.
	 */
	body,

	/**
	 * take only the article that contain 'textToCompare' string in his comments.
	 */
	comment

}
