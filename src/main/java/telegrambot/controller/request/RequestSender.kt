package telegrambot.controller.request

import org.apache.http.client.fluent.Request
import org.apache.http.client.fluent.Response

interface RequestSender {
    fun sendRequest(request : Request) : Response
}