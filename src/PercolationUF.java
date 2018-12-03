
public class PercolationUF implements IPercolate{
	private boolean [][] myGrid;
	private Integer myOpenCount;
	private IUnionFind myFinder;
	private final int VTOP;
	private final int VBOTTOM;

	public PercolationUF(int size, IUnionFind finder) {
		myGrid = new boolean[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				myGrid[i][j] = false;
			}
		}
		finder.initialize(size*size + 2);
		myFinder = finder;
		VTOP = size*size;
		VBOTTOM = size*size+1;
		myOpenCount = 0;
	}

	private boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) {
			return false;
		}
		if (col < 0 || col >= myGrid.length) {
			return false;
		}
		return true;
	}

	@Override
	public void open(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		if(myGrid[row][col] != true) {
			myGrid[row][col] = true;
			myOpenCount++;
		}
		if(row == 0) {
			myFinder.union(row*myGrid.length + col, VTOP);
		}
		if(row == myGrid.length-1) {
			myFinder.union(row*myGrid.length + col, VBOTTOM);
		}
		if(inBounds(row+1,col) && isOpen(row+1, col)) {
			myFinder.union(row*myGrid.length + col, (row+1)*myGrid.length + col);
		}
		if(inBounds(row-1,col) && isOpen(row-1, col)) {
			myFinder.union(row*myGrid.length + col, (row-1)*myGrid.length + col);
		}
		if(inBounds(row,col-1) &&isOpen(row, col-1)) {
			myFinder.union(row*myGrid.length + col, row*myGrid.length + (col-1));
		}
		if(inBounds(row,col+1) &&isOpen(row, col+1)) {
			myFinder.union(row*myGrid.length + col, row*myGrid.length + (col+1));
		}

	}

	@Override
	public boolean isOpen(int row, int col) {
		if (!inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		return myGrid[row][col];
	}

	@Override
	public boolean isFull(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		return myFinder.connected(row*myGrid.length, VTOP);
	}

	@Override
	public boolean percolates() {
		return myFinder.connected(VBOTTOM, VTOP);
	}

	@Override
	public int numberOfOpenSites() {
		return myOpenCount;
	}

}
