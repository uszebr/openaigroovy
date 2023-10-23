package util

import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertEquals
import static org.junit.Assert.fail
import java.net.HttpURLConnection

class ApiClientTest {
    private static final String BASE_URL = "https://catfact.ninja" // Replace with your base URL
    private ApiClient apiClient

    @Before
    void setUp() {
        apiClient = new ApiClient(BASE_URL,null)
    }

    @Test
    void testMakeGetRequest() {
        // You can write a test for the GET request here.
        // Ensure you have a valid URL to test against.
        // For example:
        String path = "/fact"
         apiClient.makeGetRequest(path)
        // Assert the response content or status code as needed.
        assertEquals(apiClient.getResponseCode(),200)
    }

    @Test
    void testMakePostRequest() {
        // You can write a test for the POST request here.
        // Ensure you have a valid URL and request body to test against.
        // For example:
        String path = "/api/resource"
        String requestBody = "{\"key\": \"value\"}"
        String response = apiClient.makePostRequest(path, requestBody)
        // Assert the response content or status code as needed.
        assertEquals(HttpURLConnection.HTTP_CREATED, responseCode)
    }

    @Test
    void testMakePutRequest() {
        // You can write a test for the PUT request here.
        // Ensure you have a valid URL and request body to test against.
        // For example:
        String path = "/api/resource/123"
        String requestBody = "{\"key\": \"updatedValue\"}"
        String response = apiClient.makePutRequest(path, requestBody)
        // Assert the response content or status code as needed.
        assertEquals(HttpURLConnection.HTTP_OK, responseCode)
    }

    @Test
    void testMakeDeleteRequest() {
        // You can write a test for the DELETE request here.
        // Ensure you have a valid URL to test against.
        // For example:
        String path = "/api/resource/123"
        apiClient.makeDeleteRequest(path)
        // Assert the response status code or any expected side-effects.
        // In a real scenario, you might check that the resource is deleted.
    }
}
