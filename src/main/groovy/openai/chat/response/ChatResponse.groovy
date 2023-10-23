package openai.chat.response

import openai.OpenAIUsage

class ChatResponse {
    def data

    ChatResponse(data) {
        this.data = data
    }

    boolean isResponse() {
        return data != null
    }

    List<Choice> getChoices(){
        //todo
    }

    Choice getFirstChoice(){
        //todo
    }

    String getId(){
        //todo
    }

    String getModelString(){
        //todo
    }

    String getObject(){
        //todo
    }

    Integer getCreated(){
        //todo
    }

    OpenAIUsage getUsage(){
        //todo
    }


}
