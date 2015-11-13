package ninja.ard.trading.btce;

import java.util.ArrayList;
import java.util.List;

import ninja.ard.bfcore.tradebeans.CurrencyCycle;
import ninja.ard.bfcore.tradebeans.CurrencyTrade;

public class MultiStepCurrencyTrade extends MultiStepTrade{

	private CurrencyCycle currencyCycle;
	private String baseCurrency;
	private double baseCurrencyOrderSize;
	private List<BtceCurrencyTrade> trades;
	
	public MultiStepCurrencyTrade(String baseCurrency, double baseCurrencyOrderSize, CurrencyCycle cycle) {
		this.currencyCycle = cycle;
		this.baseCurrency = baseCurrency;
		this.baseCurrencyOrderSize = baseCurrencyOrderSize;
		this.setNumTrades(cycle.getTrades().size());
	}
	
	public void init() throws Exception {
		setupSteps();
		setDefaultValues();
	}
	
	private void setDefaultValues() {
		this.setTotalTimeoutMillis(10000);
		this.setMinProfitabilityAllowablePercentage(0.25); // .25 % for btce
		// calculate from all the trade steps.
		this.setThoereticalMaxProfitPercentage(this.currencyCycle.getPotentialProfitMinusFees());
		
	}
	
	private void setupSteps() throws Exception{
		trades = new ArrayList<>();
		
		// currency cycle has to start with the base
		if(!currencyCycle.getTrades().get(0).getFrom().getCurrency().equals(baseCurrency)) {
			throw new Exception("Initial step is not the base currency");
		} 
		
		for(CurrencyTrade t: currencyCycle.getTrades()) {
			BtceCurrencyTrade trade = buildTradeData(t);
			trades.add(trade);
		}
		
	}
	
	
	/*
	 * Trade Parameters:
		Parameter	description	assumes value
		pair	pair	btc_usd (example)
		type	order type	buy or sell
		rate	the rate at which you need to buy/sell	numerical
		amount	the amount you need to buy / sell	numerical
	 */
	
	// TODO: build in the code to find the enpoints and the allowable percentages.
	/**
	 * This converts trade data to everything we need to do a trade!
	 * 
	 * @param rawTradeData
	 * @return
	 */
	private BtceCurrencyTrade buildTradeData(CurrencyTrade rawTradeData) {
		BtceCurrencyTrade trade = new BtceCurrencyTrade();
		
		// code for finding pair
		// type - buy or sell
		// rate - price
		// amount - numerical amount in correct units.
		trade.setBaseCurrency(rawTradeData.getFrom().getCurrency());
		trade.setPairCode(rawTradeData.getPair());
		trade.setOrderType(rawTradeData.getOrderType());
		trade.setTargetPrice(rawTradeData.getCost().weight);
		
		return trade;
	}

	public CurrencyCycle getCurrencyCycle() {
		return currencyCycle;
	}

	public void setCurrencyCycle(CurrencyCycle currencyCycle) {
		this.currencyCycle = currencyCycle;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public double getBaseCurrencyOrderSize() {
		return baseCurrencyOrderSize;
	}

	public void setBaseCurrencyOrderSize(double baseCurrencyOrderSize) {
		this.baseCurrencyOrderSize = baseCurrencyOrderSize;
	}

	public List<BtceCurrencyTrade> getTrades() {
		return trades;
	}

	public void setTrades(List<BtceCurrencyTrade> trades) {
		this.trades = trades;
	}
	
	
	
	
	
}
