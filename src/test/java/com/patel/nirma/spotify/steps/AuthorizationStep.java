package com.patel.nirma.spotify.steps;

import com.patel.nirma.spotify.SpotifyConstants;
import io.restassured.http.Header;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.Base64;

public class AuthorizationStep extends AbstractStep {

    private EnvironmentVariables environmentVariables;
    
    public Response getAuthorizationResponse() {

        String baseUrl =  EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(SpotifyConstants.PROP_SPOTIFY_AUTH_BASE_URL);
        String tokenPath =  EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(SpotifyConstants.PROP_SPOTIFY_AUTH_TOKEN_PATH);
        String clientId =  EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(SpotifyConstants.PROP_SPOTIFY_AUTH_CLIENT_ID);
        String clientSecret =  EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(SpotifyConstants.PROP_SPOTIFY_AUTH_CLIENT_SECRET);

        String base64Credentials = encodeBase64(clientId + ":" + clientSecret);

        String reqBody = "grant_type=client_credentials";

        return SerenityRest.given()
                .spec(getSpecification(baseUrl, tokenPath))
                .header(new Header(SpotifyConstants.HEADER_NAME_AUTHORIZATION, "Basic " + base64Credentials))
                .contentType("application/x-www-form-urlencoded")
                .body(reqBody)
                .when()
                .post();
    }


    private String encodeBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
