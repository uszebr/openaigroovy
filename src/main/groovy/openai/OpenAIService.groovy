package openai


import java.time.Duration

class OpenAIService {
    final String BASE_URL
    final Duration CONNECT_TIMEOUT
    final Duration READ_TIMEOUT
    final String API_KEY

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private String baseUrl = "https://api.openai.com"
        private Duration connectTimeout = Duration.ofSeconds(5)
        private Duration readTimeout = Duration.ofSeconds(10)
        private String apiKey

        Builder withBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl
            return this
        }

        Builder withConnectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout
            return this
        }

        Builder withReadTimeout(Duration readTimeout) {
            this.readTimeout = readTimeout
            return this
        }

        Builder withApiKey(String apiKey) {
            this.apiKey = apiKey
            return this
        }

        OpenAIService build() {
            return new OpenAIService(baseUrl, connectTimeout, readTimeout, apiKey)
        }
    }

    OpenAIService(String baseUrl, Duration connectTimeout, Duration readTimeout, String apiKey) {
        this.BASE_URL = baseUrl
        this.CONNECT_TIMEOUT = connectTimeout
        this.READ_TIMEOUT = readTimeout
        this.API_KEY = apiKey
    }

}


