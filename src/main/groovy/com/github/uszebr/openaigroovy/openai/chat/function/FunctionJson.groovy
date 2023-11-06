package com.github.uszebr.openaigroovy.openai.chat.function

import com.github.uszebr.openaigroovy.util.FunctionReadable

/**
 * This class to create OpenAI function based on json
 * for builder with each of parameters use FunctionStep class
 */
class FunctionJson implements FunctionReadable {
    //example:
   /* {
            "name": "GetCurrentWeather",
            "description": "getting current weather on selected location",
            "parameters": {
                "type": "object",
                "properties": {
                     "location": {
                        "type": "string",
                        "description": "Location Name, City, Country or Region"
                    }
                },
                "required": ["location"]
            }
        }*/
    String functionPartInJson


    @Override
    String readFunctionForRequest() {
        return this.functionPartInJson
    }

    static Builder builder() {
        return new Builder()
    }

    static class Builder {
        private String functionPartInJson

        Builder withFunctionInJson(String functionPartInJson) {
            this.functionPartInJson = functionPartInJson
            return this
        }

        FunctionJson build() {
            FunctionJson functionJson = new FunctionJson()
            functionJson.functionPartInJson = this.functionPartInJson
            return functionJson
        }
    }
}
