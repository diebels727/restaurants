package com.koverse.restaurants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.koverse.sdk.data.SimpleRecord;
import com.koverse.sdk.test.ListSourceTestRunner;

public class MyCustomListSourceTest {

	/**
	 * This method tests the MyCustomListSource to ensure that it's returning
	 * the right number of records, each with the correct value.
	 */
	@Test
	public void test() throws InstantiationException, IllegalAccessException,
			IOException {

		Map<String, Object> parameterValues = new HashMap<>();

		parameterValues.put(MyCustomListSource.PARAMETER_USER_VALUES, "one,two,three");

		List<SimpleRecord> records = ListSourceTestRunner.testImportSource(
				MyCustomListSource.class, parameterValues, 11);

		Assert.assertTrue("Record count not equal to 3", records.size() == 3);

	}

}
