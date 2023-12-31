package com.github.uszebr.openaigroovy.openai.model

import com.github.uszebr.openaigroovy.openai.OpenAIService
import com.github.uszebr.openaigroovy.util.ApiClient
import com.github.uszebr.openaigroovy.util.Json

/**
 * Handling https://api.openai.com/v1/models endpoint
 * no Usage in response
 */
class ModelService {

    OpenAIService service

    Integer responseCode
    def responseData


    private static final String PATH = "/v1/models"

    ModelService(OpenAIService service) {
        this.service = service
    }

    ModelApiResponse call() {
        def apiClient = new ApiClient(service.BASE_URL, service.API_KEY, service.CONNECT_TIMEOUT, service.READ_TIMEOUT)
        apiClient.makeGetRequest(PATH)

        responseCode = apiClient.responseCode

        if (responseCode == 200) {
            responseData = Json.textToData(apiClient.responseData)
        }
        return new ModelApiResponse(responseData)
    }

}
