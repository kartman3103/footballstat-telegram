package telegrambot.controller.request

import org.apache.http.client.fluent.Request
import org.apache.http.client.fluent.Response
import org.slf4j.LoggerFactory

open class DirectRequestSender : RequestSender {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun sendRequest(request : Request) : Response {
        try {
            return request.execute()
        }
        catch (ex : Exception) {
            logger.error("Unsuccessfull request: ", ex)
            throw ex
        }
    }
}