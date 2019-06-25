package com.prj.model.transform;

import java.util.HashMap;
import java.util.Map;

import com.prj.model.Stats;

import scala.math.BigInt;

/**
 * @author Prachi Chaurasia
 * Convert string to object model.
 * Calculate count by hour for each data row.
 */
public class TransformationUtils {
	
	private static final String REGEX = "(?<=\\G..)";
	private static final int TOTAL_COLUMNS = 4;
	private static final String DELIMITER = " ";

	public static Stats convertFromStringToStats(String data) {
		Stats stats = new Stats();
		String[] parts =  data.split(DELIMITER);
		try {
			if(parts.length < TOTAL_COLUMNS) {
				System.err.println("Invalid data row => " + data);
			}
			stats.setProjectCode(parts[0]);
			stats.setTitle(parts[1]);
			stats.setHours(parts[3]);
			stats.setCount(BigInt.apply(parts[2]));
			setCountByHour(stats);
			
		} catch(Exception exp) {
			System.err.println("Unable to parse row => " + data);
		}
		return stats;		
	}
	
	public static void main(String[] args) {
		String abc = "aa.b Main_Page 64 A2B2C5D1E6F1G2I5J2K2L4M6N1O1Q1R2S4T7U3V2W4X1";
		Stats stats = TransformationUtils.convertFromStringToStats(abc);
		System.out.println("Stats => " + stats);
	}
	
	private static void setCountByHour(Stats stats) {
		String hours = stats.getHours();
		Map<Character, Character> hourMap = new HashMap<>();
		// String[] parts = hours.split(REGEX);
		int length = hours.length() - 1;
		int i = 0 ;
		while(i < length ) {
			hourMap.put(hours.charAt(i), hours.charAt(i+1));
			i = i + 2;
		}
		stats.setCountByHourMap(hourMap);
		
	}

}
