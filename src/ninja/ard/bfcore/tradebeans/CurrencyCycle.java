package ninja.ard.bfcore.tradebeans;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import ninja.ard.bfcore.dto.CurrencyEdge;
import ninja.ard.bfcore.dto.CurrencyNode;

public class CurrencyCycle {
	
	final static Logger logger = Logger.getLogger(CurrencyCycle.class);
	Properties properties;

	double transactionFeePercentage = 0.2; // 0.2% @ btce
	List<CurrencyTrade> trades = new ArrayList<CurrencyTrade>();
	
	public CurrencyCycle() {
		
	}
	
	public CurrencyCycle(Properties props) {
		this.properties = props;
		if(properties.containsKey("pertradefee")) {
			transactionFeePercentage = Double.valueOf(properties.getProperty("pertradefee"));
		}
	}
	
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
	
	// as a percentage, after fees.
	public double getPotentialProfitPercentage(){
		double start = 1.0;
		for(CurrencyTrade t : trades) {
			if(t.getCost().isInverted) {
				start *= 1 / t.getCost().quote.getBid();
			} else {
				// otherwise, use the ASK price.
				start *= t.getCost().quote.getAsk();
			}
		}
		return (start - 1.0) * 100.0;
	}
	
	public double getFeesPercentage(){
		return  transactionFeePercentage * trades.size();
	}
	
	/**
	 * In a percentage
	 * @return
	 */
	public double getPotentialProfitMinusFees() {
		double profit = getPotentialProfitPercentage();
		double fees = getFeesPercentage();
		return profit - fees;
	}
	
	public void logExampleProfit() {
		double profitpercentage = getPotentialProfitMinusFees();
		double example = 100.0 * (1 + (profitpercentage / 100));
		NumberFormat percent = new DecimalFormat("#0.0000");     
		NumberFormat currency = new DecimalFormat("#0.000");
		
		if(profitpercentage > 0) {
			logger.info(percent.format(profitpercentage) + "% profit. If you started with $100.0, you could end up with $" + currency.format(example));
		} else {
			logger.info("trade with value of " + percent.format(getPotentialProfitPercentage()) + 
					"% is not profitable due to fees of " + percent.format(getFeesPercentage()) + "%");
		}
	}
	
	public void logTrades(){
		StringBuilder builder = new StringBuilder();
		// do stuff
		int i = 0;
		double start = 100.0;
		builder.append("[printing trades] (" + trades.size() + "): ");
		for(CurrencyTrade t : trades) {
			i++;
			builder.append("Trade " + i + 
					": from=" + t.getFrom().toString() + 
					" to=" + t.getTo().toString() + 
					" cost=" + t.getCost().quote.getLast());
		}
		logger.info(builder.toString());
	}
	
	public boolean startsAt(CurrencyNode node) {
		if(trades.isEmpty()) {
			return false;
		}
		if(trades.get(0).from.equals(node)) {
			return true;
		}
		return false;
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
