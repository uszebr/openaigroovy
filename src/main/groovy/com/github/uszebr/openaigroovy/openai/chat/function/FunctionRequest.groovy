package com.github.uszebr.openaigroovy.openai.chat.function

import com.github.uszebr.openaigroovy.util.RequestUtil
import groovy.transform.builder.Builder

/**
 * Handling Function in Chat Request
 *
 */
@Builder
class FunctionRequest {

    String name
    String description




    // example: "required": ["locations"]
    List<String> required

    static String createStringForOne(FunctionRequest functionRequest){
        //todo
//        {
//            "name": "GetCurrentWeather",
//            "description": "getting current weather on selected location",
//            "parameters": {
    //            "type": "object",
    //            "properties": {
    //                "locations": {
    //                    "type":"array",
    //                    "items": {
    //                        "type": "string"
    //                    },
    //                    "description": "List of Location Names, City, Countries or Regions"
    //                }
    //            },
//            "required": ["locations"]
//        }
//        }
        if(!functionRequest){
            return ''
        }
        def parameterEntities = [
                //todo
        ]
        def parameterBody//todo

        def functionEntities =[
                RequestUtil.createOneParamPartRequest('name',functionRequest.name) ,
                RequestUtil.createOneParamPartRequest('description',functionRequest.description) ,


        ]
        //todo
        return """  """
    }

    static String createStringForRequest(List<FunctionRequest> functionRequestList){
        //todo
        return []
    }



}
