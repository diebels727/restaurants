//package com.koverse.restaurants;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import com.koverse.sdk.data.Parameter;
//import com.koverse.sink.file.FileBasedSink;
//
//public class MyCustomFileSink extends FileBasedSink {
//
//
//
//
//	/** The name shown to the user from which to select this sink */
//	@Override
//	public String getName() {
//		return "My Custom File Sink";
//	}
//
//	/**
//	 * A universally unique string that uniquely identifies all versions of this
//	 * sink.
//	 */
//	@Override
//	public String getSinkTypeId() {
//		return "myCustomFileSink";
//	}
//
//	@Override
//	public String getDescription() {
//		return " A sink that does nothing";
//	}
//
//	@Override
//	public String getVersion() {
//		return "0.0.1";
//	}
//
//	@Override
//	public List<Parameter> getFileSinkParameters() {
//		ArrayList<Parameter> params = new ArrayList<>();
//
//		return params;
//	}
//
//	@Override
//	public List<Parameter> getFileSinkJobParameters() {
//		return new ArrayList<>();
//
//	}
//	
//	/**
//	 * Opens an OutputStream to which Koverse can write a file. Use the
//	 * parameter values to configure the output stream.
//	 */
//	@Override
//	public OutputStream openOutputStream(String filename) throws IOException {
//
//		Map<String, Object> params = getContext().getParameterValues();
//		String path = (String) params.get(FileBasedSink.WRITE_DIRECTORY_PARAM);
//
//		
//		return new FileOutputStream(new File(filename));
//	}
//	
//
//}
