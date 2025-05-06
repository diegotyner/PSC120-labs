package aggregation;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Agent implements Steppable {
	int x; // x location in space
	int y; // y location in space
	int dirx; // direction of movement along the x axis
	int diry; // direction of movement along the y axis
	
	public Agent(int x, int y, int dirx, int diry) {
		super();
		this.x = x;
		this.y = y;
		this.dirx = dirx;
		this.diry = diry;
	}
	
	public void move (Environment state) {
		if (state.random.nextBoolean(state.active)) {
			if (state.random.nextBoolean(state.p)) {
				dirx = state.random.nextInt(3) - 1;
				diry = state.random.nextInt(3) - 1;
			}
			placeAgent(state);
		}
	}
	public void aggregate (Environment state) {

	}

	public void placeAgent(Environment state) {
		if (state.oneAgentperCell) {
			int tempx = x + dirx;
			int tempy = y + diry;
			if (state.sparseSpace.getObjectsAtLocation(tempx, tempy) == null) {
				x = tempx;
				y = tempy; 
				state.sparseSpace.setObjectLocation(this, x , y);
			}
		} else {
			x = state.sparseSpace.stx(x + dirx);
			y = state.sparseSpace.sty(y + diry);
			state.sparseSpace.setObjectLocation(this, x, y);
		}
	}
	
	@Override
	public void step(SimState state) {
		// TODO Auto-generated method stub
		
		Environment e = (Environment) state;
		if (e.random.nextBoolean(e.aggregate)) {
			aggregate(e);
		} else {
			move(e);
		}

		
		
	}

}
