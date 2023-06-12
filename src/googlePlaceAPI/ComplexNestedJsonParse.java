package googlePlaceAPI;

import io.restassured.path.json.JsonPath;

public class ComplexNestedJsonParse {

	public static void main(String[] args) {
		String APIResponse = AddPlacaePayload.courcePrice(); // Mocking API response when API is not ready
		JsonPath js = ReuseableMethod.rawToJson(APIResponse);
		System.out.println("//1. Print No of courses returned by API");
		int courses = js.getInt("courses.size()");
		System.out.println(courses);
		System.out.println("//2.Print Purchase Amount");		
		int PurchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(PurchaseAmount);
		//3. Print Title of the first course
		System.out.println("//3. Print Title of the first course");	
		String TitleOfFirstCourse = js.getString("courses[0].title");
		System.out.println(TitleOfFirstCourse);		
		System.out.println("//4. Print All course titles and their respective Prices");	
		for(int i=0;i<courses;i++)
		{
			//System.out.println(js.getString("courses["+i+"].title").toString()); 
			String title =js.getString("courses["+i+"].title");
			System.out.println(title);
			//System.out.println(js.getString("courses["+i+"].price").toString()); 
			String price =js.getString("courses["+i+"].price");
			System.out.println(price);


		}		 
		System.out.println("//5. Print no of copies sold by RPA");
		for(int i=0;i<courses;i++)
		{
			//System.out.println(js.getString("courses["+i+"].title").toString()); 
			String title =js.getString("courses["+i+"].title");
			if(title.equalsIgnoreCase("RPA"))
			{
				int copiesSold = js.getInt("courses["+i+"].copies");
				System.out.println(copiesSold);
				break;
			}
		}		 

	}

}
