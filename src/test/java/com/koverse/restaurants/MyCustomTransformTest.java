package com.koverse.restaurants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.koverse.sdk.data.Record;
import com.koverse.sdk.test.TransformTestRunner;

public class MyCustomTransformTest {

	@Test
	public void test() throws InstantiationException, IllegalAccessException, IOException, InterruptedException {
		
		Map<String,String> parameterValues = new HashMap<>();
		
		parameterValues.put(MyCustomTransform.PARAMETER_TEXT_FIELD, "doc");
		
		List<Record> records = new ArrayList<>();		
		
		for(int i=0; i < 10; i++) {
			Record record = new Record();
			record.addField("doc", "this is some test text");
		
			records.add(record);
		}
		
		List<Record> outputRecords = TransformTestRunner.runTest(MyCustomTransform.class, parameterValues, records);
		for(Record r : outputRecords) {
			System.out.println(r);
		}
		
		Assert.assertTrue("Should return five records.", outputRecords.size() == 5);

	}
	
}
