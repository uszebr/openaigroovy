package openai.model

import openai.OpenAIService
import util.ApiClient
import util.Json

class ModelService {

    OpenAIService service

    Integer responseCode
    def responseData


    private static final String PATH = "/v1/models"

    ModelService(OpenAIService service) {
        this.service = service
    }

    def call() {
        def apiClient = new ApiClient(service.BASE_URL, service.API_KEY, service.CONNECT_TIMEOUT, service.READ_TIMEOUT)
        apiClient.makeGetRequest(PATH)

        responseCode = apiClient.responseCode

        if (responseCode == 200) {
            responseData = Json.textToData(apiClient.responseData)
        }
        return apiClient.responseData
    }

}
