package de.broo.test.ebay;

public class suggest_test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Query = "Herrenbekleidung G-Star";
		ebay_get_suggested suggested = new ebay_get_suggested();
		suggested.ebayGetSuggeestedCatId(Query);

	}

}
