package com.koverse.restaurants;

import com.google.inject.multibindings.Multibinder;
import com.koverse.sdk.security.webapp.AbstractWebAppAuthModule;
import com.koverse.sdk.security.webapp.HttpServletRequestAuthenticator;
import com.koverse.sdk.security.webapp.WebAppAuthorizer;
import com.koverse.sdk.security.webapp.WebAppParameterAuthenticator;

/*
 * To use a custom auth module, update WEB-INF/conf/koverse-webapp.properties to set
 * com.koverse.webapp.auth.modules=com.koverse.restaurants.MyCustomAuthModule
 * 
 * Additionally, the JAR containing the module and authentication and authorization 
 * implementations and dependencies (uber jar) needs to be placed in the classpath
 * of the Koverse Webapp
 */
public class MyCustomAuthModule extends AbstractWebAppAuthModule {

	@Override
	protected void configure(
			Multibinder<WebAppAuthorizer> authorizersBinder,
			Multibinder<HttpServletRequestAuthenticator> servletRequestAuthenticatorsBinder,
			Multibinder<WebAppParameterAuthenticator> parameterAuthenticatorsBinder) {
		
		servletRequestAuthenticatorsBinder.addBinding().to(MyCustomAuthenticator.class);
		authorizersBinder.addBinding().to(MyCustomAuthorizer.class);
	}

}
