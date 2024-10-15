package nl.spring.template.project.common.okhttp;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;

@AllArgsConstructor
public class HttpClient<T extends HttpClientProperties> extends OkHttpClient {
    private final T properties;
    private final OkHttpClient httpClient;

    public OkHttpClient getClient() {
        return httpClient;
    }

    public String getUrl(final String subPath) {
        return this.properties.getBaseUrl() + subPath;
    }
}

