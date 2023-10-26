package com.github.uszebr.openaigroovy.embedding

import com.github.uszebr.openaigroovy.openai.OpenAIService
import com.github.uszebr.openaigroovy.openai.embedding.EmbeddingApiResponse
import com.github.uszebr.openaigroovy.openai.embedding.EmbeddingService
import org.junit.Before
import org.junit.Test
import com.github.uszebr.openaigroovy.util.ConfigReader

class EmbeddingServiceTest {
    private OpenAIService openAIService

    @Before
    void setUp() {
        ConfigReader configReader = new ConfigReader('/config.properties')
        def apiKey = configReader.readConfig().apiKey.toString()
        openAIService = OpenAIService.builder().withApiKey(apiKey).build()
    }

    @Test
    void testEmbeddingRequest() {
        def embeddingService = new EmbeddingService(openAIService).withInput('Sample text')
        EmbeddingApiResponse embeddingApiResponse = embeddingService.call()

        assert embeddingApiResponse.isResponse()

        def usage = embeddingApiResponse.getUsage()
        assert usage.totalTokens == 2
        assert usage.completionTokens == null
        assert usage.totalTokens == 2

        def allEmbedding = embeddingApiResponse.getAllEmbedding()

        assert allEmbedding.size() == 1

        def firstEmbedding = embeddingApiResponse.getFirstEmbedding()
        assert firstEmbedding.index == 0
        assert firstEmbedding.object == 'embedding'
        assert firstEmbedding.embedding
        assert firstEmbedding.embedding.size() == 1536
    }

}
