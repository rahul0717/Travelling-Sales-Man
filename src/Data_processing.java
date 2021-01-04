
public class Data_processing {
	// The Data_processing Module is the important module which processes the
	// data in String form
	// to convert it into appropriate form

	// The function isSymmetric is used to process the data and identify if it's
	// symmetric or asymmetric
	public boolean isSymmetric(String data) {
		String[] splitData = data.split("xxx");
		boolean symmetry = Boolean.parseBoolean(splitData[0]);
		return symmetry;

	}

	// The processSymmetricData function is used to process the symmetric
	// coordinates data and form a matrix.
	public float[][] processSymmetricData(String data) {
		String[] splitData = data.split("xxx");
		int dimension = Integer.parseInt(splitData[1]);
		float[][] coordinates = new float[dimension][2];
		String[] currentCoordinate;
		for (int i = 2; i < splitData.length; i++) {
			if (!splitData[i].contains("EOF")) {
				currentCoordinate = splitData[i].split(" ");
				coordinates[i - 2][0] = Float.parseFloat(currentCoordinate[1]);
				coordinates[i - 2][1] = Float.parseFloat(currentCoordinate[2]);
				// System.out.println(coordinates[i - 2][0] + " " +
				// coordinates[i - 2][1]);
			}
		}
		return coordinates;

	}

	// The findDistance function is used to find the distance between two given
	// coordinates.
	public float findDistance(float p1X, float p1Y, float p2X, float p2Y) {
		float distance = (float) (Math.sqrt(Math.pow((p2X - p1X), 2) + Math.pow((p2Y - p1Y), 2)));
		return distance;

	}

	// The processAsymmetricData function is used to process the asymmetric data
	// and form the N*N matrix, where N = Dimension
	public float[][] processAsymmetricData(String data) {
		String[] splitData = data.split("xxx");
		int dimension = Integer.parseInt(splitData[1]);
		int colCount = 0;
		int rowCount = 0;
		float[][] adjacencyMatrix = new float[dimension][dimension];
		String distanceDataStr = splitData[2];
		String[] distanceData = distanceDataStr.split(" ");
		for (int i = 0; i < distanceData.length - 1; i++) {
			String x = distanceData[i];
			if (x.length() > 0) {
				if (colCount < dimension) {
					adjacencyMatrix[rowCount][colCount] = Float.parseFloat(x);
					colCount++;
				} else {
					rowCount += 1;
					colCount = 0;
					adjacencyMatrix[rowCount][colCount] = Float.parseFloat(x);
					colCount++;
				}
			}

		}
		return adjacencyMatrix;

	}

}
