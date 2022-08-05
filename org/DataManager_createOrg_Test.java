import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_createOrg_Test {

	@Test
	public void testSuccessfulCreation() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"success\",\"data\":{\"_id\":\"12345\",\"login\":\"org1\", \"password\":\"123\", \"name\":\"org1\",\"description\":\"org1\",\"funds\":[],\"__v\":0}}";

			}
			
		});
		
//		createOrg(String login, String password, String name, String description)
		Organization o = dm.createOrg("org1", "123", "org1", "org1");
		
		assertNotNull(o);		
		assertEquals("12345", o.getId());
		assertEquals("org1", o.getName());
		assertEquals("org1", o.getDescription());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidInputFailedCreation() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001));
		
		Organization o = dm.createOrg(null, null, "org1", "org1");
		assertNull(o);
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testApiErrorFailedCreation() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"error\",\"data\":{\"stringValue\":\"\\\"\\\"\",\"valueType\":\"string\",\"kind\":\"ObjectId\",\"value\":\"\",\"path\":\"_id\",\"reason\":{},\"name\":\"CastError\",\"message\":\"Cast to ObjectId failed for value \\\"\\\" (type string) at path \\\"_id\\\" for model \\\"Organization\\\"\"}}";
			}
			
		}
		);
				
		Organization o = dm.createOrg("org1", "123", "org1", "org1");
		assertNull(o);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testLoginDuplicateFailedCreation() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"login name already exists\"}";
			}
			
		}
		);
		
		Organization o = dm.createOrg("org1", "123", "org1", "org1");
		assertNull(o);
		
	}
	
	
	@Test(expected=IllegalStateException.class)
	public void testApiWrongResponse1() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "Wrong Json Response";
			}
		}
		);
		
		
		Organization o = dm.createOrg("org1", "123", "org1", "org1");
		assertNull(o);
		
	}
	
	@Test
	public void testApiWrongResponse2() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"wrong\"}";
			}
		}
		);
		
		
		Organization o = dm.createOrg("org1", "123", "org1", "org1");
		assertNull(o);
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testApiNullResponse() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return null;
			}
		}
		);
		
		
		Organization o = dm.createOrg("org1", "123", "org1", "org1");
		assertNull(o);
		
	}

}
