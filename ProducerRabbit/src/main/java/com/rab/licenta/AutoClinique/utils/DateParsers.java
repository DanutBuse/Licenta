package com.rab.licenta.AutoClinique.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParsers {

	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
}
