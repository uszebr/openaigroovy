package com.github.uszebr.openaigroovy.response

import com.github.uszebr.openaigroovy.chat.Message

class Choice {
    Integer index
    Message message

    // ex. values "function_call"  "stop"
    String finishReason

    Choice(Integer index, Message message, String finishReason) {
        this.index = index
        this.message = message
        this.finishReason = finishReason
    }

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private Integer index
        private Message message
        private String finishReason

        Builder withIndex(Integer index) {
            this.index = index
            return this
        }

        Builder withMessage(Message message) {
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
