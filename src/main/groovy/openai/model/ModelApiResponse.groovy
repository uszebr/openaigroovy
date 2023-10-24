package openai.model


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
                        .withId(permissionData.getAt('id'))
                        .withObject(permissionData.getAt('object'))
                        .withCreated(permissionData.getAt('created'))
                        .withAllowCreateEngine(permissionData.getAt('allow_create_engine'))
                        .withAllowSampling(permissionData.getAt('allow_sampling'))
                        .withAllowLogprobs(permissionData.getAt('allow_logprobs'))
                        .withAllowSearchIndices(permissionData.getAt('allow_search_indices'))
                        .withAllowView(permissionData.getAt('allow_view'))
                        .withAllowFineTuning(permissionData.getAt('allow_fine_tuning'))
                        .withOrganization(permissionData.getAt('organization'))
                        .withGroup(permissionData.getAt('group'))
                        .withIsBlocking(permissionData.getAt('is_blocking'))

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
