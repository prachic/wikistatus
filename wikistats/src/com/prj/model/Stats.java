package com.prj.model;

import java.io.Serializable;
import java.util.Map;

import scala.math.BigInt;

/**
 * @author Prachi Chaurasia
 * Object model for extracting the wikistatus.
 */
public class Stats implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String DELIMITER = "|";

	private String title;
	
	private BigInt count;
	
	private String projectCode;
	
	private String hours;
	
	private Map<Character, Character> countByHourMap;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigInt getCount() {
		return count;
	}

	public void setCount(BigInt count) {
		this.count = count;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}
	
	public Map<Character, Character> getCountByHourMap() {
		return countByHourMap;
	}

	public void setCountByHourMap(Map<Character, Character> countByHourMap) {
		this.countByHourMap = countByHourMap;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(projectCode).append(DELIMITER).append(title).append(DELIMITER).append(count).append(DELIMITER).append(hours)
		.append(DELIMITER).append(countByHourMap)
		;
		return builder.toString();
	}

}
