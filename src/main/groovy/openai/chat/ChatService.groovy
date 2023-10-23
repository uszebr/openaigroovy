package openai.chat

import openai.OpenAIService
import openai.model.AiModel
import util.ApiClient
import util.Json


class ChatService {
    private OpenAIService service

    Integer responseCode
    def responseData

    AiModel model
    Double temperature
    Double topP
    Double presencePenalty
    Integer maxTokens
    //quantity of choices
    Integer n
    List<Message> messages


    private final static PATH = "/v1/chat/completions"

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private OpenAIService service
        private AiModel model
        private Double temperature
        private Double topP
        private Double presencePenalty
        private Integer maxTokens
        private List<Message> messages
        private Integer n

        Builder withService(OpenAIService service) {
            this.service = service
            return this
        }

        Builder withModel(AiModel model) {
            this.model = model
            return this
        }

        Builder withN(AiModel model) {
            this.n = n
            return this
        }

        Builder withTemperature(Double temperature) {
            this.temperature = temperature
            return this
        }

        Builder withTopP(Double topP) {
            this.topP = topP
            return this
        }

        Builder withPresencePenalty(Double presencePenalty) {
            this.presencePenalty = presencePenalty
            return this
        }

        Builder withMaxTokens(Integer maxTokens) {
            this.maxTokens = maxTokens
            return this
        }

        Builder withMessages(List<Message> messages) {
            this.messages = messages
            return this
        }

        ChatService build() {
            return new ChatService(service, model, temperature, topP, presencePenalty, n, maxTokens, messages)
        }
    }

    ChatService(OpenAIService service, AiModel model, Double temperature, Double topP, Double presencePenalty, Integer n, Integer maxTokens, List<Message> messages) {
        this.service = service
        this.model = model
        this.temperature = temperature
        this.topP = topP
        this.presencePenalty = presencePenalty
        this.maxTokens = maxTokens
        this.messages = messages
        this.n = n
    }

    ChatResponse call() {
        def apiClient = new ApiClient(service.BASE_URL, service.API_KEY, service.CONNECT_TIMEOUT, service.READ_TIMEOUT)
        def body = ""
        apiClient.makePostRequest(PATH, body)

        responseCode = apiClient.responseCode

        if (responseCode == 200) {
            new ChatResponse(Json.textToData(apiClient.responseData))
        }
        return new ChatResponse(null)
    }
}

