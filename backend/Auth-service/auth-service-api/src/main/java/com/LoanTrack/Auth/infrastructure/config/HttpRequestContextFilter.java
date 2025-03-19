package com.LoanTrack.Backend.infrastructure.config;

import com.LoanTrack.Backend.infrastructure.util.SecurityHeaders;
import com.LoanTrack.Backend.infrastructure.util.StaticContextAccessor;
import org.apache.commons.lang3.StringUtils;

import com.LoanTrack.Backend.domain.util.HeaderName;
import com.LoanTrack.Backend.domain.util.HttpRequestContextHolder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

public class HttpRequestContextFilter implements Filter, HeaderName {

	private static final String PATH_SWAGGER = "swagger";
	private static final String PATH_APIS = "api-docs";

	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
		final HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper((HttpServletRequest) request);
		final String token = wrapper.getHeader(API_TOKEN);
		final String idSystem = wrapper.getHeader(ID_SYSTEM);
		final String authorizationType = wrapper.getHeader(AUTHORIZATION_TYPE);
		final String apiUsername = wrapper.getHeader(API_USERNAME);
		final String key = wrapper.getHeader(HEADER_KEY);

		try {
			if (StringUtils.isEmpty(token)) {
				HttpRequestContextHolder.setToken(null);
			} else {
				HttpRequestContextHolder.setToken(token);
			}
			if (StringUtils.isEmpty(idSystem)) {
				HttpRequestContextHolder.setIdSystem(null);
			} else {
				HttpRequestContextHolder.setIdSystem(idSystem);
			}
			if (StringUtils.isEmpty(authorizationType)) {
				HttpRequestContextHolder.setAuthorization(null);
			} else {
				HttpRequestContextHolder.setAuthorization(authorizationType);
			}
			if (StringUtils.isEmpty(apiUsername)) {
				HttpRequestContextHolder.setApiUsername(null);
			} else {
				HttpRequestContextHolder.setApiUsername(apiUsername);
			}

			SecurityHeaders secHeader = StaticContextAccessor.getBean(SecurityHeaders.class);
			Boolean isAuth = secHeader.validate(key,token);

			String httpRequestPath = ((HttpServletRequest) request).getServletPath();
			if(httpRequestPath.contains(PATH_SWAGGER) || httpRequestPath.contains(PATH_APIS)){
				isAuth = Boolean.TRUE;
			}
			if(!isAuth){
				((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
			else{
				chain.doFilter(request, response);
			}

		} catch (Exception e) {
			((HttpServletResponse) response).setStatus(400);
		}
	}
}

