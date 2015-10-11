package ninja.ard.bfcore;

import java.util.ArrayList;
import java.util.List;

public class CurrencyNode {

	// The identity data.
	public String currency;
	
	// used in algorithm
	public boolean visited;
	public CurrencyNode previous;
	public Double minDistance = Double.MAX_VALUE;
	
	public List<CurrencyEdge> edges = new ArrayList<>();
	
	@Override
	public int hashCode() {
		return currency.hashCode();
	}
	
	@Override
	public String toString() {
		return currency;
	}
}
