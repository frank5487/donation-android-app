import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_deleteFund_Test {

	@Test
	public void testdeleteFundSuccess() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/deleteFund") {
					return "{\"status\":\"success\",\"data\":\"Any info\"}";
				}
				else return null;
			}
			
		});
		
		
		String res = dm.deleteFund("12345");
		
		assertEquals("success", res);
	}
	

	
	@Test
	public void testdeleteFundFail() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/deleteFund") {
					return "{\"status\":\"error\", \"data\": \"Any Error info\"}";
				}
				else return null;
			}
			
		});
		
		String res = dm.deleteFund(null);

		assertEquals(null, res);
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
		
		String res = dm.deleteFund("12345");
		
		assertEquals(null, res);
	}

}
