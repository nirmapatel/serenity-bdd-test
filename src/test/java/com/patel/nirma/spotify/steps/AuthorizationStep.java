package com.patel.nirma.spotify.steps;

import com.patel.nirma.spotify.SpotifyConstants;
import io.restassured.http.Header;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import java.util.Base64;

public class AuthorizationStep extends AbstractStep {

    public Response getAuthorizationResponse() {
        
        String base64Credentials = encodeBase64(SpotifyConstants.SPOTIFY_CLIENT_ID + ":" + SpotifyConstants.SPOTIFY_CLIENT_SECRET);

        String reqBody = "grant_type=client_credentials";

        return SerenityRest.given()
                .spec(getSpecification(SpotifyConstants.SPOTIFY_AUTH_BASE_URL, SpotifyConstants.SPOTIFY_TOKEN_PATH))
                .header(new Header("Authorization", "Basic " + base64Credentials))
                .contentType("application/x-www-form-urlencoded")
                .body(reqBody)
                .when()
                .post();
    }


    private String encodeBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
