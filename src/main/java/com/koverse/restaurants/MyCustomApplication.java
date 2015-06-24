package com.koverse.restaurants;

import com.koverse.sdk.application.AbstractApplication;
import com.koverse.sdk.data.Parameter;
import java.util.List;

public class MyCustomApplication extends AbstractApplication {

	/**
	 * A universally unique string that indentifies this application. This
	 * string must be the same across all versions of the application.
	 */
	@Override
	public String getApplicationId() {
		return "myCustomApplication";
	}

	/** The application name which is shown to the users. */
	@Override
	public String getDisplayName() {
		return "My Custom Application";
	}

	/**
	 * The name of the category in which this application is grouped in the UI.
	 * The name much match the desired group name exactly.
	 */
	@Override
	public String getDefaultCategoryName() {
		return "My Custom Category";
	}

	/**
	 * A string that simply identifies the version of the application installed,
	 * but which has no bearing on version control. The last installed version
	 * of the app is the one that is used.
	 */
	@Override
	public String getVersion() {
		return "0.0.1";
	}

	/** The parameters that a system administrator can use to configure the app. */
	@Override
	protected void fillInParameters(List<Parameter> parameters) {

//		parameters.add(new Parameter.Builder()
//			.parameterName("testParameter")
//			.displayName("Test Parameter").type(Parameter.TYPE_STRING)
//			.defaultValue("default value").build());

	}

	/**
	 * Set this false to force the system administrator to manually deploy
	 * instances of this application. Usually enabling auto-deploy is the most
	 * appropriate.
	 */
	@Override
	public boolean getAutoDeploy() {
		return true;
	}
}