package com.koverse.restaurants;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.koverse.dto.json.JsonGroup;
import com.koverse.dto.json.JsonUser;
import com.koverse.sdk.security.webapp.WebAppAuthorizer;
import com.koverse.sdk.security.webapp.WebAppAuthorizerDescription;

/*
 * An example Authorizer that maps users to groups and tokens using a hardcoded, in-memory Map
 */
@Singleton
public class MyCustomAuthorizer implements WebAppAuthorizer {

	private Map<String, Set<String>> userTokensMap;
	private Map<String, Set<JsonGroup>> userGroupMap;
	
	private final Logger logger = LoggerFactory.getLogger(MyCustomAuthorizer.class);
	
	public static final WebAppAuthorizerDescription DESCRIPTION = new Description();
	private static final class Description implements WebAppAuthorizerDescription {

		@Override
		public String getDisplayName() {
			return "Example In-Memory Authorizer";
		}

		@Override
		public String getTypeId() {
			return "exampleInMemoryAuthorizer";
		}

		@Override
		public Class<? extends WebAppAuthorizer> getAuthorizerClass() {
			return MyCustomAuthorizer.class;
		}	
	}
	
	@Inject
	public MyCustomAuthorizer() {
		super();
		
		// Initialize in-memory maps of user id to groups and tokens
		// Obviously this is a trivialized, hardcoded example but it
		// can be seen how the authorizer is passed a user and is then
		// responsible for looking up any group membership or external
		// tokens that the user may have
		userTokensMap = Maps.newHashMap();
		userTokensMap.put("OU=Developers, O=Koverse, CN=Jared Winick", Sets.newHashSet("USA", "U"));
		
		userGroupMap = Maps.newHashMap();
		
		// Create an example group to be associated with the user. The fields that need
		// to be populated are the name and authorizerTypeId
		JsonGroup externalGroup = new JsonGroup();
		externalGroup.name = "USERS";
		// Always set the authorizerTypeId to the typeId of this WebAppAuthorizer
		externalGroup.authorizerTypeId = getDescription().getTypeId();
		
		userGroupMap.put("OU=Developers, O=Koverse, CN=Jared Winick", Sets.newHashSet(externalGroup));
	}
	
	@Override
	public WebAppAuthorizerDescription getDescription() {
		return DESCRIPTION;
	}

	@Override
	public Set<String> getTokens(JsonUser user) {
		
		// The unique id for the user is stored in the emailAddress property.
		// This may not always make the most sense, like in the case of the 
		// ExamplePkiAuthenticator where the unique characteristic is the user's DN.
		// That said, the id returned by the authenticator will nevertheless be found
		// in the emailAddress of the user passed in here.
		logger.info("Looking up tokens for user:" + user.emailAddress);
		
		Set<String> tokens = userTokensMap.get(user.emailAddress);
		// Return an empty Set, not null, if there are no tokens associated with the user
		if (tokens == null) {
			tokens = Sets.newHashSet();
		}
		
		return tokens;
	}

	@Override
	public Set<JsonGroup> getGroups(JsonUser user) {
	
		logger.info("Looking up groups for user:" + user.emailAddress);
		
		Set<JsonGroup> groups = userGroupMap.get(user.emailAddress);
		// Return an empty Set, not null, if there are no groups associated with the user
		if (groups == null) {
			groups = Sets.newHashSet();
		}
		
		return groups;
	}

}
