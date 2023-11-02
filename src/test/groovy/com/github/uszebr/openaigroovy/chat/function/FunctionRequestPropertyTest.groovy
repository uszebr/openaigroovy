package com.github.uszebr.openaigroovy.chat.function

import com.github.uszebr.openaigroovy.openai.chat.function.FunctionRequestProperty
import com.github.uszebr.openaigroovy.openai.chat.function.Items
import com.github.uszebr.openaigroovy.util.RequestUtil
import org.junit.Test
import static org.junit.Assert.assertEquals

class FunctionRequestPropertyTest {
    @Test
    void testSimpleBodyString() {
        FunctionRequestProperty property = FunctionRequestProperty.builder()
                .name('location')
                .type('array')
                .description('List of Location Names, City, Countries or Regions')
                .items(Items.builder().type('string').build())
                .build()

        assertEquals(RequestUtil.readRequest(property), ' "location": { "type": "array" , "description": "List of Location Names, City, Countries or Regions" , "items": { "type": "string" }}')
    }

    @Test
    void testSimpleBodyString1() {
        FunctionRequestProperty property0 = FunctionRequestProperty.builder()
                .name('Size')
                .type('number')
                .description('Size of the city')
                .build()

        assertEquals(RequestUtil.readRequest(property0), ' "Size": { "type": "number" , "description": "Size of the city" }')
    }

    @Test
    void testPropertyNullz() {
        FunctionRequestProperty property0 = FunctionRequestProperty.builder()
                .name('Size')
                .type('number')
                .description('Size of the city')
                .build()

        assertEquals(RequestUtil.readRequest(property0), ' "Size": { "type": "number" , "description": "Size of the city" }')
    }


}
