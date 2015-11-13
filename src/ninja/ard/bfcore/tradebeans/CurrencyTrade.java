package ninja.ard.bfcore.tradebeans;

import ninja.ard.bfcore.dto.CurrencyEdge;
import ninja.ard.bfcore.dto.CurrencyNode;

public class CurrencyTrade {
	
	CurrencyNode from;
	CurrencyEdge cost;
	CurrencyNode to;
	
	String pair;
	String orderType;

	public CurrencyTrade(CurrencyNode from, CurrencyEdge cost, CurrencyNode to) {
		this.from = from;
		this.cost = cost;
		this.to = to;
		
		pair = this.cost.quote.getCurrencyPairCode();
		if(this.cost.isInverted) {
			orderType = "sell";
		} else {
			orderType = "buy";
		}
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

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
