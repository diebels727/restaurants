package com.koverse.restaurants;

import com.koverse.sdk.data.Parameter;
import com.koverse.sdk.data.SimpleRecord;
import com.koverse.sdk.export.transform.ExportTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MyCustomExportTransform extends ExportTransform {
	
	private final String FIELD_PARAM = "fieldParam";
	private String fieldParam;
	
	@Override
	public void setup(Map<String, Object> params) throws IOException {
		fieldParam = (String) params.get(FIELD_PARAM);
	}
	
	@Override
	public Iterable<SimpleRecord> transform(SimpleRecord record) {
		if(record.containsKey(fieldParam)) {
			Object value = record.get(fieldParam);
			if(value instanceof String) {
				record.put(fieldParam, new StringBuilder((String)value).reverse().toString());
			}
		}
		
		return Collections.singleton(record);
	}

	@Override
	public String getName() {
		return "My Custom Export Transform";
	}

	@Override
	public String getDescription() {
		return "Simple Custom Export Transform Example";
	}

	@Override
	public String getTypeId() {
		return "my-custom-export-transform";
	}

	@Override
	public List<Parameter> getParameters() {
		ArrayList<Parameter> params = new ArrayList<>();
		params.add(new Parameter(FIELD_PARAM, "Field to Reverse", Parameter.TYPE_COLLECTION_FIELD, "", true));
		return params;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
	
}
