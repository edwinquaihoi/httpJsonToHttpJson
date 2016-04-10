package au.com.anz.iib.apps.httpjsontohttpjson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.anz.iib.commons.compute.IBlobTransformer;
import au.com.anz.json.schema.httpjsontohttpjson.Company;
import au.com.anz.json.schema.httpjsontohttpjson.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PostJsonBlobTransform implements IBlobTransformer {

	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public byte[] execute(byte[] input) {
				
		//json.
		
		String json = new String(input);
		logger.error("Transforming 1");

		// turn json into pojo for processing
		// expecting a collections of User objects to be returned
		Gson gson = new Gson();		
		Type collectionType = new TypeToken<List<User>>(){}.getType();
		List<User> users = gson.fromJson(json, collectionType);
		logger.error("Transforming 2");
		
		// go through the collection of users and get the country
		List<Company> companies = new ArrayList<Company>();
		if(users != null) {
			for(User user : users) {
				companies.add(user.getCompany());
			}
		}
		logger.error("Transforming 3");
		
		return gson.toJson(companies,new TypeToken<List<Company>>(){}.getType()).getBytes();
		
		//return input;
	}

}
