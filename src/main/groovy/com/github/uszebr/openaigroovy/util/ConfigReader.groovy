package com.github.uszebr.openaigroovy.util

class ConfigReader {
    def configFile

    ConfigReader(String resourcePath) {
        def resourceUrl = getClass().getResource(resourcePath)
        if (resourceUrl) {
            configFile = new File(resourceUrl.toURI())
        } else {
            throw new FileNotFoundException("Resource not found: $resourcePath")
        }
    }

    Map<String, Object> readConfig() {
        if (!configFile.exists()) {
            throw new FileNotFoundException("Config file not found: ${configFile.absolutePath}")
        }

        def configSlurper = new ConfigSlurper()
        def config = configSlurper.parse(configFile.toURI().toURL())

        return config
    }
}
