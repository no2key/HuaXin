package com.ht.huaxin.utils;

import java.util.Calendar;

public class UIHelper {

	public static String getYearFromThen(String year){
		int then = Integer.valueOf(year);
		int now = Calendar.getInstance().get(Calendar.YEAR);
		
		StringBuffer sb = new StringBuffer();
		sb.append("距今");
		sb.append(now - then);
		sb.append("年");
		return sb.toString();
	}
	
	public static String getPictureCount(int count){
		StringBuffer sb = new StringBuffer();
		sb.append(String.valueOf(count));
		sb.append("图");
		return sb.toString();
	}
	
}
