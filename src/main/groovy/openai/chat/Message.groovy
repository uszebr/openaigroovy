package openai.chat

class Message {
    Role role
    String text

    Message(Role role, String text) {
        this.role = role
        this.text = text
    }

    @Override
     String toString() {
        return """{"role" : "$role.name", "content" : "$text"}"""
    }
}
