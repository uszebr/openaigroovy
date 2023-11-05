package com.github.uszebr.openaigroovy.openai.chat.function

import com.github.uszebr.openaigroovy.util.FunctionReadable
import com.github.uszebr.openaigroovy.util.RequestPart
import com.github.uszebr.openaigroovy.util.RequestUtil

/**
 * Handling Functions in Chat Request
 */
class FunctionStep implements FunctionReadable, RequestPart {

    String name
    String description
    FunctionRequestParameter parameter

    @Override
    String requestPrepare() {
        def entities = []
        entities.add(RequestUtil.createOneParamPartRequest('name', this.name))
        entities.add(RequestUtil.createOneParamPartRequest('description', this.description))
        entities.add(RequestUtil.readRequest(parameter))
        entities = entities.findAll() { it != null }
        if (entities == []) {
            return null
        }
        return """ {${entities.join(", ")}}"""
    }

    @Override
    String readFunctionForRequest() {
        return requestPrepare()
    }

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private String name
        private String description
        private FunctionRequestParameter parameter

        Builder withName(String name) {
            this.name = name
            return this
        }

        Builder withDescription(String description) {
            this.description = description
            return this
        }

        Builder withParameter(FunctionRequestParameter parameter) {
            this.parameter = parameter
            return this
        }

        FunctionStep build() {
            def functionStep = new FunctionStep()
            functionStep.name = this.name
            functionStep.description =  this.description
            functionStep.parameter = this.parameter
            return functionStep
        }
    }
}
