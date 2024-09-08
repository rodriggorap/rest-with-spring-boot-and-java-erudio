package br.com.rodrigo.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.rodrigo.configs.TestConfigs;
import br.com.rodrigo.integrationtests.testcontainers.AbstratictIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTests extends AbstratictIntegrationTest {

	@Test
	public void shouldDisplaySwaggerUiPage() {
		var content = given().basePath("/swagger-ui/index.html").port(TestConfigs.SERVER_PORT).when().get().then()
				.statusCode(200).extract().body().asString();
		assertTrue(content.contains("Swagger UI"));
	}

}
