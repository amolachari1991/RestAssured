package jiraAPI;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import googlePlaceAPI.AddPlacaePayload;
import googlePlaceAPI.ReuseableMethod;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class AddCommentToJiraBug {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://localhost:8080";
		
		//Generating session authntication token
		
		SessionFilter session = new SessionFilter();//Alternative way for JsonPath class
		//no need of extracting response & parsing it to JsonPath to extract sessionId
		
		given().relaxedHTTPSValidation().log().all()
		//relaxedHTTPSValidation()=>used while dealing with https sites to bypass certificate validation
		.header("Content-Type","application/json")
		.body(AddPlacaePayload.sessionBody())
		.filter(session).
		when().post("/rest/auth/1/session").
		then().log().all().assertThat().statusCode(200); 
		
		//Adding comment to bug
		String comment = "Hi! How are You";
		String givenComment = given().log().all()
		.pathParam("id","10100")//path parameters defined {inside} in resource
		.header("Content-Type","application/json")
		.body(AddPlacaePayload.addCommentBody(comment))
		.filter(session).
		when().post("/rest/api/2/issue/{id}/comment").//path parameters attribute will get value at runtime
		then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js = ReuseableMethod.rawToJson(givenComment);
		int commentId =js.getInt("id");
		
		//Adding an attachment
		
		given().log().all().header("X-Atlassian-Token","no-check")
		.header("Content-Type","multipart/form-data")//"multipart/form-data"=>specifies type of file attached
		.filter(session).pathParam("id","10100")
		.multiPart("file", new File("jira.txt")).//multipart method used to send attachment in restassured
		when().post("/rest/api/2/issue/{id}/attachments").
		then().assertThat().statusCode(200);
		
		//get issue details and parsing complex Json
		
		//Path Parameter => rerutes u to intended subresource
		//Query Parameter => filters intended result from existing results
		
		String issueDetail = given().log().all()
		.filter(session).pathParam("id","10100")
		.queryParam("fields", "comment").
		when().get("/rest/api/2/issue/{id}").
		then().log().all().extract().response().asString();	
		JsonPath js1 = ReuseableMethod.rawToJson(issueDetail);
		int commentCount = js1.get("fields.comment.comments.size()");
		for(int i=0;i<commentCount;i++)
		{
			 int actualID = js1.getInt("fields.comment.comments["+i+"].id");
			 System.out.println(actualID);
			 if(actualID==commentId)
			 {
				String Message = js1.get("fields.comment.comments["+i+"].body");
				System.out.println(Message);
				Assert.assertEquals(comment, Message);
			 }
				
		}
		
		
		
	}

}
