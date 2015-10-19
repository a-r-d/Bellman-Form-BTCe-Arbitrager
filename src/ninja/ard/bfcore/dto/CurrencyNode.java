package ninja.ard.bfcore.dto;

import java.util.ArrayList;
import java.util.List;

public class CurrencyNode {

	// The identity data.
	public String currency;
	
	// used in algorithm
	public boolean visited;
	public CurrencyNode previous;
	public Double minDistance = Double.MAX_VALUE;
	
	public List<CurrencyEdge> adjacencies = new ArrayList<>();
	
	public List<CurrencyNode> getNeighbors(){
		List<CurrencyNode> nodes = new ArrayList<CurrencyNode>();
		for(CurrencyEdge edge : adjacencies) {
			edge.toNode.previous = this;
			nodes.add(edge.toNode);
		}
		return nodes;
	}
	
	@Override
	public int hashCode() {
		return currency.hashCode();
	}
	
	@Override
	public String toString() {
		return currency;
	}
}
