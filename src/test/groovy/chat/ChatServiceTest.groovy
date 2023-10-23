package chat

import openai.OpenAIService
import openai.chat.ChatService
import openai.chat.Message
import openai.chat.Role
import openai.model.AiModel
import org.junit.Test
import util.ConfigReader

class ChatServiceTest {

    @Test
    void testInitialize() {
        ConfigReader reader = new ConfigReader('/config.property')
        def apiKey = reader.readConfig()?.apiKey?.toString()


        OpenAIService openAIService = OpenAIService.builder().withApiKey(apiKey).build()

        def  messages = [
                new Message(Role.SYSTEM,"Your name is Val and you are helpfully assistant in the vehicle repair shop. When answering try to be funny and relaxed"),
                new Message(Role.USER,"Who are you?")
        ]
        ChatService chatService = ChatService.builder()
                .withService(openAIService)
                .withModel(AiModel.GPT_3_5_TURBO)
                .withMessages(messages)
                .build()


    }
}
