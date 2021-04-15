package api.spec;

import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Spec {
    public static final RequestSpecification spec =  new RequestSpecBuilder()
            .setBaseUri("https://petstore.swagger.io")
            .setBasePath("/v2")
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .addFilter(new SwaggerCoverageRestAssured())
            .build();
}
