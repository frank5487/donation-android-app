import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_vertifyOrgPassword_Test {

	@Test
	public void testSuccessVertify() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/vertifyOrgPasswd") {
				return "{\"status\": \"success\", \"data\": \"success\"}";
				}
				else return null;
			}
			
		});
		
		String ret = dm.vertifyOrgPassword("1", "123456");
		assertEquals("success", ret);
	}
	
	@Test
	public void testPasswordNotEqual() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/vertifyOrgPasswd") {
				return "{\"status\": \"error\", \"data\": \"password is not equal\"}";
				}
				else return null;
			}
			
		});
		
		String ret = dm.vertifyOrgPassword("1", "");
		assertEquals(null, ret);
	}
	
	@Test
	public void testFailVertify() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/vertifyOrgPasswd") {
				return "{\"status\": \"error\", \"data\": \"Any Error info\"}";
				}
				else return null;
			}
			
		});
		
		String ret = dm.vertifyOrgPassword(null, "123456");
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
		
		String ret = dm.vertifyOrgPassword("1", "123456");
		assertEquals(null, ret);
	}


}
