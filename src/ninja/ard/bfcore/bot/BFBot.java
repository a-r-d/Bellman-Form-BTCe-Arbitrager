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
import ninja.ard.bfdata.btce.BtceQuotePuller;

import org.apache.log4j.Logger;
import org.junit.Assert;

public class BFBot {
	final static Logger logger = Logger.getLogger(BFBot.class);
	
	public CurrencyCycle runAlgo() throws Exception{
		String allPairsUrl = "https://btc-e.com/api/3/ticker/btc_usd-btc_rur-btc_eur-eur_rur-usd_rur-eur_usd";
		allPairsUrl += "?cache=" + Math.random();
		
		// BTC, USD, EUR, RUR
		String [] pairsInMaster = {"btc_usd", "btc_rur", "btc_eur", "eur_rur", "usd_rur", "eur_usd"};
		List<String> pairs = Arrays.asList(pairsInMaster);
		Set<String> pairsSet = new HashSet<>(pairs);
		
		BtceQuotePuller puller = new BtceQuotePuller();
		puller.setMasterUrl(allPairsUrl, pairsSet);
		List<IQuoteDataContainer> quotes = puller.getData();
		
		
		CurrencyGraph graph = GraphFactory.buildUndirectedCurrencyGraph(quotes);
		// Add to algorithm
		BFAlgorithm algo = new BFAlgorithm(graph);
		CurrencyCycle cycle = algo.bellmanFord("USD", "BTC");
		
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
			Thread.sleep(2000);
		}
	}
}
