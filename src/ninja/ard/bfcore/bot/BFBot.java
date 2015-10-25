package ninja.ard.bfcore.bot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ninja.ard.bfcore.BFAlgorithm;
import ninja.ard.bfcore.GraphFactory;
import ninja.ard.bfcore.dto.CurrencyGraph;
import ninja.ard.bfcore.tradebeans.CurrencyCycle;
import ninja.ard.bfdata.IQuoteDataContainer;
import ninja.ard.bfdata.btce.BtceCurrencyProps;
import ninja.ard.bfdata.btce.BtceQuotePuller;

import org.apache.log4j.Logger;
import org.junit.Assert;

public class BFBot {
	final static Logger logger = Logger.getLogger(BFBot.class);
	
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
	
	
	public static void main(String[] args) throws Exception{
		logger.info("Starting bot...");

		while(true) {
			BFBot bot = new BFBot();
			bot.runAlgo();
			bot = null;
			
			// Btc-e has a 2 second cache.
			Thread.sleep(2000);
		}
	}
}
