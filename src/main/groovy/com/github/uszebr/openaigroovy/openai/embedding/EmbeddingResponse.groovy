package com.github.uszebr.openaigroovy.openai.embedding


class EmbeddingResponse {

    // example: [ -0.015513591,-0.012486892,-0.005419902, -0.02318592, ...]
    // ada vector dimensions: 1536
    List<Double> embedding

    // example: "embedding"
    String object

    Integer index

    EmbeddingResponse withEmbedding(List<Double> embedding) {
        this.embedding = embedding
        return this
    }

    EmbeddingResponse withObject(String object) {
        this.object = object
        return this
    }

    EmbeddingResponse withIndex(Integer index) {
        this.index = index
        return this
    }
}
