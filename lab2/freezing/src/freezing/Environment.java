package freezing;

import sweep.SimStateSweep;
import sim.util.Bag;
import spaces.Spaces;

public class Environment extends SimStateSweep {
	public int n = 2000;// the number of agents
	public double p = 1.0; // probability of random movement
	public boolean oneAgentperCell = true; // only one agent per cell/location
	public int searchRadius = 2;// radius of search or the view of an agent
	boolean broadRule = true;
	boolean bounded = true;

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public boolean isOneAgentperCell() {
		return oneAgentperCell;
	}

	public void setOneAgentperCell(boolean oneAgentperCell) {
		this.oneAgentperCell = oneAgentperCell;
	}

	public int getSearchRadius() {
		return searchRadius;
	}

	public void setSearchRadius(int searchRadius) {
		this.searchRadius = searchRadius;
	}

	public boolean isBroadRule() {
		return broadRule;
	}

	public void setBroadRule(boolean broadRule) {
		this.broadRule = broadRule;
	}

	public boolean isBounded() {
		return bounded;
	}

	public void setBounded(boolean bounded) {
		this.bounded = bounded;
	}

	public Environment(long seed, Class observer) {
		super(seed, observer);
		// TODO Auto-generated constructor stub
	}
	
	public Bag getObjectsPublic(int x, int y) {
		return (Bag) this.sparseSpace.getObjectsAtLocation(x, y);
	}

	public void makeAgents() {
		if (oneAgentperCell && n > (gridWidth * gridHeight)) {
			System.out.println("Too many agents: " + n);
			return;
		}
		
		int centerx = gridWidth / 2;
		int centery = gridHeight / 2;
		Agent f = new Agent(true, centerx, centery, 0, 0); // dir irrelevant
		schedule.scheduleRepeating(f);
		this.sparseSpace.setObjectLocation(f, centerx, centery);
		// The above is our freezer agent

		for (int i = 1; i < n; i++) {
			int x = random.nextInt(gridWidth);
			int y = random.nextInt(gridHeight);
			if (oneAgentperCell) {
				while (this.sparseSpace.getObjectsAtLocation(x, y) != null) {
					x = random.nextInt(gridWidth);
					y = random.nextInt(gridHeight);
				}
			}
			int dirx = random.nextInt(3) - 1;
			int diry = random.nextInt(3) - 1;
			Agent a = new Agent(x, y, dirx, diry);
			schedule.scheduleRepeating(a);
			this.sparseSpace.setObjectLocation(a, x, y);
		}
	}

	public void start() {
		super.start();
		this.make2DSpace(Spaces.SPARSE, this.gridWidth, this.gridHeight);
		makeAgents();
	}
}
