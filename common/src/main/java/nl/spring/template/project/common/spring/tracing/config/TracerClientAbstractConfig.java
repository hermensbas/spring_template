package nl.spring.template.project.common.spring.tracing.config;

import nl.spring.template.project.common.spring.tracing.okhttp.TracerRequestInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;

//@Configuration
public abstract class TracerClientAbstractConfig {

    @Bean
    public OkHttpClient.Builder createBuilder() {

        return new OkHttpClient()
            .newBuilder()
            .addNetworkInterceptor(new TracerRequestInterceptor());
    }
}
