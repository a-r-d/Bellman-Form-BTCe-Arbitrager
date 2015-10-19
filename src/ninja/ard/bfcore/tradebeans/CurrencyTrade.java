package ninja.ard.bfcore.tradebeans;

import ninja.ard.bfcore.dto.CurrencyEdge;
import ninja.ard.bfcore.dto.CurrencyNode;

public class CurrencyTrade {
	
	CurrencyNode from;
	CurrencyEdge cost;
	CurrencyNode to;

	public CurrencyTrade(CurrencyNode from, CurrencyEdge cost, CurrencyNode to) {
		this.from = from;
		this.cost = cost;
		this.to = to;
	}

	public CurrencyNode getFrom() {
		return from;
	}

	public void setFrom(CurrencyNode from) {
		this.from = from;
	}

	public CurrencyEdge getCost() {
		return cost;
	}

	public void setCost(CurrencyEdge cost) {
		this.cost = cost;
	}

	public CurrencyNode getTo() {
		return to;
	}

	public void setTo(CurrencyNode to) {
		this.to = to;
	}
	
	
}
