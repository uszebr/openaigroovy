package openai.chat.response

import openai.OpenAIUsage
import openai.chat.Message
import openai.chat.Role

class ChatResponse {
    def data

    ChatResponse(data) {
        this.data = data
    }

    boolean isResponse() {
        return data != null
    }

    /**
     * Getting all Choice instances
     */
    List<Choice> getChoices() {
        if (!isResponse()) {
            return null
        }
        def choicesData = data['choices']
        def result = []
        for (oneChoiceData in choicesData) {
            def messageData = oneChoiceData?.getAt('message')
            def functionData = messageData?.getAt('function_call')
            def message
            if (functionData) {
                def functionCall = new FunctionCall(functionData['name'] as String, functionData['arguments'] as Map)
                message = new Message(Role.readRoleFromName(messageData?.getAt('role') as String), messageData?.getAt('content') as String, functionCall)
            } else {
                message = new Message(Role.readRoleFromName(messageData?.getAt('role') as String), messageData?.getAt('content') as String)
            }
            Integer index = oneChoiceData?.getAt('index') as Integer
            String finishReason = oneChoiceData?.getAt('finish_reason')

            def choice = Choice.builder().withIndex(index).withFinishReason(finishReason).withMessage(message).build()
            result.add(choice)
        }
        return result
    }

    /**
     * Getting first Choice instance
     * @return null if not found
     */
    Choice getFirstChoice() {
        return getChoices()?.getAt(0)
    }

    /**
     * @return example: "chatcmpl-8Bn8GQVJnYK5Qxim21lX3jMhtRFqs"
     */
    String getId() {
        if (!isResponse()) {
            return null
        }
        return data.getAt('id')
    }

    /**
     * @return example: "model": "gpt-3.5-turbo-0613"
     */
    String getModelString() {
        if (!isResponse()) {
            return null
        }
        return data.getAt('model')
    }

    /**
     * @return example: "chat.completion"
     */
    String getObject() {
        if (!isResponse()) {
            return null
        }
        return data.getAt('object')
    }

    /**
     * @return example: 1699039492
     */
    Integer getCreated() {
        if (!isResponse()) {
            return null
        }
        return data.getAt('created')
    }

    OpenAIUsage getUsage() {
        if (!isResponse()) {
            return null
        }
        def usageData = data.getAt('usage')
        return new OpenAIUsage(usageData?.getAt('prompt_tokens') as Integer, usageData?.getAt('completion_tokens') as Integer, usageData?.getAt('total_tokens') as Integer)
    }


}
