package example

import openai.OpenAIService
import openai.OpenAIUsage
import openai.chat.ChatService
import openai.chat.Message
import openai.chat.Role
import openai.chat.response.ChatResponse
import openai.chat.response.Choice
import openai.model.AiModel
import util.ConfigReader

//You can read API KEY from config file or set it directly to the OpenAIService
ConfigReader configReader = new ConfigReader('/config.properties')
def apiKey = configReader.readConfig().apiKey.toString()

OpenAIService openAIService = OpenAIService.builder().withApiKey(apiKey).build()

// Setup messages List
def messages = [
        new Message(Role.USER, "What is the distance from Earth to the Moon?")
]
// you can add messages with different Roles to this list
// ex:  new Message(Role.SYSTEM, "Your helpful geography assistant provides answers that do not exceed 3 sentences"),


// Creating ChatService instance
// adding OpenAIService instance is mandatory
ChatService chatService = ChatService.builder()
        .withService(openAIService)
        .withModel(AiModel.GPT_3_5_TURBO)
        .withMessages(messages)
        .build()

// calling openAI chat API and getting response
// raw data of api response can be found in chatService.responseData
ChatResponse response = chatService.call()

println response.isResponse() //true

// by default OpenAI returns only one choice(can be set in chatService instance as n)
Choice choice = response.getFirstChoice()

println choice.index // 0
// message.content that is text of LLM model response
println choice.message.content // The average distance from Earth to the Moon is about 238,900 miles (384,400 kilometers).
println choice.message.role // ASSISTANT

// collecting usage
OpenAIUsage usage = response.getUsage()
println  usage.promptTokens // 17
println  usage.completionTokens // 21
println  usage.totalTokens // 38

// getting response details
println response.getId() // chatcmpl-8D9HfhaE4A4I0AEeMITu24ktbUKB2
println response.getObject() // chat.completion
println response.getModelString() //'gpt-3.5-turbo-XXXX'
println response.getCreated() // 1698144643

