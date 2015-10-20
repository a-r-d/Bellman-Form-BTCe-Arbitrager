package ninja.ard.bfcore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ninja.ard.bfcore.bot.BFBot;
import ninja.ard.bfcore.dto.CurrencyEdge;
import ninja.ard.bfcore.dto.CurrencyGraph;
import ninja.ard.bfcore.dto.CurrencyNode;
import ninja.ard.bfcore.tradebeans.CurrencyCycle;
import ninja.ard.bfdata.IQuoteDataContainer;

/**
 * Build with list of quotes which will be converted into a set of
 * CurrencyEdge and CurrencyNode which the algorithm will then run on.
 * 
 * 
 * @author ard
 *
 */
public class BFAlgorithm {

	CurrencyGraph graph;
	final static Logger logger = Logger.getLogger(BFAlgorithm.class);

	
	public BFAlgorithm(CurrencyGraph graph) {
		this.graph = graph;
	}

	public void negativeCycle(String startNode) throws Exception{
		CurrencyNode node = graph.findNode(startNode);
		if(node == null) {
			throw new Exception("One of the nodes provided was not found in the node map");
		}
		negativeCycle(node);
	}
	
	/***
	 * First runs bellman ford, then 
	 * Computes any negative cycles containing the given node
	 * 
	 * @param start
	 */
	public void negativeCycle(CurrencyNode start) {
		
		
	}
	
	public CurrencyCycle bellmanFord(String startNode, String endNode) throws Exception{
		CurrencyNode start = graph.findNode(startNode);
		CurrencyNode end = graph.findNode(endNode);
		
		if(start == null || end == null) {
			throw new Exception("One of the nodes provided was not found in the node map");
		}
		return bellmanFord(start, end);
	}
	
	public CurrencyCycle bellmanFord(CurrencyNode start, CurrencyNode end){
		// start from 0.0
		start.minDistance = 0.0;
		
		for(CurrencyNode node : graph.getNodeMap().values()) {
			for(CurrencyEdge edge : graph.getEdges()) {
				
				if(edge.fromNode.minDistance.equals(Double.MAX_VALUE)) {
					continue;
				}
				Double newDistance = edge.fromNode.minDistance + edge.weight;
				
				if (newDistance < edge.toNode.minDistance) {
					edge.toNode.minDistance = newDistance;
					edge.toNode.previous = edge.fromNode;
				}
			}
		}
		
		// find cycles
		for(CurrencyEdge edge : graph.getEdges()) {
			if(edge.fromNode.minDistance.equals(Double.MAX_VALUE) == false) {
				if(hasCycle(edge)){
					CurrencyNode endNode = edge.toNode;
					CurrencyNode current = endNode;
					
					StringBuilder builder = new StringBuilder();
					builder.append("Negative cycle path: [");
					CurrencyCycle cycle = new CurrencyCycle();
					
					// TODO: why is this going into infinite loops... How is the edge having a cycle 
					// when but the edge.toNode itself is not in the cycle??
					while(current.previous != null) {
						builder.append(current.toString() + "-");
						current = current.previous;
						cycle.addTrade(current, current.previous);
						if(current.equals(endNode)) {
							builder.append(current.toString() + "]");
							break;
						}
					}
					logger.info("Negative cycle found... Breaking: " + builder.toString());
					logger.info(cycle.printTrades());
					return cycle;
				}
			}
		}
		logger.info("No negative cycles found...");
		
		// Find shortest path
		if (end.minDistance != Double.MAX_VALUE) {
			logger.debug("There is a shortest path from source to target, with cost: " + end.minDistance);
			StringBuilder builder = new StringBuilder();
			CurrencyNode actualVertex = end;
			while( actualVertex.previous != null ){
				builder.append(actualVertex+"-");
				actualVertex = actualVertex.previous;
			}
			logger.debug(builder.toString());
			
		} else {
			logger.error("There is no path from source to target...");
		}
		return null; // no cycle
	}
	
	private boolean hasCycle(CurrencyEdge edge){
		return edge.toNode.minDistance > edge.fromNode.minDistance + edge.weight;
	}
	
}
