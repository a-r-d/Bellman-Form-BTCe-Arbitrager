package ninja.ard.bfdata;

import java.util.List;


/**
 * This interface defines a structure for pulling quotes from a given exchange.
 * 
 * There are methods to set endpoints and methods to get quotes from endpoints.
 * 
 * This interface assumes the api endpoint should not require any authentication
 * and that the quote container will do the parsing.
 * 
 * 
 * @author ard
 *
 */
public interface IQuotePuller {

	public void setCurrencyPairEndpoint(String pairCode, String url);

	public IQuoteDataContainer getDataForPair(String pairCode) throws Exception;
	
	public List<IQuoteDataContainer> getData() throws Exception;
}
