package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import java.util.List;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;

public class RobotSolution {

	private Path sol1;
	private Path sol2;
	
	public RobotSolution() {}
	
	public Path setPath(Path p1, Path p2, Graph graph) {
		List<Arc> arcs = new ArrayList<Arc>();
		for (int i=0; i < p1.getArcs().size(); i++) {
			arcs.add(p1.getArcs().get(i));
		}
		for (int i=0; i < p2.getArcs().size(); i++) {
			arcs.add(p2.getArcs().get(i));
		}
		
		return new Path(graph, arcs);
	}
	
	public void setSolR1(Path s1) {
		this.sol1 = s1;
	}
	
	public void setSolR2(Path s2) {
		this.sol2 = s2;
	}
	
	public Path getSolR1() {
		return this.sol1;
	}
	
	public Path getSolR2() {
		return this.sol2;
	}
	
	
	
	
}
