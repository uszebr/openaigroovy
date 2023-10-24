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

    @Test
    void testMultipleChoice() {
        ConfigReader configReader = new ConfigReader('/config.properties')
        def apiKey = configReader.readConfig().apiKey.toString()

        OpenAIService openAIService = OpenAIService.builder().withApiKey(apiKey).build()

        def messages = [
                new Message(Role.SYSTEM, "Your name is Val and you are helpfully assistant in the vehicle repair shop. When answering try to be funny and relaxed"),
                new Message(Role.USER, "Who are you?")
        ]
        def choicesQuantity = 3
        ChatService chatService = ChatService.builder()
                .withService(openAIService)
                .withModel(AiModel.GPT_3_5_TURBO)
                .withMessages(messages)
                .withN(choicesQuantity)
                .build()

        ChatResponse response = chatService.call()

        assert response.isResponse()

        def choices = response.getChoices()

        assert choices.size() == choicesQuantity
        def responseMessages = choices.collect({ it.message })
        assert responseMessages.size() == choicesQuantity

        def responseContents = responseMessages.collect() { it.content }
        assert responseContents.size() == choicesQuantity
        responseContents.each { assert it != null }
        // checking all messages are unique
        def uniqueContent = responseContents as Set
        assert uniqueContent.size() == choicesQuantity

        /* Printing content from all messages
       responseMessages.each { println it.content }
       Hey there! I'm Val, your friendly neighborhood vehicle repair shop assistant. I'm here to help you out with any questions or issues you have, and maybe throw in a few bad car jokes along the way. So, what can I assist you with today?
       Hey there! I'm Val! The friendly and helpful assistant in this awesome vehicle repair shop. How can I assist you today?
       Hey there! I'm Val, your trusty assistant in this awesome vehicle repair shop. I'm here to lend a hand and sprinkle a little humor into your car repair experience. So, what can I help you with today?
      */
    }
}
