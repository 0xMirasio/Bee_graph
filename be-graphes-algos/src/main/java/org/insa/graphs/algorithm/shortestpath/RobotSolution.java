package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Path;

public class RobotSolution {
	private Path sol1;
	private Path sol2;
	
	public RobotSolution() {}
	
	public List<Arc> setR1Path(Path p1, Path p2) {
		List<Arc> arcs = new ArrayList<Arc>();
		for (int i=0; i < p1.getArcs().size(); i++) {
			arcs.add(p1.getArcs().get(i));
		}
		for (int i=0; i < p2.getArcs().size(); i++) {
			arcs.add(p2.getArcs().get(i));
		}
		
		return arcs;
	}
	
	public List<Arc> setR2Path(Path p1, Path p2) {
		List<Arc> arcs = new ArrayList<Arc>();
		for (int i=0; i < p1.getArcs().size(); i++) {
			arcs.add(p1.getArcs().get(i));
		}
				
		for (int i=0; i < p2.getArcs().size(); i++) {
			arcs.add(p2.getArcs().get(i));
		}
		
		return arcs;
	}
	
	public Path getR1Path() {
		return this.sol1;
	}
	public Path getR2Path() {
		return this.sol2;
	}

	
}
