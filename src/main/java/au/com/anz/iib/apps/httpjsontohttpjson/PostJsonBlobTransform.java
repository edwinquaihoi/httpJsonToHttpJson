package au.com.anz.iib.apps.httpjsontohttpjson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import au.com.anz.iib.commons.compute.IBlobTransformer;
import au.com.anz.json.schema.httpjsontohttpjson.Company;
import au.com.anz.json.schema.httpjsontohttpjson.User;

public class PostJsonBlobTransform implements IBlobTransformer {

	@Override
	public byte[] execute(byte[] input) {
		//json.
		String json = new String(input);

		// trun json into pojo for processing
		// expecting a collections of User objects to be returned
		Gson gson = new Gson();		
		Type collectionType = new TypeToken<Collection<User>>(){}.getType();
		Collection<User> users = gson.fromJson(json, collectionType);
		
		// go through the collection of users and get the country
		List<Company> companies = new ArrayList<Company>();
		for(User user : users) {
			companies.add(user.getCompany());
			System.out.println(user.toString());
		}
		
		return gson.toJson(companies,new TypeToken<List<Company>>(){}.getType()).getBytes();
	}

}
