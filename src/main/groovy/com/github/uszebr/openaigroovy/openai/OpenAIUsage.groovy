package com.github.uszebr.openaigroovy.openai

class OpenAIUsage {
    Integer promptTokens
    Integer completionTokens
    Integer totalTokens

    OpenAIUsage(Integer promptTokens, Integer completionTokens, Integer totalTokens) {
        this.promptTokens = promptTokens
        this.completionTokens = completionTokens
        this.totalTokens = totalTokens
    }
}
