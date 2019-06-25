package com.prj.process;

import java.io.InputStream;
import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import com.prj.model.Stats;

/**
 *  @author Prachi Chaurasia
 *  Purpose - Read the file from specified directory, extract and process information. Store data to MySql database.
 */
public class WikiStatsProcessor {
	
	private static final int PARTITIONS = 2;

	public static void main(String[] args) {
		String inputPath, outputPath;
		
		// provide path to input text file
		inputPath = getFilePath();
		outputPath = inputPath + "_output";
		
		if(args.length < 1) {
			/* Sample input path -->  
			 * 		1. Directory -> $path\pagecounts 
			 * 		2. File -> $path\pagecounts-2019-06-01 
			 * 		3. With extension --> $path\pagecounts-2019-06-01.txt (Only text file is supported)
			 */  
			throw new IllegalArgumentException("WikiStatsProcessor -> Please specify the input and output path for files!!");
		}
		inputPath = args[0];
		outputPath = args[1];
		
		// Initialise the spark context
		JavaSparkContext jsc = initSparkContext();  
		
		processWikiStatus(jsc, inputPath, outputPath);
        
        // Stop the spark context
        jsc.close();
	}

	private static void processWikiStatus(JavaSparkContext jsc, String inputPath, String outputPath) {
		JavaRDD<String> data = loadFile(jsc, inputPath);
        
        ProcessFile processor = new ProcessFile();
        JavaRDD<Stats> statsRdd = processor.process(jsc, data);
        
		processor.saveData(outputPath, statsRdd, PARTITIONS);
	}

	/**
	 * @return  File path for the raw data. [Can be a file or directory]
	 */
	private static String getFilePath() {		
		String path = "$path\\pagecounts-2019-06-01"; //pagecounts-2019-06-01
		return path;
	}

	/**
	 * Read data from the specified path
	 * @param jsc
	 * @param path
	 * @return - JavaRdd for filtered data
	 */
	private static JavaRDD<String> loadFile(JavaSparkContext jsc, String path) {
        // Read text file to RDD
        System.out.println("Loading from file -> " + path);
        JavaRDD<String> data = jsc.textFile(path);       
        
        // Filter the lines with #
        data = data.filter(f -> !f.startsWith("#"));
        
        // ToDo: Implement custom partitioner based on project.code
        data = data.repartition(PARTITIONS);

        return data;      
	}

	private static JavaSparkContext initSparkContext() {
		// configure spark
        SparkConf sparkConf = new SparkConf().setAppName("wikistats")
        		.setMaster("local[*]")
        		.set("spark.executor.memory","2g");
        
        // start a spark context
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
		return jsc;
	}
	
	/**
	 * Reads property file and puts its content into a map.
	 */
	public static void loadProperties(String resourcePath) {
		System.out.println("Loading property => " + resourcePath);
		Properties properties = new Properties();
		try (InputStream propStream = WikiStatsProcessor.class.getResourceAsStream(resourcePath)) {
			properties.load(propStream);
			System.out.println("Properties => " + properties);
			// Populate propertyMap -> // ToDo: Fix me
		} catch (Exception e) {
			System.err.println("Could not load property file: " + resourcePath);
			e.printStackTrace();
		}
	}

}
