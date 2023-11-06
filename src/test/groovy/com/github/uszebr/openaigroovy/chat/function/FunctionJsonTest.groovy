package com.github.uszebr.openaigroovy.chat.function

import com.github.uszebr.openaigroovy.openai.chat.function.FunctionJson
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionsRequest
import org.junit.Test

class FunctionJsonTest {
    def static funcPart = """{
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

    @Test
    void testSmokeFunctionJson() {
        // can be copied from postman
        FunctionJson functionJson = FunctionJson.builder().withFunctionInJson(funcPart).build()
        assert functionJson.readFunctionForRequest() == funcPart
    }

    @Test
    void testFunctionJsonInRequest() {
        FunctionJson functionJson = FunctionJson.builder().withFunctionInJson(funcPart).build()
        FunctionsRequest functionsRequest = FunctionsRequest.builder().withFunctionReadableList([functionJson]).build()
        def expectedAllFunctionRequest = """ "functions" : [{
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
        }]"""
        assert functionsRequest.readAllFunctionsForRequest() == expectedAllFunctionRequest
    }

    @Test
    void testTwoFunctionJsonInRequest() {
        def functionPart1 = """{
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
        FunctionJson functionJson = FunctionJson.builder().withFunctionInJson(funcPart).build()
        FunctionJson functionJson1 = FunctionJson.builder().withFunctionInJson(functionPart1).build()
        FunctionsRequest functionsRequest = FunctionsRequest.builder().withFunctionReadableList([functionJson,functionJson1]).build()
        def expectedAllFunctionRequest = """ "functions" : [{
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
        }, {
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
    }]"""
        assert functionsRequest.readAllFunctionsForRequest() == expectedAllFunctionRequest
    }
}
