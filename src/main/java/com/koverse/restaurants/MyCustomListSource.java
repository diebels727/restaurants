package com.koverse.restaurants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;

import com.koverse.sdk.data.Parameter;
import com.koverse.sdk.data.SimpleRecord;
import com.koverse.sdk.source.AbstractListMapReduceSource;

public class MyCustomListSource extends AbstractListMapReduceSource {

	/** Use static strings to define the list items to be made into records */
	protected static String PARAMETER_USER_VALUES = "userValues";
	
	
	/** This will be used to hold the user defined values */
	private String userDefinedValues;
	
	/** The string this shown to the end user to select this import source */
	@Override
	public String getName() {
		return "My Custom List Source";
	}

	@Override
	public String getVersion() {
		return "1.0.0";
	}

	/** This value should be universally unique to all other sources, and should remain the
	 * same across all versions of this source
	 */
	@Override
	public String getSourceTypeId() {
		return "myCustomListSource";
	}

	@Override
	public String getDescription() {
		return " An example list source that returns a single record per mapper";
	}

	/** Whether this source ever exists under normal circumstances. For example a twitter stream
	 * would be continuous, where a file parsing import would not.
	 */
	@Override
	public Boolean isContinuous() {
		return false;
	}
	
	
	/** Return a list of parameters so that an end user can configure this source */
	@Override
	protected List<Parameter> getSourceParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();

		parameters.add(
				new Parameter.Builder()
					.parameterName(PARAMETER_USER_VALUES)
					.displayName("Values")
					.hint("Comma separated list of values")
					.type(Parameter.TYPE_STRING)
					.build()
				);
		
		return parameters;	}

	
	/** 
	 * Use this method to apply any hadoop specific configurations that are necessary. Normally nothing should be done here.
	 */
	@Override
	public void doConfigureFromConf(Configuration conf) throws IOException {
	}

	/** Use this method to pull values from the configuration context. This is called before enumerateList() in one JVM
	 * and before recordsForItems in each map taks JVM  */
	@Override
	protected void initialize() {
		
		userDefinedValues = (String) getContext().getParameterValues().get(PARAMETER_USER_VALUES);
		
	}
	
	/** This method should return a list of strings that will identify the tasks to do for each
	 * import mapper. Each task in the list will be an individual map task. 
	 * <br/><br/>
	 * A an example might be a list of file paths, or a static list of fixed string values from user configuration. 
	 */
	@Override
	public Iterable<String> enumerateList() {
		
		List<String> returnValue = new ArrayList<>();
		
		// Split user defined values by comma, then place them on the returned value list
		for(String value : userDefinedValues.split(","))
			returnValue.add(value.trim());
		
		return returnValue;
	}

	/** This method is called once for each item returned from the enumerateList() method. Note that
	 * each call of this method is in a different JVM. So you can not rely on local variables.
	 */
	@Override
	public Iterable<SimpleRecord> recordsForItem(String item)
			throws IOException {
		
		final SimpleRecord simpleRecord = new SimpleRecord();
		simpleRecord.put("value", item);
		
		// This is an example Iterable that returns an iterator that 
		// returns a single record. Normally you will use an iterator
		// from a connection, or a file, that returns many records.
		// The main point is not to use something like a Collection
		// which requires that all records be in memory before passing back the Iterable.
		return new Iterable<SimpleRecord>() {
			
			@Override
			public Iterator<SimpleRecord> iterator() {

				return new Iterator<SimpleRecord>() {
					
					Boolean recordReturned = false;
					
					@Override
					public SimpleRecord next() {
						
						recordReturned  = true;
						
						return simpleRecord;
					}
					
					@Override
					public boolean hasNext() {
						return !recordReturned;
					}

					@Override
					public void remove() {
						
					}
				};
			}
		};
		
	}

	

	/** Perform connection closing operations here. This is called after the iterator returns null value - indicating the
	 * last record has been returned. */
	@Override
	public void close() throws IOException {
		
	}

}
