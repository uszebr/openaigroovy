package com.github.uszebr.openaigroovy.chat

import com.github.uszebr.openaigroovy.openai.chat.FunctionCall
import org.junit.Test

class FunctionCallTest {

    @Test
    void testSmokeFunctionCall(){
        FunctionCall functionCall =  FunctionCall.builder().withMainParameter("auto").build()
        assert functionCall.requestPrepare() ==' "function_call": "auto" '
    }
    @Test
    void testParticularFunctionFunctionCall(){
        FunctionCall functionCall =  FunctionCall.builder().withName("myFunction").build()
        assert functionCall.requestPrepare() == '"function_call": {"name": "myFunction"}'
    }
}
