package steps;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.Rule;
import org.stringtemplate.v4.ST;

import java.sql.SQLOutput;
import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class CreateCustomerWithValidDataSteps {
    private final static String PROTOCOL = "http://";
    private final static String HOST_NAME = "loclhost";
    private final static String PATH_NAME = "/rest/api/create_customer";

    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    JSONObject requestBody = new JSONObject();

    int response_id;

//    WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());

    @Given("^User open register form on web service$")
    public void user_open_register_form_on_web_service() {
        //TODO open register form on register page
    }

    @When("^user filled the field id with input text: (.*)")
    public void user_filled_the_field_id_with_input_text(final int id) {
        requestBody.put("id", id);
        response_id = id;
    }

    @And("^filled the field first name with input text:(.*)")
    public void filled_the_field_first_name_with_input_text(final String first_name) {
        requestBody.put("first_name", first_name);
    }

    @And("^filled the field last name with input text:(.*)")
    public void filled_the_field_last_name_with_input_text(final String last_name) {
        requestBody.put("last_name", last_name);
    }

    @And("^filled the field age with input text: (.*)")
    public void filled_the_field_age_with_input_text(final int age) {
        requestBody.put("age", age);
    }

    @And("^filled the field active with input text: (.*)")
    public void filled_the_field_active_with_input_text(final boolean active) {
        requestBody.put("active", active);
    }

    @And("^filled the field date of birth with input text: (.*)")
    public void filled_the_field_date_of_birth_with_input_text(final String date_of_birth) {
        requestBody.put("date_of_birth", date_of_birth);
    }

    @And("^press submit")
    public void press_submit() {

        WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();

        configureFor("localhost", wireMockServer.port());
        stubFor(post(urlEqualTo("/rest/api/customer"))
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

    @Then("^the status code is (.*)")
    public void status_code_is(final int status_code) {

        assertThat(response.getStatusCode(), is(status_code));
        assertThat(response.getStatusLine().contains("successfully created"), is(true));;
    }

}
