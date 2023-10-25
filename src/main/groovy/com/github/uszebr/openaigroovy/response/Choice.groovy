package com.github.uszebr.openaigroovy.response

class Choice {
    Integer index
    com.github.uszebr.openaigroovy.chat.Message message

    // ex. values "function_call"  "stop"
    String finishReason

    Choice(Integer index, com.github.uszebr.openaigroovy.chat.Message message, String finishReason) {
        this.index = index
        this.message = message
        this.finishReason = finishReason
    }

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private Integer index
        private com.github.uszebr.openaigroovy.chat.Message message
        private String finishReason

        Builder withIndex(Integer index) {
            this.index = index
            return this
        }

        Builder withMessage(com.github.uszebr.openaigroovy.chat.Message message) {
            this.message = message
            return this
        }

        Builder withFinishReason(String finishReason) {
            this.finishReason = finishReason
            return this
        }

        Choice build() {
            return new Choice(index, message, finishReason)
        }
    }

}
