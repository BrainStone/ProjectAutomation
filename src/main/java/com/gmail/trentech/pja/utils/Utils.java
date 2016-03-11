package com.gmail.trentech.pja.utils;

import java.util.concurrent.TimeUnit;

public class Utils {

	public static long getTimeInMilliSeconds(String time) {
		String[] times = time.split(",");
		long milliSeconds = 0;
		for(String t : times) {
			if(t.matches("(\\d+)[s]$")) {
				milliSeconds = TimeUnit.SECONDS.toMillis(Integer.parseInt(t.replace("s", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[m]$")) {
				milliSeconds = TimeUnit.MINUTES.toMillis(Integer.parseInt(t.replace("m", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[h]$")) {
				milliSeconds = TimeUnit.HOURS.toMillis(Integer.parseInt(t.replace("h", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[d]$")) {
				milliSeconds = TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("d", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[w]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("w", ""))) * 7) + milliSeconds;
			}else if(t.matches("(\\d+)[mo]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("mo", ""))) * 30) + milliSeconds;
			}else if(t.matches("(\\d+)[y]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("y", ""))) * 365) + milliSeconds;
			}
		}
		return milliSeconds;
	}
	
	public static boolean isTimeValid(String time){
		String[] times = time.split(",");
		boolean value = true;
		for(String t : times) {
			if(t.matches("(\\d+)[s]$")) {
				continue;
			}else if(t.matches("(\\d+)[m]$")) {
				continue;
			}else if(t.matches("(\\d+)[h]$")) {
				continue;
			}else if(t.matches("(\\d+)[d]$")) {
				continue;
			}else if(t.matches("(\\d+)[w]$")) {
				continue;
			}else if(t.matches("(\\d+)[mo]$")) {
				continue;
			}else if(t.matches("(\\d+)[y]$")) {
				continue;
			}else{
				return false;
			}
		}
		return value;
	}
}
