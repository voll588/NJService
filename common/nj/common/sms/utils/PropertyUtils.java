package nj.common.sms.utils;

import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertyUtils {

	private static final String BUNDLE_NAME = "smsconf.properties";
	private static ResourceBundle RESOURCE_BUNDLE ;
	private static PropertyUtils putil=null;
	
	private PropertyUtils() throws Exception{
		RESOURCE_BUNDLE = new PropertyResourceBundle(this.getClass().getClassLoader().getResourceAsStream(BUNDLE_NAME));
	}
	
	public static PropertyUtils getInstance() throws Exception{
		if(putil==null){
			putil=new PropertyUtils();
		}
		return putil;
	}
	
	public String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static void main(String[] args){
		try {
			PropertyUtils prop =PropertyUtils.getInstance();
			prop.getString("sp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
