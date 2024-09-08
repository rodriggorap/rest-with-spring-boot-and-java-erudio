package br.com.rodrigo.integrationtests.testcontainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstratictIntegrationTest.initializer.class)
public class AbstratictIntegrationTest {

	static class initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.29");

		private static void startContainers() {
			Startables.deepStart(Stream.of(mysql)).join();
		}

		private static Map<String, String> createConnectionConfiguration() {
			return Map.of("spring.datasource.url", mysql.getJdbcUrl(), "spring.datasource.username",
					mysql.getUsername(), "spring.datasource.password", mysql.getPassword());
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();
			ConfigurableEnvironment environmet = applicationContext.getEnvironment();
			MapPropertySource testcontainers = new MapPropertySource("testcontainers",
					(Map) createConnectionConfiguration());
			environmet.getPropertySources().addFirst(testcontainers);

		}

	}

}
