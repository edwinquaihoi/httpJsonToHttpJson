package au.com.anz.iib.apps.httpjsontohttpjson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import au.com.anz.json.schema.httpjsontohttpjson.Company;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class PostJsonBlobTransformTest {
	
	private static final Logger logger = LogManager.getLogger();	
	private static final String TEST_FILE_001 = "user.test.001.json";
	private static final String TEST_FILE_002 = "user.test.002.json";
	private static final String COMPANY_NAME = "Romaguera-Crona";
	
	private PostJsonBlobTransform postJsonBlobTransform = new PostJsonBlobTransform();
	private Gson gson = new Gson();
		
	@Test
	public void testOnUserInput() throws IOException {
		
		byte[] jsonBlob = IOUtils.toByteArray(this.getClass().getResourceAsStream(TEST_FILE_001));
		
		// load up a json object string from file and 
		logger.info("{} bytes read", jsonBlob.length);
		
		// execute transform
		byte[] transformJsonBlob = postJsonBlobTransform.execute(jsonBlob);
		
		assertNotNull(transformJsonBlob);
		
		// convert json blob to expected Java pojo
		gson = new Gson();
		List<Company> companies = gson.fromJson(new String(transformJsonBlob), new TypeToken<List<Company>>(){}.getType());
		
		// assert list contains 1 company
		assertEquals(1, companies.size());
		
		// get company and check the name
		Company company = companies.get(0);
		assertEquals(COMPANY_NAME, company.getName());		
	}


	@Test
	public void testMultipleUsersInput() throws IOException {
		
		byte[] jsonBlob = IOUtils.toByteArray(this.getClass().getResourceAsStream(TEST_FILE_002));
		
		// load up a json object string from file and 
		logger.info("{} bytes read", jsonBlob.length);
		
		// execute transform
		byte[] transformJsonBlob = postJsonBlobTransform.execute(jsonBlob);
		
		assertNotNull(transformJsonBlob);
		
		// convert json blob to expected Java pojo
		gson = new Gson();
		List<Company> companies = gson.fromJson(new String(transformJsonBlob), new TypeToken<List<Company>>(){}.getType());
		
		// assert list contains 1 company
		assertEquals(2, companies.size());		
	}

	@Test
	public void testNullUserInput() throws IOException {
		byte[] jsonBlob = new byte[]{};
		
		// load up a json object string from file and 
		logger.info("{} bytes read", jsonBlob.length);
		
		// execute transform
		byte[] transformJsonBlob = postJsonBlobTransform.execute(jsonBlob);
		
		// convert json blob to expected Java pojo
		gson = new Gson();
		List<Company> companies = gson.fromJson(new String(transformJsonBlob), new TypeToken<List<Company>>(){}.getType());
		
		// assert list contains 1 company
		assertEquals(0, companies.size());
	}
	
	@Test(expected=JsonSyntaxException.class)
	public void testNoArrayInput() {
		byte[] jsonBlob = "{}".getBytes();
		byte[] transformJsonBlob = postJsonBlobTransform.execute(jsonBlob);
		
		gson = new Gson();

		List<Company> companies = gson.fromJson(new String(transformJsonBlob), new TypeToken<List<Company>>(){}.getType());
		// assert list contains 1 company
		assertEquals(0, companies.size());
	}

}
