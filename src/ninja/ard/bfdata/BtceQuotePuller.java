package ninja.ard.bfdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BtceQuotePuller implements IQuotePuller {
	
	private Long msThrottleBetweenCalls = 500L;
	private Map<String, String> pairs = new HashMap<String, String>();
	private String masterPairUrl = null;
	private List<String> pairsInMaster = null;
	
	private JSONObject getJsonFromURL(String url) throws Exception {
		JsonReader jr = new JsonReader();
		return jr.readJsonFromUrl(url);
	}
	
	public IQuoteDataContainer transformAPIResponse(JSONObject json) {
		// parse + validate json.
		BtceQuote quote = new BtceQuote();
		quote.setData(json);
		return null;
	}

	@Override
	public void setCurrencyPairEndpoint(String pairCode, String url) {
		pairs.put(pairCode, url);
	}
	
	public void setMasterUrl(String url, List<String> pairsAtEndpoint) {
		this.masterPairUrl = url;
		this.pairsInMaster = pairsAtEndpoint;
	}

	@Override
	public IQuoteDataContainer getDataForPair(String pairCode) throws Exception{
		if(pairs.containsKey(pairCode) == false)
			throw new Exception("Unkown currency pair: " + pairCode);
		JSONObject res = getJsonFromURL(pairs.get(pairCode));
		IQuoteDataContainer quote = transformAPIResponse(res);
		return quote;
	}

	@Override
	public List<IQuoteDataContainer> getData() throws Exception{
		List<IQuoteDataContainer> quotes = new ArrayList<IQuoteDataContainer>();
		if(masterPairUrl == null || pairsInMaster == null ) 
			throw new Exception("Master pair list not set");
		JSONObject res = getJsonFromURL(masterPairUrl);
		// look through res, pull out each quote
		for(String pair : pairsInMaster) {
			
		}
		return quotes;
	}

}
