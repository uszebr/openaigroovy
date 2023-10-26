package com.github.uszebr.openaigroovy.example

import com.github.uszebr.openaigroovy.openai.OpenAIService
import com.github.uszebr.openaigroovy.openai.model.ModelApiResponse
import com.github.uszebr.openaigroovy.openai.model.ModelService
import com.github.uszebr.openaigroovy.util.ConfigReader

// getting API Key from config
ConfigReader configReader = new ConfigReader('/config.properties')
def apiKey = configReader.readConfig().apiKey.toString()

// setting up OpenAIService
// timeouts can be set here
OpenAIService service = OpenAIService.builder().withApiKey(apiKey).build()

// creating ModalService
ModelService modelAPI = new ModelService(service)
// calling OpenAI model api
ModelApiResponse modelApiResponse = modelAPI.call()
// checking data response data
println modelApiResponse.isResponse() // true
// getting AI model objects from raw data
def models = modelApiResponse.getAllModels()

// printing all model names
models.each { println(it.id) }
//text-search-babbage-doc-001
//gpt-3.5-turbo-16k-0613
//curie-search-query
//gpt-3.5-turbo
//gpt-3.5-turbo-16k
//text-search-babbage-query-001
//babbage
//babbage-search-query
//text-babbage-001
//whisper-1
//text-similarity-davinci-001
//gpt-4
//...


