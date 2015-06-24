package com.koverse.restaurants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.koverse.sdk.data.SimpleRecord;
import com.koverse.sdk.test.ListSourceTestRunner;

public class MyCustomSourceTest {

	@Test
	public void test() throws InstantiationException, IllegalAccessException, IOException {
		
		
		Map<String,Object> parameterValues = new HashMap<>();
		
		parameterValues.put(MyCustomSource.PARAMETER_MAX_RECORD_COUNT, 10);
		
		List<SimpleRecord> records = ListSourceTestRunner.testImportSource(
				MyCustomSource.class,
				parameterValues,
				11);

		Assert.assertTrue("Record count not equal to 10", records.size() == 10);
		
	}
	
}
