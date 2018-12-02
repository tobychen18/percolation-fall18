
public class PercolationDFSFast extends PercolationDFS {

	public PercolationDFSFast(int n) {
		super(n);
	}
	@Override
	protected void updateOnOpen(int row, int col) {
		if(isFull(row+1, col) || isFull(row-1, col+1) || isFull(row, col-1)) {
			dfs(row, col);
		}
	}


}
