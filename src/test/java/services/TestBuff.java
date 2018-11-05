package services;


import com.github.tomakehurst.wiremock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class TestBuff {

    public static void main() {
        WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());



        System.out.println();
    }


}
