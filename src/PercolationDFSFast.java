
public class PercolationDFSFast extends PercolationDFS {
/**
 * Constructor for a faster version of DFSFast that implements a more efficient version of updateOnOpen
 * The initialization will be the same as it's parent class so we call super
 * the initialization creates a grid where all points are blocked
 * @param n is the size of the grid, which will be n x n
 */
	public PercolationDFSFast(int n) {
		super(n); //uses it's parents constructor
	}
	/**
	 * @see PercolationDFS#updateOnOpen(int, int)
	 * A more efficient version of updateOnOpen that does not clear all cells everytime but instead calls dfs once 
	 * if and only if it's adjacent to a full cell or on the top row and updates
	 * @param row and col will represent the coordinates of the space we are looking at
	 */
	@Override
	protected void updateOnOpen(int row, int col) {
		//if this point is on the top row or adjacent to a full inbound point then call dfs 
		if(row == 0 || (inBounds(row+1, col) && isFull(row+1, col)) || (inBounds(row-1, col) && isFull(row-1, col)) || (inBounds(row, col-1) && isFull(row, col-1))|| (inBounds(row, col+1) && isFull(row, col+1))) {
			dfs(row, col);
		}
	}


}
