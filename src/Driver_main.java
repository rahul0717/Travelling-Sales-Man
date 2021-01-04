import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Driver_main {

	public static void main(String[] args) {
		String filePath = "";
		String result;
		long totalTime =0;
		ExecutorService e = null;
		// Give the path to the file containing the data
//		filePath = "/Users/rahul5111/Desktop/www.math.uwaterloo.ca/tsp/world/dj38.tsp";
//		 filePath = "/Users/rahul5111/Desktop/summer2020/rbg443.atsp";
		filePath = args[0];

		// Class Data_read is the module create to read the data from the file
		Data_read readObj = new Data_read();
		String data = readObj.Readfile(filePath);

		// Class Data_processing is the module create to process the data which
		// is read and to create the necessary
		// structure for computation.
		Data_processing processObj = new Data_processing();
		boolean isSymmetricVal = processObj.isSymmetric(data);

		Display_results displayObj = new Display_results();
		// The type of data is identifies internally and flag is passed on to
		// the TSPGreedy function along with the
		// processed data.
		try {
			e = Executors.newSingleThreadExecutor();
			long time = System.currentTimeMillis();
			// SymmetricData Processing
			if (isSymmetricVal) {
				float tspInput[][] = processObj.processSymmetricData(data);
				// Class TspCompute is the module create to compute the shortest
				// path
				// using greedy(Nearest Neighbor method)
				// we are implementing the Callable to make sure our process
				// exits after 300seconds.
				Future<String> future = e.submit(new TspCompute(tspInput, true));
				try {
					while (!future.isDone()) {
						totalTime = System.currentTimeMillis() - time;
						if (totalTime > 300000) {
							System.out.println("Time Exceeded");
							future.cancel(true);
						}
					}
					try {
						result = future.get(300, TimeUnit.SECONDS);
						displayObj.printResults(result);
//						System.out.println("Total time taken to complete: "+totalTime + " milliseconds");
					} catch (TimeoutException e1) {
						e1.printStackTrace();
					}

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			} // AsymmetricData Processing
			else {
				float ajacencyMatrix[][] = processObj.processAsymmetricData(data);
				Future<String> future = e.submit(new TspCompute(ajacencyMatrix, false));
				try {
					while (!future.isDone()) {
						totalTime = System.currentTimeMillis() - time;
						if (totalTime > 300000) {
							System.out.println("Time Exceeded");
							future.cancel(true);
						}
					}
					try {
						result = future.get(300, TimeUnit.SECONDS);
						displayObj.printResults(result);
//						System.out.println("Total time taken to complete: "+totalTime + " milliseconds");
					} catch (TimeoutException e1) {
						e1.printStackTrace();
					}

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

		} catch (ExecutionException ex) {
			ex.printStackTrace();
		} finally {
			e.shutdown();
		}

	}

}