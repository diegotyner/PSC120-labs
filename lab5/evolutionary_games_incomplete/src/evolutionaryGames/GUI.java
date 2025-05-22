package evolutionaryGames;

import java.awt.Color;

import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class GUI extends GUIStateSweep {

	public GUI(SimStateSweep state, int gridWidth, int gridHeight, Color backdrop, Color agentDefaultColor,
			boolean agentPortrayal) {
		super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
	}

	public static void main(String[] args) {
		// Add histogram initialization code here
		String[] title = { "Frequency of each agent" };// A string array, where every entry is the title of a chart
		String[] x = { "Time Steps" };// A string array, where every entry is the x-axis title
		String[] y = { "Frequency" };// A string array, where every entry is the y-axis title
		GUI.initializeArrayHistogramChart(1, title, x, y, new int[10]);
		GUI.initialize(Environment.class, Experimenter.class, GUI.class, 400, 400, Color.WHITE, Color.RED, false,
				spaces.SPARSE);

	}

}
