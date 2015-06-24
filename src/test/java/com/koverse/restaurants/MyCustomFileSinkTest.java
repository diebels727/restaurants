//package com.koverse.restaurants;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import junit.framework.Assert;
//
//import org.junit.Test;
//
//import com.koverse.addon.files.export.JsonStreamFormat;
//import com.koverse.sdk.data.SimpleRecord;
//import com.koverse.sdk.ingest.records.JsonStreamRecords;
//import com.koverse.sdk.sink.Sink;
//import com.koverse.sdk.test.RecordSinkTestRunner;
//import com.koverse.sink.file.FileBasedSink;
//import com.koverse.sink.test.FileSinkTestRunner;
//
//public class MyCustomFileSinkTest {
//
//	@Test
//	public void test() throws InstantiationException, IllegalAccessException, IOException {
//		
//		
//		// Setup parameter values
//		Map<String,Object> parameterValues = new HashMap<String,Object>(){{
//			put(MyCustomRecordSink.PARAMETER_FOO, "value");
//		}};
//		
//		
//		// Setup input records for testing
//		List<SimpleRecord> inputRecords = new ArrayList<SimpleRecord>(){{
//			
//			// Add one simple record
//			add(
//					new SimpleRecord(
//							new HashMap<String, Object>(){{
//								put("fieldA", "value1");
//								put("fieldB", "value2");
//							}}
//							
//					)
//			);
//			
//		}};
//		
//		
//		// Uncomment below and the koverse-addon-files dependency in pom.xml
//		
//		// Run the test
//		Sink sink = FileSinkTestRunner.testSink(
//				MyCustomFileSink.class,
//				parameterValues,
//				inputRecords,
//				500,
//				"test",
//				JsonStreamFormat.class.getCanonicalName(),
//				"/tmp", FileBasedSink.COMPRESS_OUTPUT_GZIP );
//		
//		// Do assertions on sink state
//		Assert.assertTrue(sink != null);
//	}
//
//}
