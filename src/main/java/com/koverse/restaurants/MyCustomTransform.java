package com.koverse.restaurants;

import com.koverse.sdk.data.Parameter;
import com.koverse.sdk.data.Record;
import com.koverse.sdk.transform.AbstractCombineStage;
import com.koverse.sdk.transform.AbstractRecordMapStage;
import com.koverse.sdk.transform.AbstractReduceStage;
import com.koverse.sdk.transform.AbstractTransform;
import com.koverse.sdk.transform.TransformStage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;

import org.apache.hadoop.io.Text;

/**
 * This custom transform implements the the common word count algorithm. It is
 * intended as a working, but very simple, example of a transform.
 */
public class MyCustomTransform extends AbstractTransform {

	public static final String PARAMETER_TEXT_FIELD = "wordFieldParam";

	/** Necessary for defining a simple transform */
	public MyCustomTransform() {
	}

	/**
	 * The name of the transform that is displayed to the user to select from a
	 * list.
	 */
	@Override
	public String getName() {
		return "My Custom Transform";
	}

	/**
	 * This value should be universaly unique to identify this transfrom across
	 * all of it's versions.
	 */
	@Override
	public String getJobTypeId() {
		return "myCustomTransform";
	}

	/**
	 * This version number is simply to identify the installed version in the
	 * UI. No consideration is made at installation time as to the relative
	 * increase or decrease in versions. The last version installed is always
	 * used.
	 */
	@Override
	public String getVersion() {
		return "1.0.0";
	}

	
	/**
	 * If the transform algorithm requires reading the entire input data collection
	 * then return false. Otherwise return true. For example, a record copy transform
	 * could be incremental, where a word clustering algorithm could not.
	 */
	@Override
	public boolean isIncrementalProcessingSupported() {
		return false;
	}
	
	/**
	 * In this method, add new parameters for a list. Users define values for
	 * these parameters, so that they can configure the transform.
	 * */
	@Override
	protected void fillInParameters(List<Parameter> parameters) {

		parameters.add(new Parameter.Builder().parameterName(PARAMETER_TEXT_FIELD)
				.displayName("Field containing text")
				.type(Parameter.TYPE_COLLECTION_FIELD).build());

	}

	/**
	 * In this method, return the transform stage classes. The first stage must extend
	 * AbstractRecordMapStage. The rest must be one of AbstractReduceStage,
	 * AbstractCombineStage, AbstractReduceStage. Add the stages in the order
	 * you wish them to be executed.
	 */
	@Override
	protected void fillInStages(List<Class<? extends TransformStage>> stages) {

		stages.add(MyCustomMapStage.class);
		stages.add(MyCustomCombinerStage.class);
		stages.add(MyCustomReduceStage.class);
	}

	/** This class is the first stage of the transform. It reads Koverse records
	 * and emits word counts as key/value pairs. 
	 */
	public static class MyCustomMapStage extends AbstractRecordMapStage {

		/** Local variable to hold the word field name */
		private String textField = null;

		/** The number 1 as an int writable that will be used increment emitting */
		private IntWritable incrementIntWritable = new IntWritable(1);
		
		/** A reusable Text object for emitting word keys */
		private Text wordText = new Text();
		
		/**
		 * Perform Mapper setup here. Read parameters, setup data structures,
		 * etc
		 */
		@Override
		public void setup() {
			textField = getStageContext().getParameterValue(PARAMETER_TEXT_FIELD);
		}
		
		
		/**
		 * specify this if you're writing key value pairs to HDFS, which is done
		 * if there are more stages to run otherwise this stage would emit
		 * Records and the return value of these functions is ignored. In that
		 * case it is recommended that you return NullWritable
		 **/
		@Override
		public Class<Text> getMapOutputKeyClass() {
			return Text.class;
		}

		/**
		 * specify this if you're writing key value pairs to HDFS, which is done
		 * if there are more stages to run otherwise this stage would emit
		 * Records and the return value of these functions is ignored. In that
		 * case it is recommended that you return NullWritable
		 **/
		@Override
		public Class<IntWritable> getMapOutputValueClass() {
			return IntWritable.class;
		}

		/** Perform main mapper work here. */
		@Override
		public void map(Record inputRecord) throws IOException,
				InterruptedException {

			// Records may not conform to a fixed schema
			// so Transforms should check for field existence
			if (!inputRecord.hasField(textField))
				return;
			
			Object value = inputRecord.get(textField);

			// and also check for value type
			if (!(value instanceof String)) 
				return;

			// get all the words
			String[] words = ((String) value).toLowerCase().split("\\s+");

			// output each word as a key, and the number one to indicate a single instance of that word
			// reuse the Text and IntWritable objects to improve performance
			for (String word : words) {
				wordText.set(word);
				getStageContext().emit(wordText, incrementIntWritable);
			}
			
		}


	}
	
	
	/** This class implements a custom combiner stage, which is useful for improving performance in certain
	 * algorithms. For example, when counting words, the map stage likely outputs many identical key value paris such as
	 * 'word'/1. A combine runs on the same JVM as the Mapper. The combiner is useful for performing the associative operation,
	 *  in this case summation, locally, rather than having many key/values transfered over the network 
	 *  to the reducer. Like Hadoop MapReduce, the combine method is called once for each unique key. 
	 */
	public static class MyCustomCombinerStage extends AbstractCombineStage {

		
		
		@Override
		public void combine(Object key, Iterable<Object> values)
				throws IOException, InterruptedException {
			
			int sum = 0;

			for (Object v : values)
				sum += ((IntWritable) v).get();
			
			// Emit the combined sum of the key to the reducer
			getStageContext().emit(key, new IntWritable(sum));
			
		}
		
	}

	/**
	 * This class implements a custom reduce stage. Here each key is a word, and
	 * the values are the counts of that word. One Koverse record per word, with a 
	 * count number is emitted. 
	 */
	public static class MyCustomReduceStage extends AbstractReduceStage {

		/** Perform setup, such as reading parameter values from getStageContext()*/
		@Override
		public void setup() {
			
		}

		/** Returning NullWritable here, because this reduce class never
		 * emits key/value pairs to another stage. 
		 */
		@Override
		public Class<NullWritable> getMapOutputKeyClass() {
			return NullWritable.class;
		}

		/** Returning NullWritable here, because this reduce class never
		 * emits key/value pairs to another stage. 
		 */
		@Override
		public Class<NullWritable> getMapOutputValueClass() {
			return NullWritable.class;
		}

		
		/** This method reads the key/value streams that were output from the MyCustomMapStage - through the MyCustomCombiner stage*/
		@Override
		public void reduce(Object key, Iterable<Object> values)
				throws IOException, InterruptedException {
			
			int sum = 0;

			for (Object v : values)
				sum += ((IntWritable) v).get();

			Record r = new Record();
			r.addField("word", key);
			r.addField("count", sum);

			// last stage should write out Records
			getStageContext().writeRecord(r);
		}


	}

}