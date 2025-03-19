package com.LoanTrack.Backend.infrastructure.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	private Environment environment;

	@Bean
	WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(environment.getProperty("cors.ip.authorized"))
						.allowedHeaders("*").allowedMethods("POST", "GET", "PUT", "OPTIONS", "POST", "DELETE", "PATH");
			}

			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				registry.addRedirectViewController("/", "/swagger-ui.html");
			}

		};
	}

	@Bean
	LocaleResolver localeResolver() {
		Locale defaultLocale = new Locale("es", "CO");
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(defaultLocale);
		Locale.setDefault(defaultLocale);
		return localeResolver;
	}

	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

}
