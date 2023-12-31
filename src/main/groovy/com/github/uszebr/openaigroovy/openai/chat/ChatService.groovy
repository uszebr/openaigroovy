package com.github.uszebr.openaigroovy.openai.chat

import com.github.uszebr.openaigroovy.openai.chat.function.FunctionsRequest
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
    FunctionsRequest functionsRequest
    FunctionCall functionCall

    private final static PATH = "/v1/chat/completions"

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
        if (functionsRequest) {
            entities.add("""${functionsRequest.readAllFunctionsForRequest()}""")
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
        private FunctionsRequest functionsRequest
        private FunctionCall functionCall

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

        Builder withFunctions(FunctionsRequest functionsRequest) {
            this.functionsRequest = functionsRequest
            return this
        }

        Builder withFunctionCall(FunctionCall functionCall) {
            this.functionCall = functionCall
            return this
        }

        ChatService build() {
            ChatService chatService = new ChatService()
            chatService.service = this.service
            chatService.model = this.model
            chatService.temperature = this.temperature
            chatService.topP = this.topP
            chatService.frequencyPenalty = this.frequencyPenalty
            chatService.n = this.n
            chatService.maxTokens = this.maxTokens
            chatService.messages = this.messages
            chatService.functionsRequest = this.functionsRequest
            chatService.functionCall = this.functionCall
            return chatService
        }
    }
}

