import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Timer;

public class Gui extends JFrame implements ActionListener {
	Puzzleboard p;
	JFrame frame = new JFrame();
	JButton[][] grid;
	boolean b;
	
	//takes in size of _puzzleboard_. will have to calibrate indices later...
	public Gui(Grid g, Puzzleboard p2, int depth, int width){
		
		this.p = new Puzzleboard(g);
		frame.setSize(500,300);
		frame.setResizable(false);
		//frame.getContentPane().setBackground(Color.BLUE);
		//first = #rows (depth), second = #columns (width)
		frame.setLayout(new GridLayout(depth + 1, width));
		grid = new JButton[width][depth];
		//
		
		
		for(int i = 0; i<width; i++){
			for(int j = 0; j<depth; j++){
				grid[i][j] = new JButton(Character.toString(p2.getCage()[i][j].getValue()));
				grid[i][j].setEnabled(false);
				//grid[i][j].setForeground(Color.RED);
				//grid[i][j].setFont(new Font("sansserif",Font.BOLD,12);
				grid[i][j].setBorderPainted(false);
				grid[i][j].setMargin(new Insets(0, 0, 0, 0));
				grid[i][j].setVerticalAlignment(SwingConstants.TOP);
				grid[i][j].setAlignmentX(frame.TOP_ALIGNMENT);
				grid[i][j].setFocusPainted(false);
				grid[i][j].setContentAreaFilled(false);
				frame.add(grid[i][j]);
			}
		}
		
		JButton Run = new JButton("Find Solution");
		frame.add(Run);
		Run.addActionListener(this);
		
		JButton insert = new JButton("     ");
		frame.add(insert);
		insert.addActionListener(this);
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void iterate(){
		int count = 0;
		while(!this.getP().allPlaced()){
			
			//sleep:
		/*	try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}*/
			//sleep
			
			while(this.getP().tryNextHole()){
				
				
				count++;
				this.updateGui(this.getP(), this.getP().getWidth(), this.getP().getDepth());
				
				
				
				/*//sleeping...
				try {
				    Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				//wake up!
*/				
				//this.printCage();
			}
			//System.out.println(patterns);
			//System.out.println("removing");
			if(!this.getP().allPlaced()){
				this.getP().removeBlock();
			}
			
			
			//this.printCage();
			
			
				
		}
		this.getP().printCage();
		System.out.println("iterations: " + count);
		
	}
	
	
	public void updateGui(Puzzleboard p, int depth, int width){
		for(int i = 0; i<width; i++){
			for(int j = 0; j<depth; j++){
				this.grid[i][j].setText(Character.toString(p.getCage()[i][j].getValue()));
			}
		}
		
		this.frame.repaint();
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String name = e.getActionCommand();
		
		/*if(name.equals("Insert")){
			System.out.println("hi");
			
		}*/
		
		if(name.equals("Find Solution")){
			//System.out.println(p.getBlockNumber());
//			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
//				   @Override
//				   protected Void doInBackground() throws Exception {
//					   //this.iterate();
//				    for (int i = 0; i <= 10; i++) {
//				     Thread.sleep(1000);
//				     System.out.println("Running " + i);
//				    }
//
//				    return null;
//				   }
//				  };
//				  
//				  worker.execute();
//				  
			this.b = true;
			
		}
		
	}

	

	public Puzzleboard getP() {
		return p;
	}


	public void setP(Puzzleboard p) {
		this.p = p;
	}

	
	
	
	
	
	
	

	public static void main(String[] args) throws FileNotFoundException{
		
		Grid g = new Grid("puzzle2.txt");
		
		
		Puzzleboard poozle = new Puzzleboard(g);
		
		
		Gui vis = new Gui(g, poozle, poozle.getWidth(), poozle.getDepth());
		
		while(!vis.isB()){
		System.out.println("Loading Puzzle");	
		}

		
		
		final long startTime = System.currentTimeMillis();
		
		//+++Solver+++
		vis.iterate();
		//++++++++++++
		
		final long endTime = System.currentTimeMillis();
		final long time = endTime - startTime;
		
		System.out.println("time: " + time + " milliseconds." );
		
		
		
	}


	public boolean isB() {
		return b;
	}


	public void setB(boolean b) {
		this.b = b;
	}


}
