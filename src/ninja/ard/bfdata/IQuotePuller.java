package ninja.ard.bfdata;

import java.util.List;

public interface IQuotePuller {

	public void setCurrencyPairEndpoint(String pairCode, String url);

	public IQuoteDataContainer getDataForPair(String pairCode) throws Exception;
	
	public List<IQuoteDataContainer> getData() throws Exception;
}
