package simpleslickgame;

import java.util.List;
import java.util.ArrayList;

public class EAConnection {
	private BeerAgent ba;
	private ANN an;
	private Board board;
	
	private int numObjects = 0;
	private int iter = 0;
	
	private int contacts = 0;
	private int captures = 0;
	private int bigCaptures = 0;
	
	public EAConnection(){
		int[] hid = {2};
		an = new ANN(hid,0.5,5);
		an.test();
		board = new Board(30, 15, 0, 5, 0);
		ba = new BeerAgent(an,board);
	}
	
	
	public void run(double[] weights){
		an.setWeight(weights);
		restart();
		while(numObjects<40)
			iter();
	}
	
	public void iter(){
		iter++;
		board.iter();
		if(numObjects<40 && (iter>=7 && iter<=14 && Math.random()>0.5)){
			board.addNewObject();
			numObjects++;
			board.updateBoard();
			iter = 0;
		}
		ba.update();
		
		for (FallingObject f : board.getFallingObjects()) {
			if(f.contact(ba.getRenderPosition())){
				contacts++;
			}
			if(f.caught(ba.getRenderPosition(), 0.8)){
				captures++;
				if(f.getWidth()>5)
					bigCaptures++;
			}
		}
	}
	
	public void restart(){
		iter = 0;
		numObjects = 0;
		contacts = 0;
		captures = 0;
		board.clearAll();
	}
	
	public List<int[]> getResults(){
		return null;
	}
	
	public BeerAgent getAgent(){
		return ba;
	}
	
	public Board getBoard(){
		return board;
	}
	
	public int getNumberOfWeightsNeeded(){
		return an.getNumberOfWeightsNeeded();
	}
	
	public int getNumberOfNodesNeeded(){
		return an.getNumberOfNodes();
	}
	
	public int getCaptures(){
		return captures;
	}
	
	public int getContacts(){
		return contacts;
	}
}
