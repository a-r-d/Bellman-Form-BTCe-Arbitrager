package ninja.ard.bfcore.dto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ninja.ard.bfdata.IQuoteDataContainer;

public class CurrencyGraph {

	List<IQuoteDataContainer> quotes;
	HashMap<String, CurrencyNode> nodeMap = new HashMap<>(); 	// a node for each currency
	Set<CurrencyEdge> edges = new HashSet<>();					// an edge for each pair.
	
	/**
	 * Find a node given a currency code.
	 * E.g. "USD"
	 * 
	 * @param nodeName
	 * @return
	 */
	public CurrencyNode findNode( String nodeName) {
		CurrencyNode node = null;
		for(String nodeKey : nodeMap.keySet()) {
			if(nodeKey.equals(nodeName)) {
				node = nodeMap.get(nodeName);
			}
		}
		return node;
	}
	
	public List<IQuoteDataContainer> getQuotes() {
		return quotes;
	}
	public void setQuotes(List<IQuoteDataContainer> quotes) {
		this.quotes = quotes;
	}
	public HashMap<String, CurrencyNode> getNodeMap() {
		return nodeMap;
	}
	public void setNodeMap(HashMap<String, CurrencyNode> nodeMap) {
		this.nodeMap = nodeMap;
	}
	public Set<CurrencyEdge> getEdges() {
		return edges;
	}
	public void setEdges(Set<CurrencyEdge> edges) {
		this.edges = edges;
	}
	
	
}
