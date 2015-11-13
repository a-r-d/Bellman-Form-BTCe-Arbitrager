package ninja.ard.trading.btce;

import org.apache.log4j.Logger;

public class BtceMultiStepTradeExecutor {

	final static Logger logger = Logger.getLogger(BtceMultiStepTradeExecutor.class);

	MultiStepCurrencyTrade multiStepTrade;
	int triesPerTradeMax = 10;
	
	
	
	public BtceMultiStepTradeExecutor(MultiStepCurrencyTrade multiStepCurrencyTrade) {
		this.multiStepTrade = multiStepCurrencyTrade;
	}
	
	
	public boolean executeTrades() throws Exception{
		for(BtceCurrencyTrade trade : multiStepTrade.getTrades()) {
			boolean res = executeTrade(trade);
			if(!res) {
				throw new Exception("Trade of mulitstep trade failed to execute - " + trade.toString());
			}
		}
		return true;
	}
	
	
	
	
	// https://github.com/alexandersjn/btc_e_assist_api
	public boolean executeTrade(BtceCurrencyTrade trade) throws Exception{
		logger.info("Executing trade: " + trade.toString());
		return true;
	}
	
	
	
	
	
	
}
