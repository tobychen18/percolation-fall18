
public class PercolationUF implements IPercolate{
	private boolean [][] myGrid;
	private Integer myOpenCount;
	private IUnionFind myFinder;
	private final int VTOP;
	private final int VBOTTOM;
/**
 * Construct and initialize the NxN grid stored in the instance variable myGrid
 * Initialize instance variables and IUnionFind object of size NxN + 2 that groups cells together
 * @param size is the size of the grid that is size x size
 * @param finder is the IUnionFind object that we will use in order to link cells together 
 */
	public PercolationUF(int size, IUnionFind finder) {
		myGrid = new boolean[size][size]; //initialize size of our grid
		for(int i = 0; i < size; i++) { //set all cells to unopened
			for(int j = 0; j < size; j++) {
				myGrid[i][j] = false;
			}
		}
		finder.initialize(size*size + 2); //initialize our IUnionFind object to have all the cells in our grid plus VTOP and VBOTTOM
		myFinder = finder;
		VTOP = size*size;
		VBOTTOM = size*size+1;
		myOpenCount = 0;
		//initialize instance variables
	}
/**
 * A method to check if the respective cell is in bounds of our grid
 * @param row of the cell we are looking at
 * @param col of the cell we are looking at
 * @return false if outOfBounds or true if inBounds
 */
	private boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) {
			return false; //if the row is less than 0 or bigger than the # of rows in myGrid then it's out of bounds
		}
		if (col < 0 || col >= myGrid.length) {
			return false; //if the col is less than 0 or bigger than the # of columns in myGrid then it's out of bounds
		}
		return true; //if it's not out of bounds then it's in bounds
	}
/**
 * Opens a space that is not already open and links it with adjacent cells, the top, or the bottom when necessary
 * @param row of the cell we are looking at
 * @param col of the cell we are looking at
 */
	@Override
	public void open(int row, int col) {
		//if not in bounds throw an exception
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		//if the cell is not open then make it open/true and add one to my Open Count 
		if(myGrid[row][col] != true) {
			myGrid[row][col] = true;
			myOpenCount++;
		}
		
		//if in the top row, then link it with the top
		if(row == 0) {
			myFinder.union(row*myGrid.length + col, VTOP);
		}
		//if in the bottom row, then link it with the bottom
		if(row == myGrid.length-1) {
			myFinder.union(row*myGrid.length + col, VBOTTOM);
		}
		//Check adjacent cells that are inBounds to see if they are also open and if they are link them together 
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
/**
 * Checks to see if the cell we are looking at is open or not
 * @param row of the cell we are looking at
 * @param col of the cell we are looking at
 * @return false if it is blocked or true if it is open
 */
	@Override
	public boolean isOpen(int row, int col) {
		if (!inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		return myGrid[row][col];
	}
	/**
	 * Checks to see if the cell we are looking at is full or not 
	 * by checking if it is linked with the top, if it is then it's full
	 * @param row of the cell we are looking at
	 * @param col of the cell we are looking at
	 * @return false if it is open or blocked or true if it is full
	 */
	@Override
	public boolean isFull(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		return myFinder.connected(row*myGrid.length + col, VTOP);
	}
/**
 * Checks to see if the water can reach from the top and bottom 
 * @return true of vBottom and vTop are in the same union/connected which means there exists a path where the water can run from top to bottom
 * returns true if it percolates and false if it doesn't 
 */
	@Override
	public boolean percolates() {
		return myFinder.connected(VBOTTOM, VTOP);
	}
/**
 * Find the number of sites that have been opened
 * @returns the number of openSites
 */
	@Override
	public int numberOfOpenSites() {
		return myOpenCount;
	}

}
