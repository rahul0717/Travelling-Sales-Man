import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Data_read {

	public String Readfile(String Filepath) {
		// This the module to read the data from the file specified in the path.
		// Both the symmetrical and the asymmetrical data are handled properly.
		String data = "";
		String dimension = "";
		String currentLine = "";
		String[] tempSplitArray;
		boolean dataStart = false;

		try {
			File myObj = new File(Filepath);
			Scanner myReader = new Scanner(myObj);
			boolean symmetricData = false;
			if (Filepath.contains(".tsp")) {
				symmetricData = true;
			}

			while (myReader.hasNextLine()) {
				currentLine = myReader.nextLine();
				// The string "xxx" is used as an delimiter for the symmetrical
				// Data
				// An empty space is used as an delimiter for the asymmetrical
				// Data

				if (dataStart && symmetricData) {
					data += currentLine + "xxx";
				} else if (dataStart && symmetricData == false) {
					data += currentLine + " ";
				}
				// We use the header information to extract the dimension from
				// the file
				// We also use the header information to identify the start of
				// the actual data
				if (symmetricData == true && currentLine.contains("NODE_COORD_SECTION")) {
					dataStart = true;
				} else if (symmetricData == false && currentLine.contains("EDGE_WEIGHT_SECTION")) {
					dataStart = true;
				}

				if (symmetricData == true && currentLine.contains("DIMENSION")) {
					tempSplitArray = currentLine.split(" ");
					dimension = tempSplitArray[tempSplitArray.length - 1];
					data += Boolean.toString(symmetricData) + "xxx" + dimension + "xxx";
				}
				if (symmetricData == false && currentLine.contains("DIMENSION")) {
					tempSplitArray = currentLine.split(" ");
					dimension = tempSplitArray[tempSplitArray.length - 1];
					data += Boolean.toString(symmetricData) + "xxx" + dimension + "xxx";
				}

			}

//			System.out.println(data);
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		// The data is passed on to the Data Processing Module in the form of a
		// String for processing.
		return data;

	}

}
