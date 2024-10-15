package nl.spring.template.project.common.spring.jackson.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;

//@Configuration
public abstract class JacksonAbstractConfig {

    @Bean
    public JsonFactory createJsonJacksonFactory() {
        return new JsonFactory();
    }

    @Bean
    public ObjectMapper createJsonJacksonObjectMapper(final JsonFactory jsonFactory) {

        return new ObjectMapper(jsonFactory)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(SerializationFeature.WRAP_ROOT_VALUE)
            .disable(DeserializationFeature.UNWRAP_ROOT_VALUE)
            .enable(SerializationFeature.INDENT_OUTPUT);
    }

}


