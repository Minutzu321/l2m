package me.minutz.l2m.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import me.minutz.l2m.L2MSystem;

public class DateUtil {
	private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy,HH:mm:ss");
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date1.getTime() - date2.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	public static long getElapsedTime(Date d, TimeUnit t){
		long l = getDateDiff(Calendar.getInstance().getTime(),d,t);
		return l;
	}
	public static DateFormat getDateFormat() {
		return df;
	}

	public static String dateToString(Date date){
		return df.format(date);
	}

	public static Date stringToDate(String str){
		try{
			return df.parse(str);
		}catch(ParseException e){
			L2MSystem.logger.severe(e.toString());
		}
		return Calendar.getInstance().getTime();
	}
}
