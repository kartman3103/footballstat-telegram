package telegrambot.controller

import org.apache.http.HttpResponse
import org.apache.http.client.fluent.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.controller.request.RequestSender
import java.nio.charset.Charset

@Component
open class HttpController {
    @Autowired
    private lateinit var requestSender : RequestSender

    fun makeResponseGET(url : String) : HttpResponse {
        return requestSender.sendRequest(Request.Get(url)).returnResponse()
    }

    fun makeContentGET(url : String, charset: Charset) : String {
        return requestSender.sendRequest(Request.Get(url)).returnContent().asString(charset)
    }

    fun makeResponsePOST(url : String) : HttpResponse {
        return requestSender.sendRequest(Request.Post(url)).returnResponse()
    }

    fun makeContentPOST(url : String, charset: Charset) : String {
        return requestSender.sendRequest(Request.Post(url)).returnContent().asString(charset)
    }
}