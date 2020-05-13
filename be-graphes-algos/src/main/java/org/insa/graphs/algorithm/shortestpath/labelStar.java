package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class labelStar extends label {

	private double cout_estime;
	
	public labelStar(double cout, Node sommet_courant, Arc pere, double cout_es) {
		super(cout,sommet_courant,pere);
		this.cout_estime =  cout_es;
	}
	
	@Override
	public double getTotalCost() {
		return this.getCost() + this.cout_estime;
	}
	
	@Override
	public int compareTo(label other) {
		return Double.compare(this.getTotalCost(), other.getTotalCost());
    }
	


}

