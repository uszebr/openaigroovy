package com.github.uszebr.openaigroovy.chat.function

import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestParameter
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestProperty
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionStep
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionsRequest
import com.github.uszebr.openaigroovy.openai.chat.function.Items
import org.junit.Test

class FunctionsRequestTest {

    @Test
    void testSmokeFunctionRequest() {
        List<FunctionRequestProperty> properties = [
                FunctionRequestProperty.builder()
                        .withName("Locations")
                        .withType('array')
                        .withDescription('List of Location Names, City, Countries or Regions')
                        .withItems(Items.builder().withType('string').build())
                        .build(),
                FunctionRequestProperty.builder()
                        .withName("language")
                        .withType('string')
                        .withDescription('Language question asked in')
                        .build()
        ]
        FunctionRequestParameter parameter = FunctionRequestParameter.builder()
                .withProperties(properties)
                .withRequired(['Locations'])
                .build()

        FunctionStep functionStep = FunctionStep.builder()
                .withName('GetCurrentWeather')
                .withDescription('getting current weather on selected location')
                .withParameter(parameter)
                .build()
        FunctionsRequest functionsRequest = FunctionsRequest.builder().withFunctionReadableList([functionStep]).build()
        assert functionsRequest.readAllFunctionsForRequest() == ' "functions" : [ { "name": "GetCurrentWeather" ,  "description": "getting current weather on selected location" ,  "parameters": { "type": "object" ,  "properties": {  "Locations": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }}, "language": { "type": "string" , "description": "Language question asked in" } },  "required": ["Locations"] }}]'
    }


    @Test
    void testTwoFuncRequest() {

        FunctionStep functionStep0 = FunctionStep.builder()
                .withName("GetCurrentWeather")
                .withDescription("getting current weather on selected location")
                .withParameter(FunctionRequestParameter.builder()
                        .withProperties([FunctionRequestProperty.builder()
                                                 .withName("location")
                                                 .withType("string")
                                                 .withDescription("Location Name, City, Country or Region")
                                                 .build()])
                        .withRequired(["location"])
                        .build())
                .build()

        FunctionStep functionStep1 = FunctionStep.builder()
                .withName("GetMathOperation")
                .withDescription("Getting math/mathematical operation")
                .withParameter(FunctionRequestParameter.builder()
                        .withProperties([FunctionRequestProperty.builder()
                                                 .withName("MathOperationName")
                                                 .withDescription("Math/mathematical operation name")
                                                 .withType("string")
                                                 .build()])

                        .build())
                .build()

        FunctionsRequest functionsRequest = FunctionsRequest.builder().withFunctionReadableList([functionStep0, functionStep1]).build()
        println functionsRequest.readAllFunctionsForRequest()
        assert functionsRequest.readAllFunctionsForRequest() == ' "functions" : [ { "name": "GetCurrentWeather" ,  "description": "getting current weather on selected location" ,  "parameters": { "type": "object" ,  "properties": {  "location": { "type": "string" , "description": "Location Name, City, Country or Region" } },  "required": ["location"] }},  { "name": "GetMathOperation" ,  "description": "Getting math/mathematical operation" ,  "parameters": { "type": "object" ,  "properties": {  "MathOperationName": { "type": "string" , "description": "Math/mathematical operation name" } }}}]'
    }
}