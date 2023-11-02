package com.github.uszebr.openaigroovy.util

class RequestUtil {

    static String readRequest(RequestPart requestPart){
        if(requestPart==null){
            return null
        }
        return requestPart.requestPrepare()
    }

    static String createOneParamPartRequest(def parameterName, def parameterValue) {
        if (parameterName == null || parameterValue == null) {
            return null
        }
        return """ "$parameterName": "$parameterValue" """
    }

    static List<String> createListParamPartRequest(List<Tuple2> parameterEntity) {
        if (parameterEntity == null) {
            return null
        }
        return parameterEntity.collect(){ createOneParamPartRequest(it?.getV1(), it.getV2()) }
    }
}
