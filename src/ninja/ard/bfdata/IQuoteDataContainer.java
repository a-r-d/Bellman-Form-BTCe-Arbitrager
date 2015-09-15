package ninja.ard.bfdata;


public interface IQuoteDataContainer {

	// should record here when data is set
	public void setData(Object jsonData);
	
	public String getCurrencyPair();
	
	public Double getLast();
	
	public Double getBid();
	
	public Double getAsk();
	
	public Long getTimestamp();
	
}
