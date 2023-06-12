package libraryAPI;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import googlePlaceAPI.AddPlacaePayload;
import googlePlaceAPI.ReuseableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class AddBook {
	
	@Test(dataProvider = "bookData")
	public void addBook(String bookName,String isbn, 
						String aisle, String author) 
	{
		RestAssured.baseURI = "http://216.10.245.166"; 
		
		String addBookResponse =
				given().log().all()
				.header("Content-Type","application/json")
				.body(AddPlacaePayload.addBookDetail(bookName,isbn,aisle,author))
				.when().post("/Library/Addbook.php")
				.then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		JsonPath js =ReuseableMethod.rawToJson(addBookResponse);
		String ID = js.getString("ID"); 
		System.out.println("Newly Added Book ID "+ID);			
	}
	
	@DataProvider(name = "bookData")
	public Object [][] bookData()
	{
		return new Object [][] {{"A","scvbn","456214","j k"},
								{"B","bvhi","563564","l k"},
								{"C","uiuygui","168946","p k"},
								{"D","ioiuouibv","56326","a k"}};
	}

}
