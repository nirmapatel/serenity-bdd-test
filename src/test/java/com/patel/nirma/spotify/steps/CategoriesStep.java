package com.patel.nirma.spotify.steps;

import com.patel.nirma.spotify.SpotifyConstants;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

import java.util.List;

public class CategoriesStep extends AbstractStep {

    public Response browseCategories(String token, List<List<String>> params) {

        final RequestSpecification reqSpec = SerenityRest.given()
                .spec(getSpecification(SpotifyConstants.SPOTIFY_API_BASE_URL, SpotifyConstants.SPOTIFY_CATEGORIES_PATH))
                .header(new Header("Authorization", "Bearer " + token));

        params.forEach(list -> reqSpec.queryParam(list.get(0), list.get(1)));

        return reqSpec
                .when()
                .get();
    }

    public Response browseCategoriesWithoutToken(List<List<String>> params) {

        final RequestSpecification reqSpec = SerenityRest.given()
                .spec(getSpecification(SpotifyConstants.SPOTIFY_API_BASE_URL, SpotifyConstants.SPOTIFY_CATEGORIES_PATH));

        params.forEach(list -> reqSpec.queryParam(list.get(0), list.get(1)));

        return reqSpec
                .when()
                .get();
    }
    
}
