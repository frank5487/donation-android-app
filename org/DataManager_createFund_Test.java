import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_createFund_Test {
	
	/*
	 * This is a test class for the DataManager.createFund method.
	 * Add more tests here for this method as needed.
	 * 
	 * When writing tests for other methods, be sure to put them into separate
	 * JUnit test classes.
	 */

	@Test
	public void testSuccessfulCreation() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"success\",\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

			}
			
		});
		
		
		Fund f = dm.createFund("12345", "new fund", "this is the new fund", 10000);
		
		assertNotNull(f);
		assertEquals("this is the new fund", f.getDescription());
		assertEquals("12345", f.getId());
		assertEquals("new fund", f.getName());
		assertEquals(10000, f.getTarget());
		
	}
	
	@Test
	public void testFailedCreation() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
//				{"status":"error","data":{"stringValue":"\"\"","valueType":"string","kind":"ObjectId","value":"","path":"_id","reason":{},"name":"CastError","message":"Cast to ObjectId failed for value \"\" (type string) at path \"_id\" for model \"Organization\""}}
				return "{\"status\":\"error\",\"data\":{\"stringValue\":\"\\\"\\\"\",\"valueType\":\"string\",\"kind\":\"ObjectId\",\"value\":\"\",\"path\":\"_id\",\"reason\":{},\"name\":\"CastError\",\"message\":\"Cast to ObjectId failed for value \\\"\\\" (type string) at path \\\"_id\\\" for model \\\"Organization\\\"\"}}";
			}
			
		}
		);
		
		
		Fund f = dm.createFund("", "fund", "this is the new fund", 1000);
		
		assertNull("failed to create a new fund.",f);
		
	}
	
	@Test
	public void testNegativeTargetCreation() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001));
		
		
		Fund f = dm.createFund("62cc2b599397e92ec0263d34", "fund", "this is the new fund", -500);
		
		assertNull("failed to create a new fund.",f);
		
	}
	
	@Test
	public void testExceptionErrorInCreation() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return null;
			}
			
		}
		);
		
		
		Fund f = dm.createFund(null, null, "this is the new fund", 10000);
		
		assertNull("encountered an exception error.",f);
		
	}

}
