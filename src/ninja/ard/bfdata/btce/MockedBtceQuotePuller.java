package ninja.ard.bfdata.btce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import ninja.ard.bfdata.IQuoteDataContainer;
import ninja.ard.bfdata.IQuotePuller;

public class MockedBtceQuotePuller implements IQuotePuller{

	@Override
	public void setCurrencyPairEndpoint(String pairCode, String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IQuoteDataContainer getDataForPair(String pairCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IQuoteDataContainer> getData() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public List<IQuoteDataContainer> getDataMocked(File file) throws Exception {
		List<IQuoteDataContainer> quotes = new ArrayList<IQuoteDataContainer>();

		// load file as JsonObject...
		BufferedReader rd = new BufferedReader(new FileReader(file));
		String jsonText = readAll(rd);
		JSONObject json = new JSONObject(jsonText);
		
		// convert to quotes
		for(String key : json.keySet()){
			JSONObject quoteData = json.getJSONObject(key);
			BtceQuote quote = new BtceQuote();
			quote.setCurrencyPair(key);
			quote.setData(quoteData);
			
			quotes.add(quote);
			if(quote.getLast() == null) {
				throw new Exception("Quote last set incorrectly set for pair: " + key);
			}
		}
		
		return quotes;
	}
	
	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

}
