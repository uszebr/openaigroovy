package openai.chat

class ChatResponse {
    def data

    ChatResponse(data) {
        this.data = data
    }

    boolean isResponse() {
        return data != null
    }
}
