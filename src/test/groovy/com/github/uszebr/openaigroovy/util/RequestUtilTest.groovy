package com.github.uszebr.openaigroovy.util

import org.junit.Test
import static org.junit.Assert.assertEquals

class RequestUtilTest {
    @Test
    void testCreateOneParamPartRequestWithNullParameterName() {
        def parameterName = null
        def parameterValue = "value"
        def result = RequestUtil.createOneParamPartRequest(parameterName, parameterValue)
        assertEquals(null, result)
    }

    @Test
    void testCreateOneParamPartRequestWithNullParameterValue() {
        def parameterName = "name"
        def parameterValue = null

        def result = RequestUtil.createOneParamPartRequest(parameterName, parameterValue)

        assertEquals(null, result)
    }

    @Test
    void testCreateOneParamPartRequestWithValidParameters() {
        def parameterName = "name"
        def parameterValue = "value"

        def result = RequestUtil.createOneParamPartRequest(parameterName, parameterValue)

        assertEquals(""" "$parameterName": "$parameterValue" """ as String, result)
    }

    @Test
    void testCreateListParamPartRequestWithNullParameterList() {
        List<Tuple2> parameterList = null

        List<String> result = RequestUtil.createListParamPartRequest(parameterList)

        assertEquals(null, result)
    }

    @Test
    void testCreateListParamPartRequestWithValidParameters() {
        List<Tuple2> parameterList = [
                new Tuple2("name1", "value1"),
                new Tuple2("name2", "value2")
        ]

        List<String> result = RequestUtil.createListParamPartRequest(parameterList)

        assertEquals([
                """ "name1": "value1" """,
                """ "name2": "value2" """
        ], result)
    }

    @Test
    void testCreateListParamPartRequestWithValidParametersList() {
        List<Tuple2> parameterList = [
                ["name1", "value1"] as Tuple2,
                ["name2", "value2"] as Tuple2
        ]
        List<String> result = RequestUtil.createListParamPartRequest(parameterList)
        assertEquals([
                """ "name1": "value1" """,
                """ "name2": "value2" """
        ], result)
    }



}
