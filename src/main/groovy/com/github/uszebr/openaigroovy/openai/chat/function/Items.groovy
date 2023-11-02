package com.github.uszebr.openaigroovy.openai.chat.function

import com.github.uszebr.openaigroovy.util.RequestPart
import com.github.uszebr.openaigroovy.util.RequestUtil
import groovy.transform.builder.Builder

@Builder
class Items implements RequestPart {
    String type

    String requestPrepare() {
        def entities = [
                RequestUtil.createOneParamPartRequest("type", this.type)
        ].grep()

        if (entities == []) {
            return null
        }
        return """ "items": {${entities.join(',')}}"""
    }
}
