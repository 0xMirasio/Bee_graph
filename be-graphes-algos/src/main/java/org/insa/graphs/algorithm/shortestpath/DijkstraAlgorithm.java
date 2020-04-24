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
        	
        	for (Arc arc : graph.get(cur.getCurrentNode().getId()).getSuccessors()) {
        		after = lpm[arc.getDestination().getId()];
        		if (after ==null) {
        			after = new label(cur.getCost() + data.getCost(arc) , arc.getDestination(), arc);
        			lpm[arc.getDestination().getId()] = after;
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
        	
        	solution = new ShortestPathSolution(data, Status.OPTIMAL , new Path(graph, solution_arcs));
        }
        
        

        return solution;
    }

}
