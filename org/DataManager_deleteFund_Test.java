import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_deleteFund_Test {

	@Test
	public void testdeleteFund() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"success\",\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

			}
			
		});
		
		
		Fund f = dm.createFund("12345", "new fund", "this is the new fund", 10000);
		
		String res = dm.deleteFund("12345");
		
		assertEquals("success", res);
	}
	
	@Test
	public void testdeleteFundFail() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"error\"}";

			}
			
		});
		
		
		String res = dm.deleteFund("not exist fund");

		assertEquals(null, res);
	}

}
