package com.github.uszebr.openaigroovy.openai.chat.function

import com.github.uszebr.openaigroovy.util.RequestPart
import com.github.uszebr.openaigroovy.util.RequestUtil

class FunctionRequestProperty implements RequestPart {
    String name
    String type
    String description
    Items items

    //example:  "enum": ["ASIA","AMERICA","EUROPE","AUSTRALIA","OTHER"]
    List<String> enumProp

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
        if(enumProp){
            String enumEntitiesString =
            entities.add(""" "enum": [${enumProp.collect(){"\"$it\""}.join(', ')}]""")
        }
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
        private List<String> enumProp

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
        Builder withEnum(List<String>enumProp) {
            this.enumProp = enumProp
            return this
        }

        FunctionRequestProperty build() {
           def functionRequestProperty =  new FunctionRequestProperty()
            functionRequestProperty.name = this.name
            functionRequestProperty.type = this.type
            functionRequestProperty.description = this.description
            functionRequestProperty.items = this.items
            functionRequestProperty.enumProp = this.enumProp
            return functionRequestProperty
        }
    }
}
