package org.insa.graphs.algorithm.shortestpath;

import java.util.*;


import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Graph;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.PriorityQueue;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	 // Retrieve the graph.
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        ShortestPathSolution solution = null;
        
        // constant
        final int nbNode = graph.size();
        Node base = data.getOrigin();
        PriorityQueue<label>labheap = new BinaryHeap<>();
        label lpm[] = new label[nbNode];
        label cur,after;
        notifyOriginProcessed(data.getOrigin());
        ArrayList<Double> cout_ancien = new ArrayList<>();  //pour faire des test
         
        // algorithm
        
        for (int i=0; i< lpm.length ; i++ ) {
        	lpm[i] = null;
        }
        
        
        lpm[base.getId()] = new label(0,base,null);
        labheap.insert(lpm[base.getId()]);
        
        // main
        while(!labheap.isEmpty() && (lpm[data.getDestination().getId()] == null || !lpm[data.getDestination().getId()].hasFinish())) {
        	cur = labheap.findMin();
        	labheap.remove(cur);
        	cur.actualiseMarque(true);
        	
        	for (Arc arc : cur.getCurrentNode().getSuccessors()) {
        		after = lpm[arc.getDestination().getId()];
        		if (after ==null) {
        			after = new label(cur.getCost() + data.getCost(arc) , arc.getDestination(), arc);
        			cout_ancien.add(after.getCost()); // on sauvegarde le cout des labels pour des test.
        			lpm[arc.getDestination().getId()] = after;
        			notifyNodeReached(arc.getDestination());
        			labheap.insert(after);
        		}
        		else {
        			if (after.getCost() > cur.getCost() + data.getCost(arc)) {
        				after.ActualiseCost(cur.getCost() + data.getCost(arc));
        				after.ActualisePapa(arc);
        			}
        			        	
        		}
        	
        	}
        }
        
        if (lpm[data.getDestination().getId()] == null) {
    		solution = new ShortestPathSolution(data, Status.INFEASIBLE);
    	}
        else {
        	ArrayList<Arc> solution_arcs= new ArrayList<>();
        	Arc arc = lpm[data.getDestination().getId()].givePere();
        	while (arc !=null) {
        		solution_arcs.add(arc);
        		arc = lpm[arc.getOrigin().getId()].givePere();
        	}
        	Collections.reverse(solution_arcs);
            notifyDestinationReached(data.getDestination());
        	Path newPath = new Path(graph, solution_arcs);
        	solution = new ShortestPathSolution(data, Status.OPTIMAL ,newPath);
        	
        	
        	// TEST Section --------------------------------------------------------
        	
        	// le cout des labels est-il croissant ? 
        	double min = cout_ancien.get(0);
        	int err=0;
        	for (int i=0; i<cout_ancien.size()-1; i++) {
        		
        		if( cout_ancien.get(i) < min) {
        			err= 1;
        		}
        	}
        	if (err == 0) {
        		System.out.println("Label croissant!");	
        	}
        	else {
        		System.out.println("Label non Croissant !");
        	}
        	
        	
        	// le chemin est valide? 
            if(newPath.isValid()) {
            	System.out.println("Path valide!");	
            }
            else {
            	System.out.println("Path invalide!");
            }
            
            // le cout est-il valide ?
            double lenght = newPath.getLength();
            if (Math.round(lpm[data.getDestination().getId()].getCost()) == Math.round(lenght)) {
            	System.out.println("Cout valide!");	
            }
            else {
            	System.out.println("Cout invalide!" + Math.round(lenght) + "=!"+Math.round(lpm[data.getDestination().getId()].getCost()));	
            }
        
        }
        
       
        

        return solution;
    }

}
