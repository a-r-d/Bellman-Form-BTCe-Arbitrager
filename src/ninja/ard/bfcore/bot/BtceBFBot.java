package ninja.ard.bfcore.bot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.MaskFormatter;

import ninja.ard.bfcore.BFAlgorithm;
import ninja.ard.bfcore.GraphFactory;
import ninja.ard.bfcore.dto.CurrencyGraph;
import ninja.ard.bfcore.tradebeans.CurrencyCycle;
import ninja.ard.bfdata.IQuoteDataContainer;
import ninja.ard.bfdata.btce.BtceCurrencyProps;
import ninja.ard.bfdata.btce.BtceQuotePuller;
import ninja.ard.trading.btce.BtceMultiStepTradeExecutor;
import ninja.ard.trading.btce.MultiStepCurrencyTrade;

import org.apache.log4j.Logger;
import org.junit.Assert;

public class BtceBFBot {
	final static Logger logger = Logger.getLogger(BtceBFBot.class);
	
	public CurrencyCycle runAlgo() throws Exception{
		String allPairsUrl = "https://btc-e.com/api/3/ticker/btc_usd-btc_rur-btc_eur-eur_rur-usd_rur-eur_usd-ltc_usd-ltc_btc-ltc_eur";
		allPairsUrl += "?cache=" + Math.random();
		
		// BTC, USD, EUR, RUR
		String [] pairsInMaster = {"btc_usd", "btc_rur", "btc_eur", "eur_rur", "usd_rur", "eur_usd", "ltc_usd", "ltc_btc", "ltc_eur"};
		List<String> pairs = Arrays.asList(pairsInMaster);
		Set<String> pairsSet = new HashSet<>(pairs);
		
		BtceQuotePuller puller = new BtceQuotePuller();
		puller.setMasterUrl(allPairsUrl, pairsSet);
		List<IQuoteDataContainer> quotes = puller.getData();
		
		CurrencyGraph graph = GraphFactory.buildUndirectedCurrencyGraph(quotes);
		// Add to algorithm
		BFAlgorithm algo = new BFAlgorithm(graph);
		algo.setProperties(BtceCurrencyProps.props);
		CurrencyCycle cycle = algo.bellmanFord("USD", "BTC");
		if(cycle != null) {
			if(cycle.getPotentialProfitPercentage() > 0) {
				cycle.logTrades();
				cycle.logExampleProfit();
			} else {
				cycle.logExampleProfit();
			}
		}
		
		puller = null;
		algo = null;
		graph = null;
		return cycle;
	}
	
	// This is an example, the implementation is not complete.
	public void runTradeExecutor(CurrencyCycle cycle) throws Exception{
		MultiStepCurrencyTrade trades = new MultiStepCurrencyTrade("usd", 100, cycle);
		BtceMultiStepTradeExecutor executor = new BtceMultiStepTradeExecutor(trades);
		executor.executeTrades();
	}
	
	
	public static void main(String[] args) throws Exception{
		logger.info("Starting bot...");

		while(true) {
			BtceBFBot bot = new BtceBFBot();
			CurrencyCycle cycle = bot.runAlgo();
			if(cycle != null && cycle.getPotentialProfitMinusFees() > 0.2 && cycle.getTrades().size() == 3) {
				logger.error("Profitable trade: " + cycle.getTradesString());
				
				// If you were to run the executor - here is where you would do it.
				//bot.runTradeExecutor(cycle);
			}
			bot = null;
			
			// Btc-e has a 2 second cache.
			Thread.sleep(2000);
		}
	}
}
