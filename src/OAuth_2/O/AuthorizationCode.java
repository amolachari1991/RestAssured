package OAuth_2.O;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.Keys;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.parsing.Parser;

import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;

import io.restassured.response.ResponseBody;

//import pojo.Api;
//
//import pojo.GetCourse;

public class AuthorizationCode {

	public static void main(String[] args) {
 		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0ARtbsJohXsHfeEwEV-ipTBEgNTj5zeCA85ZoeuJD9E44c4F5TIvq6xAlEwXr8qWPoAFnRg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0]; 
		System.out.println(code);
		String response = given()
				.urlEncodingEnabled(false)
				.queryParams("code", code)
				.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type","authorization_code")
				.queryParams("state", "verifyfjdss")
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token")//.then().extract().response();
				.asString();

		// System.out.println(response);

		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		System.out.println(accessToken);
		String r2 = given().contentType("application/json").
				queryParams("access_token", accessToken)
				.expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php")
				.asString();
		System.out.println(r2);
	}
}
