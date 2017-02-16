
public class Cell {
	private char value = '-';
	private boolean blocked = false;
	private int blockNumber = 0;
	public int getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}

	private boolean[] neighbors = {false, false, false, false};
	
	public Cell(){
		
	}
	
	public Cell(char c){
		value = c;
		
	}
	
	public Cell(Cell c){
		this.value = c.getValue();
		this.blocked = c.getBlocked();
		this.setNorth(c.getNorth());
		this.setEast(c.getEast());
		this.setSouth(c.getSouth());
		this.setWest(c.getWest());
	}
	
	public Cell(char c, boolean north, boolean east, boolean south, boolean west){
		value = c;
		neighbors[0] = north;
		neighbors[1] = east;
		neighbors[2] = south;
		neighbors[3] = west;
		
	}
	
	
	public char getValue(){
		return value;
	}
	
	public void setValue(char value) {
		this.value = value;
	}

	public void setNeighbors(boolean[] neighbors) {
		this.neighbors = neighbors;
	}

	public boolean getBlocked(){
		return blocked;
	}
	
	public boolean[] getNeighbors(){
		return neighbors;
	}
	
	public boolean getNorth(){
		return neighbors[0];
	}
	
	public boolean getEast(){
		return neighbors[1];
	}
	
	public boolean getSouth(){
		return neighbors[2];
	}
	
	public boolean getWest(){
		return neighbors[3];
	}
	
	public void setBlocked(boolean b){
		blocked = b;
	}
	
	public void setNorth(boolean b){
		neighbors[0] = b;
	}
	
	public void setEast(boolean b){
		neighbors[1] = b;
	}
	
	public void setSouth(boolean b){
		neighbors[2] = b;
	}
	
	public void setWest(boolean b){
		neighbors[3] = b;
	}

}

