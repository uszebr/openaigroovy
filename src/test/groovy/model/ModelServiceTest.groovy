package model

import openai.OpenAIService
import openai.model.ModelApiResponse
import openai.model.ModelPermission
import openai.model.ModelResponse
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

        ModelApiResponse modelApiResponse = modelAPI.call()
        assert modelAPI.responseData
        assert modelApiResponse.isResponse()
        assert modelApiResponse.object == 'list'

        def models = modelApiResponse.getAllModels()

        assert models.size() > 4
        models.each { checkModel(it) }

    }

    static private checkModel(ModelResponse model) {
        assert model.id.size() > 1
        assert model.object == 'model'
        assert model.created.toString().size() > 4
        assert model.ownedBy.size() > 3
        def permissions = model.permissions
        assert permissions.size() > 0
        permissions.each { checkPermission(it) }
        assert model.root.size() > 0
    }

    static private checkPermission(ModelPermission permission) {
        assert permission.id.contains('modelperm-')
        assert permission.object == 'model_permission'
        assert permission.created.toString().size() > 4
        assert permission.allowCreateEngine != null
        assert permission.allowSampling != null
        assert permission.allowLogprobs != null
        assert permission.allowSearchIndices != null
        assert permission.allowView != null
        assert permission.allowFineTuning != null
        assert permission.organization == "*"
        assert permission.isBlocking != null
    }
}
