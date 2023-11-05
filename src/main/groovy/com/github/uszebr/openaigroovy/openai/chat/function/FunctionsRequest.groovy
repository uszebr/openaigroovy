package com.github.uszebr.openaigroovy.openai.chat.function

import com.github.uszebr.openaigroovy.util.FunctionReadable

class FunctionsRequest {
    List<FunctionReadable> functionReadableList

    String readAllFunctionsForRequest() {
        if (functionReadableList == null || functionReadableList == []) {
            return null
        }
        def entities = []
        for (functionReadable in functionReadableList) {
            if (functionReadable != null) {
                entities.add(functionReadable.readFunctionForRequest())
            }
        }
        entities = entities.findAll() { it != null && it != '' }
        if (entities == []) {
            return null
        }
        return """ "functions" : [${entities.join(', ')}]"""
    }

    static FunctionsRequestBuilder builder() {
        return new FunctionsRequestBuilder()
    }

    static class FunctionsRequestBuilder {
        List<FunctionReadable> functionReadableList

        FunctionsRequestBuilder withFunctionReadableList(List<FunctionReadable> functionReadableList) {
            this.functionReadableList = functionReadableList
            return this
        }

        FunctionsRequest build() {
            FunctionsRequest request = new FunctionsRequest()
            request.functionReadableList = functionReadableList
            return request
        }
    }
}
