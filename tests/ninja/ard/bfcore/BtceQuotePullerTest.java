package ninja.ard.bfcore;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ninja.ard.bfcore.dto.CurrencyGraph;
import ninja.ard.bfcore.tradebeans.CurrencyCycle;
import ninja.ard.bfdata.IQuoteDataContainer;
import ninja.ard.bfdata.btce.BtceQuotePuller;
import ninja.ard.bfdata.btce.MockedBtceQuotePuller;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class BtceQuotePullerTest {
	
	@Test
	public void test_getAllPairs_Mocked_has_cycle() throws Exception {
		MockedBtceQuotePuller puller = new MockedBtceQuotePuller();
		List<IQuoteDataContainer> quotes = puller.getDataMocked(new File("btce_example_data_2.json"));
		
		for(IQuoteDataContainer quote : quotes) {
			Assert.assertNotNull(quote.getCurrencyPairCode());
			Assert.assertNotNull(quote.getLast());
			Assert.assertNotNull(quote.getStartCurrency());
			Assert.assertNotNull(quote.getEndCurrency());
			Assert.assertNotNull(quote.getTimestamp());
		}
		
		CurrencyGraph graph = GraphFactory.buildUndirectedCurrencyGraph(quotes);
		// Add to algorithm
		BFAlgorithm algo = new BFAlgorithm(graph);
		CurrencyCycle cycle = algo.bellmanFord("USD", "BTC");
		Assert.assertNotNull(cycle);
	}
	
	@Ignore
	@Test
	public void test_getAllPairs_Mocked_has_NO_cycle() throws Exception {
		MockedBtceQuotePuller puller = new MockedBtceQuotePuller();
		List<IQuoteDataContainer> quotes = puller.getDataMocked(new File("btce_example_data_1.json"));
		
		for(IQuoteDataContainer quote : quotes) {
			Assert.assertNotNull(quote.getCurrencyPairCode());
			Assert.assertNotNull(quote.getLast());
			Assert.assertNotNull(quote.getStartCurrency());
			Assert.assertNotNull(quote.getEndCurrency());
			Assert.assertNotNull(quote.getTimestamp());
		}
		
		CurrencyGraph graph = GraphFactory.buildUndirectedCurrencyGraph(quotes);
		// Add to algorithm
		BFAlgorithm algo = new BFAlgorithm(graph);
		CurrencyCycle cycle = algo.bellmanFord("USD", "BTC");
		Assert.assertNull(cycle);
	}
	

	
	@Test
	public void test_getAllPairs() throws Exception {
		String allPairsUrl = "https://btc-e.com/api/3/ticker/btc_usd-btc_rur-btc_eur-eur_rur-usd_rur-eur_usd";
		// BTC, USD, EUR, RUR
		String [] pairsInMaster = {"btc_usd", "btc_rur", "btc_eur", "eur_rur", "usd_rur", "eur_usd"};
		List<String> pairs = Arrays.asList(pairsInMaster);
		Set<String> pairsSet = new HashSet<>(pairs);
		
		BtceQuotePuller puller = new BtceQuotePuller();
		puller.setMasterUrl(allPairsUrl, pairsSet);
		
		List<IQuoteDataContainer> quotes = puller.getData();
		
		for(IQuoteDataContainer quote : quotes) {
			Assert.assertNotNull(quote.getCurrencyPairCode());
			Assert.assertNotNull(quote.getLast());
			Assert.assertNotNull(quote.getStartCurrency());
			Assert.assertNotNull(quote.getEndCurrency());
			Assert.assertNotNull(quote.getTimestamp());
		}
		
		
		CurrencyGraph graph = GraphFactory.buildUndirectedCurrencyGraph(quotes);
		// Add to algorithm
		BFAlgorithm algo = new BFAlgorithm(graph);
		algo.bellmanFord("USD", "BTC");
		//algo.bellmanFord("USD", "LTC");
		algo.bellmanFord("USD", "EUR");
		algo.bellmanFord("USD", "RUR");
		 
	}

}
