package OAuth_2.O;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import SerializationDeserialisation.GetCource;
import io.github.bonigarcia.wdm.WebDriverManager;

public class OAuth2 { 
 
	public static void main(String[] args) throws InterruptedException {
		
//		WebDriverManager.chromedriver().setup();
//		WebDriver driver= new ChromeDriver();
//		//driver.get("https://rahulshettyacademy.com/AutomationPractice/");
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
//		driver.findElement(By.cssSelector("[type='email']")).sendKeys("hasachari@gmail.com");
//		driver.findElement(By.cssSelector("[type='email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("[jsname='V67aGc']")).sendKeys(Keys.ENTER);	
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("[type='password']")).sendKeys("HasAchari@#1991");
//		driver.findElement(By.cssSelector("[type='password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(5000);
//		String url=driver.getCurrentUrl();
//		System.out.println(url);
		
 		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0ARtbsJoIdGmjyqxanIIlt5CPMjc-QGzEc610Il01RZYQ_es2mNpvYTaOKwGtDQzn6my_gQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		//url needs to be generated every time by singn in to get valid access token
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0]; 
		System.out.println(code);
		
    
// code keeps on changing and can be generated everytime using 
 //https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
 
	RestAssured.useRelaxedHTTPSValidation();//needs to be used while dealing with https site	
		
	String accessTokenResponse = given()
			.urlEncodingEnabled(false)
			.queryParams("code", code)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token")//.then().extract().response();
		.asString();
	
	System.out.println(accessTokenResponse);
	JsonPath jp = new JsonPath(accessTokenResponse);
	String access_token = jp.getString("access_token");
	
	System.out.println(access_token);
		
		
	GetCource response = given().queryParam("access_token", access_token).expect().defaultParser(Parser.JSON).
		when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCource.class);
		
		System.out.println(response.getLinkedIn());
		
	}
		
		
		
	}