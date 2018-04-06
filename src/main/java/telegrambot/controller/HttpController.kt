package telegrambot.controller

import org.apache.http.HttpResponse
import org.apache.http.client.fluent.Request
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
open class HttpController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun sendGet(url : String) : HttpResponse {
        try {
            val request = Request.Get(url)
            return request.execute().returnResponse()
        }
        catch (ex : Exception) {
            logger.error("Unsuccessfull GET request with url {}", url, ex)
            throw ex
        }
    }
}