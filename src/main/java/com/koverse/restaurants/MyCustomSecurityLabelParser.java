package com.koverse.restaurants;

import com.koverse.sdk.security.SecurityLabelParser;



/** An example implementation of a security label parser. Security Label Parsers
 * are used to translate a source's raw record labels to authorization label strings
 * that allow for fine grained access controls. 
 *
 */
public class MyCustomSecurityLabelParser implements SecurityLabelParser {

	@Override
	public String getDisplayName() {
		return "My Custom Security Label Parser";
	}

	@Override
	public String getDescription() {
		return "An example implementation";
	}

	@Override
	public String getTypeId() {
		return "myCustomSecurityLabelParser";
	}

	@Override
	public String getVersion() {
		return "0.0.1";
	}

	@Override
	public String toExpression(String securityLabel) {
		// This simply appends a fixed string to the original label. 
		return securityLabel + "myCustomAppendedString";
	}

}
