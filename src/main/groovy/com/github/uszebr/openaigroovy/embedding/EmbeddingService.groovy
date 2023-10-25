package com.github.uszebr.openaigroovy.embedding


import com.github.uszebr.openaigroovy.model.AiModel
import com.github.uszebr.openaigroovy.openai.OpenAIService
import com.github.uszebr.openaigroovy.util.ApiClient
import com.github.uszebr.openaigroovy.util.Json

/**
 * Creating/Generating embeddings
 * https://api.openai.com/v1/embeddings
 */
class EmbeddingService {
    OpenAIService service

    Integer responseCode
    def responseData

    AiModel model = AiModel.ADA_02
    String input
    //example value: "float"
    String encodingFormat

    private static final String PATH = "/v1/embeddings"

    EmbeddingService(OpenAIService service) {
        this.service = service
    }

    EmbeddingApiResponse call() {
        def apiClient = new ApiClient(service.BASE_URL, service.API_KEY, service.CONNECT_TIMEOUT, service.READ_TIMEOUT)
        def body = buildBody()
        apiClient.makePostRequest(PATH, body)

        responseCode = apiClient.responseCode

        if (responseCode == 200) {
            responseData = Json.textToData(apiClient.responseData)
        }
        return new EmbeddingApiResponse(responseData)
    }

    EmbeddingService withModel(AiModel model) {
        this.model = model
        return this
    }

    EmbeddingService withInput(String input) {
        this.input = input
        return this
    }

    EmbeddingService withEncodingFormat(String encodingFormat) {
        this.encodingFormat = encodingFormat
        return this
    }

    private String buildBody() {
        List entities = []
        if (model) {
            entities.add(""""model": "$model.name" """)
        }
        if (input) {
            entities.add(""" "input": "${input}" """)
        }
        if(encodingFormat){
            entities.add(""" "encoding_format": "${encodingFormat}" """)
        }

        String body = """{
             ${entities.join(',')
        }
           } """
        return body
    }


}
