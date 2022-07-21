import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_getContributorName_Test {

	@Test
	public void testSuccessfulGetContributorName() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"success\",\"data\":\"ctb1\"}";
			}
			
		}
		);
		
		
		String name = dm.getContributorName("62cc35809397e92ec0263d7b");

		assertEquals("ctb1", name);
	}
	
	@Test
	public void testFailedGetContributorName() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\": \"not found\"}";
			}
			
		}
		);	
		
		String name = dm.getContributorName("62cc35809397e92ec0263d7c");
		
		assertNull(name);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testErrorGetContributorName() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
//				{"status":"error","data":{"stringValue":"\"001\"","valueType":"string","kind":"ObjectId","value":"001","path":"_id","reason":{},"name":"CastError","message":"Cast to ObjectId failed for value \"001\" (type string) at path \"_id\" for model \"Contributor\""}}
				return "{\"status\":\"error\",\"data\":{\"stringValue\":\"\\\"001\\\"\",\"valueType\":\"string\",\"kind\":\"ObjectId\",\"value\":\"001\",\"path\":\"_id\",\"reason\":{},\"name\":\"CastError\",\"message\":\"Cast to ObjectId failed for value \\\"001\\\" (type string) at path \\\"_id\\\" for model \\\"Contributor\\\"\"}}";
			}
			
		}
		);	
		
		String name = dm.getContributorName("001");
		
		assertNull(name);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionErrorInGetContributorName() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return null;
			}
			
		}
		);	
		
		String name = dm.getContributorName(null);
		
//		assertNull("encountered an exception error.", name);
	}
	
	
//	@Test
//	public void testGetContributorName() {
//		DataManager dm = new DataManager(new WebClient("localhost", 3001));	
//		
//		String name = dm.getContributorName("62cc35809397e92ec0263d7b");
//		
//		System.out.println(name);
//	}

}
