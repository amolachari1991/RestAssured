package googlePlaceAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class SumOfAllCources {
	
	@Test
	public void sumOfAllCources()
	{
		System.out.println("//6. Verify if Sum of all Course prices matches with Purchase Amount");
		String APIResponse = AddPlacaePayload.courcePrice(); // Mocking API response when API is not ready
		JsonPath js = ReuseableMethod.rawToJson(APIResponse);
		int courses = js.getInt("courses.size()");
		int sum = 0;
		for(int i=0;i<courses;i++)
		{
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int amount =price*copies;
			sum=sum+amount;
		}
		System.out.println("Sum of all Course prices "+sum);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(purchaseAmount, sum);
		
	}
	


}
