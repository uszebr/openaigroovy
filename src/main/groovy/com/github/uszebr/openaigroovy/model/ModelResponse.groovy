package com.github.uszebr.openaigroovy.model

class ModelResponse {
    // ex. "text-search-babbage-doc-001"
    String id
    // ex. "model"
    String object
    // ex. 1651172509
    Integer created
    // ex. "openai-dev"
    String ownedBy
    List<ModelPermission> permissions
    // ex. "davinci-similarity" "gpt-3.5-turbo-0613"
    String root
    String parent

    ModelResponse withId(String id) {
        this.id = id
        return this
    }

    ModelResponse withObject(String object) {
        this.object = object
        return this
    }

    ModelResponse withCreated(Integer created) {
        this.created = created
        return this
    }

    ModelResponse withOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy
        return this
    }

    ModelResponse withRoot(String root) {
        this.root = root
        return this
    }

    ModelResponse withParent(String parent) {
        this.parent = parent
        return this
    }

    ModelResponse withPermissions(List<ModelPermission> permissions) {
        this.permissions = permissions
        return this
    }
}
