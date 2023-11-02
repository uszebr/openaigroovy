package com.github.uszebr.openaigroovy.chat.function

import com.github.uszebr.openaigroovy.openai.chat.function.Items
import com.github.uszebr.openaigroovy.util.RequestUtil
import org.junit.Test
import static org.junit.Assert.assertEquals

class ItemsTest {

    @Test
    void testItemType() {
        Items items = null
        assertEquals(RequestUtil.readRequest (items), null)
    }

    @Test
    void testItemNullType() {
        Items items = Items.builder().build()
        assertEquals(RequestUtil.readRequest (items), null)
    }

    @Test
    void testItemValidType0() {
        Items items = Items.builder().type('number').build()
        assertEquals(RequestUtil.readRequest (items), ' "items": { "type": "number" }')
    }
    @Test
    void testItemValidType1() {
        Items items = Items.builder().type('string').build()
        assertEquals(RequestUtil.readRequest (items), ' "items": { "type": "string" }')
    }


}
