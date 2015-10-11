package ninja.ard.bfcore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import ninja.ard.bfdata.IQuoteDataContainer;
import ninja.ard.bfdata.btce.BtceQuotePuller;

public class BtceQuotePullerTest {

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
		
		
		// Add to algorithm
		BFAlgorithm algo = new BFAlgorithm(quotes);
		algo.bellmanFord("USD", "USD");
		
		 
	}

}
