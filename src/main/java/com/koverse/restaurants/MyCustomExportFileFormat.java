package com.koverse.restaurants;

import com.koverse.sdk.data.Parameter;
import com.koverse.sdk.data.SimpleRecord;
import com.koverse.sdk.export.ExportFileFormat;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;

public class MyCustomExportFileFormat extends ExportFileFormat {
	private BufferedWriter writer;

	@Override
	public List<Parameter> getParameters() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getName() {
		return "My Custom Export File Format";
	}

	@Override
	public String getTypeId() {
		return "my-custom-export-file-format";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getDescription() {
		return "A file format that writes out records as text";
	}

	@Override
	public String getExtension() {
		return ".txt";
	}

	@Override
	protected void writeRecordToFormat(SimpleRecord record) throws IOException {
		writer.write(record.toString() + "\n");
	}

	@Override
	protected void startFile() throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(getOutputStream()));
	}

	@Override
	protected void endFile() throws IOException {
		writer.flush();
	}
	
}
