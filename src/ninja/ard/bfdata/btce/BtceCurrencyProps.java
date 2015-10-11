package ninja.ard.bfdata.btce;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class BtceCurrencyProps {

	public static final Properties props = new Properties();
	
	static {
		try{
			props.load(new FileInputStream(new File("btce.properties")));
		}catch( Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
