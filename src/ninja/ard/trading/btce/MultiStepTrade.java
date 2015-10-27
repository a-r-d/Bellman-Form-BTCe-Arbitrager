package ninja.ard.trading.btce;

public abstract class MultiStepTrade {

	private long totalTimeoutMillis;
	private int numTrades;
	private double thoereticalMaxProfitPercentage;
	private double minProfitabilityAllowablePercentage;
	
	private TradeExecutor onFailExecutor;
	
	
	public boolean onFailAction() {
		return onFailExecutor.makeTrade();
	};
	
	
	public long getTotalTimeoutMillis() {
		return totalTimeoutMillis;
	}
	public void setTotalTimeoutMillis(long totalTimeoutMillis) {
		this.totalTimeoutMillis = totalTimeoutMillis;
	}
	public int getNumTrades() {
		return numTrades;
	}
	public void setNumTrades(int numTrades) {
		this.numTrades = numTrades;
	}
	public double getThoereticalMaxProfitPercentage() {
		return thoereticalMaxProfitPercentage;
	}
	public void setThoereticalMaxProfitPercentage(double thoereticalMaxProfitPercentage) {
		this.thoereticalMaxProfitPercentage = thoereticalMaxProfitPercentage;
	}
	public double getMinProfitabilityAllowablePercentage() {
		return minProfitabilityAllowablePercentage;
	}
	public void setMinProfitabilityAllowablePercentage(double minProfitabilityAllowablePercentage) {
		this.minProfitabilityAllowablePercentage = minProfitabilityAllowablePercentage;
	}
	
	
}
