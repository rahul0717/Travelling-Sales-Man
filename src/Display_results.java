
public class Display_results {
	// This module can be used as a output source. In future if we want to pass
	// it to another source or store
	// the results in db, we could simply add or alter the code here.
	public void printResults(String resultStr) {
		String[] resultArray = resultStr.split("xxx");
		System.out.println("The minimum cost is " + resultArray[0]);
		 System.out.println("The Shortest Route: \n" + resultArray[1]);
	}

}
