package com.github.uszebr.openaigroovy.chat

import com.github.uszebr.openaigroovy.openai.OpenAIService
import com.github.uszebr.openaigroovy.openai.chat.ChatService
import com.github.uszebr.openaigroovy.openai.chat.FunctionCall
import com.github.uszebr.openaigroovy.openai.chat.Message
import com.github.uszebr.openaigroovy.openai.chat.Role
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionJson
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionsRequest
import com.github.uszebr.openaigroovy.openai.chat.response.ChatApiResponse
import com.github.uszebr.openaigroovy.openai.model.AiModel
import com.github.uszebr.openaigroovy.util.ConfigReader
import org.junit.Before
import org.junit.Test

class ChatServiceWithJsonFunctionsTest {
    private OpenAIService openAIService

    private static functionJsonPart = """ {
        "name": "GetCurrentWeather",
        "description": "getting current weather on selected location",
        "parameters": {
            "type": "object",
            "properties": {
                 "location": {
                    "type": "string",
                    "description": "Location Name, City, Country or Region"
                }
            },
            "required": ["location"]
        }
    }"""
    private  static functionJsonPart1 = """{
        "name": "GetMathOperation",
        "description": "Getting math/mathematical operation",
        "parameters": {
            "type": "object",
            "properties": {
                 "MathOperationName": {
                    "type": "string",
                    "description": "Math/mathematical operation name"
                }
            }
        }
    }"""
    private static  functionJsonEnumPart = """{
        "name": "GetWorldLocation",
        "description": "getting part of the world or continent from the prompt",
        "parameters": {
            "type": "object",
            "properties": {
                 "location": {
                    "type": "string",
                    "description": "Part of the world or continent name",
                    "enum": ["ASIA","AMERICA","EUROPE","AUSTRALIA"]
                }
            },
            "required": ["location"]
        }
    }"""

    @Before
    void setUp() {
        ConfigReader configReader = new ConfigReader('/config.properties')
        def apiKey = configReader.readConfig().apiKey.toString()
        openAIService = OpenAIService.builder().withApiKey(apiKey).build()
    }

    @Test
    void testSmokeJsonFunction() {
        def messages = [
                new Message(Role.SYSTEM, "You are function extractor. Always call one of the provided functions. No content"),
                new Message(Role.USER, "What is the weather in  Kyiv?")
        ]
        FunctionJson functionJson = FunctionJson.builder().withFunctionInJson(functionJsonPart).build()
        FunctionsRequest functionRequest = FunctionsRequest.builder().withFunctionReadableList([functionJson]).build()

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

    @Test
    void testTwoJsonFunction() {
        def messages = [
                new Message(Role.SYSTEM, "You are function extractor. Always call one of the provided functions. No content"),
                new Message(Role.USER, "Multiply 2 by 3")
        ]
        FunctionJson functionJson = FunctionJson.builder().withFunctionInJson(functionJsonPart).build()
        FunctionJson functionJson1 = FunctionJson.builder().withFunctionInJson(functionJsonPart1).build()
        FunctionsRequest functionRequest = FunctionsRequest.builder().withFunctionReadableList([functionJson,functionJson1]).build()

        ChatService chatService = ChatService.builder()
                .withService(openAIService)
                .withModel(AiModel.GPT_3_5_TURBO)
                .withMessages(messages)
                .withFunctions(functionRequest)
                .build()

        ChatApiResponse response = chatService.call()

        assert response.isResponse()
        def arguments = response.firstChoice.message.functionCall.arguments
        assert response.firstChoice.message.functionCall.name == 'GetMathOperation'
        assert arguments.keySet().contains('MathOperationName')
        assert arguments.get('MathOperationName') == 'multiply'
    }

    @Test
    void testFunctionWithEnumParameter(){
        def messages = [
                new Message(Role.SYSTEM, "You are function extractor. Always call one of the provided functions. No content"),
                new Message(Role.USER, "find all information about Kyiv")
        ]
        FunctionJson functionJson = FunctionJson.builder().withFunctionInJson(functionJsonEnumPart).build()

        FunctionsRequest functionRequest = FunctionsRequest.builder().withFunctionReadableList([functionJson]).build()

        ChatService chatService = ChatService.builder()
                .withService(openAIService)
                .withModel(AiModel.GPT_3_5_TURBO)
                .withMessages(messages)
                .withFunctions(functionRequest)
                .withFunctionCall(FunctionCall.builder().withName('GetWorldLocation').build())
                .build()

        ChatApiResponse response = chatService.call()

        assert response.isResponse()
        def arguments = response.firstChoice.message.functionCall.arguments
        assert response.firstChoice.message.functionCall.name == "GetWorldLocation"
        assert arguments.keySet().contains('location')
        assert arguments.get('location') == 'EUROPE'
    }
}
