package com.github.uszebr.openaigroovy.openai.chat.response


import com.github.uszebr.openaigroovy.openai.chat.Message

class Choice {
    Integer index
    Message message

    // ex. values "function_call"  "stop"
    String finishReason

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
            def choice = new Choice()
            choice.index = this.index
            choice.message = this.message
            choice.finishReason = this.finishReason
            return choice
        }
    }

}
