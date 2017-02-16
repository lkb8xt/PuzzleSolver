import java.util.ArrayList;
import java.util.Collections;



public class Puzzleboard extends Block{
	private Block target;
	private int depth, width;
	private ArrayList<Block> baggie = new ArrayList<Block>();
	private ArrayList<ArrayList<Integer>> patterns = new ArrayList<>();
	private ArrayList<Integer> currentPattern = new ArrayList<>();
	private int currentBlockNumber = 0;
	public Puzzleboard(Grid g){
		super(g.findTarget(), -1);
		this.depth = this.getCage().length;
		this.width = this.getCage()[0].length;
		this.target = new Block(g.findTarget());
		
		//making tags
		int count = 0;
		for(int i = 0; i < g.getBaggie().size(); i++){
			for(int j = 0; j < 4; ++j){
				g.getBaggie().get(i).getRotations().get(j).setTag(count);
				count++;
			}
			
			
			this.getBaggie().add(g.getBaggie().get(i));
		}
			
	}
	
	public boolean tryBaggie(int x, int y){
		for(int i = 0; i < this.getBaggie().size(); i++){
			for(int j = 0; j < 4; j ++){
				if(!this.getBaggie().get(i).isPlaced() ){
					
					//checking for bad pattern from patterns arraylist:
					ArrayList<Integer> tempPattern = new ArrayList<>(currentPattern);
					
					
					tempPattern.add(this.getBaggie().get(i).getRotations().get(j).getTag());
					
					boolean repeat = false;
					/*System.out.println("current: " + currentPattern);
					System.out.println("comparing " + tempPattern);
					System.out.println(" to " + patterns);*/
					if(this.patterns.contains(tempPattern))
						repeat = true;
					/*for(int k = 0; k < patterns.size(); ++k){
						//System.out.println("temp: " + tempPattern);
						//System.out.println("current pattern: " + patterns);
						if(tempPattern.equals(patterns.get(k))){
							repeat = true;
							break;
						}
					}
					*/
					if(repeat){
						//System.out.println("continue");
						continue;
					}
					
					
					
					if(this.insertBlock(this.getBaggie().get(i).getRotations().get(j), x, y)){
						this.getBaggie().get(i).setPlaced(true);
						int q = this.getBaggie().get(i).getRotations().get(j).getTag();
						currentPattern.add(q);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public ArrayList<Integer> getCurrentPattern() {
		return currentPattern;
	}

	public void setCurrentPattern(ArrayList<Integer> currentPattern) {
		this.currentPattern = currentPattern;
	}

	public ArrayList<Block> getBaggie() {
		return baggie;
	}

	public void setBaggie(ArrayList<Block> baggie) {
		this.baggie = baggie;
	}
		
	public boolean allPlaced(){
		for (int i = 0; i<this.baggie.size(); ++i){
			if(!this.baggie.get(i).isPlaced()){
				//System.out.println("all placed no");
				return false;
			}
		}
		return true;
	}
	

	public boolean tryNextHole(){
		
		for(int i = 0; i < this.getCage().length; i++){
			for(int j = 0; j < this.getCage()[0].length; j++){
				//this.printCage();
				if(this.getCage()[i][j].getValue() == ' ' || this.getCage()[i][j].getValue() == '-'){
					//System.out.println(this.currentPattern);
					if(this.tryBaggie(i, j)){
						//System.out.println("at indices (" + i + "), ("+j+")");
						return true;
					}
				
					else{
						return false;
					}
					
				 }
				}
			}


		return false;
		
	}
	
	
	//Method not used
	public void recurse(Block b){
		//base case check
		boolean empty = true;
		for(int i = 0; i < this.getBaggie().size(); i++){
			if(!this.getBaggie().get(i).isPlaced()){
				empty = false;
			}
		}
		//base case
		if(empty){
			System.out.println("puzzle solved!");
			return;
		}
		
		else {
			if(this.tryNextHole()){
				this.printCage();
				System.out.println("recursing...");
				this.recurse(null);
			}
				
			else{
				
				this.printCage();
				System.out.println("block will be removed. recursing...");
				this.recurse(this.removeBlock());
			}
				
		}
		
	}
	
	
	
	public boolean insertBlock(Block b, int holeX, int holeY){
		//getting top row, left non-blank value
		
			//System.out.println(b.findPilot());
			int offsetX = holeX;
			int offsetY = holeY - b.findPilot();
			//checking cage of block
			for(int i = 0; i<b.getCage().length; i++){
				for(int j = 0; j<b.getCage()[0].length; j++){
					//out of bounds check
					if((i+offsetX >= this.depth)||(j+offsetY >= this.width)||(j+offsetY<0) || (i+offsetX < 0)){
						return false;
					}
					//overlap check
					char v = this.getCage()[i+offsetX][j+offsetY].getValue();
					if((b.getCage()[i][j].getValue() != ' ')&&((v != (' ')) && (v != ('-')))){
						return false;
					}
					
					if((b.getCage()[i][j].getValue() != this.target.getCage()[i+offsetX][j+offsetY].getValue()) && (b.getCage()[i][j].getValue() != ' ') ){
						return false;
					}
				
				
				}
			}
			
			
			currentBlockNumber++;
			
			for(int i = 0; i<b.getCage().length; i++){
				for(int j = 0; j<b.getCage()[0].length; j++){
					if(b.getCage()[i][j].getValue() != ' '){
						this.getCage()[i+offsetX][j+offsetY].setValue(b.getCage()[i][j].getValue());
						this.getCage()[i+offsetX][j+offsetY].setBlockNumber(currentBlockNumber);
						//System.out.println("inserting element!");
					}
				}	
			}
			
			b.setBlockNumber(currentBlockNumber);
			return true;
		
		

	}

	public Block getTarget() {
		return target;
	}

	public void setTarget(Block target) {
		this.target = target;
	}

	public Block removeBlock(){
		//System.out.println("++++++++++++");
		//System.out.println("current Pattern? " + currentPattern);
	
		if(!this.patterns.contains(this.currentPattern)){
			ArrayList<Integer> tempA = new ArrayList(currentPattern);
			//System.out.println("adding " + currentPattern + " to " + patterns);
			this.patterns.add(tempA);
			//System.out.println("updated patterns: " + this.patterns);
		}
		if(currentPattern.size() != 0){
			currentPattern.remove(currentPattern.size()-1);
		}
		
		

		
		Block temp;
		for(int i = 0; i < depth; i++){
			for(int j = 0; j < width; j++){
				if(this.getCage()[i][j].getBlockNumber() == currentBlockNumber){
					this.getCage()[i][j].setValue('-');
					this.getCage()[i][j].setBlockNumber(0);
				}
			}
		}
		for(int i = 0; i < this.getBaggie().size(); i++){
			for(int j = 0; j < 4; j++){
				if(this.getBaggie().get(i).getRotations().get(j).getBlockNumber() == currentBlockNumber){
					this.getBaggie().get(i).getRotations().get(j).setBlockNumber(0);
					temp = new Block(this.getBaggie().get(i).getRotations().get(j));
					this.getBaggie().get(i).setPlaced(false);
					currentBlockNumber--;
					return temp;
				}
			}
		}
		return null;
	}

	public int getDepth() {
		return depth;
	}


	public void setDepth(int depth) {
		this.depth = depth;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}
	

	
/*	
	public void iterate(Gui g){
		int count = 0;
		while(!this.allPlaced()){
			
			//sleep:
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			//sleep
			
			while(this.tryNextHole()){
				
				System.out.println("iteration" + count);
				count++;
				g.updateGui(this, this.getWidth(), this.getDepth());
				this.printCage();
				
				
				//sleeping...
				try {
				    Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				//wake up!
				
				//this.printCage();
			}
			//System.out.println(patterns);
			//System.out.println("removing");
			if(!this.allPlaced()){
				this.removeBlock();
			}
			
			
			//this.printCage();
			
			
				
		}
		this.printCage();
		System.out.println("yea we solved it bitch");
	}
	
	*/
	
	


}
