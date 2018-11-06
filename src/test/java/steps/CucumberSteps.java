package steps;

import com.github.tomakehurst.wiremock.WireMockServer;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CucumberSteps {
    private final static String PROTOCOL = "http://";
    private final static String HOST_NAME = "loclhost";
    private final static String PATH_NAME = "/rest/api/create_customer";

    private Response response;
    private JSONObject requestBody = new JSONObject();

    String response_id;
    String customer_id;

    @Given("^User open register form on web service$")
    public void openRegisterForm() {
        //TODO create Register Form
    }

    @When("^user filled the field id with input text: (.*)")
    public void fillId(final String id) {
        requestBody.put("id", id);
        response_id = id;
    }

    @And("^filled the field first name with input text:(.*)")
    public void fillFirstName(final String first_name) {
        requestBody.put("first_name", first_name);
    }

    @And("^filled the field last name with input text:(.*)")
    public void fillLastName(final String last_name) {
        requestBody.put("last_name", last_name);
    }

    @And("^filled the field age with input text:(.*)")
    public void fillAge(final String age) {
        requestBody.put("age", age);
    }

    @And("^filled the field active with input text: (.*)")
    public void fillActive(final String active) {
        requestBody.put("active", active);
    }

    @And("^filled the field date of birth with input text: (.*)")
    public void fillDateOfBirth(final String date_of_birth) {
        requestBody.put("date_of_birth", date_of_birth);
    }

    @And("^press submit")
    public void submitCreateCustomerWithValidData() {
        WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();

        configureFor("localhost", wireMockServer.port());
        stubFor(post(urlEqualTo("/rest/api/customer"))
                .withRequestBody(matchingJsonPath("$.id"))
                .withRequestBody(matchingJsonPath("$.first_name"))
                .withRequestBody(matchingJsonPath("$.last_name"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withStatusMessage("successfully created")
                        .withBody("{\n" +
                                "      \""+response_id+"\": int,\n" +
                                "      \"status\": \"successfully created\"\n" +
                                "     }")));

        RequestSpecification request = RestAssured.given();
        request.body(requestBody.toString());

        response = request.post("http://localhost:" + wireMockServer.port() + "/rest/api/customer");

        wireMockServer.stop();
    }

    @And("^with invalid data press submit$")
    public void submitCreateCustomerWithInvalidData() {
        WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();

        configureFor("localhost", wireMockServer.port());
        stubFor(post(urlPathEqualTo("/rest/api/customer/"))
                .withRequestBody(matchingJsonPath("$.id"))
                .withRequestBody(matchingJsonPath("$.first_name"))
                .withRequestBody(matchingJsonPath("$.last_name"))
                .withRequestBody(containing(""))
                .willReturn(aResponse()
                        .withStatus(401)));

        RequestSpecification request = RestAssured.given();
        request.body(requestBody.toString());

        response = request.post("http://localhost:" + wireMockServer.port() + "/rest/api/customer/");

        wireMockServer.stop();
    }

    @Given("open customer search page")
    public void openCustomerSearchPage() {
        //TODO Create Customer Search Page
    }

    @When("^customer filled search field: (.*)")
    public void fillSearchId(String id) {
        requestBody.put("id", id);
        customer_id = id;
    }

    @And("^press search")
    public void searchGetCustomerByValidId() {
        WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();

        configureFor("localhost", wireMockServer.port());
        stubFor(get(urlEqualTo("/rest/api/get_customer/"+customer_id))
                .willReturn(aResponse()
                        .withStatus(200)));

        RequestSpecification request = RestAssured.given();
        request.body(requestBody.toString());

        response = request.when().get("http://localhost:" + wireMockServer.port()
                + "/rest/api/get_customer/"+customer_id);
        System.out.println("response: " + response.prettyPrint());

        wireMockServer.stop();
    }

    @When("with invalid data press search")
    public void searchGetCustomerByInvalidId() {
        WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();

        configureFor("localhost", wireMockServer.port());
        stubFor(get(urlEqualTo("/rest/api/get_customer/"+customer_id))
                .willReturn(aResponse()
                        .withStatus(404)));

        RequestSpecification request = RestAssured.given();
        request.body(requestBody.toString());

        response = request.when().get("http://localhost:" + wireMockServer.port()
                + "/rest/api/get_customer/"+customer_id);
        System.out.println("response: " + response.prettyPrint());

        wireMockServer.stop();
    }

    @When("^customer filled search field from table below$")
    public void fillSearchId(DataTable dataTable) {
        List<String> list = dataTable.asList(String.class);
        requestBody.put("id", list.get(1));
    }


    @Then("^the status code is (.*)")
    public void statusCodeIs(final int status_code) {

        assertThat(response.getStatusCode(), is(status_code));
    }

}
