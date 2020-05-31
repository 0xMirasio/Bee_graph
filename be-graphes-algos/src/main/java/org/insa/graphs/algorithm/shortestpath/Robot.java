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

	final static String mapName = "C:\\Users\\titip\\Desktop\\Bee graph\\belgium.mapgr";
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
		doRun(A,B,C,D);
	}
	
	public static void doRun(Node A, Node B, Node C, Node D) {
		System.out.println("Starting Algorithm...");
		ShortestPathData data1= new ShortestPathData(graph, A, C, mode);
		ShortestPathAlgorithm algo1 = new AStarAlgorithm(data1);
		ShortestPathSolution sol1 = algo1.run();
		
		ShortestPathData data2= new ShortestPathData(graph, A, D, mode);
		ShortestPathAlgorithm algo2 = new AStarAlgorithm(data2);
		ShortestPathSolution sol2 = algo2.run();
		
		Path P1 = sol1.getPath();
		Path P2 = sol2.getPath();
		
		double cout_min = 99999999;
		if (P1.getMinimumTravelTime() > P2.getMinimumTravelTime()) {
			for (Arc SJ : P1.getArcs()) {
				ShortestPathData data_a1_1= new ShortestPathData(graph, C, SJ.getOrigin(), mode);
				ShortestPathAlgorithm algo_a1_1 = new AStarAlgorithm(data_a1_1);
				ShortestPathSolution sol_a1_1 = algo_a1_1.run();
				
				ShortestPathData data_a1_2= new ShortestPathData(graph, SJ.getOrigin(), D, mode);
				ShortestPathAlgorithm algo_a1_2 = new AStarAlgorithm(data_a1_2);
				ShortestPathSolution sol_a1_2 = algo_a1_2.run();
				
				double cost_A1 = sol_a1_1.getPath().getMinimumTravelTime() + sol_a1_2.getPath().getMinimumTravelTime();
				
				ShortestPathData data_a2_1= new ShortestPathData(graph, A, SJ.getOrigin(), mode);
				ShortestPathAlgorithm algo_a2_1 = new AStarAlgorithm(data_a2_1);
				ShortestPathSolution sol_a2_1 = algo_a2_1.run();
				
				ShortestPathData data_a2_2= new ShortestPathData(graph, SJ.getOrigin(), B, mode);
				ShortestPathAlgorithm algo_a2_2 = new AStarAlgorithm(data_a2_2);
				ShortestPathSolution sol_a2_2 = algo_a2_2.run();				
				
				double cost_A2 = sol_a2_1.getPath().getMinimumTravelTime() + sol_a2_2.getPath().getMinimumTravelTime();
				
				if ((cost_A1 + cost_A2)/2 < cout_min) {
					cout_min = (cost_A1 + cost_A2)/2;
					R1 = new Path(graph, solution.setR1Path(sol_a1_1.getPath(), sol_a1_2.getPath()));
					R2 = new Path(graph, solution.setR2Path(sol_a2_1.getPath(), sol_a2_2.getPath()));
				}
				
				
			}
		}
		
		else {
			int i=0;
			System.out.println(P2.getArcs().size());
			for (Arc SJ : P2.getArcs()) {
				
				ShortestPathData data_a1_1= new ShortestPathData(graph, C, SJ.getOrigin(), mode);
				ShortestPathAlgorithm algo_a1_1 = new AStarAlgorithm(data_a1_1);
				ShortestPathSolution sol_a1_1 = algo_a1_1.run();
				ShortestPathData data_a1_2= new ShortestPathData(graph, SJ.getOrigin(), D, mode);
				ShortestPathAlgorithm algo_a1_2 = new AStarAlgorithm(data_a1_2);
				ShortestPathSolution sol_a1_2 = algo_a1_2.run();
				
				double cost_A1 = sol_a1_1.getPath().getMinimumTravelTime() + sol_a1_2.getPath().getMinimumTravelTime();
				
				ShortestPathData data_a2_1= new ShortestPathData(graph, A, SJ.getOrigin(), mode);
				ShortestPathAlgorithm algo_a2_1 = new AStarAlgorithm(data_a2_1);
				ShortestPathSolution sol_a2_1 = algo_a2_1.run();
				ShortestPathData data_a2_2= new ShortestPathData(graph, SJ.getOrigin(), B, mode);
				ShortestPathAlgorithm algo_a2_2 = new AStarAlgorithm(data_a2_2);
				ShortestPathSolution sol_a2_2 = algo_a2_2.run();				
		
				double cost_A2 = sol_a2_1.getPath().getMinimumTravelTime() + sol_a2_2.getPath().getMinimumTravelTime();
				
				if ((cost_A1 + cost_A2)/2 < cout_min) {
					cout_min = (cost_A1 + cost_A2)/2;
					R1 = new Path(graph, solution.setR1Path(sol_a1_1.getPath(), sol_a1_2.getPath()));
					R2 = new Path(graph, solution.setR2Path(sol_a2_1.getPath(), sol_a2_2.getPath()));				}
				i++;
				System.out.println("Finished step : " + i);
			}
			
			System.out.println(R1.getMinimumTravelTime()+":"+R2.getMinimumTravelTime());
			
		}
		
		ShortestPathData data3= new ShortestPathData(graph, C, A, mode);
		ShortestPathAlgorithm algo3 = new AStarAlgorithm(data3);
		ShortestPathSolution sol3 = algo3.run();
		
		ShortestPathData data4= new ShortestPathData(graph, C, B, mode);
		ShortestPathAlgorithm algo4 = new AStarAlgorithm(data4);
		ShortestPathSolution sol4 = algo4.run();
		
		Path P3 = sol1.getPath();
		Path P4 = sol2.getPath();
		
		if (P3.getMinimumTravelTime() > P4.getMinimumTravelTime()) {
			for (Arc SJ : P3.getArcs()) {
				ShortestPathData data_a1_1= new ShortestPathData(graph, C, SJ.getOrigin(), mode);
				ShortestPathAlgorithm algo_a1_1 = new AStarAlgorithm(data_a1_1);
				ShortestPathSolution sol_a1_1 = algo_a1_1.run();
				
				ShortestPathData data_a1_2= new ShortestPathData(graph, SJ.getOrigin(), D, mode);
				ShortestPathAlgorithm algo_a1_2 = new AStarAlgorithm(data_a1_2);
				ShortestPathSolution sol_a1_2 = algo_a1_2.run();
				
				double cost_A1 = sol_a1_1.getPath().getMinimumTravelTime() + sol_a1_2.getPath().getMinimumTravelTime();
				
				ShortestPathData data_a2_1= new ShortestPathData(graph, A, SJ.getOrigin(), mode);
				ShortestPathAlgorithm algo_a2_1 = new AStarAlgorithm(data_a2_1);
				ShortestPathSolution sol_a2_1 = algo_a2_1.run();
				
				ShortestPathData data_a2_2= new ShortestPathData(graph, SJ.getOrigin(), B, mode);
				ShortestPathAlgorithm algo_a2_2 = new AStarAlgorithm(data_a2_2);
				ShortestPathSolution sol_a2_2 = algo_a2_2.run();				
				
				double cost_A2 = sol_a2_1.getPath().getMinimumTravelTime() + sol_a2_2.getPath().getMinimumTravelTime();
				
				if ((cost_A1 + cost_A2)/2 < cout_min) {
					cout_min = (cost_A1 + cost_A2)/2;
					R1 = new Path(graph, solution.setR1Path(sol_a1_1.getPath(), sol_a1_2.getPath()));
					R2 = new Path(graph, solution.setR2Path(sol_a2_1.getPath(), sol_a2_2.getPath()));
				}
				
				
			}
		}
		
		else {
			int i=0;
			for (Arc SJ : P4.getArcs()) {
				
				ShortestPathData data_a1_1= new ShortestPathData(graph, C, SJ.getOrigin(), mode);
				ShortestPathAlgorithm algo_a1_1 = new AStarAlgorithm(data_a1_1);
				ShortestPathSolution sol_a1_1 = algo_a1_1.run();
				ShortestPathData data_a1_2= new ShortestPathData(graph, SJ.getOrigin(), D, mode);
				ShortestPathAlgorithm algo_a1_2 = new AStarAlgorithm(data_a1_2);
				ShortestPathSolution sol_a1_2 = algo_a1_2.run();
				
				double cost_A1 = sol_a1_1.getPath().getMinimumTravelTime() + sol_a1_2.getPath().getMinimumTravelTime();
				
				ShortestPathData data_a2_1= new ShortestPathData(graph, A, SJ.getOrigin(), mode);
				ShortestPathAlgorithm algo_a2_1 = new AStarAlgorithm(data_a2_1);
				ShortestPathSolution sol_a2_1 = algo_a2_1.run();
				ShortestPathData data_a2_2= new ShortestPathData(graph, SJ.getOrigin(), B, mode);
				ShortestPathAlgorithm algo_a2_2 = new AStarAlgorithm(data_a2_2);
				ShortestPathSolution sol_a2_2 = algo_a2_2.run();				
		
				double cost_A2 = sol_a2_1.getPath().getMinimumTravelTime() + sol_a2_2.getPath().getMinimumTravelTime();
				
				if ((cost_A1 + cost_A2)/2 < cout_min) {
					cout_min = (cost_A1 + cost_A2)/2;
					R1 = new Path(graph, solution.setR1Path(sol_a1_1.getPath(), sol_a1_2.getPath()));
					R2 = new Path(graph, solution.setR2Path(sol_a2_1.getPath(), sol_a2_2.getPath()));				}
				i++;
				System.out.println("Finished step : " + i);
			}
			
		}
		
		System.out.println(R1.getMinimumTravelTime()+":"+R2.getMinimumTravelTime());

		
		
		
	}
	
	

}
