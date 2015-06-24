package com.koverse.restaurants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.koverse.sdk.data.Record;
import com.koverse.sdk.test.ImportTransformTestRunner;

public class MyCustomImportTransformTest {

	@Test 
	public void test() throws InstantiationException, IllegalAccessException, IOException, InterruptedException {
		

		ArrayList<Record> records = new ArrayList<>();

		
		Record record1 = new Record();
		record1.addField("lat", 1D);
		record1.addField("long", 2D);
		records.add(record1);

		Map<String,Object> parameterValues = new HashMap<>();
		parameterValues.put(MyCustomImportTransform.PARAMETER_PROPERTY_NAME, "propertyA");
		parameterValues.put(MyCustomImportTransform.PARAMETER_PROPERTY_VALUE, "valueZ");
		
		List<Record> recordsReturned = ImportTransformTestRunner.runTest(MyCustomImportTransform.class, parameterValues, records);
		
		Assert.assertTrue(recordsReturned.size() == 1);
		
		
	}
	
}
