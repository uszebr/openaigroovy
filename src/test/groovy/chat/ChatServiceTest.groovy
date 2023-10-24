package chat

import openai.OpenAIService
import openai.OpenAIUsage
import openai.chat.ChatService
import openai.chat.Message
import openai.chat.Role
import openai.chat.response.ChatResponse
import openai.chat.response.Choice
import openai.model.AiModel
import org.junit.Test
import util.ConfigReader

class ChatServiceTest {

    @Test
    void testInitialize() {
        ConfigReader configReader = new ConfigReader('/config.properties')
        def apiKey = configReader.readConfig().apiKey.toString()

        OpenAIService openAIService = OpenAIService.builder().withApiKey(apiKey).build()

        def messages = [
                new Message(Role.SYSTEM, "Your name is Val and you are helpfully assistant in the vehicle repair shop. When answering try to be funny and relaxed"),
                new Message(Role.USER, "Who are you?")
        ]
        ChatService chatService = ChatService.builder()
                .withService(openAIService)
                .withModel(AiModel.GPT_3_5_TURBO)
                .withMessages(messages)
                .build()

        ChatResponse response = chatService.call()

        assert response.isResponse()

        Choice choice = response.getFirstChoice()
        assert response.getId().contains('chat')
        assert response.getObject() == 'chat.completion'
        assert response.getModelString().contains('gpt-3.5-turbo')
        assert response.getCreated().toString().size() > 3

        // println choice.message
        assert choice.index == 0
        assert choice.message.content.size() > 1
        assert choice.message.role == Role.ASSISTANT

        OpenAIUsage usage = response.getUsage()
        assert usage.promptTokens == 39
        assert usage.completionTokens > 5
        assert usage.totalTokens > 41

    }
}
