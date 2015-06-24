package com.koverse.restaurants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.koverse.sdk.data.SimpleRecord;
import com.koverse.sdk.sink.Sink;
import com.koverse.sdk.test.RecordSinkTestRunner;

public class MyCustomRecordSinkTest {

	@Test
	public void test() throws InstantiationException, IllegalAccessException, IOException {
		
		
		// Setup parameter values
		Map<String,Object> parameterValues = new HashMap<String,Object>(){{
			put(MyCustomRecordSink.PARAMETER_FOO, "value");
		}};
		
		
		// Setup input records for testing
		List<SimpleRecord> inputRecords = new ArrayList<SimpleRecord>(){{
			
			// Add one simple record
			add(
					new SimpleRecord(
							new HashMap<String, Object>(){{
								put("fieldA", "value1");
								put("fieldB", "value2");
							}}
							
					)
			);
			
		}};
		
		
		// Run the test
		Sink sink = RecordSinkTestRunner.testSink(MyCustomRecordSink.class, parameterValues, inputRecords);
		
		// Do assertions on sink state
		Assert.assertTrue(sink != null);
	}
	
}
