package com.test;

import org.testng.annotations.Test;

import ALL_POJO.CreateUserPOJO;
import ALL_POJO.DeleteUserPOJO;
import ALL_POJO.UpdateUserPOJO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class TekarchAPITests {
	
	
	public String login()
	{
		Response res=RestAssured.given().contentType(ContentType.JSON)
				.body("{\"username\":\"archana.dhokane@ta.com\",\"password\":\"archana.dhokane@123\"}")
				.when()
				.post("https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/login");
		res.prettyPrint();
		String token=res.jsonPath().get("[0].token");
		System.out.println("Extracted token: "+token);
		return token;
	}
	
	@Test(priority=2)
	public void getUsers()
	{
	
		Header header1=new Header("token",login());
		Response res=RestAssured.given().header(header1)
				.when()
				.get("https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/getdata");
				
		res.then().statusCode(200);
		String accNum=res.jsonPath().get("[0].accountno");
		System.out.println("First set of account number: "+accNum);
		System.out.println("total number of records: "+res.jsonPath().get("$.size()"));
				
	}
	
	@Test(priority=1)
	
	public void createUsers()
	{
		CreateUserPOJO obj=new CreateUserPOJO();
		obj.setAccountno("TA-4444444");
		obj.setDepartmentno("1");
		obj.setSalary("11111");
		obj.setPincode("111111");
		
		Header header1=new Header("token",login());
		Response res=RestAssured.given().header(header1)
				.contentType(ContentType.JSON)
				.body(obj)
				.when()
				.post("https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/addData");
			String value=res.jsonPath().get("status");
			System.out.println("Response status: "+value);
	}
@Test(priority=3)
	
	public void updateUsers()
	{
	UpdateUserPOJO obj=new UpdateUserPOJO();
	obj.setAccountno("TA-4444333");
	obj.setDepartmentno(1);
	obj.setSalary(11111);
	obj.setPincode(111111);
		Header header1=new Header("token",login());
		Response res=RestAssured.given().header(header1)
				.contentType(ContentType.JSON)
				.body(obj)
				.when()
				.put("https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/updateData");
				
			String value=res.jsonPath().get("status");
			System.out.println("Response status: "+value);
	}

@Test(priority=4)

public void deleteUsers()
{
	DeleteUserPOJO obj=new DeleteUserPOJO();	
	obj.setId("35QsHlnlRey1FgTI7dlY");
	obj.setUserid("i4LcNXqAFuI39rMbEAn9");
	
	Header header1=new Header("token",login());
	Response res=RestAssured.given().header(header1)
			.contentType(ContentType.JSON)
			.body(obj)
			.when()
			.delete("https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/deleteData");
			
	
		String value=res.jsonPath().get("status");
		System.out.println("Response status: "+value);
}
	
}
