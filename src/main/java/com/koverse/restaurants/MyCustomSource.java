package com.koverse.restaurants;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.koverse.sdk.data.Parameter;
import com.koverse.sdk.source.SimpleSource;

public class MyCustomSource extends SimpleSource {

	/** Use static strings to define the parameter keys */
	protected static String PARAMETER_MAX_RECORD_COUNT = "recordCount";
	
	/** Storage for parameter value. The end user's value is stored here. */
	private Integer maxRecordCount = 0;
	
	/** State for executing this source */
	private Integer recordCount = 0;

	/** Return a short description of the purpose and use of this source. This string is 
	 * displayed as contextual help in the end user interface.
	 */
	@Override
	public String getDescription() {
		return "An example source from the Koverse SDK";
	}

	/** Return a string name that is displayed in the end user interface. Users select this source
	 * by this string. */
	@Override
	public String getName() {
		return "My Custom Source";
	}

	/** Return a unique string that will remain the same across all versions of this source. */
	@Override
	public String getSourceTypeId() {
		return "myCustomSource";
	}

	/** Return the string the describes the version of this source. This 
	 * is used only as extra information for the end user of this source.
	 */
	@Override
	public String getVersion() {
		return "0.0.0";
	}
	
	/**
	 * Whether the source ever exists under normal circumstances. For example a twitter
	 * feed would be continuous, whereas an FTP file retrieval would not be continuous. 
	 */
	@Override
	public final Boolean isContinuous() {
		return false;
	}

	/** Return a list of parameters that are to be presented for input, before using this source */
	@Override
	public List<Parameter> getSourceParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();

		parameters.add(
				new Parameter.Builder()
					.parameterName(PARAMETER_MAX_RECORD_COUNT)
					.displayName("Record Count")
					.type(Parameter.TYPE_INTEGER)
					.defaultValue("50")
					.build()
				);

		
		return parameters;
	}

	
	/** Perform connection setup, and related processes here. It is important to know that
	 * this method is not run in the same JVM as the getSourceParameters. So you can not use local
	 * variables to pass information between them. You must read the parameter values from the context - as seen in this
	 * example. */
	@Override
	public void connect() {
		maxRecordCount = (Integer) getContext().getParameterValues().get(PARAMETER_MAX_RECORD_COUNT);
	}

	/** Return maps of string keys with Java native type values (Date, Boolean, String, Integer, Float, Double) as values. These
	 * will be the fields and values which are placed in a single record. 
	 * <br/><br/>
	 * Return null when no more records are available.
	 * <br/><br/>
	 * It is important to know this method, the connect() method, and the disconnect() method are run in the same JVM - but none
	 * of the other methods are. Therefore you can only use local variables to pass information between these three methods.
	 */
	@Override
	public Map<String, Object> getNext() {
		
		// Go until the configured max record count
		if(recordCount++ >= maxRecordCount) 
			return null;
		
		// Create a new map of field values
		Map<String, Object> fields = new HashMap<String, Object>();

		// Set some values
		fields.put("test", "test");
		
		// Return the set of values for this record
		return fields;
		
	}
	
	/** Perform connection clean up here */
	@Override
	public void disconnect() {

	}

}