package nl.spring.template.project.common.spring.tracing.okhttp;

import nl.spring.template.project.common.spring.tracing.context.TracerContext;
import nl.spring.template.project.common.spring.tracing.context.TracerContextHolder;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static nl.spring.template.project.common.spring.tracing.filter.TracerPropagateFilter.X_CORRELATION_ID;

public class TracerRequestInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(final Chain chain) throws IOException {

        final var correlationId = TracerContextHolder.getContext()
            .map(TracerContext::correlationId)
            .orElseThrow(() -> new RuntimeException("Could find correlationId in TracerContextHolder"));

        return chain.proceed(chain.request().newBuilder()
            .addHeader(X_CORRELATION_ID, correlationId)
            .build());
    }
}
