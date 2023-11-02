package com.github.uszebr.openaigroovy.openai.chat.function

import com.github.uszebr.openaigroovy.util.RequestPart
import com.github.uszebr.openaigroovy.util.RequestUtil
import groovy.transform.builder.Builder

@Builder
class FunctionRequestProperty implements RequestPart {
    String name
    String type
    String description

    Items items



    @Override
    String requestPrepare() {
        def entities = []
        entities.add(RequestUtil.createOneParamPartRequest("type", this.type))
        entities.add(RequestUtil.createOneParamPartRequest("description", this.description))
        entities.add(RequestUtil.readRequest(this.items))
        entities = entities.grep()
        if (entities == [])
            return null
        return """ "${this.name}": {${entities.join(',')}}"""
    }


}
