package com.github.uszebr.openaigroovy.chat.function

import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestProperty
import com.github.uszebr.openaigroovy.openai.chat.function.Items
import com.github.uszebr.openaigroovy.util.RequestUtil
import org.junit.Test
import static org.junit.Assert.assertEquals

class FunctionStepPropertyTest {
    @Test
    void testSimpleBodyString() {
        FunctionRequestProperty property = FunctionRequestProperty.builder()
                .withName('location')
                .withType('array')
                .withDescription('List of Location Names, City, Countries or Regions')
                .withItems(Items.builder().withType('string').build())
                .build()

        assertEquals(RequestUtil.readRequest(property), ' "location": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }}')
    }

    @Test
    void testSimpleBodyString1() {
        FunctionRequestProperty property0 = FunctionRequestProperty.builder()
                .withName('Size')
                .withType('number')
                .withDescription('Size of the city')
                .build()

        assertEquals(RequestUtil.readRequest(property0), ' "Size": { "type": "number" , "description": "Size of the city" }')
    }

    @Test
    void testPropertyNull() {
        FunctionRequestProperty property0 = FunctionRequestProperty.builder()
                .withName(null)
                .withType('number')
                .withDescription('Size of the city')
                .build()
        assertEquals(RequestUtil.readRequest(property0), null)
    }

    @Test
    void testPropertyTypeNull() {
        FunctionRequestProperty property0 = FunctionRequestProperty.builder()
                .withName('Size')
                .withType(null)
                .withDescription(null)
                .build()
        assertEquals(RequestUtil.readRequest(property0), null)
    }

    @Test
    void testPropertyTypeOnly() {
        FunctionRequestProperty property0 = FunctionRequestProperty.builder()
                .withName('Size')
                .withType('number')
                .build()
        assertEquals(RequestUtil.readRequest(property0), ' "Size": { "type": "number" }')
    }

    @Test
    void testPropertyRequestWithListOfProperties() {
        List<FunctionRequestProperty> properties = [FunctionRequestProperty.builder()
                                                            .withName('Size')
                                                            .withType('number')
                                                            .build(),
                                                    FunctionRequestProperty.builder()
                                                            .withName('location')
                                                            .withType('array')
                                                            .withDescription('List of Location Names, City, Countries or Regions')
                                                            .withItems(Items.builder().withType('string').build())
                                                            .build()
        ]
        String requestPartForListOfProperties = FunctionRequestProperty.requestPrepareForList(properties)
        assertEquals(requestPartForListOfProperties.toString(), ' "properties": {  "Size": { "type": "number" }, "location": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }} }')
    }

    @Test
    void testPropertyRequestWithListOfPropertiesNullList() {
        assertEquals(FunctionRequestProperty.requestPrepareForList(null), null)
    }

    @Test
    void testPropertyRequestWithListOfPropertiesNull() {
        List<FunctionRequestProperty> properties = [FunctionRequestProperty.builder()
                                                            .withName(null)
                                                            .withType('number')
                                                            .build(),
                                                    FunctionRequestProperty.builder()
                                                            .withName(null)
                                                            .withType('array')
                                                            .withDescription('List of Location Names, City, Countries or Regions')
                                                            .withItems(Items.builder().withType('string').build())
                                                            .build()
        ]
        assertEquals(FunctionRequestProperty.requestPrepareForList(properties), null)
    }

    @Test
    void testPropertyRequestWithEnumProp() {
        FunctionRequestProperty functionRequestProperty = FunctionRequestProperty.builder()
                .withName('location')
                .withType('string')
                .withDescription('Part of the world or continent name')
                .withEnum(["ASIA", "AMERICA", "EUROPE", "AUSTRALIA", "OTHER"])
                .build()
        def expectedString = ' "properties": {  "location": { "type": "string" , "description": "Part of the world or continent name" , "enum": ["ASIA", "AMERICA", "EUROPE", "AUSTRALIA", "OTHER"]} }'
        assertEquals( FunctionRequestProperty.requestPrepareForList([functionRequestProperty]),expectedString)
    }

    @Test
    void testPropertyRequestWithNullEnumProp() {
        FunctionRequestProperty functionRequestProperty = FunctionRequestProperty.builder()
                .withName('location')
                .withType('string')
                .withDescription('Part of the world or continent name')
                .withEnum(null)
                .build()
        def expectedString = ' "properties": {  "location": { "type": "string" , "description": "Part of the world or continent name" } }'
        assertEquals( FunctionRequestProperty.requestPrepareForList([functionRequestProperty]),expectedString)
    }

    @Test
    void testPropertyRequestWithEmptyEnumProp() {
        FunctionRequestProperty functionRequestProperty = FunctionRequestProperty.builder()
                .withName('location')
                .withType('string')
                .withDescription('Part of the world or continent name')
                .withEnum([])
                .build()
        def expectedString = ' "properties": {  "location": { "type": "string" , "description": "Part of the world or continent name" } }'
        assertEquals( FunctionRequestProperty.requestPrepareForList([functionRequestProperty]),expectedString)
    }

}
