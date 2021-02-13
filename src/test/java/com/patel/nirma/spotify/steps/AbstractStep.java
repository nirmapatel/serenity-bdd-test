package com.patel.nirma.spotify.steps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class AbstractStep {
    
    protected RequestSpecification getSpecification(String baseURI, String basePath) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .build();
    }
}
