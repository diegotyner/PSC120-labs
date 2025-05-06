	package flocking;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

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

	public Agent() {
		// TODO Auto-generated constructor stub
	}

	public void placeAgent(Environment state) {
		if (state.oneAgentperCell) {
			int tempx = state.sparseSpace.stx(x + dirx);
			int tempy = state.sparseSpace.sty(y + diry);
			if (state.sparseSpace.getObjectsAtLocation(tempx, tempy) == null) {
				x = tempx;
				y = tempy;
				state.sparseSpace.setObjectLocation(this, x, y);
			}
		} else {
			x = state.sparseSpace.stx(x + dirx);
			y = state.sparseSpace.sty(y + diry);
			state.sparseSpace.setObjectLocation(this, x, y);
		}
	}

	public void move(Environment state) {
		if (state.random.nextBoolean(state.active)) {
			if (state.random.nextBoolean(state.p)) {
				dirx = state.random.nextInt(3) - 1;
				diry = state.random.nextInt(3) - 1;
			}
			placeAgent(state);
		}
	}

	public void aggregate(Environment state) {
		if (state.random.nextBoolean(state.active)) {
			Bag neighbors = state.sparseSpace.getMooreNeighbors(x, y, state.searchRadius, state.sparseSpace.TOROIDAL, true);
			dirx = ag_decidex(state, neighbors);
			diry = ag_decidey(state, neighbors);
			placeAgent(state);
		}
	}

	public int ag_decidex(Environment state, Bag neighbors) {
		int posx = 0, negx = 0;
		for(int i = 0;i<neighbors.numObjs;i++) {
			Agent a = (Agent)neighbors.objs[i];
			if(a.x > this.x) {
				posx++;
			}
			else if (a.x < this.x) {
				negx++;
			}
		}
		if(posx > negx) {
			return 1;
		}
		else if(negx > posx) {
			return -1;
		}
		else {
			return state.random.nextInt(3)-1;
		}
	}
	
	public int ag_decidey(Environment state, Bag neighbors) {
		int posy = 0, negy = 0;
		for(int i = 0;i<neighbors.numObjs;i++) {
			Agent a = (Agent)neighbors.objs[i];
			if(a.y > this.y) {
				posy++;
			}
			else if (a.y < this.y) {
				negy++;
			}
		}
		if(posy > negy) {
			return 1;
		}
		else if(negy > posy) {
			return -1;
		}
		else {
			return state.random.nextInt(3)-1;
		}
	}
	
	public int decidey(Environment state, Bag neighbors) {
		int a = 0, b = 0, c = 0; // num neighbors going: -1, 0, 1 respectively
		for (int i = 0; i < neighbors.numObjs; i++) {
			Agent ag = (Agent) neighbors.objs[i];
			int dy = ag.diry;
			if (dy == -1)
				a++;
			else if (dy == 0)
				b++;
			else
				c++;
		}
		// It pains me to do this but java is more of a pain so it wins
		if ((a == b) && (b == c))
			return state.random.nextInt(3) - 1;
		if (a == b)
			if (a > c)
				return state.random.nextInt(2) - 1;
			else
				return 1;
		if (b == c)
			if (b > a)
				return state.random.nextInt(2);
			else
				return 0;
		if (a == c)
			if (a > b)
				if (state.random.nextBoolean(0.5))
					return -1;
				else
					return 1;
			else
				return -1;
		else if ((a > b) && (a > c))
			return -1;
		else if ((b > a) && (b > c))
			return 0;
		else
			return 1;
	}

	public int decidex(Environment state, Bag neighbors) {
		int a = 0, b = 0, c = 0; // num neighbors going: -1, 0, 1 respectively
		for (int i = 0; i < neighbors.numObjs; i++) {
			Agent ag = (Agent) neighbors.objs[i];
			int dx = ag.dirx;
			if (dx == -1)
				a++;
			else if (dx == 0)
				b++;
			else
				c++;
		}
		// It pains me to do this but java is more of a pain so it wins
		if ((a == b) && (b == c))
			return state.random.nextInt(3) - 1;
		if (a == b)
			if (a > c)
				return state.random.nextInt(2) - 1;
			else
				return 1;
		if (b == c)
			if (b > a)
				return state.random.nextInt(2);
			else
				return 0;
		if (a == c)
			if (a > b)
				if (state.random.nextBoolean(0.5))
					return -1;
				else
					return 1;
			else
				return -1;
		else if ((a > b) && (a > c))
			return -1;
		else if ((b > a) && (b > c))
			return 0;
		else
			return 1;
	}

	public void flock(Environment state) {
		if (state.random.nextBoolean(state.active)) {
			Bag neighbors = state.sparseSpace.getMooreNeighbors(x, y, state.searchRadius, state.sparseSpace.TOROIDAL, true);
			dirx = decidex(state, neighbors);
			diry = decidey(state, neighbors);
			placeAgent(state);
		}
	}

	@Override
	public void step(SimState state) {
		Environment e = (Environment) state;
		double randNum = e.random.nextDouble();
		if (randNum <= e.aggregate) {
			aggregate(e);
		}
		else if ( randNum <= (e.aggregate + e.flock) ) { 
			flock(e);
		} else {
			move(e);
		}
	}

}
