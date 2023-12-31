package com.github.uszebr.openaigroovy.openai.chat


import com.github.uszebr.openaigroovy.openai.chat.response.FunctionCall

/**
 * Handling message in request and response
 * example in response "message": {"role": "assistant", "content": null, "function_call": {"name": "FindLocation", "arguments": "{\n  \"country\": \"US\",\n  \"state\": \"Fl\"\n}"}},
 */
class Message {
    Role role
    String content
    FunctionCall functionCall

    Message(Role role, String content, FunctionCall functionCall = null) {
        this.role = role
        this.content = content
        this.functionCall = functionCall
    }


    String prepareForRequest() {
        return """{"role" : "$role.name", "content" : "$content"}"""
    }

    @Override
    String toString() {
        return "Message{" +
                "role=" + role +
                ", content='" + content + '\'' +
                ", functionCall=" + functionCall +
                '}'
    }
}
