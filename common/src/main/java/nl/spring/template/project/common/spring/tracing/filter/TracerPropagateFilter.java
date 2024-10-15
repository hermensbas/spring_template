package nl.spring.template.project.common.spring.tracing.filter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.spring.template.project.common.spring.tracing.context.TracerContext;
import nl.spring.template.project.common.spring.tracing.context.TracerContextHolder;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TracerPropagateFilter extends OncePerRequestFilter {

    public static final String X_CORRELATION_ID = "X-Correlation-Id";

    @Override
    protected void doFilterInternal(
        @NonNull
        final HttpServletRequest request,
        @NonNull
        final HttpServletResponse response,
        @NonNull
        final FilterChain filterChain)
        throws ServletException, IOException {

        var correlationId = request.getHeader(X_CORRELATION_ID);
        if (correlationId == null ||
            correlationId.trim().isEmpty() ||
            !isValid(correlationId)) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                String.format("The '%s' header is required", X_CORRELATION_ID));
            return;
        }

        // Add to our custom context holder (mainly for learning purpose)
        TracerContextHolder.setContext(new TracerContext(correlationId), false);

        // Add the logback MDC in order to expose value to our logging pattern.
        MDC.put("cid", correlationId);

        // set correlation on the response header.
        response.setHeader(X_CORRELATION_ID, correlationId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            TracerContextHolder.clear();
            MDC.clear();
        }
    }

    //If the shouldNotFilterAsyncDispatch() method returns true, then the filter will not be called for the
    // subsequent async dispatch. However, if it returns false, the filter will be invoked for each async
    // dispatch, exactly once per thread
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

    private boolean isValid(final String correlationId) {
        return true;
    }
}
