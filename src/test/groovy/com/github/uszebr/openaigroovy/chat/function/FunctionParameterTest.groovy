package com.github.uszebr.openaigroovy.chat.function

import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestParameter
import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestProperty
import com.github.uszebr.openaigroovy.openai.chat.function.Items
import com.github.uszebr.openaigroovy.util.RequestUtil
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull

class FunctionParameterTest {
    @Test
    void testFunctionParameterSimple() {
        List<FunctionRequestProperty> properties = [FunctionRequestProperty.builder()
                                                            .withName('Size')
                                                            .withType('number')
                                                            .build(),
                                                    FunctionRequestProperty.builder()
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

        //  String requestPart = RequestUtil.readRequest(parameter)
        String requestPart = parameter.requestPrepare()
        assertEquals(requestPart, """ "parameters": { "type": "object" ,  "properties": {  "Size": { "type": "number" }, "Locations": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }} },  "required": ["Locations"] }""")
    }

    @Test
    void testFunctionOneParamNoRequired() {
        List<FunctionRequestProperty> properties = [FunctionRequestProperty.builder()
                                                            .withName('Size')
                                                            .withType('number')
                                                            .build()
        ]
        FunctionRequestParameter parameter = FunctionRequestParameter.builder()
                .withProperties(properties)

                .build()
        String requestPart = parameter.requestPrepare()
        println requestPart
        assertEquals(requestPart, """ "parameters": { "type": "object" ,  "properties": {  "Size": { "type": "number" } }}""")
    }

    @Test
    void testFunctionParameterNull() {
        FunctionRequestParameter parameter
        assertNull(RequestUtil.readRequest(parameter))
    }
}
