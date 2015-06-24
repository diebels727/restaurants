package com.koverse.restaurants;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.koverse.client.java.KoverseClientException;
import com.koverse.client.java.KoverseConnection;
import com.koverse.client.java.PlainKoverseConnector;
import com.koverse.dto.json.JsonQuery;
import com.koverse.dto.json.JsonRecord;

/**
 * This is an example for connecting to Koverse via the Koverse Java SDK.
 * Be sure to edit the URL and API Token for connection. 
 * 
 */
public class QueryExample {

    private KoverseConnection koverseConnection;
    
    
    /**
     * The normal Java main executable entrance.
     * 
     * @param args
     * @throws KoverseClientException 
     */
    public static void main( String[] args ) throws KoverseClientException {
       
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        
        URL[] urls = ((URLClassLoader)cl).getURLs();
 
        for(URL url: urls){
        	System.out.println(url.getFile());
        }
    	
    	//  Set the URL and API Token here
        QueryExample queryExample = new QueryExample( "http://localhost:8080/Koverse", "f9ffd05c-c5df-4986-909b-a2dec75e1dde" );
        queryExample.queryAnyField();
        queryExample.querySingleField();
        
    }
    
    public QueryExample( String koverseUrl, String apiToken ) throws KoverseClientException {
        
    	
    	// HTTP Connection Example
        koverseConnection = new KoverseConnection(new PlainKoverseConnector( apiToken, koverseUrl ));
    
    }
    
    /**
     * Query the any collection for the value "test" across all fields of all records
     * @throws KoverseClientException 
     */
    public void queryAnyField( ) throws KoverseClientException {
        JsonQuery query = new JsonQuery();
        query.query.put( "$any", "jared" );
        
        List<JsonRecord> records = koverseConnection.query(query );
        for ( JsonRecord record : records ) {
            System.out.println( record.toString() );
        }
    }
    
    /**
     * Query the collection with name "test" for the value "Bob" in the field "name"
     * @throws KoverseClientException
     */
    public void querySingleField( ) throws KoverseClientException {
        JsonQuery query = new JsonQuery();
        query.query.put( "id", "374929258214592500" );
        query.collectionNames.add( "tweets" );
        
        List<JsonRecord> records = koverseConnection.query( query );
        for ( JsonRecord record : records ) {
            System.out.println( record.toString() );
        	for ( Map.Entry<String,Object> entry : record.fields.entrySet() ) {
        		System.out.println("Field:" + entry.getKey() + " Type:" + entry.getValue().getClass().getName() + " Value:" + entry.getValue().toString() );
        	}
        }
    }
    
    /**
     * Query the collection named "test" for the name "Jared" and age 32.
     * A Query Builder API will be made available in future releases to make this far easier for
     * the developer
     */
    public void queryAndedTerms() throws KoverseClientException {
    	JsonQuery nameAndAgeQuery = new JsonQuery();
    	Map<String,Object> nameMap = Maps.newHashMap();
    	Map<String,Object> ageMap = Maps.newHashMap();
    	List<Map<String,Object>> andedTerms = Lists.newArrayList();
    	nameMap.put("name", "Jared");
    	ageMap.put("age", 32);
    	andedTerms.add(nameMap);
    	andedTerms.add(ageMap);
    		
    	nameAndAgeQuery.query.put("$and", andedTerms);
    	nameAndAgeQuery.collectionNames.add( "test" );
        
        List<JsonRecord> records = koverseConnection.query( nameAndAgeQuery );
        for ( JsonRecord record : records ) {
            System.out.println( record.toString() );
        }
    }
    
    


}