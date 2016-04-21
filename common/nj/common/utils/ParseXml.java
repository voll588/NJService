package nj.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ParseXml {
	
	/**
     * @description 将xml字符串转换成map
     * @param xml
     * @return Map
     */
    public static Map readStringXmlOut(String xml) {
        Map map = new HashMap();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element el=doc.getRootElement();
            
            Iterator it=el.elementIterator();
            while (it.hasNext()) {
                Element element=(Element) it.next();
                map.put(element.getName(), element.getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    public static void main(String[] str){
    	ParseXml xml = new ParseXml();
    	String s = "<?xml version=\"1.0\"?><request><event>callhangup</event><callid>62856019770745lLcWw15637715225</callid><accountid>9c9f66f078d5d9ce81abe66224f75d0c</accountid><appid>0635d70274fd4164b4d063d08c85b24e</appid><calltype>1</calltype><callertype>0</callertype><caller>62856019770745</caller><calledtype>0</calledtype><called>62856019552727</called><length>3</length><starttime>2015-03-11 10:40:09</starttime><stoptime>2015-03-11 10:40:12</stoptime><reason>0</reason></request>";
    	xml.readStringXmlOut(s);
    	
    	
    }
	
	
	
	
	
	
	
	
	
}