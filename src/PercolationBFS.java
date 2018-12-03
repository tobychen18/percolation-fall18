import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast{
	/**
	 * Same as Percolation DFS it's parent class
	 * Initialize a grid so that all cells are blocked.
	 * 
	 * @param n
	 *            is the size of the simulated (square) grid
	 */
	public PercolationBFS(int n) {
		super(n); //uses parent class constructor
	}
	/**
	 * Private helper method to mark all cells that are open and reachable from
	 * (row,col).
	 * 
	 * We will make this a breadth-first-search approach rather than a depth-first-search approach
	 * Instead of using recursion, we use a queue to store full areas and to update the grid
	 * @param row
	 *            is the row coordinate of the cell being checked/marked
	 * @param col
	 *            is the col coordinate of the cell being checked/marked
	 */
	@Override
	protected void dfs(int row, int col) {
		// out of bounds?
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col)); //if it's not in bounds throw an exception
		}
		int size = myGrid.length; //size of our grid to use in future
		int[] rowDelta = {-1,1,0,0}; //change in row
		int[] colDelta = {0,0,-1,1}; //change in column
		Queue<Integer> qp = new LinkedList<>();       //initialize a queue we will use to store full areas/cells
		myGrid[row][col] = FULL;  //update the column that we call dfs to mark it as full
		qp.add(row*size + col); //add this point to our queue
		while (qp.size() != 0){ //as long as our queue still has full values in it aka there are still adjacent cells to check
			Integer p = qp.remove();  //remove a full point which we will check/update adj cells 
			for(int i=0; i < rowDelta.length; i++){ //check adjacent cells
				row = p/size + rowDelta[i];
				col = p%size + colDelta[i];
				if (inBounds(row,col) && myGrid[row][col] == OPEN){ //if adjacent cells are inbounds and open then they should be full now
					qp.add(row*size + col);
					myGrid[row][col] = FULL;
				}
			}
		}
	}
}
