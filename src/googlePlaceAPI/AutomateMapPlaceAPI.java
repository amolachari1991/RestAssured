package googlePlaceAPI;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

public class AutomateMapPlaceAPI {
	static String placeId;
	static String newAddress;

	public static void main(String[] args) throws IOException {

//given - all input details
//when - Submit the API => resource,http method
//Then - validate the response
//Add Place =>Update place with new address =>Get place to validate if new address is present in response

		//Add Place 
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = //stored response in variable
		given().log().all()
		.queryParams("key","qaclick123")
		.header("Content-Type","application/json")
		//.body(AddPlacaePayload.addPlaace())
		.body(new String(Files.readAllBytes(Paths.get
				("D:\\RahulSheetyRestAPI\\JsonFilePath\\AddPlaceJson.json"))))
		//Alternate way to directly pass static(not changing at runtime).json file in body method
		
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		System.out.println(response);//print response in console
		JsonPath js = ReuseableMethod.rawToJson(response);
		 placeId = js.getString("place_id");
		System.out.println(placeId);
		System.out.println("************************************************************");
		 
		//Update place
		
		newAddress ="sanvatsar" ;
		
		given().log().all()
		.queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.body(AddPlacaePayload.updatePlaace())
		.when().put("/maps/api/place/update/json")
		.then().log().all(). assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		System.out.println("************************************************************");

		//Get Place 
		 String getPlaceResponse1 =
		given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js11 = ReuseableMethod.rawToJson(getPlaceResponse1);//for parsing json 
		String actualAddress = js11.getString("address");
		System.out.println(actualAddress);
		System.out.println("************************************************************");

		
		//Assert to compare actual & expected address values
		Assert.assertEquals(actualAddress, newAddress);
		System.out.println("************************************************************");

		
		//Delete Place
		
		String deleteResponse =
		given().log().all().queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.body(AddPlacaePayload.deletePlaace())
		.when().delete("/maps/api/place/delete/json")
		.then().log().all().statusCode(200).extract().response().asString();
		
		System.out.println(deleteResponse);
		
		JsonPath js_delete = ReuseableMethod.rawToJson(deleteResponse);//for parsing json 
		String actualstatus = js_delete.getString("status");
		System.out.println(actualstatus);
		System.out.println("************************************************************");

		
		//Get Place (This operation will fail as we have deleted placae in delete place code)
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
				
		JsonPath js12 = ReuseableMethod.rawToJson(getPlaceResponse);//for parsing json 
		String actualAddress1 = js12.getString("address");
		System.out.println(actualAddress1);


	}
	
}
