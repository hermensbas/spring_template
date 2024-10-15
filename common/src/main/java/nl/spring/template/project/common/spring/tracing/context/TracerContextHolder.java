package nl.spring.template.project.common.spring.tracing.context;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

import java.util.Optional;


public class TracerContextHolder {

    private static final ThreadLocal<TracerContext> localRequestContextHolder = new NamedThreadLocal<>(
        "TrackingContext");
    private static final ThreadLocal<TracerContext> inheritableRequestContextHolder = new NamedInheritableThreadLocal<>(
        "InheritableTrackingContext");

    public static void clear() {
        localRequestContextHolder.remove();
        inheritableRequestContextHolder.remove();
    }

    public static void setContext(
        @Nullable
        final TracerContext attributes,
        final boolean inheritable) {

        if (attributes == null) {
            clear();
        } else {
            if (inheritable) {
                inheritableRequestContextHolder.set(attributes);
                localRequestContextHolder.remove();
            } else {
                localRequestContextHolder.set(attributes);
                inheritableRequestContextHolder.remove();
            }
        }
    }

    public static Optional<TracerContext> getContext() {

        var context = localRequestContextHolder.get();
        if (context == null) {
            context = inheritableRequestContextHolder.get();
        }

        return Optional.ofNullable(context);
    }

}