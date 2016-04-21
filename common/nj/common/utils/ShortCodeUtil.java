package nj.common.utils;

public class ShortCodeUtil {
	private static int short_num = 0;
	public synchronized Long getShortCode(long time) {
		String shortCode = "";
		if(short_num>99) {
			short_num = 0;
		} else if(short_num==0){
			short_num=0;
		}
		if(String.valueOf(short_num).length()<2) {
			shortCode = time+"0"+short_num;
		} else {
			shortCode = time+""+short_num;
		}
		short_num+=1;
		return Long.parseLong(shortCode);
	}
 }
