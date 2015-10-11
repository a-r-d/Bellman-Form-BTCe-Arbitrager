package ninja.ard.bfcore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	List<IQuoteDataContainer> quotes;
	HashMap<String, CurrencyNode> nodeMap = new HashMap<>(); 	// a node for each currency
	Set<CurrencyEdge> edges = new HashSet<>();					// an edge for each pair.
	
	public BFAlgorithm(List<IQuoteDataContainer> quotes) {
		this.quotes = quotes;
		init();
	}
	
	private void init(){
		nodeMap = new HashMap<>();
		edges = new HashSet<>();
		for(IQuoteDataContainer quote: quotes) {
			// each quote will be an edge and maybe two nodes.
			// the nodes will go into a hashmap so that we can quickly grab them
			CurrencyNode start = null;
			CurrencyNode end = null;
			CurrencyEdge edge = new CurrencyEdge();
			// init start
			if(nodeMap.containsKey(quote.getStartCurrency()))  {
				start = nodeMap.get(quote.getStartCurrency());
			} else {
				start = new CurrencyNode();
				start.currency = quote.getStartCurrency();
				nodeMap.put(quote.getStartCurrency(), start);
			}
			// init end
			if(nodeMap.containsKey(quote.getEndCurrency()))  {
				end = nodeMap.get(quote.getEndCurrency());
			} else {
				end = new CurrencyNode();
				end.currency = quote.getEndCurrency();
				nodeMap.put(quote.getEndCurrency(), end);
			}
			
			// init edges, this is a directed graph so we will only add edges to the start nodes.
			edge.fromNode = start;
			edge.toNode = end;
			edge.quote = quote;
			edge.weight = quote.getLast();
			edges.add(edge);
		}
	}
	
	
	public void bellmanFord(String startNode, String endNode) throws Exception{
		CurrencyNode start = null;
		CurrencyNode end = null;
		for(String nodeKey : nodeMap.keySet()) {
			if(nodeKey.equals(startNode)) {
				start = nodeMap.get(nodeKey);
			}
			if(nodeKey.equals(endNode)) {
				end = nodeMap.get(nodeKey);
			}
		}
		if(start == null || end == null) {
			throw new Exception("One of the nodes provided was not found in the node map");
		}
		bellmanFord(start, end);
	}
	
	public void bellmanFord(CurrencyNode start, CurrencyNode end){
		// start from 0.0
		start.minDistance = 0.0;
		
		for(CurrencyNode node : nodeMap.values()) {
			for(CurrencyEdge edge : edges) {
				
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
		for(CurrencyEdge edge : edges) {
			if(edge.fromNode.minDistance.equals(Double.MAX_VALUE) == false) {
				if(hasCycle(edge)){
					// TODO: do something when there is a negative cycle.
					// this is what we are looking for!
					System.out.println("Negative cycle found...");
				}
			}
		}
		
		// Find shortest path
		if (end.minDistance != Double.MAX_VALUE) {
			System.out.println("There is a shortest path from source to target, with cost: " + end.minDistance);
			
			CurrencyNode actualVertex = end;
			while( actualVertex.previous != null ){
				System.out.print(actualVertex+"-");
				actualVertex = actualVertex.previous;
			}
			
		} else {
			System.out.println("There is no path from source to target...");
		}
	}
	
	private boolean hasCycle(CurrencyEdge edge){
		return edge.toNode.minDistance > edge.fromNode.minDistance + edge.weight;
	}
	
}
