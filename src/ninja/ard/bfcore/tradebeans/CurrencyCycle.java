package ninja.ard.bfcore.tradebeans;

import java.util.ArrayList;
import java.util.List;

import ninja.ard.bfcore.dto.CurrencyEdge;
import ninja.ard.bfcore.dto.CurrencyNode;

public class CurrencyCycle {
	
	double transactionFeePercentage = 0.002; // 0.2% 
	List<CurrencyTrade> trades = new ArrayList<CurrencyTrade>();
	
	public void addTrade(CurrencyNode from, CurrencyNode to){
		CurrencyTrade t = new CurrencyTrade(from, findEgdeBetween(from, to), to);
		trades.add(t);
	}
	
	private CurrencyEdge findEgdeBetween(CurrencyNode from, CurrencyNode to) {
		for(CurrencyEdge edge: from.adjacencies) {
			if(edge.toNode.equals(to)){
				return edge;
			}
		}
		return null;
	}
	
	public String printTrades(){
		StringBuilder builder = new StringBuilder();
		// do stuff
		int i = 0;
		double start = 100.0;
		builder.append("[printing trades]");
		for(CurrencyTrade t : trades) {
			i++;
			builder.append("Trade " + i + 
					": from=" + t.getFrom().toString() + 
					" to=" + t.getTo().toString() + 
					" cost=" + t.getCost().quote.getLast());
			
			// If inverted, use the BID
			if(t.getCost().isInverted) {
				start *= 1 / t.getCost().quote.getBid();
			} else {
				// otherwise, use the ASK price.
				start *= t.getCost().quote.getAsk();
			}
			
		}
		double fees = start * transactionFeePercentage * trades.size();
		builder.append("\n example profit: 100--> " + start + " ... fees: " + fees + " ... profit: " + (start - fees - 100.0));
		
		return builder.toString();
	}

	public double getTransactionFeePercentage() {
		return transactionFeePercentage;
	}

	public void setTransactionFeePercentage(double transactionFeePercentage) {
		this.transactionFeePercentage = transactionFeePercentage;
	}

	public List<CurrencyTrade> getTrades() {
		return trades;
	}

	public void setTrades(List<CurrencyTrade> trades) {
		this.trades = trades;
	}
	
	
	
}
