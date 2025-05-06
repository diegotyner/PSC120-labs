package flocking;

import sweep.SimStateSweep;
import spaces.Spaces;

public class Environment extends SimStateSweep {
	public int n = 2000;//the number of agents
	public double active = 1.0; //probability that an agent is active
	public double p = 1.0; //probability of random movement
	public boolean oneAgentperCell = true; //only one agent per cell/location
	public int searchRadius = 2;//radius of search or the view of an agent
	public double aggregate = 0.0;//probability of aggregation
	public double flock = 0.0;//probability of flock

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getActive() {
		return active;
	}

	public void setActive(double active) {
		this.active = active;
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

	public double getAggregate() {
		return aggregate;
	}

	public void setAggregate(double aggregate) {
		this.aggregate = aggregate;
	}
	
	public double getFlock() {
		return flock;
	}

	public void setFlock(double flock) {
		this.flock = flock;
	}

	public Environment(long seed, Class observer) {
		super(seed, observer);
		// TODO Auto-generated constructor stub
	}
	
	public void makeAgents() {
		if(oneAgentperCell && n > (gridWidth * gridHeight)) {
			System.out.println("Too many agents: "+n);
			return;
		}
		for(int i=0; i<n;i++) {
			int x = random.nextInt(gridWidth);
			int y = random.nextInt(gridHeight);
			if(oneAgentperCell) {
				while(this.sparseSpace.getObjectsAtLocation(x, y) != null) {
					x = random.nextInt(gridWidth);
					y = random.nextInt(gridHeight);
				}
			}
			int dirx = random.nextInt(3)-1;
			int diry = random.nextInt(3)-1;
			Agent a = new Agent(flock, x,y,dirx,diry);
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
