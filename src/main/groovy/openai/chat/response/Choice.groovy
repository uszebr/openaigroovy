package openai.chat.response

import openai.chat.Message

class Choice {
    Integer index
    Message message

    // ex. values "function_call"  "stop"
    String finishReason

}
