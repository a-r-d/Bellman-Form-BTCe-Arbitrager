package ninja.ard.bfcore;

import java.util.Arrays;
import java.util.List;

import ninja.ard.bfdata.BtceQuotePuller;
import ninja.ard.bfdata.IQuoteDataContainer;

import org.junit.Test;

public class BtceQuotePullerTest {

	@Test
	public void test_getAllPairs() throws Exception {
		String allPairsUrl = "https://btc-e.com/api/3/ticker/btc_usd-btc_rur-btc_eur-eur_rur-usd_rur-eur_usd";
		// BTC, USD, EUR, RUR
		String [] pairsInMaster = {"btc_usd", "btc_rur", "btc_eur", "eur_rur", "usd_rur", "eur_usd"};
		List<String> pairs = Arrays.asList(pairsInMaster);
		
		BtceQuotePuller puller = new BtceQuotePuller();
		puller.setMasterUrl(allPairsUrl, pairs);
		
		List<IQuoteDataContainer> quotes = puller.getData();
		
	}

}
