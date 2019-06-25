package com.prj.process;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import com.prj.model.Stats;
import com.prj.model.transform.TransformationUtils;

/**
 * @author Prachi Chaurasia
 * Process the data 
 */
public class ProcessFile {
	
	/**
	 * Extract the information from String and convert to object model.
	 * @param jsc
	 * @param data
	 * @return - JavaRDD<Stats> statsRdd
	 */
	public JavaRDD<Stats> process(JavaSparkContext jsc, JavaRDD<String> data) {		
		System.out.println("Processing the data");		
		JavaRDD<Stats> statsRdd = data.map(f -> TransformationUtils.convertFromStringToStats(f));		
		return statsRdd;
	}

	/**
	 * Sort and save data to database/FileSystem
	 * @param path
	 * @param statsRdd
	 */
	public void saveData(String path, JavaRDD<Stats> statsRdd, int partitions) {
		System.out.println("Saving data to path -> "  + path);
		 // Store remaining files
		statsRdd.sortBy(f -> f.getProjectCode(), true, partitions);
		statsRdd.saveAsTextFile(path);
	}

}
