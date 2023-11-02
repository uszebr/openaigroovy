package com.github.uszebr.openaigroovy.openai.chat.function

import com.github.uszebr.openaigroovy.util.RequestPart
import com.github.uszebr.openaigroovy.util.RequestUtil

class FunctionRequestProperty implements RequestPart {
    String name
    String type
    String description
    Items items

    FunctionRequestProperty(String name, String type, String description, Items items) {
        this.name = name
        this.type = type
        this.description = description
        this.items = items
    }

    static String requestPrepareForList(List<FunctionRequestProperty> properties) {
        if (properties == null) {
            return null
        }
        def propertyRequestEntities = []
        for (property in properties) {
            propertyRequestEntities.add(RequestUtil.readRequest(property))
        }
        propertyRequestEntities = propertyRequestEntities.findAll() { it != null }
        if (propertyRequestEntities == []) {
            return null
        }
        return """ "properties": { ${propertyRequestEntities.join(',')} }"""
    }

    @Override
    String requestPrepare() {
        if (this.name == null) {
            return null
        }
        def entities = []
        entities.add(RequestUtil.createOneParamPartRequest("type", this.type))
        entities.add(RequestUtil.createOneParamPartRequest("description", this.description))
        entities.add(RequestUtil.readRequest(this.items))
        entities = entities.grep()
        if (entities == [])
            return null
        return """ "${this.name}": {${entities.join(',')}}"""
    }

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private String name
        private String type
        private String description
        private Items items

        Builder withName(String name) {
            this.name = name
            return this
        }

        Builder withType(String type) {
            this.type = type
            return this
        }

        Builder withDescription(String description) {
            this.description = description
            return this
        }

        Builder withItems(Items items) {
            this.items = items
            return this
        }

        FunctionRequestProperty build() {
            return new FunctionRequestProperty(this.name, this.type, this.description, this.items)
        }
    }
}
