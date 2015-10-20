package ninja.ard.bfdata;

import org.json.JSONObject;

/**
 * This interface defines a container that can be used 
 * as a data transfer object for a specific timestamped quote 
 * of a currency pair.
 * 
 * Each exchange should implement their own quote class to 
 * expose the data correctly.
 * 
 * A currency pair consists of two currencies, one is first and one is last.
 * E.g. EUR_USD
 * The price will be how much it costs to go from USD --> EURO
 * where the first currency in the pair is the final destination. 
 * 
 * It is important to be able to know what is the start currency and what is the end currency,
 * regardless of the pair codes, howver they may be defined.
 * 
 * 
 * @author ard
 *
 */
public interface IQuoteDataContainer {

	// should record here when data is set
	public void setData(JSONObject jsonData) throws Exception;
	
	public String getCurrencyPairCode();
	
	public String getStartCurrency();
	
	public String getEndCurrency();
	
	public Double getLast();
	
	public Double getBid();
	
	public Double getAsk();
	
	public Long getTimestamp();
	
}
