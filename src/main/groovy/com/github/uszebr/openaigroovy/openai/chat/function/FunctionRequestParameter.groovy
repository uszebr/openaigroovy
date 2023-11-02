package com.github.uszebr.openaigroovy.openai.chat.function

import groovy.transform.builder.Builder

@Builder
class FunctionRequestParameter {
    // example: "object"
    String type = 'object'

    // converting to Map in request body
    // example in request body: "properties": {"locations": {"type":"array","items": {"type": "string" },"description": "List of Location Names, City, Countries or Regions"}}
    List<FunctionRequestProperty> properties

    //todo
}
