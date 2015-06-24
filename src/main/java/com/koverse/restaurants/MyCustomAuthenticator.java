package com.koverse.restaurants;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.koverse.sdk.security.webapp.HttpServletRequestAuthenticator;
import com.koverse.sdk.security.webapp.HttpServletRequestAuthenticatorDescription;

/*
 * An example Authenticator that uses the user information from an X.509 certificate
 */
@Singleton
public class MyCustomAuthenticator implements HttpServletRequestAuthenticator {

	private final Logger logger = LoggerFactory.getLogger(MyCustomAuthenticator.class);
	
	public static final HttpServletRequestAuthenticatorDescription DESCRIPTION = new Description();
	private static final class Description implements HttpServletRequestAuthenticatorDescription {

		@Override
		public String getDisplayName() {
			return "Example PKI Authenticator";
		}

		@Override
		public String getTypeId() {
			return "examplePkiAuthenticator";
		}

		@Override
		public Class<? extends HttpServletRequestAuthenticator> getAuthenticatorClass() {
			return MyCustomAuthenticator.class;
		}	
	}
	
	@Inject
	public MyCustomAuthenticator() {
		super();
	}
	
	@Override
	public HttpServletRequestAuthenticatorDescription getDescription() {
		return DESCRIPTION;
	}

	@Override
	public Optional<String> authenticate(HttpServletRequest httpServletRequest) {
		
		/*
		 * This example assumes a user is using a certificate in their web
		 * browser, and that a reverse proxy (ex. Apache) or application server
		 * (ex. JBoss) has an appropriate SSL configuration such that by the time the
		 * request gets here, they are authenticated by the PKI. For example, Apache
		 * would have verified that the certificate was signed by a trusted CA. At this
		 * point we simply will return the DN from the cert as the unique user id if found.
		 */
		String dn = null;
		if (httpServletRequest != null && httpServletRequest.getAttribute("javax.servlet.request.X509Certificate") != null) {			
 			X509Certificate[] certs = (X509Certificate[])httpServletRequest.getAttribute("javax.servlet.request.X509Certificate");
 			dn = certs[0].getSubjectDN().getName();
 			logger.info("Found DN:" + dn);
		}
		
		return Optional.fromNullable(dn);
	}

}
