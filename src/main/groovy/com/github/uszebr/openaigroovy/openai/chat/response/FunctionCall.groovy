package com.github.uszebr.openaigroovy.openai.chat.response

import com.github.uszebr.openaigroovy.util.Json

/**
 * Handling "function_call" for chat response message
 */
class FunctionCall {

    String name
    String argumentsInString
    Map arguments


    FunctionCall(String name, String argumentsInString) {
        this.name = name
        this.argumentsInString = argumentsInString
        if(argumentsInString){
            try {
                this.arguments = Json.textToData(argumentsInString) as Map
            }catch(ignored){

            }
        }
    }
}
