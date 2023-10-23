package model

import openai.OpenAIService
import openai.model.ModelService
import org.junit.Test
import util.ConfigReader

class ModelServiceTest {

    @Test
    void testModelReturn() {
        ConfigReader configReader = new ConfigReader('/config.properties')
        def apiKey = configReader.readConfig().apiKey.toString()

        OpenAIService service = OpenAIService.builder().withApiKey(apiKey).build()
        ModelService modelAPI = new ModelService(service)

        println modelAPI.call()
        assert modelAPI.responseData
    }
}
