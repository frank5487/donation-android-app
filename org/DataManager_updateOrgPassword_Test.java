import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_updateOrgPassword_Test {

	@Test
	public void testSuccessUpdatePassword() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/updateOrgPassword") {
				return "{\"status\": \"success\", \"data\": \"Successfully updated Organization's password\"}";
				}
				else return null;
			}
			
		});
		
		String ret = dm.updateOrgPassword("1", "123456", "1234");
		assertEquals("success", ret);
	}
	
	@Test
	public void testOldPasswordNotEqual() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/updateOrgPassword") {
				return "{\"status\": \"error\", \"data\": \"Old password is not equal\"}";
				}
				else return null;
			}
			
		});
		
		String ret = dm.updateOrgPassword("1", "", "1234");
		assertEquals("notequal", ret);
	}
	
	@Test
	public void testFailsUpdatePassword() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/updateOrgPassword") {
				return "{\"status\": \"error\", \"data\": \"Any Error info\"}";
				}
				else return null;
			}
			
		});
		
		String ret = dm.updateOrgPassword(null, "123456", "1234");
		assertEquals(null, ret);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testFailsConnectApi() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return null;
			}
			
		});
		
		String ret = dm.updateOrgPassword("1", "123456", "1234");
		assertEquals(null, ret);
	}


}
