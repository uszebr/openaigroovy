package openai.chat

enum Role {
        SYSTEM('system'),
        USER('user'),
        ASSISTANT('assistant'),
        FUNCTION('function')

        String name

        Role(String name) {
            this.name = name
        }

}