# spring_template



# Jaeger tracing

[OpenTelemetry OTLP http-request](https://opentelemetry.io/docs/specs/otlp/#otlphttp-request)

Application.yml
```yml

# spring default sample rate is 10% we set ot 100%, migh want to reconsider the sample rate on production.
management.tracing.sampling.probability=1.0
management.otlp.tracing.endpoint=http://localhost:4318/v1/traces
```

Jeager basic config:
```
jaeger:
image: jaegertracing/all-in-one:latest
ports:
- "16686:16686"
- "4318:4318"
- "4317:4317"
```