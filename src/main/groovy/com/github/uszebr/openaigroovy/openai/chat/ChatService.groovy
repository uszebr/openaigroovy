package com.github.uszebr.openaigroovy.openai.chat

import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequest
import com.github.uszebr.openaigroovy.openai.chat.response.ChatApiResponse
import com.github.uszebr.openaigroovy.openai.model.AiModel
import com.github.uszebr.openaigroovy.openai.OpenAIService
import com.github.uszebr.openaigroovy.util.ApiClient
import com.github.uszebr.openaigroovy.util.Json

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
    List<FunctionRequest> functions

    private final static PATH = "/v1/chat/completions"

    ChatService(OpenAIService service, AiModel model, Double temperature, Double topP, Double frequencyPenalty, Integer n, Integer maxTokens, List<Message> messages, List<FunctionRequest> functions) {
        this.service = service
        this.model = model
        this.temperature = temperature
        this.topP = topP
        this.frequencyPenalty = frequencyPenalty
        this.maxTokens = maxTokens
        this.messages = messages
        this.n = n
        this.functions = functions
    }

    ChatApiResponse call() {
        def apiClient = new ApiClient(service.BASE_URL, service.API_KEY, service.CONNECT_TIMEOUT, service.READ_TIMEOUT)
        def body = buildBody()
        apiClient.makePostRequest(PATH, body)
        responseCode = apiClient.responseCode
        if (responseCode == 200) {
            responseData = apiClient.responseData
            return new ChatApiResponse(Json.textToData(apiClient.responseData))
        }
        return new ChatApiResponse(null)
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
            entities.add("""$messagesBody""")
        }
        String body = """{
             ${entities.grep().join(',')
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
        // default model
        private AiModel model = AiModel.GPT_3_5_TURBO
        private Double temperature
        private Double topP
        private Double frequencyPenalty
        private Integer maxTokens
        private List<Message> messages
        private Integer n
        private List<FunctionRequest> functions

        Builder withService(OpenAIService service) {
            this.service = service
            return this
        }

        Builder withModel(AiModel model) {
            this.model = model
            return this
        }

        Builder withN(Integer n) {
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

        Builder withFunctions(List<FunctionRequest> functions) {
            this.functions = functions
            return this
        }

        ChatService build() {
            return new ChatService(service, model, temperature, topP, frequencyPenalty, n, maxTokens, messages, functions)
        }
    }
}

