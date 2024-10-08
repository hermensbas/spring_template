package nl.spring.template.project.app.opera.config;

// configuration for creating new webclient and propagate the correlation-id place holder
public class WebClientConfig {

    // Webclient, Java HttpClient, okhttp,
    // apache httpClient, blocking, none blocking?
    // reactive, futures, traditional threads

    //   @Bean
//    public HttpRequest.Builder HttpClient() {
//        return HttpRequest.newBuilder().build();
//    }
//
//    @Bean
//    @Autowired
//    public HttpRequest.Builder HttpClient(
//        final HttpClient httpClient,
//        final MeterRegistry meterRegistry,
//        final ObservationRegistry observationRegistry) {
//
//        return HttpRequest.newBuilder()
//            .header("X-Our-Header-1", "value1");
//
//        return MicrometerHttpClient.instrumentationBuilder(httpClient, meterRegistry)
//            .observationRegistry(observationRegistry)
//            .build();
//    }

}


