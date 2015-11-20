package ninja.ard.bfcore;

import java.util.Properties;

import org.apache.log4j.Logger;

import ninja.ard.bfcore.dto.CurrencyEdge;
import ninja.ard.bfcore.dto.CurrencyGraph;
import ninja.ard.bfcore.dto.CurrencyNode;
import ninja.ard.bfcore.tradebeans.CurrencyCycle;

/**
 * Build with list of quotes which will be converted into a set of
 * CurrencyEdge and CurrencyNode which the algorithm will then run on.
 * 
 * 
 * @author Aaron Decker
 *
 */
public class BFAlgorithm {

	CurrencyGraph graph;
	final static Logger logger = Logger.getLogger(BFAlgorithm.class);
	Properties properties;
	
	public BFAlgorithm(CurrencyGraph graph) {
		this.graph = graph;
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
					CurrencyNode endNode = edge.fromNode;
					CurrencyNode current = endNode;
					
					StringBuilder builder = new StringBuilder();
					builder.append("Negative cycle path: [");
					CurrencyCycle cycle = new CurrencyCycle();
					if(properties != null) {
						cycle = new CurrencyCycle(properties);
					}
					
					int iter = 0;
					while(current.previous != null) {
						current = current.previous;
						builder.append(current.toString() + "-");
						cycle.addTrade(current, current.previous);
						if(cycle.startsAt(current.previous) && iter != 0) {
							builder.append(current.previous.toString() + "]");
							break;
						}
						iter++;
						if(iter > graph.getNodeMap().size()) {
							logger.error("Error: infinite cycle occurred... ");
							break;
						}
					}
					logger.info("Negative cycle found: " + builder.toString());
					return cycle;
				}
			}
		}
		logger.info("Analysis complete, No negative cycles found...");
		
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

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
}
