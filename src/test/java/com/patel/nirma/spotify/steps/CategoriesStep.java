package com.patel.nirma.spotify.steps;

import com.patel.nirma.spotify.SpotifyConstants;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.List;

public class CategoriesStep extends AbstractStep {

    private EnvironmentVariables environmentVariables;
    
    public Response browseCategories(String token, List<List<String>> params) {

        String baseUrl =  EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(SpotifyConstants.PROP_SPOTIFY_API_BASE_URL);
        String categoriesPath =  EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(SpotifyConstants.PROP_SPOTIFY_CATEGORIES_API_PATH);
        
        final RequestSpecification reqSpec = SerenityRest.given()
                .spec(getSpecification(baseUrl, categoriesPath))
                .header(new Header(SpotifyConstants.HEADER_NAME_AUTHORIZATION, "Bearer " + token));

        params.forEach(list -> reqSpec.queryParam(list.get(0), list.get(1)));

        return reqSpec
                .when()
                .get();
    }

    public Response browseCategoriesWithoutToken(List<List<String>> params) {

        String baseUrl =  EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(SpotifyConstants.PROP_SPOTIFY_API_BASE_URL);
        String categoriesPath =  EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(SpotifyConstants.PROP_SPOTIFY_CATEGORIES_API_PATH);

        final RequestSpecification reqSpec = SerenityRest.given()
                .spec(getSpecification(baseUrl, categoriesPath));

        params.forEach(list -> reqSpec.queryParam(list.get(0), list.get(1)));

        return reqSpec
                .when()
                .get();
    }
    
}
