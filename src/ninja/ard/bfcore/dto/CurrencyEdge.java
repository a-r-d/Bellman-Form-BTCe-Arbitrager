package ninja.ard.bfcore.dto;

import ninja.ard.bfdata.IQuoteDataContainer;

public class CurrencyEdge {

	// Only one edge should exist for each from-to one-way combo.
	public double weight; // the exchange price!
	public boolean isInverted = false; // backward edges have this as true.
	
	public CurrencyNode fromNode;
	public CurrencyNode toNode;
	
	// source quote
	public IQuoteDataContainer quote;
	
	
	@Override
	public int hashCode() {
		return fromNode.getCurrency().hashCode() + toNode.getCurrency().hashCode();
	}
	
	
	public boolean equals(CurrencyEdge otherEdge) {
		return fromNode.getCurrency().equals(otherEdge.fromNode.getCurrency()) && toNode.getCurrency().equals(otherEdge.toNode.getCurrency());
	}
	
	@Override
	public String toString() {
		return "From: " + fromNode.getCurrency() + " to: " + toNode.getCurrency() + " rate: " + weight;
	}
	
}
