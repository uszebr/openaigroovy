package com.github.uszebr.openaigroovy.example

import com.github.uszebr.openaigroovy.embedding.EmbeddingApiResponse
import com.github.uszebr.openaigroovy.embedding.EmbeddingService
import com.github.uszebr.openaigroovy.openai.OpenAIService
import com.github.uszebr.openaigroovy.util.ConfigReader

// Reading API key from config
ConfigReader configReader = new ConfigReader('/config.properties')
def apiKey = configReader.readConfig().apiKey.toString()
// Creating open ai service instance
// timeouts can be added on this level
def openAIService = OpenAIService.builder().withApiKey(apiKey).build()

// Creating embedding service
// put text embedding needed for instead of 'Sample text'
def embeddingService = new EmbeddingService(openAIService).withInput('Sample text')
// calling API
EmbeddingApiResponse embeddingApiResponse = embeddingService.call()
// checking response is present
println embeddingApiResponse.isResponse()//true

// getting usage if needed
def usage = embeddingApiResponse.getUsage()
println usage.totalTokens// 2
println usage.completionTokens// null
println usage.totalTokens// 2

def allEmbedding = embeddingApiResponse.getAllEmbedding()

println allEmbedding.size() // 1

def firstEmbedding = embeddingApiResponse.getFirstEmbedding()

println firstEmbedding.embedding.size() // 1536 - vector dimensions
println firstEmbedding.embedding // [-0.007196536, 0.002429933, 0.000446816, -0.009122847, -0.012331105, 0.030278357, -0.012086924,... ]

