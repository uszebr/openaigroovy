package com.github.uszebr.openaigroovy.util


import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

class ConfigReaderTest {
    private ConfigReader configReader

    @Before
    void setUp() {
        // Assuming that the config file is in the 'resources' package
        def resourcePath = "/config.properties"

        // Initialize the ConfigReader with the resource path
        configReader = new ConfigReader(resourcePath)
    }

    @Test
    void testReadConfig() {
        Map<String, Object> config = configReader.readConfig()
        assertNotNull(config)
        assertNotNull( config.apiKey)

    }

    @Test
    void testFileNotFoundException() {
        // Create a ConfigReader with a non-existent resource path
        boolean isException = false
        try {
            new ConfigReader("/nonexistent.config")
        } catch (FileNotFoundException ignored) {
            isException = true
        }
        assertTrue(isException)
    }
}
