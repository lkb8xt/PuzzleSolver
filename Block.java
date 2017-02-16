import java.util.ArrayList;


public class Block {
	private ArrayList<Integer> xcoords = new ArrayList<Integer>(); 
	private ArrayList<Integer> ycoords = new ArrayList<Integer>(); 
	private int tag;
	
	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	private int count;
	private Cell[][] cage;
	private ArrayList<Block> rotations = new ArrayList<Block>();
	private int maxLine;
	private int maxChar;
	private int minLine = 1000000;
	private int minChar = 1000000;
	private int blockNumber = 0;
	
	public int getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}

	private boolean placed = false;
	private int pilot;
	
	
	
	

	public boolean isPlaced() {
		return placed;
	}

	public void setPlaced(boolean placed) {
		this.placed = placed;
	}


	public int getPilot() {
		return pilot;
	}

	public void setPilot(int pilot) {
		this.pilot = pilot;
	}

	public Block(Block b){
		this.cage = new Cell[b.getCage().length][b.getCage()[0].length];
		for(int i = 0; i < this.cage.length; i++){
			for(int j = 0; j < this.cage[0].length; j++){
				this.cage[i][j] = new Cell(b.getCage()[i][j]);
			}
		}
		
		for(int k = 0; k < b.getRotations().size(); k++){
			Cell [][] temp = new Cell[b.getRotations().get(k).getCage().length][b.getRotations().get(k).getCage()[0].length];
			this.rotations.add(new Block(temp));
			for(int i = 0; i < b.getRotations().get(k).getCage().length; i++){
				for(int j = 0; j < b.getRotations().get(k).getCage()[0].length; j++){
					this.rotations.get(k).getCage()[i][j] = new Cell(b.rotations.get(k).getCage()[i][j]);
				}
			}
			
		}
		
		
		this.count = b.getCount();
		this.maxChar = b.getMaxChar();
		this.maxLine = b.getMaxLine();
		this.minChar = b.getMinChar();
		this.minLine = b.getMinLine();
		this.pilot = b.getPilot();
		
		
		
	}

	
	public Block(Block b, int x){
		this.cage = new Cell[b.getCage().length][b.getCage()[0].length];
		for(int i = 0; i < this.cage.length; i++){
			for(int j = 0; j < this.cage[0].length; j++){
				this.cage[i][j] = new Cell('-');
			}
		}
		
		
		
		
		this.count = b.getCount();
		this.maxChar = b.getMaxChar();
		this.maxLine = b.getMaxLine();
		this.minChar = b.getMinChar();
		this.minLine = b.getMinLine();
		//this.pilot = b.findPilot();
		
		
	}
	
	public Block(){
		count = 1;
	}
	
	public Block(Cell[][] n){
		this.cage = new Cell[n.length][n[0].length];
		for(int i = 0; i < this.cage.length; i++){
			for(int j = 0; j < this.cage[0].length; j++){
				this.cage[i][j] = new Cell('-');
			}
		}

	}
	
	public int findPilot(){
		
		for(int i = 0; i < this.getCage()[0].length; ++i){
			if((this.getCage()[0][i].getValue() != ' ') &&(this.getCage()[0][i].getValue() != '-')){
				//System.out.println("in pilot");
				this.setPilot(i);
				return i;
			}
		}
		//did not find value on top line - broken
		System.out.println("No value on top line!");
		return -1;
		
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Cell[][] getCage() {
		return cage;
	}
	

	public ArrayList<Block> getRotations() {
		return rotations;
	}

	public void setRotations(ArrayList<Block> rotations) {
		this.rotations = rotations;
	}

	public void setCage(Cell[][] block) {
		this.cage = block;
	}

	public int getMaxLine() {
		return maxLine;
	}

	public void setMaxLine(int maxLine) {
		this.maxLine = maxLine;
	}

	public int getMaxChar() {
		return maxChar;
	}

	public void setMaxChar(int maxChar) {
		this.maxChar = maxChar;
	}

	public int getMinLine() {
		return minLine;
	}

	public void setMinLine(int minLine) {
		this.minLine = minLine;
	}

	public int getMinChar() {
		return minChar;
	}

	public void setMinChar(int minChar) {
		this.minChar = minChar;
	}

	public ArrayList<Integer> getXcoords() {
		return xcoords;
	}

	public void setXcoords(ArrayList<Integer> xcoords) {
		this.xcoords = xcoords;
	}

	public ArrayList<Integer> getYcoords() {
		return ycoords;
	}

	public void setYcoords(ArrayList<Integer> ycoords) {
		this.ycoords = ycoords;
	}

	
	
	public void makeBlockCage(Grid g){
		int x = maxLine - minLine + 1;
		int y = maxChar - minChar + 1;
		cage = new Cell[x][y];
		
		for(int i = 0; i < this.getXcoords().size(); ++i){
			int tempx = this.getXcoords().get(i);
			int tempy  = this.getYcoords().get(i);
			cage[tempx - minLine][tempy - minChar] = new Cell(g.getPlot()[tempx][tempy]);
		}
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				if(cage[i][j] == null) { 
					cage[i][j] = new Cell(' ');
				}
			}
		}
	}
	
	public void storeRotations(){
		this.rotations.add(this);
		for(int i = 0; i < 3; i++){
			this.rotations.add(this.makeRotations(rotations.get(i).getCage()));
			
		}
	}
	
	public Block makeRotations(Cell[][] c){
		Cell[][] rot = new Cell[c[0].length][c.length];
		Block newCage = new Block(rot);
		
		for(int i = 0; i < c.length; i++){
			for(int j = 0; j < c[0].length; j++){
				newCage.getCage()[c[0].length - j - 1][i] = new Cell(c[i][j]);
			}
		}
		//newCage.printCage(newCage.getCage());
		newCage.findPilot();
		return newCage;
	}
	
	public void printCage(){
		for(int i = 0; i < this.getCage().length; i++){
			for(int j = 0; j < this.getCage()[0].length; j++){
				//System.out.print(Character.toString((char) (this.getCage()[i][j].getBlockNumber() + 65)));
				System.out.print(this.getCage()[i][j].getValue());
			}
			System.out.println();
			
		}
		System.out.println();
		
	}
	
	public boolean equals(Block b){
		if(b == null)
			return false;
		if(this.getCage().length != b.getCage().length)
			return false;
		if(this.getCage()[0].length != b.getCage()[0].length)
			return false;
		for( int i = 0; i < this.getCage().length; i++){
			for(int j = 0; j < this.getCage()[0].length; j++){
				if(this.getCage()[i][j].getValue() != b.getCage()[i][j].getValue()){
					return false;
				}
			}
		}
		return true;
	}
}
