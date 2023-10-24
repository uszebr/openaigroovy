package openai.chat

import openai.OpenAIService
import openai.chat.response.ChatResponse
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
    Double frequencyPenalty
    Integer maxTokens
    //quantity of choices
    Integer n
    List<Message> messages

    private final static PATH = "/v1/chat/completions"

    ChatService(OpenAIService service, AiModel model, Double temperature, Double topP, Double frequencyPenalty, Integer n, Integer maxTokens, List<Message> messages) {
        this.service = service
        this.model = model
        this.temperature = temperature
        this.topP = topP
        this.frequencyPenalty = frequencyPenalty
        this.maxTokens = maxTokens
        this.messages = messages
        this.n = n
    }

    ChatResponse call() {
        def apiClient = new ApiClient(service.BASE_URL, service.API_KEY, service.CONNECT_TIMEOUT, service.READ_TIMEOUT)
        def body = buildBody()
        apiClient.makePostRequest(PATH, body)
        responseCode = apiClient.responseCode
        if (responseCode == 200) {
            responseData = apiClient.responseData
           return new ChatResponse(Json.textToData(apiClient.responseData))
        }
        return new ChatResponse(null)
    }

    private String buildBody() {
        List entities = [""""model": "$model.name" """]
        if (temperature) {
            entities.add(""" "temperature": $temperature """)
        }
        if (topP) {
            entities.add(""" "top_p": $topP """)
        }
        if (frequencyPenalty) {
            entities.add(""" "frequency_penalty": $frequencyPenalty """)
        }
        if (maxTokens) {
            entities.add(""" "max_tokens": $maxTokens """)
        }
        if (n) {
            entities.add(""" "n": $n """)
        }
        String messagesBody = buildMessagesBody()
        if (messagesBody) {
            entities.add(messagesBody)
        }
        String body = """{
             ${entities.join(',')
        }
           } """
        return body
    }

    private String buildMessagesBody() {
        if (!messages) {
            return ''
        }
        def preparedMessages = messages.collect() { it.prepareForRequest() }
        return """ "messages": [ ${preparedMessages.join(',')} ]"""
    }

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private OpenAIService service

        private AiModel model
        private Double temperature
        private Double topP
        private Double frequencyPenalty
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

        Builder withFrequencyPenalty(Double frequencyPenalty) {
            this.frequencyPenalty = frequencyPenalty
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
            return new ChatService(service, model, temperature, topP, frequencyPenalty, n, maxTokens, messages)
        }
    }
}

