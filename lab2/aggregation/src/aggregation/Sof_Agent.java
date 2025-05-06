package freezing_aggregation;

import sim.engine.Steppable;
import sim.field.grid.SparseGrid2D;

public class Agent implements Steppable {

	boolean frozen = false;
	int x;
	int y;
	int dirx;
	int diry;
	
	public Agent(boolean frozen, int x, int y, int dirx, int diry ) {
		this.x = x;
		this.y = y;
		this.dirx = dirx;
		this.diry = diry;
	}
	
	public void placeAgent(Environment state) {
		int tempx;
		int tempy;
		if(state.bounded) {
			if((x + dirx > 0) && (x + dirx < state.gridWidth)) {
				tempx = state.sparseSpace.stx(x + dirx); 
			}
			else { //If we are attempting to move outside the bounds
				
			}
			if(state.sparseSpace.getObjectsAtLocation(tempx, tempy) == null) {
				x = tempx;
				y = tempy;
				state.sparseSpace.setObjectLocation(this, x, y);	
		}
		else {
			tempx = state.sparseSpace.stx(x + dirx);
			tempy = state.sparseSpace.sty(y + diry);
		}
		Bag b  = state.sparseSpace.getMooreNeighbors(x, y, 1, SparseGrid2D.BOUNDED, false);
		if(b == null) {
			x = tempx;
			y=tempy;
			state.sparseSpace.setObjectLocation(this, x, y);
		}
		if(state.broadRule) {
			if(state.bounded)
				b = state.sparseSpace.getMooreNeighbors(x, y, 1, SparseGrid2D.TOROIDAL, false);
			else
				b = state.sparseSpace.getMooreNeighbors(x,y, 1, SparseGrid2D.BOUNDED, false);
			testFrozen(state,b);
		}
		else if(b !=null) {
			testFrozen(state, b);
		}
	}
	
	
	public void move (Environment state) {
		if(!this.frozen && state.random.nextBoolean(state.active)) {
			if(state.random.nextBoolean(state.p)) {
				dirx = state.random.nextInt(3)-1;
				diry = state.random.nextInt(3)-1;
			}
			placeAgent(state);
		}
	}
	
	public void testFrozen(Environment state, bag Neighbors) {
		
	}
	
	public int bx(int x, Environment state) {
		
	}
	public int by(int y, Environment state) {
		
	}
	public void step(SimState state) {
	    if(frozen) return; //nothing else to do
	         move((Environment)state);

}
