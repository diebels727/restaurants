package com.koverse.restaurants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.koverse.sdk.data.Parameter;
import com.koverse.sdk.data.SimpleRecord;
import com.koverse.sdk.ingest.transform.ImportTransform;

/**
 * This is an example import transform that adds a user configured
 * property and value to all records. Use this example ImporTransform
 * implementation to start your custom ImportTransform
 *  *
 */
public class MyCustomImportTransform extends ImportTransform {

	public static final String PARAMETER_PROPERTY_NAME = "propertyName";
	public static final String PARAMETER_PROPERTY_VALUE = "propertyValue";

	private Logger logger = Logger.getLogger(MyCustomImportTransform.class);

	// Reused list in transform method
	private List<SimpleRecord> simpleRecords = new ArrayList<SimpleRecord>();

	// User configured parameter value
	private String propertyName;

	// User configured parameter value
	private String propertyValue;
	
	@Override
	public void setup(Map<String, Object> params) throws IOException {
		super.setup(params);

		// Get the value of parameter
		propertyName = (String) params
				.get(PARAMETER_PROPERTY_NAME);

		propertyValue = (String) params
				.get(PARAMETER_PROPERTY_VALUE);
	}

	@Override
	public Iterable<SimpleRecord> transform(SimpleRecord inputRecord) {

		// Clear the reusable simple records list from previous values
		simpleRecords.clear();

		// Do something with the inputRecord that adds one or more simpleRecords
		// to the output list

		// Example: pass through and modify
		simpleRecords.add(inputRecord);
		
		// Add the new property
		inputRecord.put(propertyName, propertyValue);
		
		return simpleRecords;
	}

	@Override
	public String getName() {
		return "My Custom Import Transform";
	}

	@Override
	public String getDescription() {
		return "Adds a user configured property and value to input records.";
	}

	@Override
	public String getTypeId() {
		return "myCustomImportTransform";
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();

		parameters.add(new Parameter(PARAMETER_PROPERTY_NAME,
				"Property Name", Parameter.TYPE_STRING));
		parameters.add(new Parameter(PARAMETER_PROPERTY_VALUE, "Property Value",
				Parameter.TYPE_STRING, ","));
		

		return parameters;
	}

}
