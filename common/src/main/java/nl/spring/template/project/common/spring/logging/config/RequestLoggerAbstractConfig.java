package nl.spring.template.project.common.spring.logging.config;

import nl.spring.template.project.common.spring.logging.interceptor.RequestLogInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
@ConditionalOnProperty(prefix = "application.spring-interceptor.request-logger", name = "enabled", havingValue = "true", matchIfMissing = true)
public abstract class RequestLoggerAbstractConfig implements WebMvcConfigurer {

    private static final String URL_PATTERN_ALL = "/**";

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLogInterceptor()).addPathPatterns(URL_PATTERN_ALL);
        //registry.addInterceptor(new AnotherExampleInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");
    }
}

