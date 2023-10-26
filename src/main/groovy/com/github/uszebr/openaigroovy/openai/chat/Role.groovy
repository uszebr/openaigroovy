package com.github.uszebr.openaigroovy.openai.chat

enum Role {
    SYSTEM('system'),
    USER('user'),
    ASSISTANT('assistant'),
    FUNCTION('function')

    String name

    Role(String name) {
        this.name = name
    }

    static Role readRoleFromName(String roleName) {
        for (Role role : values()) {
            if (role.name == roleName) {
                return role
            }
        }
        throw new IllegalArgumentException("Invalid role name: $roleName")
    }

}