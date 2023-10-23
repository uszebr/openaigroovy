package openai.model

enum AiModel {
    GPT_3_5_TURBO_16K("gpt-3.5-turbo-16k"),
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    ADA_02("text-embedding-ada-002"),
    DAVINCI("davinci")

    final String name

    AiModel(String name) {
        this.name = name
    }
}


