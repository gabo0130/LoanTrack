package com.LoanTrack.Backend.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class ConfigBeans {

    @Value("${rest.template.connection.timeout}")
    private Long connectionTimeout;

    @Value("${rest.template.connection.request.timeout}")
    private Long connectionRequestTimeout;

    @Value("${rest.template.response.timeout}")
    private Long responseTimeout;

    @Value("${rest.template.pool.max.total}")
    private Integer poolMaxTotal;

    @Value("${rest.template.pool.default.max.per.route}")
    private Integer poolDefaultMaxPerRoute;

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return new RestTemplateBuilder()
                .requestFactory(this::requestFactory)
                .build();
    }

    private HttpComponentsClientHttpRequestFactory requestFactory() {
        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
                .setConnectionRequestTimeout(connectionRequestTimeout, TimeUnit.MILLISECONDS)
                .setResponseTimeout(responseTimeout, TimeUnit.MILLISECONDS)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(poolMaxTotal);
        connectionManager.setDefaultMaxPerRoute(poolDefaultMaxPerRoute);

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean(name = "customOpenAPI")
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("api-key", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("api-key"))
                        .addSecuritySchemes("api-token", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("api-token")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("api-key")
                        .addList("api-token"));
    }

}
