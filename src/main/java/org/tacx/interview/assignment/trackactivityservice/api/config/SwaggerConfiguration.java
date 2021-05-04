package org.tacx.interview.assignment.trackactivityservice.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration to enable swagger doc. swagger-ui won't be enabled during execution for
 * test cases.
 */
@Slf4j
@Profile("!test")
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	private static final String ORG_TACX_INTERVIEW_ASSIGNMENT_TRACTACTIVITYSERVICE = "org.tacx.interview.assignment.trackactivityservice";

	private static final String ACTIVITY_SERVICE_API = "Activity Service API";

	private static final String TRACK_YOUR_ACTIVITIES = "Track your Activities";

	/**
	 * Custom description about the Activity Service API.
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors
						.basePackage(ORG_TACX_INTERVIEW_ASSIGNMENT_TRACTACTIVITYSERVICE))
				.paths(PathSelectors.any()).build()
				.apiInfo(new ApiInfo(ACTIVITY_SERVICE_API, TRACK_YOUR_ACTIVITIES, "MVP",
						"Terms of Services",
						new Contact("Anand Aili", "https://github.com/AnandAili",
								"anandaili08@gmail.com"),
						"License: Open Source", "license URL"));

	}

}
