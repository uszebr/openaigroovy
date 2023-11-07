package com.github.uszebr.openaigroovy.openai.chat

import com.github.uszebr.openaigroovy.util.RequestPart

/**
 * Request function_call json parameter
 * Can be one string "auto"
 * or object with name of the mandatory function
 */
class FunctionCall implements RequestPart {
    //example "auto"
    String mainParameter
    //Name of the function that AI has to call
    String name

    @Override
    String requestPrepare() {
        if (name) {
            return """"function_call": {"name": "$name"}"""
        }
        if (mainParameter) {
            return """ "function_call": "$mainParameter" """
        }
        return null
    }

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private String mainParameter
        private String name

        Builder withMainParameter(String mainParameter) {
            this.mainParameter = mainParameter
            return this
        }

        Builder withName(String name) {
            this.name = name
            return this
        }

        FunctionCall build() {
            FunctionCall functionCall = new FunctionCall()
            functionCall.mainParameter = this.mainParameter
            functionCall.name = this.name
            return functionCall
        }
    }
}
