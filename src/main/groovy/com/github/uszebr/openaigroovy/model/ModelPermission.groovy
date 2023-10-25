package com.github.uszebr.openaigroovy.model

class ModelPermission {

    // ex. "modelperm-s9n5HnzbtVn7kNc5TIZNiCFC"
    String id
    // ex. "model_permission"
    String object
    // ex. 1695933794
    Integer created
    Boolean allowCreateEngine
    Boolean allowSampling
    Boolean allowLogprobs
    Boolean allowSearchIndices
    Boolean allowView
    Boolean allowFineTuning
    String organization
    Object group
    Boolean isBlocking

    ModelPermission withId(String id) {
        this.id = id
        return this
    }

    ModelPermission withObject(String object) {
        this.object = object
        return this
    }

    ModelPermission withCreated(Integer created) {
        this.created = created
        return this
    }

    ModelPermission withAllowCreateEngine(Boolean allowCreateEngine) {
        this.allowCreateEngine = allowCreateEngine
        return this
    }

    ModelPermission withAllowSampling(Boolean allowSampling) {
        this.allowSampling = allowSampling
        return this
    }

    ModelPermission withAllowLogprobs(Boolean allowLogprobs) {
        this.allowLogprobs = allowLogprobs
        return this
    }

    ModelPermission withAllowSearchIndices(Boolean allowSearchIndices) {
        this.allowSearchIndices = allowSearchIndices
        return this
    }

    ModelPermission withAllowView(Boolean allowView) {
        this.allowView = allowView
        return this
    }

    ModelPermission withAllowFineTuning(Boolean allowFineTuning) {
        this.allowFineTuning = allowFineTuning
        return this
    }

    ModelPermission withOrganization(String organization) {
        this.organization = organization
        return this
    }

    ModelPermission withGroup(Object group) {
        this.group = group
        return this
    }

    ModelPermission withIsBlocking(Boolean isBlocking) {
        this.isBlocking = isBlocking
        return this
    }

}
