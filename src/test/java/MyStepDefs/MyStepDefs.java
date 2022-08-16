package MyStepDefs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import models.Student;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.Endpoints;
import utils.TestNGListener;

import static io.restassured.RestAssured.given;
import static utils.JsonInputParser.data;

public class MyStepDefs {
JsonPath jsonpath;
    Student users;
    JSONObject jsonobject;
    ObjectMapper objectMapper=new ObjectMapper();
    Response response;
    Student responseUser;

    @Given("create a student")
    public void userDetails() throws JsonProcessingException {
        jsonobject = (JSONObject) TestNGListener.userdata.get("createRequest");


    }

    @When("updating the student")
    public void creatingAUser() throws JsonProcessingException {

        users =new Student(

                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(200).extract().response();
        responseUser=objectMapper.readValue(response.asString(), Student.class);
    }
    @Then("the student is updated")
    public void theStudentIsCreated() throws JsonProcessingException {

        Student responseUser =  objectMapper.readValue(response.asString(), Student.class);
        Assert.assertEquals(users.getEmail(),responseUser.getEmail());

    }


    @When("creating a student with no name")
    public void creatingAStudentWithNoName()  {
        users =new Student(

                null,
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(400).extract().response();


    }

    @Then("Name is required error thrown")
    public void nameIsRequiredErrorThrown() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Name is required");
    }

    @When("creating a student with no age")
    public void creatingAStudentWithNoAge() {

        users =new Student(

                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                0,
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(400).extract().response();


    }

    @Then("age is required error thrown")
    public void ageIsRequiredErrorThrown() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Age is required");
    }

    @When("creating a student with no email")
    public void creatingAStudentWithNoEmail() {

        users =new Student(

                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get(null));
                response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(400).extract().response();


    }

    @Then("Email is required error thrown")
    public void emailIsRequiredErrorThrown() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Email is required");

    }

    @When("creating a student with no id")
    public void creatingAStudentWithNoId() {

        users =new Student(

                (String) jsonobject.get("name"),
                0,
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(500).extract().response();



    }

    @Then("Internal server error thrown")
    public void internalServerErrorThrown() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("error"),"Internal Server Error");
    }

    @When("creating a student")
    public void creatingAStudent() throws JsonProcessingException {
        jsonobject = (JSONObject) data.get("createRequest");

        users =new Student(

                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(200).extract().response();
        responseUser=objectMapper.readValue(response.asString(), Student.class);

    }

    @And("updating a student")
    public void updatingAStudent() throws JsonProcessingException {
        jsonobject = (JSONObject) data.get("postRequest");

        users =new Student(

                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().put(Endpoints.studentEndPoint + "/" +responseUser.getId())
                .then()
                .statusCode(200).extract().response();
        responseUser=objectMapper.readValue(response.asString(), Student.class);


    }

    @Then("Student is updated")
    public void studentIsUpdated() throws JsonProcessingException {

        Student responseUser =  objectMapper.readValue(response.asString(), Student.class);
        Assert.assertEquals(users.getName(),responseUser.getName());

    }

    @Then("getting a student")
    public void gettingAStudent() {

        response=given()
                .body(users)
                .when().get(Endpoints.studentEndPoint)
                .then()
                .statusCode(200).extract().response();
    }

    @Then("student deleted")
    public void studentDeleted() throws JsonProcessingException {

        response=given()
                .body(users)
                .when().delete(Endpoints.studentEndPoint + "/" +responseUser.getId())
                .then()
                .statusCode(200).extract().response();


    }

    @And("updating without path")
    public void updatingWithoutPath() throws JsonProcessingException {

        jsonobject = (JSONObject) data.get("postRequest");

        users =new Student(

                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().put(Endpoints.studentEndPoint)
                .then()
                .statusCode(405).extract().response();


    }

    @Then("Method error thrown")
    public void methodErrorThrown() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("error"),"Method Not Allowed");
    }

    @And("deleting without path")
    public void deletingWithoutPath() {
        response=given()
                .body(users)
                .when().delete(Endpoints.studentEndPoint)
                .then()
                .statusCode(405).extract().response();

    }
}
