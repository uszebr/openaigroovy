package com.github.uszebr.openaigroovy.openai.chat.function

import com.github.uszebr.openaigroovy.util.RequestPart
import com.github.uszebr.openaigroovy.util.RequestUtil

class FunctionRequestParameter implements RequestPart {
    // example: "object"
    String type

    // converting to Map in request body
    // example in request body: "properties": {"locations": {"type":"array","items": {"type": "string" },"description": "List of Location Names, City, Countries or Regions"}}
    List<FunctionRequestProperty> properties

    // example: "required": ["locations"]
    List<String> required

    FunctionRequestParameter(String type, List<FunctionRequestProperty> properties, List<String> required) {
        this.type = type
        this.properties = properties
        this.required = required
    }

    @Override
    String requestPrepare() {
        def entities = []
        entities.add(RequestUtil.createOneParamPartRequest("type", this.type))
        entities.add(FunctionRequestProperty.requestPrepareForList(properties))
        if (required != null) {
            entities.add(""" "required": [${required.collect { "\"${it}\"" }.join(', ')}] """)
        }
        entities = entities.findAll() { it != null }
        if (entities == []) {
            return null
        }

        return """ "parameters": {${entities.join(', ')}}"""
    }

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private String type = 'object'
        private List<FunctionRequestProperty> properties
        private List<String> required

        Builder withType(String type) {
            this.type = type
            return this
        }

        Builder withProperties(List<FunctionRequestProperty> properties) {
            this.properties = properties
            return this
        }

        Builder withRequired(List<String> required) {
            this.required = required
            return this
        }

        FunctionRequestParameter build() {
            return new FunctionRequestParameter(this.type, this.properties, this.required)
        }
    }
}
