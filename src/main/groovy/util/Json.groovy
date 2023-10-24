package util

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/**
 * Utility for converting Json to text and back
 */
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
            try {
                return JsonOutput.toJson(data)
            } catch (Exception ignored) {
                throw new RuntimeException("Error converting data to json: $data")
            }
    }
}
