package flocking;

import java.awt.Color;

import spaces.Spaces;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class FlockingUI extends GUIStateSweep {

	public FlockingUI(SimStateSweep state, int gridWidth, int gridHeight, Color backdrop, Color agentDefaultColor,
			boolean agentPortrayal) {
		super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
		// TODO Auto-generated constructor stub
	}

	public FlockingUI(SimStateSweep state) {
		super(state);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		FlockingUI.initialize(Environment.class, null, FlockingUI.class, 600, 600, Color.WHITE, Color.blue, true, Spaces.SPARSE);

	}

}
