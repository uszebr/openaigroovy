package com.github.uszebr.openaigroovy.openai.chat.function

import com.github.uszebr.openaigroovy.util.RequestPart
import com.github.uszebr.openaigroovy.util.RequestUtil

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

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private String type

        Builder withType(String type) {
            this.type = type
            return this
        }

        Items build() {
            def items = new Items()
            items.type = this.type
            return items
        }
    }
}
