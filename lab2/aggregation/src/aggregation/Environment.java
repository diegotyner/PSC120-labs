package aggregation;

import spaces.Spaces;
import sweep.SimStateSweep;

public class Environment extends SimStateSweep {
	public int n = 2000; // Agent nums
	public double active = 1.0; // Prob that agent is active
	public double p = 1.0; // Prob of random movement
	public boolean oneAgentperCell = true; // oneAgentperCell
	public int searchRadius = 2; // radius of search/view of agent
	public double aggregate = 0.0; // Prob of aggregation
	

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

	public Environment(long seed, Class observer) {
		super(seed, observer);
		// TODO Auto-generated constructor stub
	}
	
	public void makeAgents() {
		if (oneAgentperCell && n > (gridWidth * gridHeight)) {
			System.out.println("Too many agents: " + n);
		}
		
		for (int i =0; i<n; i++) {
			int x = random.nextInt(gridWidth);
			int y = random.nextInt(gridHeight);
			
			if (oneAgentperCell) {
				while (this.sparseSpace.getObjectsAtLocation(x,y) != null) {
					x = random.nextInt(gridWidth);
					y = random.nextInt(gridHeight);
				} 
			}

			int dirx = random.nextInt(3) - 1; // Generates -1, 0, 1
			int diry = random.nextInt(3) - 1; // Generates -1, 0, 1
			Agent a = new Agent(x, y, dirx, diry);
			schedule.scheduleRepeating(a);
			this.sparseSpace.setObjectLocation(a, x, y);
		}
	}
	
	public void start() {
		super.start();
		this.make2DSpace(Spaces.SPARSE, this.gridHeight, this.gridHeight);
		makeAgents();
	}
		
}
