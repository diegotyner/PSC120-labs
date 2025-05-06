package freeze_chart;

import freeze_chart.Agent;
import freeze_chart.Environment;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

public class Agent implements Steppable {
	boolean frozen;
	int x; // x location in space
	int y; // y location in space
	int dirx; // direction of movement along the x axis
	int diry; // direction of movement along the y axis

	public Agent(boolean frozen, int x, int y, int dirx, int diry) {
		super();
		this.frozen = frozen;
		this.x = x;
		this.y = y;
		this.dirx = dirx;
		this.diry = diry;
	}

	public Agent(int x, int y, int dirx, int diry) {
		super();
		this.x = x;
		this.y = y;
		this.dirx = dirx;
		this.diry = diry;
	}

	public Agent() {
		// TODO Auto-generated constructor stub
	}

	public void placeAgent(Environment state) {
		int tempx;
		int tempy;
		tempx = state.sparseSpace.stx(x + dirx);
		tempy = state.sparseSpace.sty(y + diry);
		if (state.bounded) {
			if (tempx != x + dirx || tempy != y + diry) {
				tempx = x; // We failed our check. Bounded, and would've moved out of bounds
				tempy = y;
			}
		}
		Bag b = (Bag) state.getObjectsPublic(tempx, tempy);
		if (b == null) {
			x = tempx;
			y = tempy;
			state.sparseSpace.setObjectLocation(this, x, y);
		}
		if (state.broadRule) {
			if (state.bounded)
				b = (Bag) state.sparseSpace.getMooreNeighbors(x, y, 1, state.sparseSpace.BOUNDED, false);
			else
				b = (Bag) state.sparseSpace.getMooreNeighbors(x, y, 1, state.sparseSpace.TOROIDAL, false);
			testFrozen(state, b);
		} else if (b != null) {
			testFrozen(state, b);
		}
	}

	public void move(Environment state) {
		if (!this.frozen && state.random.nextBoolean(state.p)) {
			if (state.random.nextBoolean(state.p)) {
				dirx = state.random.nextInt(3) - 1;
				diry = state.random.nextInt(3) - 1;
			}
			placeAgent(state);
		}
	}

	public void testFrozen(Environment state, Bag neighbors) {
		for (int i = 0; i < neighbors.numObjs; i++) {
			Agent a = (Agent) neighbors.objs[i];
			if (a.isFrozen()) {
				this.frozen = true;
				break;
			}
		}
	}

	// public int bx(int x, Environment state) {
	//
	// }
	// public int by(int y, Environment state) {
	//
	// }

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	@Override
	public void step(SimState state) {
		if (frozen)
			return; // nothing else to do
		move((Environment) state);
	}

}

