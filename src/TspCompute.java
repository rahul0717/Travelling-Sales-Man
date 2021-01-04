import java.util.concurrent.Callable;

public class TspCompute implements Callable<String>{
	// The TspCompute module is the module which performs the TSP logic on the
	// processed data
	float[][] tsp;
	boolean symmetric;
	public TspCompute(float[][] tspParam, boolean symmetricParam){
		tsp = tspParam;
		symmetric = symmetricParam;
	}
	@Override
	public String call() throws Exception{
		Data_processing processObj = new Data_processing();
		int startNode = 0;
		boolean[] visitedNodes = new boolean[tsp.length];
		float min = Float.MAX_VALUE;
		visitedNodes[0] = true;
		int i = 0;
		int j = 0;
		int[] path = new int[tsp.length];
		int totalNodeVisited = 1;
		int curNode = 0;
		float minCost = 0;
		String result = "";
		int selectedNode = 0;
		float distance = 0;
		// We are using the nearest neighbor method(Greedy) to find the shortest
		// path
		// We will not compute the entire adjacency matrix for the symmetric
		// data for the data is huge, instead will
		// we will compute the distance between two cities each time to save
		// memory
		// The boolean parameter is used to identify the type of data and then do the computation accordingly
		while (i < this.tsp.length && j < this.tsp.length) {
			if (totalNodeVisited >= tsp.length) {
				break;
			}
			if (j != i && visitedNodes[j] != true && this.symmetric) {
				float p1X = this.tsp[i][0];
				float p1Y = this.tsp[i][1];
				float p2X = this.tsp[j][0];
				float p2Y = this.tsp[j][1];
				distance = processObj.findDistance(p1X, p1Y, p2X, p2Y);

				if (distance < min) {
					min = distance;
					path[curNode] = j;

				}
			}
			if (j != i && visitedNodes[j] != true && this.symmetric == false) {
				if (this.tsp[i][j] < min) {
					min = this.tsp[i][j];
					path[curNode] = j;
				}
			}

			j++;
			if (j == this.tsp.length) {
				minCost += min;
				min = Integer.MAX_VALUE;
				selectedNode = path[curNode];
				visitedNodes[selectedNode] = true;
				curNode = selectedNode;
				i = selectedNode;
				j = 0;
				totalNodeVisited++;
			}
		}
		i = selectedNode;
		if (this.symmetric) {
			float p1X = this.tsp[i][0];
			float p1Y = this.tsp[i][1];
			float p2X = this.tsp[startNode][0];
			float p2Y = this.tsp[startNode][1];
			distance = processObj.findDistance(p1X, p1Y, p2X, p2Y);
			min = distance;
		} else {
			min = this.tsp[i][startNode];
		}

		int xx = 0;
		int yy = 0;
		result ="City" + startNode + " -->";
		while (yy < path.length) {
			if(yy == path.length -1){
				result += "City" + path[xx];
			}else{
				result += "City" + path[xx] + "-->";
				xx = path[xx];
			}
			yy++;
			
		}
//		result += "City" + startNode;
		minCost += min;
		result = minCost +"xxx"+ result;
//		System.out.println("\n Minimum Cost for the route:  " + minCost);
		return result;
	}
}
