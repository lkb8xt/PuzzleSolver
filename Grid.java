import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Grid {
	Cell[][] plot;

	Cell[] blockList;
	ArrayList<Block> baggie = new ArrayList<Block>();

	public Grid(String filename) throws FileNotFoundException{
		this.readInFile(filename);
		this.determineNeighbors();
		this.makeBlocks();
		this.makeAllCages();
		this.makeAllRotations();
	}
	
	public Cell[][] getPlot(){
		return this.plot;
	}
	
	public void readInFile(String filename) throws FileNotFoundException{
		Scanner fIn = new Scanner(new File(filename));
		ArrayList<String> ret = new ArrayList<String>();
		int max = 0;
		while(fIn.hasNextLine()){
			String oneLine = fIn.nextLine();
			ret.add(oneLine);
			if(oneLine.length() > max)
				max = oneLine.length();
		}
		int index = 0; 
		plot = new Cell[ret.size()][max];
		for(String line: ret){
			for(int i = 0; i < max; i++){
				if(i<line.length()){
					plot[index][i] = new Cell(line.charAt(i));
				}
				else{
					plot[index][i] = new Cell(' ');
				}
			}
			index++;
		}

	}

	public void determineNeighbors(){
		for(int i = 0; i < this.plot.length; i++){
			for(int j = 0; j < this.plot[0].length; j++){
				if(this.plot[i][j] != null){
					  if(this.plot[i][j].getValue() != ' '){
						//South
						if(i < this.plot.length - 1){
							if(this.plot[i+1][j] != null){
								if(this.plot[i+1][j].getValue() != ' ')
									this.plot[i][j].setSouth(true);
							}
						}
						//North
						if(i != 0 ){
							if(this.plot[i-1][j] != null){
								if(this.plot[i-1][j].getValue() != ' ')
									this.plot[i][j].setNorth(true);
							}	
						}
						
						if(j != 0){
							if(this.plot[i][j-1] != null){
								if(this.plot[i][j-1].getValue() != ' ')
									this.plot[i][j].setWest(true);
							}
						}
						if(j < this.plot[i].length - 1){
							if(this.plot[i][j+1] != null){
								if(this.plot[i][j+1].getValue() != ' ')
									this.plot[i][j].setEast(true);
							}
						}
					  }	
				}
			}
		}
			
	}
	public void connectNeighbors(Cell[][] temp, Block b, int x, int y){
	  if((!temp[x][y].getBlocked())){
		temp[x][y].setBlocked(true);
		b.getXcoords().add(x);
		b.getYcoords().add(y);
		
		if(b.getMinChar() > y){
			b.setMinChar(y);
		}
		if(b.getMaxChar() < y){
			b.setMaxChar(y);

		}
		if(b.getMinLine() > x){
			b.setMinLine(x);
		}
		if(b.getMaxLine() < x){
			b.setMaxLine(x);
		}
		
		if(temp[x][y].getNorth() && x > 0){
			if(!temp[x-1][y].getBlocked()){
				this.connectNeighbors(this.plot, b, x-1, y);
				b.setCount((b.getCount())+1);
				
			}
		}
		
		if(temp[x][y].getSouth() && x < temp.length - 1){
			if(!temp[x+1][y].getBlocked()){
				this.connectNeighbors(this.plot, b, x+1, y);
				b.setCount((b.getCount())+1);
				
			}
		}
		
		if(temp[x][y].getEast() && y < temp[x].length - 1){
			if(!temp[x][y+1].getBlocked()){
				this.connectNeighbors(this.plot, b, x, y+1);
				b.setCount((b.getCount())+1);
				

			}
		}
		if(temp[x][y].getWest() && y > 0){
			if(!temp[x][y-1].getBlocked()){
				this.connectNeighbors(this.plot, b, x, y-1);
				b.setCount((b.getCount())+1);
				
			}
		}
	  }
	}

	public void makeBlocks(){
		for(int i = 0; i < this.plot.length; i++){
			for(int j = 0; j < this.plot[i].length; j++){
				if((!this.plot[i][j].getBlocked()) && (this.plot[i][j].getValue() != ' ')){
					Block b = new Block();
					this.connectNeighbors(this.plot, b, i, j);
					//b.setTag(this.baggie.size() + 1);
					this.baggie.add(b);
				}else{}
			}
		}
	}
	
	public Block findTarget(){
		int max = 0;
		int index = 0;
		for(int i = 0; i < this.baggie.size(); i++){
			if(this.baggie.get(i).getCount() > max){
				max = this.baggie.get(i).getCount();
				index = i;
			}
				
		}	
		this.baggie.get(index).setPlaced(true);
		return this.baggie.get(index);
	}

	public void printGrid(){
		for(int i = 0; i < this.plot.length; i++){
			for(int j = 0; j < this.plot[0].length; j++){
				System.out.print(this.plot[i][j].getValue());
			}
			System.out.println();
			
		}
		System.out.println();
		
	}
	
	public void makeAllCages(){
		for(int i = 0; i<this.baggie.size(); ++i){
			this.baggie.get(i).makeBlockCage(this);
		}	
	}
	
	public void makeAllRotations(){
		for( int i = 0; i < this.baggie.size(); i++){
			//this.baggie.get(i).printCage(this.baggie.get(i).getCage());
			this.baggie.get(i).storeRotations();
			
		}
	}
	
	public ArrayList<Block> getBaggie() {
		return baggie;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		Grid g = new Grid("test2.txt");
		
		
		Puzzleboard p = new Puzzleboard(g);
		
		
		Gui vis = new Gui(g, p, p.getWidth(), p.getDepth());
		
		
		/*
		while(p.tryNextHole()){
			p.printCage();
		}
		*/
		
		
		
		
		
		
		//p.iterate(vis);
		
	
		
		
		
		

	}


	
}
