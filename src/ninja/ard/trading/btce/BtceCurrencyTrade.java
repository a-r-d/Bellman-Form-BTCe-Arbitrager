package ninja.ard.trading.btce;

public class BtceCurrencyTrade extends Trade {

	String baseCurrency;
	String toCurrency;
	String pairCode; // e.g. btc_usd
	String orderType; // buy or sell
	String endpoint;
	
	double targetPrice;
	double maximumDeviationPrice;
	double deviationStep;
	
	// increment as you deviate from the oirignal limit price.
	int currentDeviation = 0;

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	public String getPairCode() {
		return pairCode;
	}

	public void setPairCode(String pairCode) {
		this.pairCode = pairCode;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public double getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(double targetPrice) {
		this.targetPrice = targetPrice;
	}

	public double getMaximumDeviationPrice() {
		return maximumDeviationPrice;
	}

	public void setMaximumDeviationPrice(double maximumDeviationPrice) {
		this.maximumDeviationPrice = maximumDeviationPrice;
	}

	public double getDeviationStep() {
		return deviationStep;
	}

	public void setDeviationStep(double deviationStep) {
		this.deviationStep = deviationStep;
	}

	public int getCurrentDeviation() {
		return currentDeviation;
	}

	public void setCurrentDeviation(int currentDeviation) {
		this.currentDeviation = currentDeviation;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
	// sell btc_usd @ 250 usd
	@Override
	public String toString() {
		return  orderType + " " + pairCode + " @ " + targetPrice + " " + baseCurrency; 
	}
	
}
