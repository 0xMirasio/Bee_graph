package org.insa.graphs.algorithm.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;

public class Robot {

	final static String mapName = "C:\\Users\\titip\\Desktop\\INSA\\graphe\\Bee graph\\belgium.mapgr";
	static GraphReader reader;
	final static List<ArcInspector> filter =  ArcInspectorFactory.getAllFilters();
	static Graph graph; 
	final static ArcInspector mode = filter.get(0);
	static RobotSolution solution = new RobotSolution();
	static Path R1;
	static Path R2;
	
	public static void main(String[] args) throws Exception {
		reader=   new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph = reader.read();
		final Node A = graph.get(296776);
		final Node B = graph.get(674768);
		final Node C = graph.get(896968);
		final Node D = graph.get(990510);
		reader.close();
		RobotSolution solve = doRun(A,B,C,D);
	}
	
	public static Node findCommonNode(Path P1, Path P2) {
		Node res = null;
		for (Arc arc1 : P1.getArcs()) {
			for (Arc arc2 : P2.getArcs()) {
				if (arc1.equals(arc2)) {
					System.out.println("Find a common arc !");
					res = graph.get(arc1.getOrigin().getId());
				}
			}
		}
		return res;
	}
	
	public static Path createPath(Node origine, Node destination) {
		Path res;
		ShortestPathData data= new ShortestPathData(graph, origine, destination, mode);
		ShortestPathAlgorithm algo = new AStarAlgorithm(data);
		ShortestPathSolution sol = algo.run();
		res = sol.getPath();
		return res;
	}
	
	public static RobotSolution doRun(Node A, Node B, Node C, Node D) {
		System.out.println("Starting Algorithm...");
		
		Path P1 = createPath(A,C);
		Path P2 = createPath(A,D);
		
		if (P2.getMinimumTravelTime() > P1.getMinimumTravelTime()) { // si cout(A,D) > cout(A,C)
			
			Path P3 = createPath(C,B);
			Node rencontre = findCommonNode(P2,P3);
			if (rencontre == null) {
				Path PERR1 = solution.setPath(P1, createPath(C,B), graph); // solution R1
				Path PERR2 = createPath(C,D); // solution R2
				solution.setSolR1(PERR1); 
				solution.setSolR2(PERR2);
				return solution;
			}
			else {
				Path POPTI1 = solution.setPath(createPath(A,rencontre), createPath(rencontre, B), graph); //solution R1
				Path POPTI2 = solution.setPath(createPath(C,rencontre), createPath(rencontre, D), graph); //solution R2
				solution.setSolR1(POPTI1); 
				solution.setSolR2(POPTI2);
				return solution;
			}
		}
		
		else {
			
			Path P4 = createPath(D,B);
			Node rencontre = findCommonNode(P1,P4);
			if (rencontre == null) {
				Path PERR1 = solution.setPath(P1, createPath(C,B), graph); // solution R1
				Path PERR2 = createPath(C,D); // solution R2
				solution.setSolR1(PERR1); 
				solution.setSolR2(PERR2);
				return solution;
			}
			else {
				Path POPTI1 = solution.setPath(createPath(A,rencontre), createPath(rencontre, B), graph); //solution R1
				Path POPTI2 = solution.setPath(createPath(C,rencontre), createPath(rencontre, D), graph); //solution R2
				solution.setSolR1(POPTI1); 
				solution.setSolR2(POPTI2);
				return solution;
			}
		}
		
	
		
	}
	
	

}
