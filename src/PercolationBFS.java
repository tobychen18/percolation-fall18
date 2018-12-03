import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast{

	public PercolationBFS(int n) {
		super(n);
	}
	@Override
	protected void dfs(int row, int col) {
		// out of bounds?
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException("this [row, col] is not in bounds");
		}
		int size = myGrid.length;
		int[] rowDelta = {-1,1,0,0};
		int[] colDelta = {0,0,-1,1};
		Queue<Integer> qp = new LinkedList<>();       
		myGrid[row][col] = FULL; 
		qp.add(row*size + col);
		while (qp.size() != 0){
			Integer k = qp.remove();
			for(int i=0; i < rowDelta.length; i++){
				row = (k-col)/row + rowDelta[k];
				col = (k-row*size) + colDelta[k];
				if (inBounds(row,col) && myGrid[row][col] == OPEN){
					qp.add(row*size + col);
					myGrid[row][col] = FULL;
				}
			}
		}
	}
}
