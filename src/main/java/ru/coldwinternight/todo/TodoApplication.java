package ru.coldwinternight.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ru.coldwinternight.todo.configuration.JwtConfig;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class TodoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

}
