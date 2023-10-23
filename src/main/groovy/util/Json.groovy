package util

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class Json {
    static def textToData(String text) {
        def slurper = new JsonSlurper()
        try {
            return slurper.parseText(text)
        } catch (Exception e) {
            throw new RuntimeException("Error parsing json from text: $text")
        }

    }

    static String dataToText(def data) {
        if (data) {
            try {
                return JsonOutput.toJson(data)
            } catch (Exception ignored) {
                return null
            }
        } else {
            return null
        }
    }
}
