import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_updateOrgInfo_Test {

	@Test
	public void testSuccessUpdateInfo() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/updateOrgInfo") {
				return "{\"status\": \"success\", \"data\": \"Successfully updated Organization's information\"}";
				}
				else return null;
			}
			
		});
		
		String ret = dm.updateOrgInfo("1", "123456", "a", "some desc");
		assertEquals("success", ret);
	}
	
	@Test
	public void testPasswordNotEqual() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/updateOrgInfo") {
				return "{\"status\": \"error\", \"data\": \"password is not equal\"}";
				}
				else return null;
			}
			
		});
		
		String ret = dm.updateOrgInfo("1", "", "a", "some desc");
		assertEquals("notequal", ret);
	}
	
	@Test
	public void testFailsUpdateInfo() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/updateOrgInfo") {
				return "{\"status\": \"error\", \"data\": \"Any Error info\"}";
				}
				else return null;
			}
			
		});
		
		String ret = dm.updateOrgInfo(null, "123456", "a", "some desc");
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
		
		String ret = dm.updateOrgInfo("1", "123456", "a", "some desc");
		assertEquals(null, ret);
	}


}
