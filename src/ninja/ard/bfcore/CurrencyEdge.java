package ninja.ard.bfcore;

import ninja.ard.bfdata.IQuoteDataContainer;

public class CurrencyEdge {

	// Only one edge should exist for each from-to one-way combo.
	public double weight; // the exchange price!

	public CurrencyNode fromNode;
	public CurrencyNode toNode;
	
	// source quote
	public IQuoteDataContainer quote;
	
	
	@Override
	public int hashCode() {
		return fromNode.currency.hashCode() + toNode.currency.hashCode();
	}
	
	
	public boolean equals(CurrencyEdge otherEdge) {
		return fromNode.currency.equals(otherEdge.fromNode.currency) && toNode.currency.equals(otherEdge.toNode.currency);
	}
	
	@Override
	public String toString() {
		return "From: " + fromNode.currency + " to: " + toNode.currency + " rate: " + weight;
	}
	
}
