package com.github.uszebr.openaigroovy.openai.embedding

import com.github.uszebr.openaigroovy.openai.OpenAIUsage

class EmbeddingApiResponse {
    def rawData

    EmbeddingApiResponse(rawData) {
        this.rawData = rawData
    }

    boolean isResponse() {
        return rawData != null
    }

    List<EmbeddingResponse> getAllEmbedding() {
        if (!isResponse()) {
            return null
        }
        def allEmbeddingData = rawData['data']
        def result = []
        for (embeddingData in allEmbeddingData) {
            def embedding = new EmbeddingResponse()
                    .withIndex(embeddingData['index'] as Integer)
                    .withObject(embeddingData['object'] as String)
                    .withEmbedding(embeddingData['embedding'] as List)
            result.add(embedding)
        }
        return result
    }

    EmbeddingResponse getFirstEmbedding() {
        if (!isResponse()) {
            return null
        }
        return getAllEmbedding()?.getAt(0)
    }

    /**
     * @return example: "text-embedding-ada-002-v2"
     */
    String getModel() {
        if (!isResponse()) {
            return null
        }
        return rawData.getAt('model')
    }

    /**
     *
     * @return example: "list"
     */
    String getObject() {
        if (!isResponse()) {
            return null
        }
        return rawData.getAt('object')
    }

    OpenAIUsage getUsage() {
        if (!isResponse()) {
            return null
        }
        def usageData = rawData.getAt('usage')
        return new OpenAIUsage(usageData?.getAt('prompt_tokens') as Integer, usageData?.getAt('completion_tokens') as Integer, usageData?.getAt('total_tokens') as Integer)
    }
}
