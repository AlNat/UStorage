package dev.alnat.ustorage;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Необходимо для сборки классческого war-ника
 * https://www.baeldung.com/spring-boot-servlet-initializer
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UStorageApplication.class);
	}

}
