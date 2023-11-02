package com.github.uszebr.openaigroovy.chat.function

import com.github.uszebr.openaigroovy.openai.chat.function.FunctionStep
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestParameter
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestProperty
import com.github.uszebr.openaigroovy.openai.chat.function.Items
import com.github.uszebr.openaigroovy.util.RequestUtil
import org.junit.Test


class FunctionStepTest {

    @Test
    void testSimpleFunction() {
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

        FunctionStep functionRequest = FunctionStep.builder()
                .withName('GetCurrentWeather')
                .withDescription('getting current weather on selected location')
                .withParameter(parameter)
                .build()
        assert functionRequest.requestPrepare() == ' { "name": "GetCurrentWeather" ,  "description": "getting current weather on selected location" ,  "parameters": { "type": "object" ,  "properties": {  "Locations": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }} },  "required": ["Locations"] }}'
        assert RequestUtil.readRequest(functionRequest) == ' { "name": "GetCurrentWeather" ,  "description": "getting current weather on selected location" ,  "parameters": { "type": "object" ,  "properties": {  "Locations": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }} },  "required": ["Locations"] }}'
    }

    @Test
    void testSimpleFunctionNull() {
        FunctionStep functionRequest = null
        assert RequestUtil.readRequest(functionRequest) == null
    }
}