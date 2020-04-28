package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class labelStar extends label implements Comparable<label> {

	private double cout_estime;
	
	public labelStar(double cout, Node sommet_courant, Arc pere) {
		super(cout,sommet_courant,pere);
		this.cout_estime = cout_estime;
	}
	
	public double giveCoutEstime() {
		return this.cout_estime;
	}
	
	@Override
	public double getTotalCost() {
		return this.getCost() + this.cout_estime;
	}
	
	public void actualiseCoutEstime(double value) {
		this.cout_estime = value;
	}


}

