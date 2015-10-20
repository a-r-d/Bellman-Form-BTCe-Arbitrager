package ninja.ard.bfdata.btce;

import org.json.JSONObject;

import ninja.ard.bfdata.IQuoteDataContainer;

/**
 * Takes the data from a btce api call for one quote
 * and implments appropriate getters + setters and parsing +
 * type inference of the data from API.
 * 
 * Types should be cast to match the interface signature. 
 * 
 * @author ard
 *
 */
public class BtceQuote implements IQuoteDataContainer {

	JSONObject data;
	String pair;
	String start;
	String end;
	
	Double bid;
	Double ask;
	Double last;
	Long timestamp;
	
	public void setCurrencyPair(String pair) {
		this.pair = pair;
		start = BtceCurrencyProps.props.getProperty(pair + ".start");
		end = BtceCurrencyProps.props.getProperty(pair + ".end");
	}
	
	@Override
	public void setData(JSONObject jsonData) throws Exception{
		this.data = jsonData;
		this.ask = jsonData.getDouble("buy");
		this.bid = jsonData.getDouble("sell");
		this.last = jsonData.getDouble("last");
		this.timestamp = jsonData.getLong("updated");
	}

	@Override
	public String getCurrencyPairCode() {
		return pair;
	}

	@Override
	public Double getLast() {
		return last;
	}

	@Override
	public Long getTimestamp() {
		return this.timestamp;
	}

	@Override
	public Double getBid() {
		return bid;
	}

	@Override
	public Double getAsk() {
		return ask;
	}

	@Override
	public String getStartCurrency() {
		// take the currency pair and return the correct start currency
		// e.g. pair = "BTC_USD"  --> USD
		return start;
	}

	@Override
	public String getEndCurrency() {
		return end;
	}

}
