package aggregation;

import java.awt.Color;

import spaces.Spaces;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class AggregationUI extends GUIStateSweep {
	
	
	public AggregationUI(SimStateSweep state, int gridWidth, int gridHeight, Color backdrop, Color agentDefaultColor,
			boolean agentPortrayal) {
		super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
		// TODO Auto-generated constructor stub
	}

	// THIS WAS IMPLEMENTED
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AggregationUI.initialize(Environment.class, null, AggregationUI.class, 400, 400, Color.WHITE, Color.darkGray, true, Spaces.SPARSE);
	}

}
