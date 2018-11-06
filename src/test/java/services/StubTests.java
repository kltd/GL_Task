package services;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;

import java.util.Date;

import io.restassured.specification.RequestSpecification;

public class StubTests {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8888);

    @Test
    public void statusMessage() {


        String someRandomString = String.format("%1$TH%1$TM%1$TS", new Date());
        JSONObject requestBody = new JSONObject();

        requestBody.put("id", "123");
        requestBody.put("firstName", "Marco");
        requestBody.put("lastName", "Jackson");
        requestBody.put("age", "");
        requestBody.put("active", "");
        requestBody.put("date_of_birth", "");

        //Given
        RequestSpecification request = RestAssured.given();
        request.header("POST", "/rest/api/customer");
        request.body(requestBody.toString());

        wireMockRule.stubFor(post(urlEqualTo("/rest/api/customer"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withStatusMessage("Everything was just fine!")
                        .withHeader("Content-Type", "text/plain")));


        Response response = request.post("http://localhost:" + 8888 + "/rest/api/customer");


        assertThat(response.getStatusCode(), is(200));


        assertThat(response.statusCode(), is(401));


    }
}
