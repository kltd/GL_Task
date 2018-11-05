package steps;

import com.github.tomakehurst.wiremock.WireMockServer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GetCustomerByValidId {

    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    JSONObject requestBody = new JSONObject();
    int customer_id;

    @Given("^a customer exists with an id of (.*)")
    public void a_customer_exists_with_an_id(int id) {
        request = given().param("id",id);
        customer_id = id;
    }

    @When("^a user retrieves the customer by id$")
    public void a_user_retrieves_the_customer_by_id() {
        WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();

        configureFor("localhost", wireMockServer.port());
        stubFor(get(urlEqualTo("/rest/api/customer/"+customer_id))
                .willReturn(aResponse()
                        .withStatus(201)));


        response = request.when().get("http://localhost:" + wireMockServer.port() + "/rest/api/customer"+customer_id);
        System.out.println("response: " + response.prettyPrint());

        wireMockServer.stop();
    }

//    @Then("^the status code is (.*)")
//    public void get_customer_status_code_is(final int status_code) {
//
//        assertThat(response.getStatusCode(), is(status_code));
//    }
}
