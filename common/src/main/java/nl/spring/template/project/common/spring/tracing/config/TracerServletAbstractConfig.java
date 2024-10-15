package nl.spring.template.project.common.spring.tracing.config;

import nl.spring.template.project.common.spring.tracing.filter.TracerPropagateFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

//@Configuration
public abstract class TracerServletAbstractConfig {

    private static final String URL_PATTERN_ALL = "/*";

    @Bean
    @ConditionalOnProperty(prefix = "application.servlet-filter.tracer", name = "enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<TracerPropagateFilter> correlationIdPropagateFilter() {

        var filterRegistrationBean = new FilterRegistrationBean<TracerPropagateFilter>();
        filterRegistrationBean.setName(TracerPropagateFilter.class.getSimpleName());

        filterRegistrationBean.setFilter(new TracerPropagateFilter());
        filterRegistrationBean.addUrlPatterns(URL_PATTERN_ALL);
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }
}
