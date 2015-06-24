package com.koverse.restaurants;

import com.koverse.sdk.data.Parameter;
import com.koverse.sdk.data.Recordable;
import com.koverse.sdk.data.SimpleRecord;
import com.koverse.sdk.record.io.RecordIO;
import com.koverse.sdk.record.io.writer.RecordWriter;
import com.koverse.sdk.sink.Sink;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.conf.Configuration;

/** This a fully functional, but minimally featured export sink */
public class MyCustomRecordSink implements Sink {

	public static String PARAMETER_FOO = "foo";

	/**
	 * This value must be universally unique, and must remain the same across
	 * all versions of the sink
	 */
	@Override
	public String getSinkTypeId() {
		return "myCustomSink";
	}

	/** The name displayed to the user by which to select this type of sink */
	@Override
	public String getName() {
		return "My Custom Sink";
	}

	/** A short description of what this sink does.  */
	@Override
	public String getDescription() {
		return "A sink that reads records and does nothing";
	}

	/**
	 * A string that identifies the version of the sink. This value is just a
	 * string and is not interpreted. The last version installed into the
	 * koverse server is the one that is used.
	 */
	@Override
	public String getVersion() {
		return "0.0.0";
	}

	/**
	 * The parameters for configuration by the user when creating the source.
	 * Generally this is for basic access information and not for specific
	 * storage paths, etc.
	 * 
	 * @see MyCustomSink.getJobParameters()
	 * */
	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<>();

		parameters.add(new Parameter.Builder().parameterName(PARAMETER_FOO)
				.displayName("Foo").type(Parameter.TYPE_STRING)
				.defaultValue("default value").required(true).build());

		return parameters;
	}

	/**
	 * Parameters that are used when the job is configured and run. Generally
	 * this is for defining the path for storage, and not for authentication.
	 * 
	 * @see MyCustomSink.getParameters()
	 */
	@Override
	public List<Parameter> getJobParameters() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * Use this method to write special hadoop configuration if necessary. Not
	 * normally needed
	 */
	@Override
	public void configureFromConf(Configuration arg0) throws IOException {

	}

	/**
	 * The method by which to execute this sink. This should always be MapReduce
	 */
	@Override
	public Sink.ExecutionMethod getExecutionMethod() {
		return Sink.ExecutionMethod.MapReduce;
	}

	/**
	 * Returns an instance of a class that reads records and writes them out to
	 * an external system
	 */
	@Override
	public RecordWriter openRecordWriter() throws IOException {
		return new MyCustomRecordWritter();
	}

	/**
	 * This class implements the record writing to an external system logic.
	 * This example does not actually do any record writing.
	 */
	public static class MyCustomRecordWritter implements RecordWriter {

		private Long recordCount = 0L;

		/** This method should clean up connections */
		@Override
		public void close() throws IOException {

		}

		/**
		 * This method should not return until all pending records have been
		 * written.
		 */
		@Override
		public void flush() throws IOException {

		}

		/** This method should return one of the RecordIO.States */
		@Override
		public RecordIO.State getState() {
			return null;
		}

		/**
		 * This method should indicate whether the external system connection is
		 * closed
		 */
		@Override
		public boolean isClosed() throws IOException {
			return false;
		}

		/**
		 * This method should indicate whether the external system connection is
		 * open
		 */
		@Override
		public boolean isOpen() throws IOException {
			return false;
		}

		/** This method should attempt to open a connection to an external system */
		@Override
		public void open() throws IOException {

		}

		/**
		 * This method should return the number of records actually written
		 * successfully to the external system
		 */
		@Override
		public long getNumberOfRecordsWritten() {
			return recordCount;
		}

		/**
		 * This method should write, but not necessarily commit or flush, a
		 * record to an external system
		 */
		@Override
		public void writeRecord(SimpleRecord arg0) throws IOException {
			recordCount++;
		}

		/**
		 * This method should turn a "Recordable" object into a Record, and then
		 * write the record to the external system
		 */
		@Override
		public void writeRecord(Recordable arg0) throws IOException {
			recordCount++;
		}
	}
}