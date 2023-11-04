package com.github.uszebr.openaigroovy.chat.function

import com.github.uszebr.openaigroovy.openai.chat.function.FunctionStep
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestParameter
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestProperty
import com.github.uszebr.openaigroovy.openai.chat.function.Items
import com.github.uszebr.openaigroovy.util.RequestUtil
import org.junit.Test


class FunctionStepTest {

    @Test
    void testSmokeFunction() {
        List<FunctionRequestProperty> properties = [FunctionRequestProperty.builder()
                                                            .withName("Locations")
                                                            .withType('array')
                                                            .withDescription('List of Location Names, City, Countries or Regions')
                                                            .withItems(Items.builder().withType('string').build())
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


        assert functionStep.requestPrepare() == ' { "name": "GetCurrentWeather" ,  "description": "getting current weather on selected location" ,  "parameters": { "type": "object" ,  "properties": {  "Locations": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }} },  "required": ["Locations"] }}'
        assert RequestUtil.readRequest(functionStep) == ' { "name": "GetCurrentWeather" ,  "description": "getting current weather on selected location" ,  "parameters": { "type": "object" ,  "properties": {  "Locations": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }} },  "required": ["Locations"] }}'
    }

    @Test
    void testTwoParameterFunction() {
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

        def expectedJsonPart = ' { "name": "GetCurrentWeather" ,  "description": "getting current weather on selected location" ,  "parameters": { "type": "object" ,  "properties": {  "Locations": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }}, "language": { "type": "string" , "description": "Language question asked in" } },  "required": ["Locations"] }}'
        assert functionStep.requestPrepare() == expectedJsonPart
        assert RequestUtil.readRequest(functionStep) == expectedJsonPart
    }

    @Test
    void testReadFunction() {
        List<FunctionRequestProperty> properties = [FunctionRequestProperty.builder()
                                                            .withName("Locations")
                                                            .withType('array')
                                                            .withDescription('List of Location Names, City, Countries or Regions')
                                                            .withItems(Items.builder().withType('string').build())
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
        assert functionStep.readFunctionForRequest() == ' { "name": "GetCurrentWeather" ,  "description": "getting current weather on selected location" ,  "parameters": { "type": "object" ,  "properties": {  "Locations": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }} },  "required": ["Locations"] }}'
    }

    @Test
    void testSimpleFunctionNull() {
        FunctionStep functionStep = null
        assert RequestUtil.readRequest(functionStep) == null
    }
}