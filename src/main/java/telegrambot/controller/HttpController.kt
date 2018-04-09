package telegrambot.controller

import org.apache.http.HttpResponse
import org.apache.http.client.fluent.Content
import org.apache.http.client.fluent.Request
import org.apache.http.client.fluent.Response
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.nio.charset.Charset

@Component
open class HttpController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun makeResponseGET(url : String) : HttpResponse {
        return sendGET(url).returnResponse()
    }

    fun makeContentGET(url : String) : Content {
        return sendGET(url).returnContent()
    }

    fun makeContentGET(url : String, charset: Charset) : String {
        return sendGET(url).returnContent().asString(charset)
    }

    private fun sendGET(url : String) : Response {
        try {
            val request = Request.Get(url)
            return request.execute()
        }
        catch (ex : Exception) {
            logger.error("Unsuccessfull GET request with url {}", url, ex)
            throw ex
        }
    }
}