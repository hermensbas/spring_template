package nl.spring.template.project.app.opera.config;

import nl.spring.template.project.app.opera.config.properties.OzonProperties;
import nl.spring.template.project.common.okhttp.HttpClient;
import nl.spring.template.project.common.spring.tracing.config.TracerClientAbstractConfig;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties({OzonProperties.class})
public class WebClientConfig extends TracerClientAbstractConfig {

    @Bean
    @Autowired
    public HttpClient<OzonProperties> ozonClient(
        final OkHttpClient.Builder tracerBuilder,
        final OzonProperties properties) {

        final var connectionPool = new ConnectionPool(5, 5, TimeUnit.MINUTES);
        final var client = tracerBuilder
            .callTimeout(5, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .connectionPool(connectionPool)
            .build();

        return new HttpClient<>(properties, client);

    }
}


