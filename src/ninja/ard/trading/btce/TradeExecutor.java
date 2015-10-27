package ninja.ard.trading.btce;

public class TradeExecutor {

	Trade tradeToMake;
	double totalSizeOfTradeInBaseCurrency;
	double allowableMinSizeOfTradeToFill = -1; // -1 is the entire thing.
	
	
	public boolean makeTrade() {
		return false;
		
	}


	public Trade getTradeToMake() {
		return tradeToMake;
	}


	public void setTradeToMake(Trade tradeToMake) {
		this.tradeToMake = tradeToMake;
	}


	public double getTotalSizeOfTradeInBaseCurrency() {
		return totalSizeOfTradeInBaseCurrency;
	}


	public void setTotalSizeOfTradeInBaseCurrency(double totalSizeOfTradeInBaseCurrency) {
		this.totalSizeOfTradeInBaseCurrency = totalSizeOfTradeInBaseCurrency;
	}


	public double getAllowableMinSizeOfTradeToFill() {
		return allowableMinSizeOfTradeToFill;
	}


	public void setAllowableMinSizeOfTradeToFill(double allowableMinSizeOfTradeToFill) {
		this.allowableMinSizeOfTradeToFill = allowableMinSizeOfTradeToFill;
	}
	
	
	
	
}
