package util

import java.time.Duration

class ApiClient {
    String baseUrl
    String apiKey
    Duration connectTimeout
    Duration readTimeout

    Integer responseCode
    String responseData

    ApiClient(String baseUrl, String apiKey, Duration connectTimeout , Duration readTimeout) {
        this.baseUrl = baseUrl
        this.apiKey = apiKey
        this.connectTimeout = connectTimeout
        this.readTimeout = readTimeout
    }

    def makeGetRequest(String path) {
        makeRequest(path, "GET")
    }

    def makePostRequest(String path, String requestBody) {
        makeRequest(path, "POST", requestBody)
    }

    def makePutRequest(String path, String requestBody) {
        makeRequest(path, "PUT", requestBody)
    }

    def makeDeleteRequest(String path) {
        makeRequest(path, "DELETE")
    }

    private def makeRequest(String path, String method, String requestBody = null) {
        try {
            URL url = new URL("$baseUrl$path")
            HttpURLConnection connection = url.openConnection() as HttpURLConnection
            connection.setRequestMethod(method)
            connection.setConnectTimeout(connectTimeout.toMillis().intValue())
            connection.setReadTimeout(readTimeout.toMillis().intValue())

            if (apiKey) {
                connection.setRequestProperty("Authorization", "Bearer $apiKey")
            }

            if (requestBody) {
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json")
                OutputStream os = connection.outputStream
                os.write(requestBody.bytes)
                os.close()
            }

            this.responseCode = connection.getResponseCode()
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))
                String inputLine
                StringBuilder response = new StringBuilder()

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine)
                }
                reader.close()
                responseData = response.toString()
                return responseData
            } else {
                throw new RuntimeException("Failed to retrieve data. Status code: $responseCode")
            }
        } catch (Exception e) {
            throw new RuntimeException("Error making API request: ${e.message}", e)
        }
    }
}

