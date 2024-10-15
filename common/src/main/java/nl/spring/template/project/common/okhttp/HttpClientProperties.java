package nl.spring.template.project.common.okhttp;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
//@ConfigurationProperties(prefix = "application.http-client....")
public class HttpClientProperties {

    @NotBlank
    private String baseUrl;
}

