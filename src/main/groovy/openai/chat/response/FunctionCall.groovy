package openai.chat.response

/**
 * Handling "function_call" for chat response message
 */
class FunctionCall {

    String name
    Map<String,Object> arguments

    FunctionCall(String name, Map<String, Object> arguments) {
        this.name = name
        this.arguments = arguments
    }
}
