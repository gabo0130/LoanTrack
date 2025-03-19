package com.LoanTrack.Backend.infrastructure.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean systemUsuarioContextFilter() {
		final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
		filterRegBean.setFilter(new HttpRequestContextFilter());
		filterRegBean.addUrlPatterns(new String[] { "/*" });
		filterRegBean.setEnabled(Boolean.TRUE);
		filterRegBean.setName("Usuario Context Filter");
		filterRegBean.setAsyncSupported(Boolean.TRUE);
		filterRegBean.setOrder(1);
		return filterRegBean;
	}
}
