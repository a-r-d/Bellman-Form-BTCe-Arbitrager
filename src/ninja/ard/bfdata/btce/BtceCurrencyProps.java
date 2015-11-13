package ninja.ard.bfdata.btce;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import ninja.ard.bfcore.bot.BtceBFBot;

public class BtceCurrencyProps {

	final static Logger logger = Logger.getLogger(BtceCurrencyProps.class);

	public static final Properties props = new Properties();
	
	static {
		try{
			props.load(new FileInputStream(new File("btce.properties")));
		}catch( Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}
