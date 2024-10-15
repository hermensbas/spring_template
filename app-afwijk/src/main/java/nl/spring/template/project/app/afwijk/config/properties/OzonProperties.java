package nl.spring.template.project.app.afwijk.config.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.spring.template.project.common.okhttp.HttpClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Data
@Validated
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "application.http-client.ozon")
public class OzonProperties extends HttpClientProperties {}

