package com.github.uszebr.openaigroovy.chat

import com.github.uszebr.openaigroovy.openai.OpenAIService
import com.github.uszebr.openaigroovy.openai.OpenAIUsage
import com.github.uszebr.openaigroovy.openai.chat.ChatService
import com.github.uszebr.openaigroovy.openai.chat.Message
import com.github.uszebr.openaigroovy.openai.chat.Role
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionStep
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestParameter
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestProperty
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionsRequest
import com.github.uszebr.openaigroovy.openai.chat.response.ChatApiResponse
import com.github.uszebr.openaigroovy.openai.chat.response.Choice
import com.github.uszebr.openaigroovy.openai.model.AiModel
import org.junit.Before
import org.junit.Test
import com.github.uszebr.openaigroovy.util.ConfigReader

class ChatServiceTest {

    private OpenAIService openAIService

    @Before
    void setUp() {
        ConfigReader configReader = new ConfigReader('/config.properties')
        def apiKey = configReader.readConfig().apiKey.toString()
        openAIService = OpenAIService.builder().withApiKey(apiKey).build()
    }

    @Test
    void testInitialize() {
        def messages = [
                new Message(Role.SYSTEM, "Your name is Val and you are helpfully assistant in the vehicle repair shop. When answering try to be funny and relaxed"),
                new Message(Role.USER, "Who are you?")
        ]
        ChatService chatService = ChatService.builder()
                .withService(openAIService)
                .withModel(AiModel.GPT_3_5_TURBO)
                .withMessages(messages)
                .build()

        ChatApiResponse response = chatService.call()

        assert response.isResponse()

        Choice choice = response.getFirstChoice()
        assert response.getId().contains('chat')
        assert response.getObject() == 'chat.completion'
        assert response.getModelString().contains('gpt-3.5-turbo')
        assert response.getCreated().toString().size() > 3

        // println choice.message
        assert choice.index == 0
        assert choice.message.content.size() > 1
        assert choice.message.role == Role.ASSISTANT

        OpenAIUsage usage = response.getUsage()
        assert usage.promptTokens == 39
        assert usage.completionTokens > 5
        assert usage.totalTokens > 41

    }

    @Test
    void testMultipleChoice() {
        def messages = [
                new Message(Role.SYSTEM, "Your name is Val and you are helpfully assistant in the vehicle repair shop. When answering try to be funny and relaxed"),
                new Message(Role.USER, "Who are you?")
        ]
        def choicesQuantity = 3
        ChatService chatService = ChatService.builder()
                .withService(openAIService)
                .withModel(AiModel.GPT_3_5_TURBO)
                .withMessages(messages)
                .withN(choicesQuantity)
                .build()

        ChatApiResponse response = chatService.call()

        assert response.isResponse()

        def choices = response.getChoices()

        assert choices.size() == choicesQuantity
        def responseMessages = choices.collect({ it.message })
        assert responseMessages.size() == choicesQuantity

        def responseContents = responseMessages.collect() { it.content }
        assert responseContents.size() == choicesQuantity
        responseContents.each { assert it != null }
        // checking all messages are unique
        def uniqueContent = responseContents as Set
        assert uniqueContent.size() == choicesQuantity

        /* Printing content from all messages
       responseMessages.each { println it.content }
       Hey there! I'm Val, your friendly neighborhood vehicle repair shop assistant. I'm here to help you out with any questions or issues you have, and maybe throw in a few bad car jokes along the way. So, what can I assist you with today?
       Hey there! I'm Val! The friendly and helpful assistant in this awesome vehicle repair shop. How can I assist you today?
       Hey there! I'm Val, your trusty assistant in this awesome vehicle repair shop. I'm here to lend a hand and sprinkle a little humor into your car repair experience. So, what can I help you with today?
      */
    }

    @Test
    void testFunctionSimple() {
        def messages = [
                new Message(Role.SYSTEM, "You are function extractor. Always call one of the provided functions. No content"),
                new Message(Role.USER, "What is the weather in  Kyiv?")
        ]
        def functionProperty0 = FunctionRequestProperty.builder()
                .withName('location')
                .withType('string')
                .withDescription('Location Name, City, Country or Region')
                .build()
        def functionParameter = FunctionRequestParameter.builder()
                .withType("object")
                .withProperties([functionProperty0])
                .withRequired(['location'])
                .build()
        def function0 = FunctionStep.builder()
                .withName('GetCurrentWeather')
                .withDescription('getting current weather on selected location')
                .withParameter(functionParameter)
                .build()

        FunctionsRequest functionRequest = FunctionsRequest.builder().withFunctionReadableList([function0]).build()

        ChatService chatService = ChatService.builder()
                .withService(openAIService)
                .withModel(AiModel.GPT_3_5_TURBO)
                .withMessages(messages)
                .withFunctions(functionRequest)
                .build()

        ChatApiResponse response = chatService.call()

        assert response.isResponse()

        def arguments = response.firstChoice.message.functionCall.arguments
        assert response.firstChoice.message.functionCall.name == 'GetCurrentWeather'
        assert arguments.keySet().contains('location')
        assert arguments.get('location') == 'Kyiv'
    }
}
