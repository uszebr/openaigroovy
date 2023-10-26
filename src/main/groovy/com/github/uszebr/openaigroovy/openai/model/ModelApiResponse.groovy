package com.github.uszebr.openaigroovy.openai.model


class ModelApiResponse {
    def rawData

    ModelApiResponse(def rawData) {
        this.rawData = rawData
    }

    boolean isResponse() {
        return rawData != null
    }

    /**
     *
     * @return example "list"
     */
    String getObject() {
        if (!isResponse()) {
            return null
        }
        return rawData.getAt('object') as String
    }

    List<ModelResponse> getAllModels() {
        if (!isResponse()) {
            return null
        }
        def allModelData = rawData.getAt('data')
        def result = []
        for (modelData in allModelData) {

            def permissionAllData = modelData.getAt('permission')
            def permissionsForModel = []
            for (permissionData in permissionAllData) {
                ModelPermission permission = new ModelPermission()
                        .withId(permissionData['id'] as String)
                        .withObject(permissionData['object'] as String)
                        .withCreated(permissionData['created'] as Integer)
                        .withAllowCreateEngine(permissionData['allow_create_engine'] as Boolean)
                        .withAllowSampling(permissionData['allow_sampling'] as Boolean)
                        .withAllowLogprobs(permissionData['allow_logprobs'] as Boolean)
                        .withAllowSearchIndices(permissionData['allow_search_indices'] as Boolean)
                        .withAllowView(permissionData['allow_view'] as Boolean)
                        .withAllowFineTuning(permissionData['allow_fine_tuning'] as Boolean)
                        .withOrganization(permissionData['organization'] as String)
                        .withGroup(permissionData['group'])
                        .withIsBlocking(permissionData['is_blocking'] as Boolean)

                permissionsForModel.add(permission)
            }
            def modelEntity = new ModelResponse()
                    .withId(modelData['id'] as String)
                    .withObject(modelData['object'] as String)
                    .withCreated(modelData['created'] as Integer)
                    .withOwnedBy(modelData['owned_by'] as String)
                    .withPermissions(permissionsForModel)
                    .withRoot(modelData['root'] as String)
                    .withParent(modelData['parent'] as String)

            result.add(modelEntity)
        }
        return result
    }


}
